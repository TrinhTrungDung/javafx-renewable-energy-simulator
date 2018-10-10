package gui;

import java.awt.SystemTray;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import interfaces.AbstractComponent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import vgu.control.Control;

public class FrequencyChartController implements Initializable {
	
	@FXML
	private LineChart<Number, Number> chart;
	
	@FXML
	private NumberAxis iterationFrequencyAxis;
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
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("StatisticsView.fxml"));
		try {
			loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StatisticsViewController controller = loader.getController();
		System.out.println(controller.getIteration());
		
		chart.setData(DataUtils.getFrequencyChartSeries(controller.getIteration(), control));
	}

}
