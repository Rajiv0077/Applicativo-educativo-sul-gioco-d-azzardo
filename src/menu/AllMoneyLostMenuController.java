package menu;

import fileManager.UserFileManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import main.*;

public class AllMoneyLostMenuController extends SceneController{
	
	public static Player player = Main.getPlayer();
	private UserFileManager fileManager = new UserFileManager();
	
    @FXML
    public void turnToSwitch(ActionEvent event) {
       switchScene(event, "/menu/fxml/ModeMenu.fxml");
    }

    @FXML
    private void exitCasino() {
    	fileManager.updateFileContent(player.getMoney().toString(), "money.txt");
        fileManager.updateFileContent(player.getGamesPlayed().toString(), "gamesPlayed.txt");
        fileManager.updateFileContent(player.getGamesWon().toString(), "gamesWon.txt");
        fileManager.updateFileContent(player.getGamesLost().toString(), "gamesLost.txt");
        fileManager.updateFileContent(player.getHighestEarning().toString(), "highestEarning.txt");
        
    	javafx.application.Platform.exit();
    }
    
    @FXML
    private Button btnContinuePlay;

    @FXML
    private Button btnExit;

    @FXML
    public void initialize() {
  
    }
}