package guessNumber;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.QuadCurve;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.Main;
import main.SceneController;

import java.util.ArrayList;
import java.util.Arrays;

import gameChart.GameChart;

public class GameController extends SceneController {
    private Stage primaryStage;
    private GameChart gameChart;
    
    @FXML
    private GridPane numeriContainer;

    @FXML
    private TextField InputBet;

    @FXML
    private Button btnPlay;

    @FXML
    private Text lblBet;

    @FXML
    private Label lblMoney;

    @FXML
    private Label lblMultiplier;
    
    @FXML
    private Button goBackBtn;
    
    @FXML
    private Button btnStats;
    
    @FXML
    private Label lblResult;
    
    @FXML
    private Button goBackBstn;
    
    @FXML
    private Button btnReplay;

    @FXML
 	private Label botText;

 	@FXML
 	private Group messageGroup;

 	@FXML
 	private QuadCurve smile;

 	private boolean isSmiling = true;

 	private ArrayList<String> winMessages = new ArrayList<String>(Arrays.asList("You got lucky this time",
 			"Try again, I'll take your money", "Luck,luck,luck, it won't last forever", "1 more game?",
 			"Wow, you won... again", "The house always wins... except this time", "Don't get used to it, champ",
 			"Even a broken clock is right twice a day", "The odds were 0.1%... today was your day, huh?",
 			"Winner winner chicken dinner! …Too bad it's virtual.",
 			"Alert: System error detected. Just kidding… or am I?"));

 	private ArrayList<String> lossMessages = new ArrayList<String>(Arrays.asList(
 			"Try again, maybe you'll win... or maybe not", "Congratulations! You lost $", "HAHAHAHA my money now",
 			"If we had a scoreboard of the top losers you'll be the 1st",
 			"Your wallet is crying, and so am I... from laughter", "At this rate, you'll need a second mortgage",
 			"Was that your strategy? Bold choice!", "Pro tip: Quitting now saves you money. Just saying.",
 			"I'd say 'better luck next time', but we both know the answer.",
 			"Thanks for the donation. My yacht is almost paid off."));
 	
 	private ArrayList<String> noProfitMessages = new ArrayList<String>(Arrays.asList(
 		    "Wow. Such excitement. Much thrill. Zero profit.", // Riferimento al meme Doge
 		    "The universe is in balance... and so is your wallet.", 
 		    "Not a win, not a loss... just pure existential dread.",
 		    "Congratulations! You've achieved the financial equivalent of watching paint dry.",
 		    "The house edge remains intact. You? Meh.","Really? All of that for nothing..."
 		));
    private int min;
    private int max;
    private GuessNumber g = new GuessNumber();
    private static String intervallo = "1-15";
    private int numerosegreto;

    public GameController() {
        this.gameChart = new GameChart();
    }

    public static void setIntervallo(String interval) {
        intervallo = interval;
    }
    
    @FXML
    void start(ActionEvent event) {
    	   btnReplay.setVisible(false);
    	   lblResult.setVisible(false);
    	   lblMultiplier.setText("");
    	    lblMultiplier.setStyle("-fx-text-fill: #00c853;");
        String[] parts = intervallo.split("-");
        min = Integer.parseInt(parts[0]);
        max = Integer.parseInt(parts[1]);
        g = new GuessNumber(max, min);

        if (InputBet.getText().isEmpty()) {
            lblBet.setText("Enter a valid bet!");
            lblBet.setVisible(true);
            return;
        }

        try {
            int bet = Integer.parseInt(InputBet.getText());

            if (bet > Main.player.getMoney()) {
                lblBet.setText("Insufficient funds!");
                lblBet.setVisible(true);
                return;
            }

            g.setBet(bet);
           // int newbudget = Main.player.getMoney() - g.getBet();
            //Main.player.setMoney(newbudget);
            
          

            lblBet.setVisible(false);
            btnPlay.setVisible(false);
            InputBet.setVisible(false);

            numerosegreto = g.generateRandomNumber();
            System.out.println(numerosegreto);
            aggiungiNumeri(min, max);
            showMoney();

        } catch (NumberFormatException e) {
            lblBet.setText("Enter a valid number!");
            lblBet.setVisible(true);
        }
    }
    

    @FXML
    private void setBetFromButton(ActionEvent event) {
        Button clicked = (Button) event.getSource();
        String value = clicked.getText().replace("$", "").trim();
        InputBet.setText(value);
    }
    
