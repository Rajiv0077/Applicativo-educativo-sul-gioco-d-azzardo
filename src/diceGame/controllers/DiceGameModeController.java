package diceGame.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import main.SceneController;

public class DiceGameModeController extends SceneController {

    @FXML private Button oneDiceButton;
    @FXML private Button twoDiceButton;
    @FXML private Button threeDiceButton;
    @FXML private Button fourDiceButton;
    @FXML private Button backButon;
    
    private static Integer gameMode = 0;


    @FXML
    private void switchToOneDiceMode(ActionEvent event) {
    	gameMode = 1;
    	setGameMode(gameMode);
        switchScene(event, "/diceGame/fxml/oneDiceMode.fxml");
    }

    @FXML
    private void switchToTwoDiceMode(ActionEvent event) {
    	gameMode = 2;
    	setGameMode(gameMode);
        switchScene(event, "/diceGame/fxml/twoDiceMode.fxml");
    }

    @FXML
    private void switchToThreeDiceMode(ActionEvent event) {
    	gameMode = 3;
    	setGameMode(gameMode);
        switchScene(event, "/diceGame/fxml/threeDiceMode.fxml");
    }

    @FXML
    private void switchToFourDiceMode(ActionEvent event) {
    	gameMode = 4;
    	setGameMode(gameMode);
        switchScene(event, "/diceGame/fxml/fourDiceMode.fxml");
    }
    
    @FXML
    private void turnToMenu(ActionEvent event) {
    	resetGameMode();
        switchScene(event, "/menu/fxml/GameChoiceMenu.fxml");
    }
    
    public static void resetGameMode() {
    	gameMode = 0;
    }
    
    public static Integer getGameMode() {
    	return gameMode;
    }
    
    public static void setGameMode(Integer newMode) {
    	gameMode = newMode;
    }
}
