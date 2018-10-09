package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import interfaces.AbstractComponent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import vgu.control.Control;

public class ChartController implements Initializable {
	
	@FXML
	private LineChart<Number, Number> chart;
	
	@FXML
	private NumberAxis iterationAxis;
	@FXML
	private NumberAxis frequencyAxis;

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
		
		chart.setData(DataUtils.getFrequencyChartSeries(StatisticsViewController.getIteration(), control));
	}

}
