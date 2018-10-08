package vgu.price;

import interfaces.AbstractComponent;
import vgu.consumer.Consumer;
import vgu.generator.Generator;

public class Price {
	
	private AbstractComponent component;
	
	public Price(AbstractComponent component) {
		this.component = component;
	}
	
	/**
	 * Get type of the component
	 * @param component
	 * @return 1 if type of component is consumer 
	 * 			2 if its type is generator
	 * @throws IllegalArgumentException
	 */
	public int getType(AbstractComponent component) {
		if (component instanceof Consumer) {
			return 1;
		} else if (component instanceof Generator) {
			return 2;
		} else {
			throw new IllegalArgumentException("Unknown type: " + component.toString());
		}
	}
	
	public double calculatePrice() {
		if (getType(this.component) == 1) {
			return consumerCost();
		} else if (getType(this.component) == 2) {
			return generatorCost();
		} else {
			return 0;
		}
	}
	
	/**
	 * Calculate cost for consumer.
	 * @return used power of consumer. (since its cost equals to power times 1)
	 */
	private double consumerCost() {
		return this.component.getPower();
	}
	
	/**
	 * Calculate cost for generator.
	 * @return generated power of generator.
	 */
	private double generatorCost() {
		double baseCost = 50;
		
		if (this.component.getMaxPower() < 2500 || 
				this.component.getMaxChange() > (this.component.getMaxPower() / 2) ||
				this.component.getMinChange() < (this.component.getMaxPower() / 2)) {
			baseCost += 25;
		}
		
		return baseCost * this.component.getPower() / 100;
	}
	
}
