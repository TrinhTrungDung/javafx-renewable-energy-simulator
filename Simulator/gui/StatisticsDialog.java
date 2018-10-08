package gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import vgu.control.Control;

public class StatisticsDialog {
	
	public static void display(String title, Control control) {
		Stage window = new Stage();
		
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinWidth(500);
		
		Label costLabel = new Label();
		costLabel.setText("Cost: " + String.valueOf(control.getCost()));
		Label frequencyLabel = new Label();
		frequencyLabel.setText("Frequency: " + String.valueOf(control.getFrequency()));
		Label profitLabel = new Label();
		profitLabel.setText("Profit: " + String.valueOf(control.getProfit()));
		Label demandLabel = new Label();
		demandLabel.setText("Demand: " + String.valueOf(control.getTotalDemand()));
		Label supplyLabel = new Label();
		supplyLabel.setText("Supply: " + String.valueOf(control.getTotalSupply()));
		
		VBox layout = new VBox();
		layout.getChildren().addAll(costLabel, frequencyLabel, profitLabel, demandLabel, supplyLabel);
		layout.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
	}
	
}
