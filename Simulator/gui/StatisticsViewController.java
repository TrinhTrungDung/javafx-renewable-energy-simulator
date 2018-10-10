package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import interfaces.AbstractComponent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import vgu.control.Control;

public class StatisticsViewController implements Initializable {
	
	private Control control;
	
	private static int iteration = 1;

	@FXML
	private Text currentIteration;
	@FXML
	private Text currentDemand;
	@FXML
	private Text currentSupply;
	@FXML
	private Text currentFrequency;
	
	@FXML
	private Button nextButton;
	
	@FXML
	private ToggleGroup group;
	
	@FXML
	private Button showChartButton;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		control = new Control();
		
		try {
			List<AbstractComponent> consumers = DataUtils.getConsumersFromCSV("consumers.csv");
			DataUtils.addConsumers(control, consumers);
			List<AbstractComponent> generators = DataUtils.getGeneratorsFromCSV("generators.csv");
			DataUtils.addGenerators(control, generators);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		updateUI();
	}
	
	@FXML
	void onNextButtonClicked(ActionEvent event) {
		control.nextIteration();
		iteration++;
		updateUI();
	}
	
	@FXML
	void onShowChartButtonClicked(ActionEvent event) throws IOException {
		Scene chartScene = null;
		
		if (group.getSelectedToggle() != null) {
			RadioButton selectedButton = (RadioButton) group.getSelectedToggle();
			
			switch (selectedButton.getText()) {
				case "Frequency":
					chartScene = loadChart("FrequencyChartView.fxml");
					break;
				case "Demand-Supply":
					chartScene = loadChart("DemandSupplyChartView.fxml");
					break;
				default:
					break;
			}
		}
		
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setScene(chartScene);
		stage.setTitle("");
		stage.showAndWait();
	}
	
	private void updateUI() {
		currentIteration.setText(String.valueOf(iteration));
		currentDemand.setText(String.valueOf(control.getTotalDemand()));
		currentSupply.setText(String.valueOf(control.getTotalSupply()));
		currentFrequency.setText(String.valueOf(control.getFrequency()));
	}
	
	private Scene loadChart(String chartFileName) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(chartFileName));
		Parent chartParent = loader.load();
		
		return new Scene(chartParent);
	}
	
	public static void resetIteration() {
		iteration = 1;
	}
	
	public int getIteration() {
		return iteration;
	}

}