    @FXML
    void backToSettings(ActionEvent event) {
    	 switchScene(event, "/guessNumber/InitialPage.fxml");
    }
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

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    private void aggiungiNumeri(int min, int max) {
        numeriContainer.getChildren().clear();
        numeriContainer.getColumnConstraints().clear();
        numeriContainer.getRowConstraints().clear();

        int colonne = 5;
        int righe = (int) Math.ceil((max - min + 1) / (double) colonne);

        for (int i = 0; i < colonne; i++) {
            numeriContainer.getColumnConstraints().add(new javafx.scene.layout.ColumnConstraints());
        }
        for (int i = 0; i < righe; i++) {
            numeriContainer.getRowConstraints().add(new javafx.scene.layout.RowConstraints());
        }

        for (int i = min; i <= max; i++) {
            Button numeroButton = new Button(String.valueOf(i));
            numeroButton.setPrefSize(80, 80);
            numeroButton.setStyle("-fx-font-size: 18px; -fx-background-color: lightblue; -fx-border-color: black;");
            numeroButton.setOnAction(event -> gestisciTentativo(numeroButton));

            int col = (i - min) % colonne;
            int row = (i - min) / colonne;
            numeriContainer.add(numeroButton, col, row);
        }
    }

    private void gestisciTentativo(Button numeroButton) {
        // Disabilita temporaneamente tutti i pulsanti durante l'animazione
        numeriContainer.getChildren().forEach(node -> {
            if (node instanceof Button) {
                node.setMouseTransparent(true);  // Blocca input ma mantiene l'aspetto visivo
            }
        });

        int numeroScelto = Integer.parseInt(numeroButton.getText());
        numeroButton.toFront();

        ScaleTransition enlarge = new ScaleTransition(Duration.millis(500), numeroButton);
        enlarge.setToX(1.5);
        enlarge.setToY(1.5);

        RotateTransition rotate = new RotateTransition(Duration.millis(500), numeroButton);
        rotate.setAxis(Rotate.Y_AXIS);
        rotate.setFromAngle(0);
        rotate.setToAngle(90);

        ScaleTransition shrink = new ScaleTransition(Duration.millis(500), numeroButton);
        shrink.setToX(1);
        shrink.setToY(1);

        if (g.verifyNumber(numeroScelto)) {
            enlarge.setOnFinished(e -> {
                rotate.setOnFinished(ev -> {
                    numeroButton.setText("✅");
                    numeroButton.setStyle("-fx-font-size: 18px; -fx-background-color: lightgreen; -fx-border-color: black;");
                    
                    RotateTransition rotateBack = new RotateTransition(Duration.millis(500), numeroButton);
                    rotateBack.setAxis(Rotate.Y_AXIS);
                    rotateBack.setFromAngle(90);
                    rotateBack.setToAngle(0);
                    
                    rotateBack.setOnFinished(ev2 -> {
                        shrink.play();
                        shrink.setOnFinished(event -> {
                            // Riabilita l'interazione solo per questo pulsante
                            numeroButton.setMouseTransparent(false);
                            numeroButton.setDisable(true);
                            
                            g.calculateFinalBudget();
                            Main.player.incrementGamesWon();
                            showMoney();
                            stopGame();
                        });
                    });
                    rotateBack.play();
                });
                rotate.play();
            });
            enlarge.play();
        } else {
            enlarge.setOnFinished(e -> {
                rotate.setOnFinished(ev -> {
                    numeroButton.setText("❌");
                    numeroButton.setStyle("-fx-font-size: 18px; -fx-background-color: lightcoral; -fx-border-color: black;");
                    
                    RotateTransition rotateBack = new RotateTransition(Duration.millis(500), numeroButton);
                    rotateBack.setAxis(Rotate.Y_AXIS);
                    rotateBack.setFromAngle(90);
                    rotateBack.setToAngle(0);
                    
                    rotateBack.setOnFinished(ev2 -> {
                        shrink.play();
                        shrink.setOnFinished(event -> {
                            // Riabilita l'interazione per tutti i pulsanti tranne questo
                            numeriContainer.getChildren().forEach(node -> {
                                if (node instanceof Button && node != numeroButton) {
                                    node.setMouseTransparent(false);
                                }
                            });
                            numeroButton.setDisable(true);
                        });
                    });
                    rotateBack.play();
                });
                rotate.play();
            });
            enlarge.play();
        }

        showMultiplier();
    }
    
    

    public void showMultiplier() {
        double updated = g.updateMultiplier();
        System.out.println(g.getMultiplier());
        if(g.getMultiplier()<0.0) {
        	lblMultiplier.setStyle("-fx-text-fill: red;");
        }
        lblMultiplier.setText(String.valueOf(g.getMultiplier()));
    }

    public void showMoney() {
        int currentMoney = Main.player.getMoney();
        lblMoney.setText(String.valueOf(currentMoney));

        // Animazione tremolio orizzontale
        TranslateTransition shake = new TranslateTransition(Duration.millis(100), lblMoney);
        shake.setFromX(-5);
        shake.setToX(5);
        shake.setCycleCount(6);
        shake.setAutoReverse(true);

        // Animazione scala
        ScaleTransition scale = new ScaleTransition(Duration.millis(150), lblMoney);
        scale.setToX(1.2);
        scale.setToY(1.2);
        scale.setCycleCount(2);
        scale.setAutoReverse(true);

        // Imposta colore in base al risultato del gioco
        if (g.isGameWinnedOrLosed()) {
            lblMoney.setStyle("-fx-text-fill: #00c853; -fx-font-size: 40px"); // verde acceso se vinto
        } else {
            lblMoney.setStyle("-fx-text-fill: red; -fx-font-size: 40px"); // rosso se perso
        }

        // Torna al colore bianco dopo 1 secondo
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(e -> lblMoney.setStyle("-fx-text-fill: white; -fx-font-size: 40px")); // colore finale: bianco
        pause.play();

        // Avvia animazioni
        shake.play();
        scale.play();
    }



