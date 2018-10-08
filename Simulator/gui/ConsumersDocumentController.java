package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import interfaces.AbstractComponent;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import vgu.consumer.ConsumerFactory;

public class ConsumersDocumentController implements Initializable {
	
	private ObservableList<AbstractComponent> consumers = null;
	
	public void setData(ObservableList<AbstractComponent> consumers) {
		this.consumers = consumers;
		table.getItems().addAll(this.consumers);
	}
	
	@FXML
	private MenuBar menuBar;
	
	@FXML
	private TableView<AbstractComponent> table;
	
	@FXML
	private VBox vBox;
	
	@FXML
	private TableColumn<AbstractComponent, String> nameColumn;
	@FXML
	private TableColumn<AbstractComponent, Double> maxPowerColumn;
	@FXML
	private TableColumn<AbstractComponent, Double> minPowerColumn;
	@FXML
	private TableColumn<AbstractComponent, Double> maxChangeColumn;
	@FXML
	private TableColumn<AbstractComponent, Double> minChangeColumn;
	
	@FXML
	private HBox hBox;
	
	@FXML
	private TextField nameInput;
	@FXML
	private TextField maxPowerInput;
	@FXML
	private TextField minPowerInput;
	@FXML
	private TextField maxChangeInput;
	@FXML
	private TextField minChangeInput;
	
	@FXML
	private Button addButton;
	@FXML
	private Button deleteButton;
	@FXML
	private Button saveButton;
	
	@FXML
	private Text numOfConsumers;
	
	@FXML
    void onConsumersViewClicked(ActionEvent event) throws IOException {
		Parent consumersViewParent = FXMLLoader.load(getClass().getResource("ConsumersDocument.fxml"));
		Scene consumersViewScene = new Scene(consumersViewParent);
		
		Stage stage = (Stage) menuBar.getScene().getWindow();
		stage.setScene(consumersViewScene);
		stage.setTitle("Consumers");
		stage.show();
    }

    @FXML
    void onGeneratorsViewClicked(ActionEvent event) throws IOException {
    	Parent generatorsViewParent = FXMLLoader.load(getClass().getResource("GeneratorsDocument.fxml"));
		Scene generatorsViewScene = new Scene(generatorsViewParent);
		
		Stage stage = (Stage) menuBar.getScene().getWindow();
		stage.setScene(generatorsViewScene);
		stage.setTitle("Generators");
		stage.show();
    }
    
    @FXML
    public void onAddConsumerClicked(ActionEvent event) {
    	String name = nameInput.getText();
    	Double maxPower = Double.parseDouble(maxPowerInput.getText());
    	Double minPower = Double.parseDouble(minPowerInput.getText());
    	Double maxChange = Double.parseDouble(maxChangeInput.getText());
    	Double minChange = Double.parseDouble(minChangeInput.getText());
    	
    	AbstractComponent component = ConsumerFactory.generate(name, maxPower, minPower, maxChange, minChange);
    	table.getItems().add(component);
    	
    	nameInput.clear();
    	maxPowerInput.clear();
    	minPowerInput.clear();
    	maxChangeInput.clear();
    	minChangeInput.clear();
    	
    	numOfConsumers.setText("Total: " + table.getItems().size());
    }
    
    @FXML
    public void onDeleteConsumerClicked(ActionEvent event) {
    	ObservableList<AbstractComponent> selectedComponents, allComponents;
    	allComponents = table.getItems();
    	selectedComponents = table.getSelectionModel().getSelectedItems();
    	
    	selectedComponents.forEach(allComponents::remove);
    	numOfConsumers.setText("Total: " + table.getItems().size());
    }
    
    @FXML
    public void onSaveButtonClicked(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("MainDocument.fxml"));
    	Parent mainParent = loader.load();
    	MainDocumentController controller = loader.getController();
    	controller.addConsumers(table.getItems());
		Scene mainScene = new Scene(mainParent);
		
		Stage stage = (Stage) menuBar.getScene().getWindow();
		stage.setScene(mainScene);
		stage.setTitle("");
		stage.show();
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (numOfConsumers != null) {
			numOfConsumers.setText("Total: " + table.getItems().size());
		}
	}
	
}
