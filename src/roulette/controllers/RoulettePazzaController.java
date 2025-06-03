package roulette.controllers;
import java.io.IOException;

import fileManager.UserFileManager;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.Main;
import main.Player;
import main.SceneController;
public class RoulettePazzaController extends SceneController {
	private Boolean PlayerTurn = true;
	private Boolean isTransitionOver = false;
	public static Boolean isRicaricaAnimationOver = true;
	private static Player player = Main.getPlayer();
	
	public static Integer nGiriRevolver;
	public static Boolean amIDead;
	
	@FXML
	private Button startBtn;
	@FXML
	private ImageView RevolverBot;
	@FXML
	private ImageView RevolverIMG;
	@FXML
	private ImageView RevolverPlayer;
	@FXML
	private ImageView GunShotPlayer;
	@FXML
	private ImageView GunShotBot;
	@FXML
	private Button sparaBtn;
	@FXML
	private Button giraBtn;
	@FXML
	private Label lblBot;
	@FXML
	private Label lblPlayer;
	@FXML
	private ImageView bloodDrip; // ImageView per l'effetto di gocciolamento
	@FXML
	private BorderPane borderPane;
    @FXML
    private ImageView bloodSpatBot;
	
	private boolean buttonClicked = false;
	public void initialize() {
		// Imposta l'ImageView come invisibile all'inizio
		bloodDrip.setVisible(false);
		// Gestione dell'evento hover
		sparaBtn.setOnMouseEntered(event -> {
			isTransitionOver = false;
			if (!isTransitionOver) {
				bloodDrip.setVisible(true); // Mostra le gocce di sangue
				// Crea l'animazione
				TranslateTransition transition = new TranslateTransition(Duration.seconds(1), bloodDrip);
				transition.setFromY(40); // Parti dalla posizione iniziale
				transition.setToY(70); // Sposta le gocce verso il basso
				transition.setCycleCount(1); // Esegui l'animazione solo una volta
				transition.play(); // Avvia l'animazione
				transition.setOnFinished(event1 -> {
					isTransitionOver = true;
				});
			}
		});
		sparaBtn.setOnMouseExited(event -> {
			if (isTransitionOver) {
				// Riporta le gocce alla posizione iniziale e nascondile
				TranslateTransition transition = new TranslateTransition(Duration.seconds(0.5), bloodDrip);
				transition.setFromY(70); // Parti dalla posizione finale
				transition.setToY(40); // Riporta le gocce alla posizione iniziale
				transition.setOnFinished(e -> bloodDrip.setVisible(false)); // Nasconde le gocce alla fine //
																			// dell'animazione
				transition.play(); // Avvia l'animazione
			}
		});
	}
	@FXML
	private void giraRevolver(ActionEvent e) {
		if (buttonClicked) {
			return;
		}
		buttonClicked = true;
		lblPlayer.setStyle("-fx-text-fill: black;");
		lblBot.setStyle("-fx-text-fill: black;");
		RevolverIMG.setVisible(true);
		if (PlayerTurn) {
			startBtn.setVisible(false);
			RevolverBot.setVisible(false);
			// Reset dell'angolo di rotazione prima di applicare la nuova animazione
			RevolverIMG.setRotate(90);
			// Crea la transizione di rotazione
			RotateTransition rotateTransition = new RotateTransition(Duration.seconds(1), RevolverIMG);
			rotateTransition.setByAngle(-90 * 10);
			rotateTransition.setCycleCount(1);
			rotateTransition.setAutoReverse(false);
			rotateTransition.play();
			rotateTransition.setOnFinished(event -> {
				RevolverPlayer.setVisible(true);
				RevolverIMG.setVisible(false);
//				sparaBtn.setVisible(true);
				giraBtn.setVisible(true);
				lblPlayer.setStyle("-fx-text-fill: green;");
				lblBot.setStyle("-fx-text-fill: black;");
				buttonClicked = false;
			});
			PlayerTurn = false;
		} else {
			startBtn.setVisible(false);
			RevolverPlayer.setVisible(false);
//			sparaBtn.setVisible(false);
			giraBtn.setVisible(false);
			bloodDrip.setVisible(false);
			// Reset dell'angolo di rotazione prima di applicare la nuova animazione
			RevolverIMG.setRotate(-90);
			// Crea la transizione di rotazione
			RotateTransition rotateTransition = new RotateTransition(Duration.seconds(1), RevolverIMG);
			rotateTransition.setByAngle(90 * 10);
			rotateTransition.setCycleCount(1);
			rotateTransition.setAutoReverse(false);
			rotateTransition.play();
			rotateTransition.setOnFinished(event -> {
				RevolverBot.setVisible(true);
				RevolverIMG.setVisible(false);
				lblPlayer.setStyle("-fx-text-fill: black;");
				lblBot.setStyle("-fx-text-fill: green;");
				buttonClicked = false;
				
				RotateTransition rotateTransitionR = new RotateTransition(Duration.seconds(0.5), RevolverBot);
				rotateTransitionR.setByAngle(-360);
				rotateTransitionR.setCycleCount(1);
				rotateTransitionR.setAutoReverse(false);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				rotateTransitionR.play();
				
				rotateTransitionR.setOnFinished(evt ->{
					try {
						Thread.sleep(500);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					Boolean botDead;
					int rand = (int)(Math.random()*6)+1;
					int nScelto = (int)(Math.random()*6)+1;
//					System.out.println(rand + " " + nScelto ); //DEBUG
					if(rand == nScelto) {
						botDead = true;
					}else {
						botDead = true;//--------------------------------
					}
					if(botDead) {
						
						GunShotBot.setVisible(true);
						RotateTransition rotateTransitionD = new RotateTransition(Duration.seconds(0.1), RevolverBot);
						rotateTransitionD.setByAngle(17);
						rotateTransitionD.setCycleCount(1);
						rotateTransitionD.setAutoReverse(false);
						rotateTransitionD.play();
						RotateTransition rotateTransitionDB = new RotateTransition(Duration.seconds(0.1), GunShotBot);
						rotateTransitionDB.setByAngle(17);
						rotateTransitionDB.setCycleCount(1);
						rotateTransitionDB.setAutoReverse(false);
						rotateTransitionDB.play();
						
						rotateTransitionDB.setOnFinished(ev ->{
							GunShotBot.setVisible(false);
							try {
								Thread.sleep(100);
							} catch (InterruptedException ex) {
								ex.printStackTrace();
							}
							youWon(e);
						});
						
						bloodSpatBot.setVisible(true);
					}else{
						startBtn.setVisible(true);
						RevolverBot.setVisible(false);
					}
				});
			});
			PlayerTurn = true;
		}
	}
	@FXML
	private void gira(ActionEvent event) {
		giraBtn.setVisible(false);
		sparaBtn.setVisible(true);
		
		RoulettePazzaController.nGiriRevolver = (int)(Math.random()*6);
//		System.out.println("Il bot ha girato " + RoulettePazzaController.nGiriRevolver);
		
		switchSceneModal(event, "/roulette/fxml/Ricarica.fxml");
		
	}
	@FXML
	private void spara(ActionEvent event) {
		RotateTransition rotateTransition = new RotateTransition(Duration.seconds(0.5), RevolverPlayer);
		rotateTransition.setByAngle(-360);
		rotateTransition.setCycleCount(1);
		rotateTransition.setAutoReverse(false);
		rotateTransition.play();
		sparaBtn.setVisible(false);
		bloodDrip.setVisible(false);
		
		rotateTransition.setOnFinished(evt ->{
			if(amIDead) {
				GunShotPlayer.setVisible(true);
				RotateTransition rotateTransitionD = new RotateTransition(Duration.seconds(0.1), RevolverPlayer);
				rotateTransitionD.setByAngle(17);
				rotateTransitionD.setCycleCount(1);
				rotateTransitionD.setAutoReverse(false);
				rotateTransitionD.play();
				RotateTransition rotateTransitionDB = new RotateTransition(Duration.seconds(0.1), GunShotPlayer);
				rotateTransitionDB.setByAngle(17);
				rotateTransitionDB.setCycleCount(1);
				rotateTransitionDB.setAutoReverse(false);
				rotateTransitionDB.play();
				
				rotateTransitionDB.setOnFinished(ev ->{
					GunShotPlayer.setVisible(false);
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					youLost(event);
				});
				
			}else {
				startBtn.setVisible(true);
				RevolverPlayer.setVisible(false);
			}
		});
		
	}
	
	@FXML
	private void youLost(ActionEvent event) {
//	delete the folder with all files that have name of Player.username
		UserFileManager.deleteUserFolderIfExists(player.getName());
		
		switchScene(event, "/roulette/fxml/YouLost.fxml");
	}
	
	@FXML
	private void youWon(ActionEvent event) {
		switchScene(event, "/roulette/fxml/YouWon.fxml");
	}
	
}




