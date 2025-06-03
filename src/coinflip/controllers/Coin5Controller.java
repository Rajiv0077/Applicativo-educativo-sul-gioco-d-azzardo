/**
 * Sample Skeleton for 'TescrosSample.fxml' Controller Class
 */

package coinflip.controllers;
import javafx.animation.*;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import gameChart.GameChart;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point3D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.QuadCurve;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.SceneController;
import main.Player;
import main.Main;
import main.MoneyHandler;

import gameChart.ChartDataManager;
import gameChart.ChartInteractions;
public class Coin5Controller extends SceneController{
	
	

    private GameChart gameChart;
	@FXML 
	private BorderPane rootBorderPane;
	
	@FXML
    private Region casinoTable;
	
	@FXML
    private Cylinder coin1;
	
	@FXML
    private Cylinder coin2;
	
	@FXML
    private Cylinder coin3;
	
	@FXML
    private Cylinder coin4;
	
	@FXML
    private Cylinder coin5;
	

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="startButton"
    private Button startButton; // Value injected by FXMLLoader
    
    @FXML
    private Button backButton;

    @FXML // fx:id="Risultato"
    Label Risultato; // Value injected by FXMLLoader
    
    @FXML 
    Label yourBet;
    
    @FXML 
    Label Soldi;
    
 //---------------------------------------------------------------------------------------------------
    
    @FXML private Label resultLabel;
    
