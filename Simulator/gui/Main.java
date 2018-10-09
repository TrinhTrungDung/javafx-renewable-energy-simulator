package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

    @Override
    public void start(Stage primaryStage) throws Exception {
    	Parent root = FXMLLoader.load(getClass().getResource("MainView.fxml"));
    	
    	Scene scene = new Scene(root);
    	
    	primaryStage.setScene(scene);
    	primaryStage.show();
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
    }


    public static void main(String[] args) {
        launch(args);
    }
    
}
