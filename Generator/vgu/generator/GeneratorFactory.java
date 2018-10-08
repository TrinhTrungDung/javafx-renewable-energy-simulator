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
		
		if (amount > 4) {
			double maxGeneratedPower = totalPower / 4;
			double minGenratedPower = totalPower / (2 * (amount - 2));
			AbstractComponent generator = new Generator("generator_1", maxGeneratedPower, 0, maxGeneratedPower / 2, maxGeneratedPower / 2);
			
			if (power > maxGeneratedPower / 2) {
				generator.setPower(maxGeneratedPower / 2);
				power -= maxGeneratedPower / 2;
			}
			generators.add(generator);
			for (int i = 0; i < amount - 2; i++) {
				generator = new Generator("generator_" + String.valueOf(i + 2), minGenratedPower, minGenratedPower / 10,
						minGenratedPower * 0.75, minGenratedPower / 10);
				if (power > minGenratedPower * 0.75) {
					generator.setPower(minGenratedPower * 0.75);
					power -= minGenratedPower * 0.75;
				}
				generators.add(generator);
			}
		} else {
			double avgPower = totalPower / amount;
			for (int i = 0; i < amount; i++) {
				AbstractComponent generator = new Generator("generator_" + String.valueOf(i + 1), avgPower, 0, avgPower, avgPower);
				if (power > avgPower) {
					generator.setPower(avgPower);
					power -= avgPower;
				}
				generators.add(generator);
			}
		}
		
		return generators;
	}
}
