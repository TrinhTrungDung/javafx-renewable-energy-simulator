package interfaces;

public abstract class AbstractComponent {

	protected String name;
	private double maxPower;
	private double minPower;
	private double maxChange;
	private double minChange;
	public abstract double getPower();
	public abstract void setPower(double power);
	public abstract void next();
	public abstract double getCost();
	public double getMinChange() {
		return minChange;
	}
	public void setMinChange(double minChange) {
		this.minChange = minChange;
	}
	public double getMaxChange() {
		return maxChange;
	}
	public void setMaxChange(double maxChange) {
		this.maxChange = maxChange;
	}
	public double getMaxPower() {
		return maxPower;
	}
	public void setMaxPower(double maxPower) {
		this.maxPower = maxPower;
	}
	public double getMinPower() {
		return minPower;
	}
	public void setMinPower(double minPower) {
		this.minPower = minPower;
	}
}
