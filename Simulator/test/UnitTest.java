package test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import interfaces.AbstractComponent;
import vgu.consumer.ConsumerFactory;
import vgu.control.Control;
import vgu.generator.GeneratorFactory;
//
class UnitTest {

	@BeforeAll
	static void initialize() {
		ConsumerFactory.setRunBehaviour(new double[] {1,1,1,1,1,1,1,1,1,1,1,1});		
	}
	
	@Test
	/**
	 *register a 100W Device
	 *register a 90W Generator (minimum and maximum supply)
	 *I0: Check Frequency equals 49Hz
	 */
	void testLowFrequency() {
		AbstractComponent c1 = ConsumerFactory.generate("c1", 100, 100, 100, 100);
		AbstractComponent g1 = GeneratorFactory.generate("g1", 90, 90, 90, 90);
		Control control = new Control();
		control.addConsumer(c1);
		control.addGenerator(g1);
		double frequency = control.getFrequency();
		System.out.println("T1: Frequency: " + frequency + "Hz");
		assertEquals(49, frequency);
	}
	
	@Test
	/**
	 *register a 90W Device
	 *register a 100W Generator (minimum and maximum supply)
	 *I0: Check Frequency equals 51Hz
	 */
	void testHighFrequency() {
		AbstractComponent g1 = GeneratorFactory.generate("g1", 100, 100, 100, 100);
		AbstractComponent c1 = ConsumerFactory.generate("c1", 90, 90, 90, 90);
		Control control = new Control();
		control.addConsumer(c1);
		control.addGenerator(g1);
		double frequency = control.getFrequency();
		System.out.println("T2: Frequency: " + frequency + "Hz");
		assertEquals(51, frequency);
	}
	
	@Test
	/**
	- register two 90W Device
	- register two 100W Generator (minimum and maximum supply)
	- I0: Check total demand equals 180W and total supply equal 200W
	*/
	void testDemandSupplyComputation() {
		AbstractComponent g1 = GeneratorFactory.generate("g1", 100, 100, 100, 100);		
		AbstractComponent c1 = ConsumerFactory.generate("c1", 90, 90, 90, 90);
		AbstractComponent g2 = GeneratorFactory.generate("g2", 100, 100, 100, 100);		
		AbstractComponent c2 = ConsumerFactory.generate("c2", 90, 90, 90, 90);
		Control control = new Control();
		control.addConsumer(c1);
		control.addGenerator(g1);
		control.addConsumer(c2);
		control.addGenerator(g2);
		double demand = control.getTotalDemand();
		double supply = control.getTotalSupply();
		System.out.println("T3: Demand: "+demand+"W Supply: "+supply+"W");
		assertEquals(180, demand);
		assertEquals(200, supply);
	}
	@Test
//	- register a 100W Device
//	- register a Generator with 90W, minimum 90W, maximum 100W, max-change 5W
//	- I0: check supply is 90W
//	- I1: check supply > previous supply
//	- I2: check supply > previous supply
	void testSupplyAdaption() {

		AbstractComponent c1 = ConsumerFactory.generate("c1", 100, 100, 100, 100);
		AbstractComponent g1 = GeneratorFactory.generate("g1", 100, 90, 5, 5);
		Control control = new Control();
		control.addConsumer(c1);
		control.addGenerator(g1);
		double supply1 = control.getTotalSupply();
		assertEquals(90, supply1);
		control.nextIteration();
		double supply2 = control.getTotalSupply();
		assertTrue(supply2>90);
		control.nextIteration();
		double supply3 = control.getTotalSupply();		
		assertTrue(supply3>supply2);		
		System.out.println("T4: Supply I0 "+supply1+"W Supply I1 "+supply2+"W Supply I2 "+supply3+"W");		
	}
	
	
	
