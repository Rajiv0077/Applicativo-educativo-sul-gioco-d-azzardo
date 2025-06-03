package main;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SceneController {
	
	protected void switchScene(String fxmlFile) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
            Stage stage = (Stage) Main.getPrimaryStage();
            
            System.out.println("switchScene was used");
            // Force full-screen regardless of previous state
            stage.setScene(new Scene(root));
            stage.setFullScreen(true); // Enforce full-screen
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
    protected void switchScene(ActionEvent event, String fxmlFile) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
            Stage stage = (Stage) Main.getPrimaryStage();
            
            System.out.println("switchScene was used");
            // Force full-screen regardless of previous state
            stage.setScene(new Scene(root));
            stage.setFullScreen(true); // Enforce full-screen
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    protected void switchScene(HBox h ,String fxmlFile) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
            Stage stage = (Stage) h.getScene().getWindow();
            
            // Force full-screen regardless of previous state
            stage.setScene(new Scene(root));
            stage.setFullScreen(true); // Enforce full-screen
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    protected void switchSceneModal( ActionEvent event, String fxmlFile) {
    	try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
			Parent root = loader.load();
			Stage stage = new Stage();
			Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Ottieni lo stage dall'evento
			stage.initOwner(mainStage); // Imposta lo stage principale come owner
			stage.setScene(new Scene(root));
			stage.setFullScreen(true); // Facoltativo: se vuoi che anche il popup sia fullscreen
			stage.setTitle("Ricarica");
			// Impedisci all'utente di interagire con lo stage principale
			stage.initModality(Modality.WINDOW_MODAL);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}