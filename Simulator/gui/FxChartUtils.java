package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

public class FxChartUtils {
	
	// Create line chart data
	public static ObservableList<XYChart.Series<Number, Number>> getSeries() {
		XYChart.Series<Number, Number> powerSeries = new XYChart.Series<Number, Number>();
		
		// Add data here
		
		
		ObservableList<XYChart.Series<Number, Number>> observableData = FXCollections.<XYChart.Series<Number, Number>>observableArrayList();
		observableData.add(powerSeries);
		
		return observableData;
	}
	
}