	@Test
	void testControlledBlackout() {
//	- register 100 individual 1W Device
//	- register a 70W Generator (minimum and maximum supply)
//	- I0: Check Frequency is 47Hz, demand 100W, supply 70W -> 15 Devices will be unregistered
//	- I1: Check Frequency is ~48.7Hz, demand 85W, supply 70W -> 13 Devices will be unregistered
//	- I2: Check frequency is ~49.8Hz demand 72W, supply 70W
		Control control = new Control();
		
		ArrayList<AbstractComponent> consumer;
		
		consumer = ConsumerFactory.generate(100, 1, 0);
		for(AbstractComponent a : consumer) {
			control.addConsumer(a);
		}
		
		AbstractComponent g1 = GeneratorFactory.generate("g1", 70, 70, 70, 70);		
		control.addGenerator(g1);
		double f1 = control.getFrequency();
		int count1 = control.getConsumers().size();
		assertEquals(100, count1);
		
		control.nextIteration();
		double f2 = control.getFrequency();
		System.out.println(f2);
		int count2 = control.getConsumers().size();
		assertEquals(85, count2);
		
		control.nextIteration();		
		double f3 = control.getFrequency();	
		int count3 = control.getConsumers().size();
		assertEquals(72, count3);
		
		System.out.println("T5: I0 (Frequency: "+f1+"Hz Consumers:"+count1+") I1 (Frequency: "+f2+"Hz Consumers:"+count2+") I2 (Frequency: "+f3+"Hz Consumers:"+count3+")");
	}
	@Test
	void testGeneratorTurnOff() {
//	- register a 100W Device
//	- register a 1000W Generator (minimum and maximum supply)
//	- I1: Check Generator will unregister
		AbstractComponent c1 =  ConsumerFactory.generate("c1", 100, 100, 100, 100);		
		AbstractComponent g1 = GeneratorFactory.generate("g1", 1000, 1000, 1000, 1000);		
				
		Control control = new Control();
		control.addConsumer(c1);
		control.addGenerator(g1);
		int count1 = control.getGenerators().size();
		control.nextIteration();		
		int count2 = control.getGenerators().size();
				
		System.out.println("T7: I0 (Generators:"+count1+") I1 (Generators:"+count2+")");
		assertEquals(count2, 0);
	}
	@Test
	void testOverDemand() {
//	- register 100 individual 1W Device
//	- register a 10W Generator (minimum and maximum supply)
//	- I3: Check all Generator and devices will unregister
		Control control = new Control();
		ArrayList<AbstractComponent> consumer;
		consumer = ConsumerFactory.generate(100, 1, 0);
		for(AbstractComponent a : consumer) {
			control.addConsumer(a);
		}
		AbstractComponent g1 = GeneratorFactory.generate("g1", 10, 10, 10, 10);		
		control.addGenerator(g1);				
		//double f1 = control.getFrequency();	
		int count1 = control.getConsumers().size();
		assertEquals(100,count1);
		
		control.nextIteration();
		//double f2 = control.getFrequency();
		int count2 = control.getConsumers().size();
		assertEquals(85, count2);
		
		control.nextIteration();
		//double f3 = control.getFrequency();
		int count3 = control.getConsumers().size();
		assertEquals(72, count3);
		control.nextIteration();
		int count4 = control.getConsumers().size();
		assertEquals(count4, 0);
		System.out.println("T8: I0 (Consumers:"+count1+") I1 (Consumers:"+count2+") I2 (Consumers:"+count3+") I3 (Consumers:"+count4+")");
	}
	
	@Test
	void testOverSupply() {
//	- register a 10W Device
//	- register 100 individual 1W Generator (minimum and maximum supply)
//	- I3: Check all Generators and Devices will unregister
		Control control = new Control();
		AbstractComponent g1;
		for(int i = 0; i < 100; i++) {
			g1 = GeneratorFactory.generate("g"+i, 1, 1, 1, 1);
			control.addGenerator(g1);
		}
		AbstractComponent c1 = ConsumerFactory.generate("c1", 10, 10, 10, 10);
		control.addConsumer(c1);
		//double f1 = control.getFrequency();
		int count1 = control.getGenerators().size();
		assertEquals(100,count1);
		
		control.nextIteration();
		//double f2 = control.getFrequency();
		int count2 = control.getGenerators().size();
		assertEquals(90, count2);
		
		control.nextIteration();
		//double f3 = control.getFrequency();
		int count3 = control.getGenerators().size();
		assertEquals(81, count3);
		control.nextIteration();
		int count4 = control.getGenerators().size();
		assertEquals(count4, 0);
		System.out.println("T9: I0 (Generators:"+count1+") I1 (Generators:"+count2+") I2 (Generators:"+count3+") I3 (Generators:"+count4+")");
	}
}
