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

public class DemandSupplyChartController implements Initializable {
	
	@FXML
	private BarChart<String, Number> chart;
	
	@FXML
	private CategoryAxis iterationDemandSupplyAxis;
	@FXML
	private NumberAxis powerAxis;

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
		
		for (int i = 1; i <= StatisticsViewController.getIteration(); i++) {
			iterationList.add(String.valueOf(i));
		}
		
		iterationDemandSupplyAxis.setCategories(FXCollections.<String>observableArrayList(iterationList));
		chart.getData().addAll(DataUtils.getDemandSupplyChartSeries(StatisticsViewController.getIteration(), control));
	}
	
}
