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
	private Text currentCost;
	@FXML
	private Text currentProfit;
	
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
		try {
			if (iteration >= 12) {
				AlertDialog.display("Iteration limit", "You cannot perform over 12 iterations!!");
			} else {
				for (AbstractComponent consumer : control.getConsumers()) {
					consumer.next();
				}
				
				control.nextIteration();
				iteration++;
				updateUI();
			}
		} catch (ArrayIndexOutOfBoundsException indexException) {
			AlertDialog.display("Iteration limit", "You cannot perform over 12 iterations!!");
		}
	}
	
	@FXML
	void onShowChartButtonClicked(ActionEvent event) throws IOException {
		Scene chartScene = null;
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		
		if (group.getSelectedToggle() != null) {
			RadioButton selectedButton = (RadioButton) group.getSelectedToggle();
			
			switch (selectedButton.getText()) {
				case "Frequency":
					chartScene = loadChart("FrequencyChartView.fxml");
					chartScene.getStylesheets().add(getClass().getResource("frequencyChart.css").toExternalForm());
					stage.setTitle("Frequency Chart");
					break;
				case "Demand-Supply":
					chartScene = loadChart("DemandSupplyChartView.fxml");
					stage.setTitle("Demand-Supply Chart");
					break;
				case "Cost-Profit":
					chartScene = loadChart("CostProfitChartView.fxml");
					stage.setTitle("Revenue Chart");
					break;
				default:
					break;
			}
		}
		
		stage.setScene(chartScene);
		stage.showAndWait();
	}
	
	private void updateUI() {
		currentIteration.setText(String.valueOf(iteration));
		currentDemand.setText(String.valueOf(control.getTotalDemand()));
		currentSupply.setText(String.valueOf(control.getTotalSupply()));
		currentFrequency.setText(String.valueOf(control.getFrequency()));
		currentCost.setText(String.valueOf(control.getCost()));
		currentProfit.setText(String.valueOf(control.getProfit()));
	}
	
	private Scene loadChart(String chartFileName) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(chartFileName));
		Parent chartParent = loader.load();
		
		return new Scene(chartParent);
	}
	
	public static void resetIteration() {
		iteration = 1;
	}
	
	public static int getIteration() {
		return iteration;
	}

}
