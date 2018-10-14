package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import interfaces.AbstractComponent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import vgu.consumer.ConsumerFactory;
import vgu.control.Control;
import vgu.generator.GeneratorFactory;

public class MainViewController implements Initializable {
	
	private Control control;
	
	@FXML
	private MenuBar menuBar;
	
	@FXML
	private TextField consumerSize;
	@FXML
	private TextField consumerAvgPower;
	@FXML
	private TextField consumerStd;
	
	@FXML
	private TextField generatorSize;
	@FXML
	private TextField generatorTotalPower;
	@FXML
	private TextField generatorStartPower;
	
	@FXML
	private Button submitConsumer;
	
	@FXML
	private Button submitGenerator;
	
	@FXML
	private Button resultButton;
	
	@FXML
    void onConsumersViewClicked(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ConsumersView.fxml"));
    	Parent consumersViewParent = loader.load();
    	
		Scene consumersViewScene = new Scene(consumersViewParent);
		Stage stage = (Stage) menuBar.getScene().getWindow();
		stage.setScene(consumersViewScene);
		stage.setTitle("Consumers");
		stage.show();
    }

    @FXML
    void onGeneratorsViewClicked(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("GeneratorsView.fxml"));
    	Parent generatorsViewParent = loader.load();
    	
		Scene generatorsViewScene = new Scene(generatorsViewParent);
		Stage stage = (Stage) menuBar.getScene().getWindow();
		stage.setScene(generatorsViewScene);
		stage.setTitle("Generators");
		stage.show();
    }
    
    @FXML
    public void onSubmitConsumerClicked(ActionEvent event) throws IOException {
    	Integer amount, avgPower, std;
    	
    	try {
    		amount = Integer.parseInt(consumerSize.getText().trim());
        	avgPower = Integer.parseInt(consumerAvgPower.getText());
        	std = Integer.parseInt(consumerStd.getText());
        	
        	List<AbstractComponent> consumers = ConsumerFactory.generate(amount, avgPower, std);
        	DataUtils.addConsumers(control, consumers);
        	DataUtils.generateConsumers(control.getConsumers());
        	
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("ConsumersView.fxml"));
        	Parent consumersViewParent = loader.load();
    		Scene consumersViewScene = new Scene(consumersViewParent);
    		
    		Stage stage = (Stage) menuBar.getScene().getWindow();
    		stage.setScene(consumersViewScene);
    		stage.setTitle("Consumers");
    		stage.show();
    	} catch (NumberFormatException nfe) {
			AlertDialog.display("Missing Number", "You must perform inputting number!!!");
		}
    }
    
    @FXML
    public void onSubmitGeneratorClicked(ActionEvent event) throws IOException {
    	Integer amount;
    	Double totalPower, startPower;
    	
    	try {
    		amount = Integer.parseInt(generatorSize.getText().trim());
        	totalPower = Double.parseDouble(generatorTotalPower.getText());
        	startPower = Double.parseDouble(generatorStartPower.getText());
    		List<AbstractComponent> generators = GeneratorFactory.generate(amount, totalPower, startPower);
        	DataUtils.addGenerators(control, generators);
        	DataUtils.generateGenerators(control.getGenerators());
        	
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("GeneratorsView.fxml"));
        	Parent generatorsViewParent = loader.load();
    		Scene generatorsViewScene = new Scene(generatorsViewParent);
    		
    		Stage stage = (Stage) menuBar.getScene().getWindow();
    		stage.setScene(generatorsViewScene);
    		stage.setTitle("Generators");
    		stage.show();
    	} catch (NumberFormatException nfe) {
			AlertDialog.display("Missing Number", "You must perform inputting number!!!");
		}
    }
    
    @FXML
    public void onResultButtonClicked(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("StatisticsView.fxml"));
    	Parent statisticsViewParent = loader.load();
		Scene statisticsViewScene = new Scene(statisticsViewParent);
		
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				StatisticsViewController.resetIteration();
			}
		});
		stage.setScene(statisticsViewScene);
		stage.setTitle("Statistics");
		stage.showAndWait();
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		control = new Control();
	}

}
