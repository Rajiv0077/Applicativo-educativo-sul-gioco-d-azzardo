package roulette.controllers;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
public class RicaricaController {
	@FXML
	private Group sezioniGroup; // Group che contiene le sezioni e i numeri
	@FXML
	private Group comandiGroup; // Group che contiene le sezioni e i numeri
	@FXML
	private Circle proiettile;
	@FXML
	private Button spinCounterBtn;
	private Integer spinCounter = 0;
	private Integer nGiriBot = 0;
	private Boolean buttonClicked = false;
	private Boolean counterClicked = false;
	@FXML
	private Button ricaricaBtn;
	private double angoloInizialeProiettile = 0; // Angolo iniziale del proiettile
	// Angoli di ogni slot (6 slot, ogni slot è a 60 gradi di distanza)
	private final double[] angoliSlot = { 0.0, // Slot 1 (Proiettile)
			60.0, // Slot 2
			120.0, // Slot 3
			180.0, // Slot 4
			240.0, // Slot 5
			300.0 // Slot 6
	};
	private int posizioneCorrente = 0; // Posizione corrente dello slot (0 = Slot 1)
	private int posizioneFinale = 0; // Posizione corrente dello slot (0 = Slot 1)
	private double angoloRotazione;
	
	@FXML
	private Group groupSlot1;// ha il proiettile
	
	@FXML
	private Group groupSlot2;
	@FXML
	private Group groupSlot3;
	
	@FXML
	private Group groupSlot4;
	
	@FXML
	private Group groupSlot5;
	@FXML
	private Group groupSlot6;
	
	@FXML
	private void initialize() {
		groupSlot1.setVisible(true);
		groupSlot2.setVisible(true);
		groupSlot3.setVisible(true);
		groupSlot4.setVisible(true);
		groupSlot5.setVisible(true);
		groupSlot6.setVisible(true);
		// Memorizza l'angolo iniziale del proiettile rispetto alla ruota
		angoloInizialeProiettile = proiettile.getRotate();
		nGiriBot = RoulettePazzaController.nGiriRevolver;
		posizioneFinale = (posizioneCorrente + nGiriBot) % 6;
		// Calcola l'angolo di rotazione in base alla posizione finale
//		double angoloRotazione = calcolaAngoloRotazione(posizioneFinale);
		RotateTransition rotateTransition = new RotateTransition(Duration.seconds(1), sezioniGroup); // Durata ridotta a
		// 1 secondo
		rotateTransition.setByAngle(360 * 2 + 0); // Ruota 2 volte + l'angolo desiderato
		rotateTransition.setCycleCount(1);
		rotateTransition.setAutoReverse(false);
		rotateTransition.play();
		sezioniGroup.setRotate(0);
		posizioneCorrente = posizioneFinale;
//		System.out.println("Posizione corrente: " + posizioneCorrente);
		rotateTransition.setOnFinished(event -> {
			sezioniGroup.setRotate(0);
			comandiGroup.setVisible(true);
		});
	}
	@FXML
	private void increaseCounter(ActionEvent event) {
		if(!counterClicked) {
			counterClicked = true;
		if (spinCounter < 9) {
			ricaricaBtn.setDisable(false);
			spinCounter++;
			spinCounterBtn.setText("x" + spinCounter.toString());
		} else {
			spinCounter = 1;
			spinCounterBtn.setText("x" + spinCounter.toString());
		}
		RotateTransition rotateTransition = new RotateTransition(Duration.seconds(0.5), sezioniGroup);
		rotateTransition.setByAngle(60);
		rotateTransition.setCycleCount(1);
		rotateTransition.setAutoReverse(false);
		rotateTransition.play();
		
		rotateTransition.setOnFinished(evt ->{
			counterClicked = false;
		});
		}
	}
	@FXML
	private void ricarica() {
		if (buttonClicked) {
			return;
		}
		buttonClicked = true;
		// Calcola la posizione finale in base allo spinCounter
		posizioneFinale = (posizioneCorrente + spinCounter) % 6;
//		System.out.println("posizione finale " + posizioneFinale);
		// Calcola l'angolo di rotazione in base alla posizione finale
		switch (posizioneCorrente) {
		case 0:
			angoloRotazione = calcolaAngoloRotazione(posizioneFinale);
			break;
		case 1:
			angoloRotazione = calcolaAngoloRotazione(5);
			break;
		case 2:
			angoloRotazione = calcolaAngoloRotazione(4);
			break;
		case 3:
			angoloRotazione = calcolaAngoloRotazione(3);
			break;
		case 4:
			angoloRotazione = calcolaAngoloRotazione(2);
			break;
		case 5:
			angoloRotazione = calcolaAngoloRotazione(1);
			break;
		}
		// Animazione di ROTAZIONE della roulette
		RotateTransition rotateTransition = new RotateTransition(Duration.seconds(1), sezioniGroup); // Durata ridotta a
																										// 1 secondo
		rotateTransition.setByAngle(360 * 2 + angoloRotazione); // Ruota 2 volte + l'angolo desiderato
		rotateTransition.setCycleCount(1);
		rotateTransition.setAutoReverse(false);
		// Movimento CIRCOLARE in alto a destra
		TranslateTransition moveTransition = new TranslateTransition(Duration.seconds(1), sezioniGroup); // Durata
																											// ridotta a
																											// 1 secondo
		moveTransition.setByX(199.5); // Sposta a destra
		moveTransition.setByY(-119); // Sposta in alto
		moveTransition.setCycleCount(1);
		// ParallelTransition per eseguire entrambe le animazioni contemporaneamente
		ParallelTransition parallelTransition = new ParallelTransition(rotateTransition, moveTransition);
		
		parallelTransition.play(); // Avvia la transizione parallela
		
		groupSlot1.setVisible(false);
		groupSlot2.setVisible(false);
		groupSlot3.setVisible(false);
		groupSlot4.setVisible(false);
		groupSlot5.setVisible(false);
		groupSlot6.setVisible(false);
		
		parallelTransition.setOnFinished(event -> {
			// Aggiorna la posizione corrente
			posizioneCorrente = posizioneFinale;
			// Calcola l'angolo finale della rotazione
			double angoloFinale = sezioniGroup.getRotate() % 360; // Normalizza l'angolo tra 0 e 360
			// Determina se il proiettile è in canna (solo se la posizione finale è 3, cioè
			// Slot 4)
			boolean proiettileInCanna = (posizioneFinale == 0);
			if(proiettileInCanna) {
				RoulettePazzaController.amIDead = true;
			}else {
				RoulettePazzaController.amIDead = false;
			}
			// Stampa il risultato nella console
//			System.out.println("Spin Counter: " + spinCounter);
//			System.out.println("Angolo finale: " + angoloFinale + " gradi");
//			System.out.println("Posizione finale: " + posizioneFinale);
//			System.out.println("Proiettile in canna: " + (proiettileInCanna ? "Sì" : "No"));
			// Chiudi la finestra (opzionale)
			
			Stage stage = (Stage) sezioniGroup.getScene().getWindow();
			Stage mainStage = (Stage) stage.getOwner();
			stage.close();
			
			// Ripristina il fullscreen sullo stage principale
			Platform.runLater(() -> {
				if (mainStage != null) {
					mainStage.setFullScreen(true);
				}
			});
		});
	}
	private double calcolaAngoloRotazione(int posizioneFinale) {
		// Angolo dello slot corrispondente alla posizione finale
		double angoloSlot = angoliSlot[posizioneFinale];
		// Calcola l'angolo di rotazione necessario per raggiungere lo slot desiderato
		return angoloSlot - angoloInizialeProiettile;
	}
}




