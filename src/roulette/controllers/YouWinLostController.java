package roulette.controllers;

import gameChart.GameChart;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.util.Duration;
import main.Main;
import main.SceneController;

public class YouWinLostController extends SceneController{
    @FXML
    private Group winImg;

    @FXML
    private Group winBtn;

    @FXML
    private Button showStats;

    @FXML
    private Button goBackBtn;

	private GameChart gameChart;
    
	@FXML
	private void goBack(ActionEvent event) {
		 switchScene(event, "/menu/fxml/GameChoiceMenu.fxml");
	}
	@FXML
	private void showStats(ActionEvent event) {
		if (gameChart != null && gameChart.isWindowOpen()) {
            // If it's open, show a warning alert
            gameChart.showGraphAlreadyOpenAlert();
        } else {
            // If the graph window is not open, create a new instance and show it
            gameChart = new GameChart();
            gameChart.showChartWindow();
        }
	}
	
	static void deleteAccoult() {
		
	}
	
	@FXML
	public void initialize() {
	    // Imposta lo stato iniziale
	    winImg.setVisible(true);
	    winBtn.setVisible(false);
	    
	    // Usa Platform.runLater per assicurarti che la scena sia disponibile
	    Platform.runLater(() -> {
	        // Crea la transizione
	        PauseTransition delay = new PauseTransition(Duration.seconds(1.5));
	        delay.setOnFinished(e -> {
	            winImg.setVisible(false);
	            winBtn.setVisible(true);
	            Main.player.addMoney(10000);
	        });
	        
	        // Avvia la transizione immediatamente
	        delay.play();
	        
	        // Debug: verifica se la scena esiste
	        if (winBtn.getScene() == null) {
	            System.out.println("Attenzione: la scena non è ancora disponibile!");
	        }
	    });
	}
}