    public void showResult() {
        numeriContainer.getChildren().clear();
        numeriContainer.getColumnConstraints().clear();
        numeriContainer.getRowConstraints().clear();
        showMoney();
        System.out.println(" sono dentro");
        
        // Modifica qui: rimuovi il setVisible(false)
        if(g.isGameWinnedOrLosed()) {
        	lblResult.setStyle("-fx-text-fill : white; -fx-font-size: 61px;");
            lblResult.setText("You won! ");
            System.out.println(" hai vinto ");
            playerWon();
        } else {
        	playerLost();
        	lblResult.setStyle("-fx-text-fill : red; -fx-font-size: 61px; ");
        	System.out.println(" hai perso ");
            lblResult.setText("You lost! ");
        }
        lblResult.setVisible(true); // Solo questo setVisible deve rimanere
    }
    
    private void stopGame() {
        numeriContainer.getChildren().forEach(node -> {
            if (node instanceof Button) {
                ((Button) node).setDisable(true);
            }
            
        });
        Main.player.incrementGamesPlayed();
		gameChart.addData(Main.player.getGamesPlayed(), Main.player.getMoney());
		 showResult();
		 System.out.println(" il bottone e' " + btnReplay);
		 btnReplay.setVisible(true);
		 btnReplay.setManaged(true); // utile se viene nascosto dallo spacing/layout

    }
    
 	private void playerWon() {
 		turnSmile(false);
 		showMessage();
 		FadeTransition fadeTransition = new FadeTransition(Duration.millis(6000), messageGroup);
 		fadeTransition.setFromValue(1);
 		fadeTransition.setToValue(1);

 		fadeTransition.setOnFinished(evnt -> {
 			hideMessageGroup();
 		});
 		fadeTransition.play();

 	}
 	private void playerDraw() {
 		showDrawMessage();
 		/*FadeTransition fadeTransition = new FadeTransition(Duration.millis(6000), messageGroup);
 		fadeTransition.setFromValue(1);
 		fadeTransition.setToValue(1);
 		
 		fadeTransition.setOnFinished(evnt -> {
 			hideMessageGroup();
 		});
 		fadeTransition.play();*/
 		
 	}

 	private void playerLost() {
 		turnSmile(true);
 		showMessage();
 		FadeTransition fadeTransition = new FadeTransition(Duration.millis(6000), messageGroup);
 		fadeTransition.setFromValue(1);
 		fadeTransition.setToValue(1);

 		fadeTransition.setOnFinished(evnt -> {
 			hideMessageGroup();
 		});
 		fadeTransition.play();
 	}

 	private void turnSmile(Boolean smiling) {
 	    if (isSmiling != smiling) {
 	        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(0.01), smile);
 	        rotateTransition.setByAngle(smiling ? 180 : -180);  // Ruota in base allo stato desiderato
 	        rotateTransition.setCycleCount(1);
 	        rotateTransition.setAutoReverse(false);
 	        rotateTransition.play();
 	        isSmiling = smiling;
 	    }
 	}

 	private String getMessage() {
 	    String message;
 	    if (isSmiling) {
 	        message = lossMessages.get((int) (Math.random() * lossMessages.size()));
 	    } else {
 	        message = winMessages.get((int) (Math.random() * winMessages.size()));
 	    }
 	    return message;
 	}

 	private void showMessage() {
 		botText.setText(getMessage());
 		botText.setStyle("-fx-background-color: white;");
 		messageGroup.setVisible(true);
 		messageGroup.setOpacity(1);
 	}
 	
 	private void showDrawMessage() {
 		botText.setText(noProfitMessages.get((int) (Math.random() * noProfitMessages.size() - 1)));
 		messageGroup.setVisible(true);
 		messageGroup.setOpacity(1);
 	}

 	private void hideMessageGroup() {
 		FadeTransition fadeTransition = new FadeTransition(Duration.millis(1500), messageGroup);
 		fadeTransition.setFromValue(1);
 		fadeTransition.setToValue(0);
 		fadeTransition.setOnFinished(evnt -> messageGroup.setVisible(false));
 		fadeTransition.play();
 	}
    
    
 
    @FXML
    void initialize() {
        btnReplay.setVisible(false);
        gameChart = new GameChart();
        showMoney();
      // Nasconde il bottone all'avvio
    }

}