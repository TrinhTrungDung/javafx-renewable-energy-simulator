package vgu.generator;

import interfaces.AbstractComponent;
import vgu.price.Price;

public class Generator extends AbstractComponent {
	
	private String name;
	private double maxPower;
	private double minPower;
	private double maxChange;
	private double minChange;
	private double power;
	
	private Price price;
	
	public Generator(String name, double maxPower, double minPower, double maxChange, double minChange) {
		this.name = name;
		this.maxPower = maxPower;
		this.minPower = minPower;
		this.maxChange = maxChange;
		this.minChange = minChange;
		this.power = minPower;
		this.price = new Price(this);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public double getMaxPower() {
		return this.maxPower;
	}

	@Override
	public void setMaxPower(double maxPower) {
		super.setMaxPower(maxPower);
		this.maxPower = maxPower;
	}
	
	@Override
	public double getMinPower() {
		return this.minPower;
	}

	@Override
	public void setMinPower(double minPower) {
		super.setMinPower(minPower);
		this.minPower = minPower;
	}
	
	@Override
	public double getMaxChange() {
		return this.maxChange;
	}

	@Override
	public void setMaxChange(double maxChange) {
		super.setMaxChange(maxChange);
		this.maxChange = maxChange;
	}

	@Override
	public double getMinChange() {
		return this.minChange;
	}

	@Override
	public void setMinChange(double minChange) {
		super.setMinChange(minChange);
		this.minChange = minChange;
	}

	@Override
	public double getPower() {
		return power;
	}

	@Override
	public void setPower(double power) {
		// Check the difference between current power and the previous power
		// If it is greater than maximum change, then add the maximum change to current power.
		if (Math.abs(this.power - power) > getMaxChange()) {
			this.power += getMaxChange();
		}
		// If it is smaller than minimum change, then the current power is equal to the sum of
		// previous power and the minimum change.
		else if (Math.abs(this.power - power) < getMinChange()) {
			this.power = (power + getMinChange());
		}
		
		// Then check the current power
		// If the current power is greater than maximum power, then assign the current power to maximum power.
		if (this.power > getMaxPower()) {
			this.power = getMaxPower();
		} 
		// If it is smaller than minimum power, then it equals to minimum power.
		else if (this.power < getMinPower()) {
			this.power = getMinPower();
		} 
		// And if it is between the interval of minimum and maximum power, simply assign it to that value.
		else {
			this.power = power;
		}
	}

	@Override
	public void next() {}

	@Override
	public double getCost() {
		return price.calculatePrice();
	}
	
	
	
}
