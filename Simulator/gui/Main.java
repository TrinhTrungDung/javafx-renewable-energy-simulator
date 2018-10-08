package gui;

import interfaces.AbstractComponent;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import vgu.consumer.ConsumerFactory;

/**
 * GUI requirements:
 * - MUST be capable to control current the iteration (not yet)
 * - MUST be capable to show current demand, supply and frequency (not yet)
 * - MUST be capable to show the number of consumer and generators (done)
 * - MUST be capable to show current weather and electricity price (not yet)
 * - MUST be capable to show name and power of individual consumer or generator (not yet)
 * @author DungTrinhTrung
 *
 */
//TODO control current the iteration
//TODO show current demand, supply and frequency
//TODO show the number of consumer and generators
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
    
    public void saveMenuItemClicked() {
    	
    }
    
    public void exitMenuItemClicked() {
    	
    }
    
    public void onConsumersViewClicked() {
    	Scene scene = new Scene(layout);
    	window.setTitle("Consumers");
    	window.setScene(scene);
    	window.show();
    }
    
    public void onGeneratorsViewClicked() {
    	
    }
    
    private void setup() {
    	// Menu creation part
    	// Save menu item
    	MenuItem saveItem = new MenuItem("_Save");
    	saveItem.setOnAction(e -> saveMenuItemClicked());
    	saveItem.setAccelerator(KeyCombination.keyCombination("CTRL+S"));
    	
    	// Exit menu item
    	MenuItem exitItem = new MenuItem("Exit");
    	exitItem.setOnAction(e -> exitMenuItemClicked());
    	
    	// File menu
    	Menu fileMenu = new Menu("_File");
    	fileMenu.getItems().addAll(saveItem, exitItem);
    	fileMenu.setAccelerator(KeyCombination.keyCombination("ALT+F"));
    	
    	// Consumers view menu item
    	MenuItem consumersView = new MenuItem("Consumers");
    	consumersView.setOnAction(e -> onConsumersViewClicked());
    	
    	// Generators view menu item
    	MenuItem generatorsView = new MenuItem("Generators");
    	generatorsView.setOnAction(e -> onGeneratorsViewClicked());
    	
    	// View menu
    	Menu viewMenu = new Menu("_View");
    	viewMenu.getItems().addAll(consumersView, generatorsView);
    	
    	// Main menu bar
    	MenuBar menuBar = new MenuBar();
    	menuBar.getMenus().addAll(fileMenu, viewMenu);
    	
    	layout = new BorderPane();
    	layout.setTop(menuBar);
    }
    
}
