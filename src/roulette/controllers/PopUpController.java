package roulette.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import main.SceneController;

public class PopUpController extends SceneController {
	@FXML
	private Button siButton;
	@FXML
	private Button noButton;

	@FXML
	public void initialize() {
		siButton.setStyle("-fx-font-size: 50px; -fx-alignment: center;");
		noButton.setStyle("-fx-font-size: 50px; -fx-alignment: center;");
	}

	@FXML
	void si(ActionEvent event) throws IOException {
		switchScene(event, "/roulette/fxml/RoulettePazza.fxml");
	}

	@FXML
	void no(ActionEvent event) {
		switchScene(event, "/menu/fxml/GameChoiceMenu.fxml");
	}
}
