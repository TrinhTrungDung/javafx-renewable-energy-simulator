package vgu.consumer;
import java.util.ArrayList;
import java.util.Random;

import interfaces.AbstractComponent;

/***
 *
 * This is an empty template to implement a Consumer Factory.
 * This Factory should return one or several of the self-implemented
 * Consumers, derived from 'interfaces.AbstractComponent'
 * It is a convenient place implement distribution and frequency of
 * consumer types.
 *
 */
public class ConsumerFactory {

	/**
	 * Stores the amount of consumers running at every iteration in percentage
	 */
	static double[] run = new double[] {.5,.2,.15,.45,.75,.60,.55,.40,.45,.65,.95,.75};

	public static void setRunBehaviour(double[] runTime) {
		run = runTime;
	}

	/**
	 * Generate a single consumer
	 * @param name
	 * @param maxPower
	 * @param minPower
	 * @param maxChange
	 * @param minChange
	 * @return
	 */
	public static AbstractComponent generate(String name, double maxPower, double minPower, double maxChange,
			double minChange) {
		return new Consumer(name, maxPower, minPower, maxChange, minChange, run);
	}

	/**
	 *
	 * Generate a set of consumers.
	 * @param amount	Number of consumers to generate
	 * @param avgPower	Mean Power of <amount> consumers
	 * @param std		Standard Deviation of power
	 */
	public static ArrayList<AbstractComponent> generate(int amount, int avgPower, int std) {
		Random random = new Random();
		ArrayList<AbstractComponent> consumers = new ArrayList<>();
		
		for (int i = 0; i < amount; i++) {
			// Calculate the power based on normally distribution
			double distributedPower = random.nextGaussian() * std + avgPower;
			consumers.add(new Consumer("consumer_" + String.valueOf(i + 1), distributedPower, 0, distributedPower, 0, run));
		}
		
		return consumers;
	}

}