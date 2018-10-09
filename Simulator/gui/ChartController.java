package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.shape.Line;

public class ChartController implements Initializable {
	
	@FXML
	private LineChart<X, Y> chart;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

}
