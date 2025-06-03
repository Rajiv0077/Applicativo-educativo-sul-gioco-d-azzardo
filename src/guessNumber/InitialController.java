package guessNumber;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import gameChart.GameChart;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button; // <--- Import corretto
import javafx.scene.control.Label;  // <--- Import corretto
import javafx.stage.Stage;
import main.SceneController;

public class InitialController extends SceneController {
    private Stage primaryStage;
    private GameChart gameChart;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private Button goBackBstn;

    @FXML
    private Label lblMoney;

    @FXML
    private Button showStats;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void goBack(ActionEvent event) {
        switchScene(event, "/menu/fxml/GameChoiceMenu.fxml");
    }

    @FXML
    void showStats(ActionEvent event) {
        if (gameChart != null && gameChart.isWindowOpen()) {
            gameChart.showGraphAlreadyOpenAlert();
        } else {
            gameChart = new GameChart();
            gameChart.showChartWindow();
        }
    }

    @FXML
    void handle1_10(ActionEvent event) {
        apriSchermataGioco(event, "1-10");
    }

    @FXML
    void handle1_15(ActionEvent event) {
        apriSchermataGioco(event, "1-15");
    }

    @FXML
    void handle1_20(ActionEvent event) {
        apriSchermataGioco(event, "1-20");
    }

    @FXML
    void initialize() {
    	
    }

    private void apriSchermataGioco(ActionEvent event, String intervallo) {
        GameController.setIntervallo(intervallo);
        switchScene(event, "/guessNumber/Game.fxml");
    }
}
