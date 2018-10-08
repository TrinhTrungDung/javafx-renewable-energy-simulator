package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import interfaces.AbstractComponent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import vgu.consumer.ConsumerFactory;
import vgu.control.Control;
import vgu.generator.GeneratorFactory;

public class MainDocumentController implements Initializable {
	
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
		ObservableList<AbstractComponent> observableConsumers = FXCollections.observableArrayList(control.getConsumers());
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ConsumersDocument.fxml"));
    	Parent consumersViewParent = loader.load();
    	ConsumersDocumentController controller = loader.getController();
    	controller.setData(observableConsumers);
		Scene consumersViewScene = new Scene(consumersViewParent);
		
		Stage stage = (Stage) menuBar.getScene().getWindow();
		stage.setScene(consumersViewScene);
		stage.setTitle("Consumers");
		stage.show();
    }

    @FXML
    void onGeneratorsViewClicked(ActionEvent event) throws IOException {
    	ObservableList<AbstractComponent> observableGenerators = FXCollections.observableArrayList(control.getGenerators());
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("GeneratorsDocument.fxml"));
    	Parent generatorsViewParent = loader.load();
    	GeneratorsDocumentController controller = loader.getController();
    	controller.setData(observableGenerators);
		Scene generatorsViewScene = new Scene(generatorsViewParent);
		
		Stage stage = (Stage) menuBar.getScene().getWindow();
		stage.setScene(generatorsViewScene);
		stage.setTitle("Generators");
		stage.show();
    }
    
    @FXML
    public void onSubmitConsumerClicked(ActionEvent event) throws IOException {
    	Integer amount = Integer.parseInt(consumerSize.getText().trim());
    	Integer avgPower = Integer.parseInt(consumerAvgPower.getText());
    	Integer std = Integer.parseInt(consumerStd.getText());
    	
    	List<AbstractComponent> consumers = ConsumerFactory.generate(amount, avgPower, std);
    	ObservableList<AbstractComponent> observableConsumers = FXCollections.observableArrayList(consumers);
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("ConsumersDocument.fxml"));
    	Parent consumersViewParent = loader.load();
    	ConsumersDocumentController controller = loader.getController();
    	controller.setData(observableConsumers);
		Scene consumersViewScene = new Scene(consumersViewParent);
		
		Stage stage = (Stage) menuBar.getScene().getWindow();
		stage.setScene(consumersViewScene);
		stage.setTitle("Consumers");
		stage.show();
    }
    
    @FXML
    public void onSubmitGeneratorClicked(ActionEvent event) throws IOException {
    	Integer amount = Integer.parseInt(generatorSize.getText().trim());
    	Double totalPower = Double.parseDouble(generatorTotalPower.getText());
    	Double startPower = Double.parseDouble(generatorStartPower.getText());
    	
    	List<AbstractComponent> generators = GeneratorFactory.generate(amount, totalPower, startPower);
    	ObservableList<AbstractComponent> observableGenerators = FXCollections.observableArrayList(generators);
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("GeneratorsDocument.fxml"));
    	Parent generatorsViewParent = loader.load();
    	GeneratorsDocumentController controller = loader.getController();
    	controller.setData(observableGenerators);
		Scene generatorsViewScene = new Scene(generatorsViewParent);
		
		Stage stage = (Stage) menuBar.getScene().getWindow();
		stage.setScene(generatorsViewScene);
		stage.setTitle("Generators");
		stage.show();
    }
    
    @FXML
    public void onResultButtonClicked(ActionEvent event) throws IOException {
    	StatisticsDialog.display("Result", control);
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		control = new Control();
	}
	
	public void addConsumers(List<AbstractComponent> consumers) {
		consumers.forEach(consumer -> this.control.addConsumer(consumer));
	}
	
	public void addGenerators(List<AbstractComponent> generators) {
		generators.forEach(generator -> this.control.addGenerator(generator));
	}
	
	public Control getControl() {
		return this.control;
	}

}