    public void showAnimatedResult(Label resultLabel, Integer amount) {
        // Imposta il testo
        if (amount >= 0) {
            resultLabel.setText("+" + amount );
            resultLabel.setTextFill(Color.GREEN);
        } else {
            resultLabel.setText(amount.toString()); // amount è già negativo
            resultLabel.setTextFill(Color.RED);
        }

        // Posizione iniziale (in basso)
        resultLabel.setTranslateY(20);
        resultLabel.setOpacity(0);

        // Animazione di salita + fade in
        TranslateTransition moveUp = new TranslateTransition(Duration.seconds(1), resultLabel);
        moveUp.setToY(-20); // si alza di 40px

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), resultLabel);
        fadeIn.setToValue(1.0);

        // Dopo un attimo, fade out
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), resultLabel);
        fadeOut.setDelay(Duration.seconds(2)); // aspetta un attimo
        fadeOut.setToValue(0);

        // Timeline combinata
        ParallelTransition showUp = new ParallelTransition(moveUp, fadeIn);
        SequentialTransition sequence = new SequentialTransition(showUp, fadeOut);
        sequence.play();
    }
    
    //-----------------------------------------------------------------------------------------------------------
    

    @FXML // fx:id="testaButton"
    private Button testaButton; // Value injected by FXMLLoader

    @FXML // fx:id="croceButton"
    private Button croceButton; // Value injected by FXMLLoader
    
    @FXML
    TextField BetSoldi;

    
    boolean scelta;
    boolean sceltaEffettuata = false;
    
    private List<Boolean> risultatiMonete = new ArrayList<>();


    private Random random = new Random();
    
    @FXML // fx:id="moneteChoiceBox"
    private ChoiceBox<Integer> moneteChoiceBox; // Value injected by FXMLLoader

    
    @FXML // fx:id="moneteChoiceBox"
    private ChoiceBox<Integer> scommessa; // Value injected by FXMLLoader
    
    private Integer[] n = {1, 2, 3, 4, 5, 6};
    
    private long corrette = 0;

   	private int molt = 0;
   	
   	
 // Chat BOT

  	@FXML
  	private Label botText;

  	@FXML
  	private Group messageGroup;

  	@FXML
  	private QuadCurve smile;

  	private boolean isSmiling = true;

  	private Integer startMoney = Main.player.getMoney();
  	private Integer spinningMoney;
  	private Integer finishMoney;

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
  		FadeTransition fadeTransition = new FadeTransition(Duration.millis(3000), messageGroup);
  		fadeTransition.setFromValue(1);
  		fadeTransition.setToValue(1);
  		
  		fadeTransition.setOnFinished(evnt -> {
  			hideMessageGroup();
  		});
  		fadeTransition.play();
  		
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
  			rotateTransition.setByAngle(180);
  			rotateTransition.setCycleCount(1);
  			rotateTransition.setAutoReverse(false);
  			rotateTransition.play();
  			isSmiling = smiling;
  		}
  	}

  	private String getMessage() {
  		String message;
  		if (isSmiling) {
  			message = lossMessages.get((int) (Math.random() * lossMessages.size() - 1));
  		} else {
  			message = winMessages.get((int) (Math.random() * winMessages.size() - 1));
  		}
  		return message;
  	}

  	private void showMessage() {
  		botText.setText(getMessage());
  		botText.setStyle("-fx-text-fill: black;");
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
     
   	
   	
   	
   	
    public void aggiornaSaldoAnimato() {
        int saldo = Main.player.getMoney();

        Timeline timeline = new Timeline();
        int saldoAttuale = getSaldoVisualeAttuale(); // ricavato dalla label
        int fine = saldo;

        int steps = Math.abs(fine - saldoAttuale);
        if (steps == 0) return;

        Duration durata = Duration.millis(800);
        for (int i = 0; i <= steps; i++) {
            int valore = (saldoAttuale < fine) ? saldoAttuale + i : saldoAttuale - i;

            KeyFrame frame = new KeyFrame(
                durata.multiply((double) i / steps),
                e -> Soldi.setText("$" + valore)
            );

            timeline.getKeyFrames().add(frame);
        }

        timeline.play();
    }
    
    private int getSaldoVisualeAttuale() {
        String text = Soldi.getText().replaceAll("[^0-9]", "");
        return text.isEmpty() ? 0 : Integer.parseInt(text);
    }
    

    @FXML
    void HandlecroceButton(ActionEvent event) {
        sceltaEffettuata = true;
        aggiornaStatoStartButton(); // <-- usa questa
        startAnimation(croceButton, testaButton);
        scelta = false;
    }
    
    private void animateCoin(Cylinder coin, Runnable onFinished) {
        coin.setMaterial(new PhongMaterial(Color.GOLD));

        RotateTransition rotateX = new RotateTransition(Duration.seconds(2), coin);
        rotateX.setByAngle(3600);
        rotateX.setAxis(new Point3D(1, 1, 1));

        RotateTransition rotateY = new RotateTransition(Duration.seconds(0.25), coin);
        rotateY.setByAngle(720);
        rotateY.setAxis(new Point3D(1, 0, 1));

        ScaleTransition zoomOut = new ScaleTransition(Duration.seconds(0.5), coin);
        zoomOut.setToX(0.25);
        zoomOut.setToY(0.25);
        zoomOut.setToZ(0.25);

        ScaleTransition zoomIn = new ScaleTransition(Duration.seconds(1), coin);
        zoomIn.setToX(1.5);
        zoomIn.setToY(1.5);
        zoomIn.setToZ(1.5);

        ScaleTransition zoomReset = new ScaleTransition(Duration.seconds(0.5), coin);
        zoomReset.setToX(1);
        zoomReset.setToY(1);
        zoomReset.setToZ(1);

        SequentialTransition a = new SequentialTransition(rotateX, rotateY);
        SequentialTransition b = new SequentialTransition(zoomIn, zoomReset);
        ParallelTransition c = new ParallelTransition(b, a);
        SequentialTransition d = new SequentialTransition(zoomOut, c);

        d.setOnFinished(event -> {
            boolean monetaValue = new Random().nextBoolean();
            risultatiMonete.add(monetaValue);
            if (monetaValue) {
                coin.setMaterial(new PhongMaterial(Color.GREEN));
            } else {
                coin.setMaterial(new PhongMaterial(Color.RED));
            }
            onFinished.run();
        });

        d.play();
    }


    @FXML
    private void HandlestartButton() {

        

        int scommessaVal;
        try {
            scommessaVal = Integer.parseInt(BetSoldi.getText());
        } catch (NumberFormatException e) {
            Risultato.setText("ENTER A VALID NUMBER");
            Risultato.setStyle("-fx-text-fill: red;");
            Risultato.setStyle("-fx-font-size: 12;");
            return;
        }

        if (scommessaVal <= 0 || scommessaVal > Main.player.getMoney()) {
            Risultato.setText("ENTER A VALID NUMBER");
            Risultato.setStyle("-fx-text-fill: red;");
            Risultato.setStyle("-fx-font-size: 12;");
            return;
        }

        Risultato.setVisible(false);
        risultatiMonete.clear();

        List<Cylinder> coins = Arrays.asList(coin1, coin2, coin3, coin4, coin5);
        final int totalCoins = coins.size();
        final int[] finished = {0};

        for (Cylinder coin : coins) {
            animateCoin(coin, () -> {
                finished[0]++;
                if (finished[0] == totalCoins) {
                    // Tutte le animazioni sono terminate
                    corrette = risultatiMonete.stream().filter(r -> r == scelta).count();
                    guadagno(); // calcola il valore di 'molt'

                    int vincitaPerdita = casi(scommessaVal);

                    // Gestione vincita o perdita
                    if (molt == 0) {
                        Main.player.incrementGamesWon();
                        Main.player.addMoney(scommessaVal + vincitaPerdita);
                        playerWon();
                        showAnimatedResult(resultLabel, scommessaVal + vincitaPerdita);
                    } else {
                        Main.player.incrementGamesLost();
                        Main.player.subtractMoney(vincitaPerdita);
                        playerLost();
                        showAnimatedResult(resultLabel,-vincitaPerdita);
                    }

                    Main.player.incrementGamesPlayed();
                    aggiornaSaldoAnimato();
                    gameChart.addData(Main.player.getGamesPlayed(), Main.player.getMoney());

                    Risultato.setText(corrette + "/" + risultatiMonete.size());
                    Risultato.setStyle("-fx-text-fill: white;");
                    Risultato.setStyle("-fx-font-size: 36;");
                    Risultato.setVisible(true);
                    testaButton.setVisible(true);
                    croceButton.setVisible(true);
                    startButton.setDisable(true);
                }
            });
        }
        
    }
    
    @FXML
    void HandlebackButton(ActionEvent event) {
    	
    	switchScene(event, "/menu/fxml/GameChoiceMenu.fxml");

    	
    }
    
    @FXML
    public void HandleChartButton(ActionEvent event) {
    	
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
    void HandletestaButton(ActionEvent event) {
        sceltaEffettuata = true;
        aggiornaStatoStartButton(); // <-- usa questa
        scelta = true;
        startAnimation(testaButton, croceButton);
    }
    
    private void startAnimation(Button clickedButton, Button otherButton) {
        // Nasconde l'altro bottone
        otherButton.setVisible(false);
    }
    
    private void aggiornaStatoStartButton() {
        if (sceltaEffettuata && scommessa.getValue() != null) {
            startButton.setDisable(false);
        } else {
            startButton.setDisable(true);
        }
    }
    
    @FXML
    void Change(ActionEvent event) {
    	
Integer x = moneteChoiceBox.getValue();
    	
    	switch (x) {
    	case 1:
        	switchScene(event, "/coinflip/fxml/Coin1.fxml");

    		break;
    	case 2:
        	switchScene(event, "/coinflip/fxml/Coin2.fxml");

    		break;
    	case 3:
        	switchScene(event, "/coinflip/fxml/Coin3.fxml");

    		break;
    	case 4:
        	switchScene(event, "/coinflip/fxml/Coin4.fxml");

    		break;
    	case 5:
        	switchScene(event, "/coinflip/fxml/Coin5.fxml");

    		break;
    	case 6:
        	switchScene(event, "/coinflip/fxml/Coin6.fxml");

    		break;
    	
    	}

    }
    
    @FXML
    void ScommessaChange(ActionEvent event) {
    	Integer y = scommessa.getValue();
    	switch  (y) {
    	case 1:
    		yourBet.setText(1 + "/" + 5);

    		break;
    	case 2:
    		yourBet.setText(2 + "/" + 5);

    		break;
    	case 3:
    		yourBet.setText(3 + "/" + 5);

    		break;
    	case 4:
    		yourBet.setText(4 + "/" + 5);

    		break;
    	case 5:
    		yourBet.setText(5 + "/" + 5);

    		break;
    	}
    	
    }
    
    public int getCorretteInt() {
        return (int) corrette;
    }
    
    void guadagno( ) {
    	Integer n = scommessa.getValue();
    	int ris = getCorretteInt();
    	
    	molt = Math.abs(n - ris);
    }
    
 public int casi (int a) {
    	
    	switch (molt) {
    	case 0:
    		a = (int) Math.round(a * 1.2);
    		break;
    	case 1:
    		a =  (int) Math.round(a * 0.75);

    		break;
    	case 2:
    		a =  (int) Math.round(a * 0.8);

    		break;
    	case 3:
    		a =  (int) Math.round(a * 0.9);

    		break;
    	case 4:
    		a =  (int) Math.round(a * 0.95);
    		
    		break;
    	case 5:
    		a =  (int) Math.round(a * 1);
    		
    		break;
    	}
		return a;
    }
    
   
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
    	startButton.setDisable(true);
    	coin1.setMaterial(new PhongMaterial(javafx.scene.paint.Color.GOLD));
    	coin1.getTransforms().add(new Rotate(90, Rotate.X_AXIS));
    	coin2.setMaterial(new PhongMaterial(javafx.scene.paint.Color.GOLD));
    	coin2.getTransforms().add(new Rotate(90, Rotate.X_AXIS));
    	coin3.setMaterial(new PhongMaterial(javafx.scene.paint.Color.GOLD));
    	coin3.getTransforms().add(new Rotate(90, Rotate.X_AXIS));
    	coin4.setMaterial(new PhongMaterial(javafx.scene.paint.Color.GOLD));
    	coin4.getTransforms().add(new Rotate(90, Rotate.X_AXIS));
    	coin5.setMaterial(new PhongMaterial(javafx.scene.paint.Color.GOLD));
    	coin5.getTransforms().add(new Rotate(90, Rotate.X_AXIS));
    	moneteChoiceBox.getItems().addAll(n);
    	scommessa.getItems().clear();
    	List<Cylinder> coins = Arrays.asList(coin1, coin2, coin3, coin4, coin5); // oppure una lista già esistente se l’hai da un’altra parte
    	for (int i = 1; i <= coins.size(); i++) {
    	    scommessa.getItems().add(i);
    	}        assert startButton != null : "fx:id=\"startButton\" was not injected: check your FXML file 'TescrosSample.fxml'.";
        assert Risultato != null : "fx:id=\"Risultato\" was not injected: check your FXML file 'TescrosSample.fxml'.";
        assert testaButton != null : "fx:id=\"testaButton\" was not injected: check your FXML file 'TescrosSample.fxml'.";
        assert croceButton != null : "fx:id=\"croceButton\" was not injected: check your FXML file 'TescrosSample.fxml'.";
        Soldi.setText("$" + Main.player.getMoney().toString());
        yourBet.setText("/" + 5);
        gameChart = new GameChart();
        
        scommessa.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            aggiornaStatoStartButton();
        });
        
    }
}
