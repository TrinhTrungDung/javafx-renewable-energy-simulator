package test;

import java.util.ArrayList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import interfaces.AbstractComponent;
import vgu.consumer.ConsumerFactory;
import vgu.control.Control;
import vgu.generator.GeneratorFactory;

class TestScenarios {

	@BeforeAll
	static void initialize() {
		ConsumerFactory.setRunBehaviour(new double[]{.5,.2,.15,.45,.75,.60,.55,.40,.45,.65,.95,.75});
	}

	@Test
	void testScenario1() {

		Control control = new Control();

		ArrayList<AbstractComponent> consumer;
		consumer = ConsumerFactory.generate(100, 100, 0);

		for(AbstractComponent a : consumer) {
			control.addConsumer(a);
		}
		ArrayList<AbstractComponent> generator;
		generator = GeneratorFactory.generate(15, 11000, 5000);
		for(AbstractComponent a : generator) {
			control.addGenerator(a);
		}

		double totalCost=0;
		double totalProfit=0;
		for(int i = 0; i < 12; i++) {
			totalProfit+=control.getProfit();
			totalCost+=control.getCost();
			System.out.println("Frequency: "+control.getFrequency()+"Hz Demand: "+control.getTotalDemand()+"W  Supply: "+control.getTotalSupply()+"W");
			for (AbstractComponent c : consumer) {
				c.next();
			}
			control.nextIteration();
		}
		System.out.println("Daily Profit:"+totalProfit);
		System.out.println("Daily Cost:"+totalCost);

	}

	/**
	- The consumers show a consumption according to table 1.
	- Due to an outage 10% of the supply will unregister at a random iteration between 0 and 5
	- The frequency should stabilize after 5 more iterations
	 */
	@Test
	void testScenario2() {
	Control control = new Control();

		ArrayList<AbstractComponent> consumer;
		consumer = ConsumerFactory.generate(100, 100, 0);

		for(AbstractComponent a : consumer) {
			control.addConsumer(a);
		}
		ArrayList<AbstractComponent> generator;
		generator = GeneratorFactory.generate(6, 10000, 5000);
		for(AbstractComponent a : generator) {
			control.addGenerator(a);
		}

		double totalCost=0;
		double totalProfit=0;
		for(int i = 0; i < 11; i++) {
			if(i==2) {
				generator.remove(0);
				generator.remove(0);
			}
			totalProfit+=control.getProfit();
			totalCost+=control.getCost();
			System.out.println("Frequency: "+control.getFrequency()+"Hz Demand: "+control.getTotalDemand()+"W  Supply: "+control.getTotalSupply()+"W");
			for (AbstractComponent c : consumer) {
				c.next();
			}
			control.nextIteration();
		}
		System.out.println("Daily Profit:"+totalProfit);
		System.out.println("Daily Cost:"+totalCost);
	}


}
