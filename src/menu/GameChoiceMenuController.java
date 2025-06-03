package menu;

import java.net.URL;
import java.util.ResourceBundle;
import gameChart.GameChart;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import main.Main;
import main.SceneController;

public class GameChoiceMenuController extends SceneController {	
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private BorderPane mainBg;
    
    @FXML
    private Button btDiceGame;

    @FXML
    private Button btGame5;

    @FXML
    private Button btGoBack;

    @FXML
    private Button btGuessNumber;

    @FXML
    private Button btRoulette;

    @FXML
    private Button btSlotMachines;
    
    @FXML
    private Label moneyLabel;

    @FXML
    private Button btViewStats;
	private GameChart gameChart;

    @FXML
    void goBack(ActionEvent event) {
    	//go back to mode menu
    	switchScene(event, "fxml/ModeMenu.fxml");
    }

    @FXML
    void startDiceGame(ActionEvent event) {
    	switchScene(event, "/diceGame/fxml/modeSwitch.fxml");
    }


    @FXML
    void startGuessNumber(ActionEvent event) {
    	switchScene(event, "/guessNumber/InitialPage.fxml");
    }

    @FXML
    void startRoulette(ActionEvent event) {
    	switchScene(event,"/roulette/fxml/Roulette.fxml");

    }

    @FXML
    void startSlotMachines(ActionEvent event) {
    	switchScene(event,"/slot/fxml/Slot.fxml");
    }

    @FXML
    void startGame5(ActionEvent event) {
    	switchScene(event,"/coinflip/fxml/Coin1.fxml");
    }
    
    @FXML
    void viewStats(ActionEvent event) {
    	// Check if the gameChart window is already open
        if (gameChart != null && gameChart.isWindowOpen()) {
            // If it's open, show a warning alert
            gameChart.showGraphAlreadyOpenAlert();
        } else {
            // If the graph window is not open, create a new instance and show it
            gameChart = new GameChart();
            gameChart.showChartWindow();
        }
    }


    
    @FXML
    void initialize() {
    	moneyLabel.setText("$" + Main.player.getMoney().toString());
    	if (Main.getPlayer().getMoneyIsZero()) {
        	PauseTransition delay = new PauseTransition(Duration.seconds(2));
            delay.setOnFinished(e -> {
                // Switch scenes after the delay
            	switchScene("../menu/fxml/AllMoneyLostMenu.fxml");
            });
            delay.play();    
        }

        assert moneyLabel != null : "fx:id=\"moneyLabel\" was not injected: check your FXML file 'GameChoiceMenu.fxml'.";
        assert btDiceGame != null : "fx:id=\"btCraps\" was not injected: check your FXML file 'GameMenu.fxml'.";
        assert btGame5 != null : "fx:id=\"btGame5\" was not injected: check your FXML file 'GameMenu.fxml'.";
        assert btGoBack != null : "fx:id=\"btGoBack\" was not injected: check your FXML file 'GameMenu.fxml'.";
        assert btGuessNumber != null : "fx:id=\"btGuessNumber\" was not injected: check your FXML file 'GameMenu.fxml'.";
        assert btRoulette != null : "fx:id=\"btRoulette\" was not injected: check your FXML file 'GameMenu.fxml'.";
        assert btSlotMachines != null : "fx:id=\"btSlotMachines\" was not injected: check your FXML file 'GameMenu.fxml'.";
        assert btViewStats != null : "fx:id=\"btViewStats\" was not injected: check your FXML file 'GameMenu.fxml'.";
        assert mainBg != null : "fx:id=\"mainBg\" was not injected: check your FXML file 'GameChoiceMenu.fxml'.";
        
    }

}
