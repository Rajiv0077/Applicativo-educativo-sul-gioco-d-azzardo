/**
- * Sample Skeleton for 'TescrosSample.fxml' Controller Class
 */

package coinflip.controllers;
import javafx.animation.*;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.ResourceBundle;
import gameChart.GameChart;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.QuadCurve;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import main.*;

public class Coin1Controller  extends SceneController{
	

	private static Random random = new Random();
    private GameChart gameChart;
	
	@FXML 
	private BorderPane rootBorderPane;
	
	@FXML
    private Cylinder coinCylinder;
	
	@FXML
    private Region casinoTable;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="startButton"
    private Button startButton; // Value injected by FXMLLoader

    @FXML
    private Button backButton;
    
    @FXML 
    private Button ChartBUtton;
    
    @FXML // fx:id="Risultato"
    Label Risultato; // Value injected by FXMLLoader
    
    
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
    @FXML 
    TextField BetSoldi;
    
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


    @FXML // fx:id="testaButton"
    private Button testaButton; // Value injected by FXMLLoader

    @FXML // fx:id="croceButton"
    private Button croceButton; // Value injected by FXMLLoader

    
    boolean scelta;
    boolean sceltaEffettuata = false;
    
    @FXML // fx:id="moneteChoiceBox"
    private ChoiceBox<Integer> moneteChoiceBox; // Value injected by FXMLLoader

    
    @FXML // fx:id="moneteChoiceBox"
    private ChoiceBox<Integer> scommessa; // Value injected by FXMLLoader
    
    private Integer[] n = {1, 2, 3, 4, 5, 6};

    
    
 // Chat BOT

 	@FXML
 	private Label botText;

 	@FXML
 	private Group messageGroup;

 	@FXML
 	private QuadCurve smile;

 	private boolean isSmiling = true;

 	//private Integer startMoney = Main.player.getMoney();
 	//private Integer spinningMoney;
 	//private Integer finishMoney;

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
 		FadeTransition fadeTransition = new FadeTransition(Duration.millis(6000), messageGroup);
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
    
    
    
    

    @FXML
    void HandlecroceButton(ActionEvent event) {
    	sceltaEffettuata = true;  // Imposta la scelta come effettuata
        startButton.setDisable(false);
    	startAnimation(croceButton, testaButton);
    	scelta = false;
    	  
    	
    }	

    @FXML
    private void HandlestartButton() {
    	
    	
    	int scommessa;

        try {
            scommessa = Integer.parseInt(BetSoldi.getText());
        } catch (NumberFormatException e) {
            Risultato.setText("ENTER A VALID NUMBER");
            Risultato.setStyle("-fx-text-fill: red;");
            Risultato.setStyle("-fx-font-size: 12;");
            return;
        }

        if (scommessa <= 0 || scommessa > Main.player.getMoney()) {
            Risultato.setText("ENTER A VALID NUMBER");
            Risultato.setStyle("-fx-text-fill: red;");
            Risultato.setStyle("-fx-font-size: 12;");
            return;
        }
    	 
    	Risultato.setVisible(false);
    	coinCylinder.setMaterial(new PhongMaterial(javafx.scene.paint.Color.GOLD));
    	RotateTransition rotateX = new RotateTransition(Duration.seconds(2), coinCylinder);
        rotateX.setByAngle(3600);
        rotateX.setAxis(javafx.geometry.Point3D.ZERO.add(1, 1, 1));
        
        
        

        RotateTransition rotateY = new RotateTransition(Duration.seconds(0.25), coinCylinder);
        rotateY.setByAngle(720);
        rotateY.setAxis(javafx.geometry.Point3D.ZERO.add(1, 0, 1));
        
        ScaleTransition zoomOut = new ScaleTransition(Duration.seconds(0.5), coinCylinder);
        zoomOut.setToX(0.25);
        zoomOut.setToY(0.25);
        zoomOut.setToZ(0.25);

        // **Zoom avanti (ingrandimento)**
        ScaleTransition zoomIn = new ScaleTransition(Duration.seconds(1), coinCylinder);
        zoomIn.setToX(1.5);
        zoomIn.setToY(1.5);
        zoomIn.setToZ(1.5);

        // **Ritorno alla grandezza originale**
        ScaleTransition zoomReset = new ScaleTransition(Duration.seconds(0.5), coinCylinder);
        zoomReset.setToX(1);
        zoomReset.setToY(1);
        zoomReset.setToZ(1);
        
        
        
        SequentialTransition a= new SequentialTransition(rotateX, rotateY);
        SequentialTransition b= new SequentialTransition(zoomIn, zoomReset);
        ParallelTransition c= new ParallelTransition(b, a);
        SequentialTransition d= new  SequentialTransition(zoomOut, c);
        
        	a.setOnFinished(event -> {
            boolean monetaValue = random.nextBoolean();
            if (monetaValue) {
                coinCylinder.setMaterial(new PhongMaterial(Color.GREEN));
            } else {
                coinCylinder.setMaterial(new PhongMaterial(Color.RED));
            } if (scelta == monetaValue) {
            	Main.player.incrementGamesWon();
                Risultato.setText("YOU WON");
                Risultato.setStyle("-fx-text-fill: green;");
                Risultato.setStyle("-fx-font-size: 36;");
                playerWon();
                Main.player.addMoney(scommessa + (int) Math.round(scommessa * 0.25));
                showAnimatedResult(resultLabel, scommessa + (int) Math.round(scommessa * 0.25));
            } else {
            	Main.player.incrementGamesLost();
                Risultato.setText("YOU LOST");
                Risultato.setStyle("-fx-text-fill: red;");
                Risultato.setStyle("-fx-font-size: 36;");
                playerLost();
                Main.player.subtractMoney(scommessa);
                showAnimatedResult(resultLabel, -scommessa);
            }
            
            Main.player.incrementGamesPlayed();
            aggiornaSaldoAnimato();
            gameChart.addData(Main.player.getGamesPlayed(), Main.player.getMoney());
            
            testaButton.setVisible(true);
            croceButton.setVisible(true);
            Risultato.setVisible(true);
            startButton.setDisable(true);
        });
        
        	d.play();
        	
			
           
        	

        
    	
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
    	sceltaEffettuata = true;  // Imposta la scelta come effettuata
        startButton.setDisable(false);

    	scelta = true;
    	startAnimation(testaButton, croceButton);

    	
    }
    
    private void startAnimation(Button clickedButton, Button otherButton) {
        // Nasconde l'altro bottone
        otherButton.setVisible(false);
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
    
    // Coin Flip Method
    public static int coinFlipResult(int bet) {
        return random.nextBoolean() ? bet : -bet;
    }
   
    
    @FXML
   void initialize() {
    	startButton.setDisable(true);
    	coinCylinder.setMaterial(new PhongMaterial(javafx.scene.paint.Color.GOLD));
    	coinCylinder.getTransforms().add(new Rotate(90, Rotate.X_AXIS));
    	moneteChoiceBox.getItems().addAll(n);
        assert startButton != null : "fx:id=\"startButton\" was not injected: check your FXML file 'TescrosSample.fxml'.";
        assert Risultato != null : "fx:id=\"Risultato\" was not injected: check your FXML file 'TescrosSample.fxml'.";
        assert testaButton != null : "fx:id=\"testaButton\" was not injected: check your FXML file 'TescrosSample.fxml'.";
        assert croceButton != null : "fx:id=\"croceButton\" was not injected: check your FXML file 'TescrosSample.fxml'.";
        Soldi.setText("$" + Main.player.getMoney().toString());
        gameChart = new GameChart();
        
    }
}
    
