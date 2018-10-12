package vgu.control;
import java.util.ArrayList;
import java.util.List;

import interfaces.AbstractComponent;
import interfaces.IControl;
import vgu.consumer.Consumer;
import vgu.generator.Generator;

/**
 * This empty control template can be best implemented by evaluating all
 * JUnit-TestCases.
 */
/**
 * Control model requirements:
 * - MUST be able register an arbitrary number of generators and consumers. (done)
 * - MUST be able to unregister each component. (done)
 * - MUST compute the total demand every iteration. (done, need improving)
 * - MUST compute the total cost every iteration. (done)
 * - MUST compute the mains frequency every iteration. (done)
 *	(50Hz minus the difference of demand-supply, whereas 10% equals 1Hz)
 * - MUST unregister 10% Generators, if mains frequency > 51Hz (overload) (done)
 * - MUST unregister 15% of the consumers if mains frequency < 49Hz (blackout) (done)
 * - MUST unregister components if mains frequency > 51Hz < 49 Hz for 3 Iterations (defect) (done)
 * - MAY request state changes in consumers for demand side management. (not yet)
 * - MAY request supply and state changes in generators for demand side management (not yet)
 * - MAY request to start or shutdown generators for demand side management ( not yet)
 * - MAY change the electricity price (not yet)
 * @author DungTrinhTrung
 *
 */
//TODO request state changes in consumers for demand side management.
//TODO request supply and state changes in generators for demand side management.
//TODO request to start or shutdown generators for demand side management.
//TODO change the electricity price.
public class Control implements IControl {
	
	private List<AbstractComponent> generators = new ArrayList<>();
	private List<AbstractComponent> consumers = new ArrayList<>();
	
	private int blackout = 0;
	private int overload = 0;

	@Override
	public void addGenerator(AbstractComponent generator) {
		if (generator instanceof Generator) {
			generators.add(generator);
		} else {
			throw new IllegalArgumentException("Wrong type: " + generator.getClass().toString());
		}
	}

	@Override
	public void removeGenerator(AbstractComponent generator) {
		if (generators.contains(generator)) {
			generators.remove(generator);
		} else {
			throw new IllegalArgumentException("There is no " + generator.toString() + " in generators.");
		}
	}

	@Override
	public List<AbstractComponent> getGenerators() {
		return generators;
	}

	@Override
	public void addConsumer(AbstractComponent consumer) {
		if (consumer instanceof Consumer) {
			consumers.add(consumer);
		} else {
			throw new IllegalArgumentException("Wrong type: " + consumer.getClass().toString());
		}
	}

	@Override
	public void removeConsumer(AbstractComponent consumer) {
		if (consumers.contains(consumer)) {
			consumers.remove(consumer);
		} else {
			throw new IllegalArgumentException("There is no " + consumer.toString() + " in consumers.");
		}
	}

	@Override
	public List<AbstractComponent> getConsumers() {
		return consumers;
	}

	@Override
	public double getTotalDemand() {
		double total = 0;
		
		for (AbstractComponent consumer : consumers) {
			total += consumer.getPower();
		}
		
		return total;
	}

	@Override
	public double getTotalSupply() {
		double total = 0;
		
		for (AbstractComponent generator : generators) {
			total += generator.getPower();
		}
		
		return total;
	}

	@Override
	public double getFrequency() {
		double totalDemand = getTotalDemand();
		double totalSupply = getTotalSupply();
		
		double frequencyChange = 10.0 - (Math.min(totalDemand, totalSupply) * 10 / Math.max(totalDemand, totalSupply));
		
		if (totalDemand == 0 && totalSupply == 0 || isDefect()) {
			return 0;
		}
		
		if (totalDemand > totalSupply) {
			return 50 - frequencyChange;
		} else if (totalDemand < totalSupply) {
			return 50 + frequencyChange;
		} else {
			return 50;
		}
	}

	@Override
	public double getCost() {
		double total = 0;
		
		for (AbstractComponent generator : generators) {
			total += generator.getCost();
		}
		
		return total;
	}

	@Override
	public double getProfit() {
		double total = 0;
		
		for (AbstractComponent consumer : consumers) {
			total += consumer.getCost();
		}
		
		return total;
	}

	@Override
	public void nextIteration() {
	    double diff = getTotalSupply() - getTotalDemand();
	    double powerChange = 0;
	    
	    for (int i = 0; i < generators.size(); i++) {
	    	AbstractComponent generator = generators.get(i);
	    	
	    	if (diff > 0) {
	    		powerChange = Math.min(generator.getMaxChange(), generator.getPower() - generator.getMinPower());
	    		if (Math.abs(diff) >= powerChange) {
	    			generator.setPower(generator.getPower() - powerChange);
	    			diff -= powerChange;
	    		} else {
	    			powerChange = Math.max(Math.abs(diff), generator.getMinChange());
	    			if (Math.abs(diff) >= powerChange) {
		    			generator.setPower(generator.getPower() - powerChange);
		    			diff -= powerChange;
		    		}
	    		}
	    	} else if (diff < 0) {
	    		powerChange = Math.min(generator.getMaxChange(), generator.getMaxPower() - generator.getPower());
	    		if (Math.abs(diff) >= powerChange) {
	    			generator.setPower(generator.getPower() + powerChange);
	    			diff += powerChange;
	    		} else {
	    			powerChange = Math.max(Math.abs(diff), generator.getMinChange());
	    			if (Math.abs(diff) >= powerChange) {
	    				generator.setPower(generator.getPower() + powerChange);
	    				diff += powerChange;
	    			}
	    		}
	    	}
	    }
		
		frequencyBalancer(getFrequency());
	}
	
	private boolean isOverload(double frequency) {
		if (frequency > 51) return true;
		else return false;
	}
	
	private boolean isBlackout(double frequency) {
		if (frequency < 49) return true;
		else return false;
	}
	
	private boolean isDefect() {
		if (overload >= 3 || blackout >= 3) {
			return true;
		}
		
		return false;
	}
	
	private void frequencyBalancer(double frequency) {
		int unregisterComponents = 0;
		
		if (isOverload(frequency)) {
			overload += 1;
			blackout = 0;
			unregisterComponents = (int) Math.ceil(generators.size() * 0.1);
			generators.subList(0, unregisterComponents).clear();
		} else if (isBlackout(frequency)) {
			blackout += 1;
			overload = 0;
			unregisterComponents = (int) Math.ceil(consumers.size() * 0.15);
			consumers.subList(0, unregisterComponents).clear();
		} else {
			overload = 0;
			blackout = 0;
		}
		
		if (isDefect()) {
			consumers.clear();
			generators.clear();
		}
	}

}
