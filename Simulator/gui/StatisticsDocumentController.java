package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import interfaces.AbstractComponent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import vgu.control.Control;

public class StatisticsDocumentController implements Initializable {
	
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
	void onShowChartButtonClicked(ActionEvent event) {
		
	}
	
	private void updateUI() {
		currentIteration.setText(String.valueOf(iteration));
		currentDemand.setText(String.valueOf(control.getTotalDemand()));
		currentSupply.setText(String.valueOf(control.getTotalSupply()));
		currentFrequency.setText(String.valueOf(control.getFrequency()));
	}

}
