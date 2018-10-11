package vgu.generator;

import java.util.ArrayList;

import interfaces.AbstractComponent;

/***
 *
 * Empty template to generate a Generator Factory
 * This Factory should return one or several of the self-implemented
 * Power-Supplies, derived from 'interfaces.AbstractComponent'.
 * It is a convenient place to implement distribution and frequency of
 * different generator types.
 *
 */
public class GeneratorFactory {

	public static AbstractComponent generate(String name, double maxPower, double minPower, double maxChange,
			double minChange) {
		return new Generator(name, maxPower, minPower, maxChange, minChange);
	}

	/**
	 *
	The nextGaussian() method returns random numbers with a mean of 0 and a standard deviation of 1.
    to change the mean (average) of the distribution, we add the required value;
    to change the standard deviation, we multiply the value.
	 * @param amount	Number of Values
	 * @param avgPower	Mean
	 * @param std		Standard Deviation
	 */
	public static ArrayList<AbstractComponent> generate(int amount, double totalPower, double startPower) {
		ArrayList<AbstractComponent> generators = new ArrayList<>();
		double power = startPower;
		double avgPower = totalPower / amount;
		AbstractComponent generator;
		
		generator = new Generator("generator_1", avgPower, 0, avgPower, 0);
		generators.add(generator);
			
		for (int i = 2; i <= amount; i++) {
			generator = new Generator("generator_" + String.valueOf(i), avgPower, 0, avgPower / 2, avgPower / 2);
			if (power > avgPower * 0.5) {
				generator.setPower(avgPower * 0.5);
				power -= avgPower * 0.5;
			}
			generators.add(generator);
		}
		
		return generators;
	}
}
