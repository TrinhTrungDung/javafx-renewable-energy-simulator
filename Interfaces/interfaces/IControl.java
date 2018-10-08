package interfaces;
import java.util.List;

public interface IControl {
	void addGenerator(AbstractComponent generator);
	void removeGenerator(AbstractComponent generator);
	List<AbstractComponent> getGenerators();
	void addConsumer(AbstractComponent consumer);
	void removeConsumer(AbstractComponent consumer);
	List<AbstractComponent> getConsumers();
	double getTotalDemand();
	double getTotalSupply();
	double getFrequency();
	double getCost();
	double getProfit();
	void nextIteration();
}
