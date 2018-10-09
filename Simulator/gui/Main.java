package gui;

import interfaces.AbstractComponent;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * GUI requirements:
 * - MUST be capable to control current the iteration (done)
 * - MUST be capable to show current demand, supply and frequency (done)
 * - MUST be capable to show the number of consumer and generators (done)
 * - MUST be capable to show current weather and electricity price (not yet)
 * - MUST be capable to show name and power of individual consumer or generator (add more power column)
 * @author DungTrinhTrung
 *
 */
//TODO show current weather and electricity price
//TODO show name and power of individual consumer or generator
public class Main extends Application {
	
	Stage window;
	BorderPane layout;
	TableView<AbstractComponent> table;
	TextField nameInput, maxPowerInput, minPowerInput, maxChangeInput, minChangeInput;

    @Override
    public void start(Stage primaryStage) throws Exception {
    	Parent root = FXMLLoader.load(getClass().getResource("MainDocument.fxml"));
    	
    	Scene scene = new Scene(root);
    	
    	primaryStage.setScene(scene);
    	primaryStage.show();
    	
//    	setup();
//    	
//    	
////    	Control control = new Control();
////    	AbstractComponent c1 = ConsumerFactory.generate("c1", 100, 100, 100, 100);
////		AbstractComponent g1 = GeneratorFactory.generate("g1", 90, 90, 90, 90);
////		control.addConsumer(c1);
////		control.addGenerator(g1);
////		
////		DataUtils.generateConsumers(control);
////		DataUtils.generateGenerators(control);
////		
////		Text text = new Text();
////		
////		text.setText(((Consumer) control.getConsumers().get(0)).getName());
////		
////		VBox vBox = new VBox();
////		vBox.getChildren().add(text);
//		
////		StackPane root = new StackPane();
////		root.getChildren().add(vBox);
//    	
////    	// Create axis for iteration
////    	NumberAxis iterationAxis = new NumberAxis();
////    	iterationAxis.setLabel("Iteration");
////    	iterationAxis.setAutoRanging(false);
////    	iterationAxis.setLowerBound(0);
////    	iterationAxis.setUpperBound(11);
////    	iterationAxis.setTickUnit(1);
//    	
////    	// Create axis for power
////    	NumberAxis powerAxis = new NumberAxis();
////    	powerAxis.setLabel("Power");
////    	powerAxis.setAutoRanging(false);
////    	powerAxis.setLowerBound(0);
////    	powerAxis.setUpperBound(100);
////    	powerAxis.setTickUnit(1);
//    	
////    	// Create line chart based on 2 above axes
////    	LineChart<Number, Number> chart = new LineChart<>(iterationAxis, powerAxis);
////    	chart.setTitle("Demand-side management");
////    	// Set data for the chart
////    	ObservableList<XYChart.Series<Number, Number>> chartData = FxChartUtils.getSeries();
////    	chart.setData(chartData);
//    	
////    	StackPane root = new StackPane(chart);
////    	
////    	root.setStyle("-fx-padding: 10;" + 
////    			"-fx-border-style: solid inside;" +
////    			"-fx-border-width: 2;" +
////    			"-fx-border-insets: 5;" +
////    			"-fx-border-radius: 5;" +
////    			"-fx-border-color: blue;");
////    	
////    	Scene scene = new Scene(root);
////    	scene.getStylesheets().add(getClass().getResource("linechart.css").toExternalForm());
//    	
//    	
//    	layout.setLeft(vBox);
//    	
//    	Scene scene = new Scene(layout);
//    	
//    	window = primaryStage;
//        window.setTitle("Demand-side Management");
//        window.setScene(scene);
//        window.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
    
}
