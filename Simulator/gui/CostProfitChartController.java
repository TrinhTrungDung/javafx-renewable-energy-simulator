package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import interfaces.AbstractComponent;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import vgu.control.Control;

public class CostProfitChartController implements Initializable {
	
	@FXML
	private BarChart<String, Number> costProfitChart;
	
	@FXML
	private CategoryAxis iterationCostProfitAxis;
	@FXML
	private NumberAxis moneyAxis;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Control control = new Control();
		
		try {
			List<AbstractComponent> consumers = DataUtils.getConsumersFromCSV("consumers.csv");
			DataUtils.addConsumers(control, consumers);
			List<AbstractComponent> generators = DataUtils.getGeneratorsFromCSV("generators.csv");
			DataUtils.addGenerators(control, generators);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		List<String> iterationList = new ArrayList<>();
		int iteration = StatisticsViewController.getIteration();
		
		for (int i = 1; i <= iteration; i++) {
			iterationList.add(String.valueOf(i));
		}
		
		iterationCostProfitAxis.setCategories(FXCollections.<String>observableArrayList(iterationList));
		costProfitChart.getData().addAll(DataUtils.getCostProfitChartSeries(iteration, control));
	}
	
}
