package vgu.consumer;

import java.util.Random;

import interfaces.AbstractComponent;
import vgu.price.Price;

public class Consumer extends AbstractComponent {
	
	private String name;
	private double maxPower;
	private double minPower;
	private double maxChange;
	private double minChange;
	private double[] runTime;
	private double power;
	private Price price;
	
	private int iteration = 0;
	private static boolean isOn = false;
	
	public Consumer(String name, double maxPower, double minPower, double maxChange, double minChange, double[] runTime) {
		this.name = name;
		this.maxPower = maxPower;
		this.minPower = minPower;
		this.maxChange = maxChange;
		this.minChange = minChange;
		this.runTime = runTime;
		this.power = minPower;
		this.price = new Price(this);
		next();
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
		if (Math.abs(this.power - power) > getMaxChange()) {
			this.power += getMaxChange();
		} else if (Math.abs(this.power - power) < getMinChange()) {
			this.power = (power + getMinChange());
		}
		
		if (this.power > getMaxPower()) {
			this.power = getMaxPower();
		} else if (this.power < getMinPower()) {
			this.power = getMinPower();
		} else {
			this.power = power;
		}
	}

	@Override
	public void next() {
		Random random = new Random();
		
		if (random.nextDouble() < runTime[iteration]) {
			isOn = true;
		} else {
			isOn = false;
		}
		
		if (isOn) {
			this.power = getMaxPower();
		} else {
			this.power = getMinPower();
		}
		
		iteration++;
	}

	@Override
	public double getCost() {
		return this.price.calculatePrice();
	}
	
}
