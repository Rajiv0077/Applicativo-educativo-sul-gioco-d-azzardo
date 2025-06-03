package diceGame.controllers;

import java.util.ArrayList;
import java.util.Arrays;

import diceGame.DiceGame;
import gameChart.GameChart;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.QuadCurve;
import javafx.util.Duration;
import main.Alerter;
import main.Main;
import main.SceneController;

public class DiceGameController extends SceneController implements Alerter{

    @FXML private Label resultLabel, multiplierLabel, errorLabel;
	@FXML public Label moneyLabel;
	@FXML public Label multiplierOverlay;
    @FXML private TextField betInput;
    @FXML private Button rollButton, backButton;
    @FXML private StackPane PlayerDiceContainer1, PlayerDiceContainer2, BotDiceContainer1, BotDiceContainer2, PlayerDiceContainer3, PlayerDiceContainer4, BotDiceContainer3, BotDiceContainer4;
    @FXML private ImageView botDiceImage1, botDiceImage2, playerDiceImage1, playerDiceImage2, botDiceImage3, botDiceImage4, playerDiceImage3, playerDiceImage4;
    
    
    @FXML private ImageView dealerIMG;
    @FXML private Label botText;
    @FXML private Group messageGroup;
    @FXML private QuadCurve smile;

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
    private DiceGame diceGame;
	private GameChart gameChart;

    @FXML
    public void initialize() {
    	dealerIMG.setImage(new Image(getClass().getResourceAsStream("/resources/img/dealer.png")));
        diceGame = new DiceGame(this);
        // Get the actual selected mode from DiceGameModeController
        int mode = DiceGameModeController.getGameMode();
        diceGame.setupGame(mode);
    }

    @FXML
    private void rollDice() {
        String betText = betInput.getText().trim();
        
        if (betText.isEmpty()) {
            showError("Please enter a bet amount");
            return;
        }

        try {
            int bet = Integer.parseInt(betText);
            if (bet < 10) {
                showError("Minimum bet is 10");
                return;
            }
            
            if (bet > Main.player.getMoney()) {
                showError("You do not have enough money");
                return;
            }
            // If valid, proceed with the game
            diceGame.startGame();
        } catch (NumberFormatException e) {
            showError("Invalid bet amount");
        }
    }

    //animated show of the error text && validating the bet field
    public void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setOpacity(0.0);
        errorLabel.setVisible(true);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(500), errorLabel);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        PauseTransition pause = new PauseTransition(Duration.seconds(3));

        FadeTransition fadeOut = new FadeTransition(Duration.millis(500), errorLabel);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        SequentialTransition sequence = new SequentialTransition(fadeIn, pause, fadeOut);
        sequence.setOnFinished(e -> {
            errorLabel.setVisible(false);
            errorLabel.setOpacity(1.0); // Reset opacity for future use
        });
        sequence.play();
    }

    // Getter Methods for `DiceGame` to access UI elements
    public TextField getBetInput() { return betInput; }
    public Button getRollButton() { return rollButton; }
    public Label getResultLabel() { return resultLabel; }
    public Label getMultiplierLabel() { return multiplierLabel; }
    public Label getMoneyLabel() { return moneyLabel; }

    
 // New method to get the dice container dynamically based on index
    public StackPane getPlayerDiceContainer(int index) {
        try {
            switch (index) {
                case 0: return PlayerDiceContainer1;
                case 1: return PlayerDiceContainer2;
                case 2: return PlayerDiceContainer3;
                case 3: return PlayerDiceContainer4;
                default: return null;
            }
        } catch (NullPointerException e) {
            return null;
        }
    }

    public StackPane getBotDiceContainer(int index) {
        try {
            switch (index) {
                case 0: return BotDiceContainer1;
                case 1: return BotDiceContainer2;
                case 2: return BotDiceContainer3;
                case 3: return BotDiceContainer4;
                default: return null;
            }
        } catch (NullPointerException e) {
            return null;
        }
    }
    
    public ImageView getPlayerDiceImage(int index) {
        try {
            switch (index) {
                case 0: return playerDiceImage1;
                case 1: return playerDiceImage2;
                case 2: return playerDiceImage3;
                case 3: return playerDiceImage4;
                default: return null;
            }
        } catch (NullPointerException e) {
            return null;
        }
    }

    public ImageView getBotDiceImage(int index) {
        try {
            switch (index) {
                case 0: return botDiceImage1;
                case 1: return botDiceImage2;
                case 2: return botDiceImage3;
                case 3: return botDiceImage4;
                default: return null;
            }
        } catch (NullPointerException e) {
            return null;
        }
    }

    // UI Update Methods
    public void updateMoneyLabel(String moneyText) { moneyLabel.setText(moneyText); }
    public void updateResult(String resultText) { resultLabel.setText(resultText); }
    public void updateMultiplier(String multiplierText) { multiplierLabel.setText(multiplierText); }

    public void turnToSwitch(ActionEvent event) {
        // Switch to the mode selection scene
        switchScene(event, "/diceGame/fxml/modeSwitch.fxml");
        // Reset game mode in the DiceGameModeController if applicable
        DiceGameModeController.resetGameMode(); 
        diceGame.setupGame(0);
    }

    @FXML
    public void showStats(ActionEvent event) {
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

    public void updateDiceVisibility(int diceCount) {
        for (int i = 0; i < 4; i++) {
            StackPane playerContainer = getPlayerDiceContainer(i);
            StackPane botContainer = getBotDiceContainer(i);
            
            if (playerContainer != null) {
                playerContainer.setVisible(i < diceCount);
            }
            if (botContainer != null) {
                botContainer.setVisible(i < diceCount);
            }
        }
    }

    // Call this method when the number of dice changes, so the UI is updated accordingly
    public void setGameMode(int mode) {
        // Update dice visibility based on the selected mode
        updateDiceVisibility(mode);
        diceGame.setupGame(mode);  // Pass the mode to setupGame
    }
    
    
    //---------------------------------------------ALL THE MESSAGING SYSTEM--------------------------------------------
    public void playerWon() {
    	  turnSmile(false);
    	  showMessage();
    	  FadeTransition fadeTransition = new FadeTransition(Duration.millis(2000), messageGroup);
    	  fadeTransition.setFromValue(1);
    	  fadeTransition.setToValue(1);

    	  fadeTransition.setOnFinished(evnt -> {
    	   hideMessageGroup();
    	  });
    	  fadeTransition.play();

    }
    
    public void playerDraw() {
    	  showDrawMessage();
    	  FadeTransition fadeTransition = new FadeTransition(Duration.millis(2000), messageGroup);
    	  fadeTransition.setFromValue(1);
    	  fadeTransition.setToValue(1);
    	  
    	  fadeTransition.setOnFinished(evnt -> {
    	   hideMessageGroup();
    	  });
    	  fadeTransition.play();
    	  
    }

	public void playerLost() {
    	  turnSmile(true);
    	  showMessage();
    	  FadeTransition fadeTransition = new FadeTransition(Duration.millis(2000), messageGroup);
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
    	 //---------------------------------------------END MESSAGING SYSTEM-----------------------------------------
    	 
    	 //animation for a money label
    	 public void showMoneyAnimation(int amountChange, boolean won) {
    		    // Create a temporary label for the animation
    		    Label animationLabel = new Label();
    		    
    		    // Style it similarly to your money label
    		    animationLabel.setFont(moneyLabel.getFont());
    		    animationLabel.setStyle("-fx-font-weight: bold;");
    		    
    		    // Position it relative to the original money label
    		    animationLabel.setLayoutX(moneyLabel.getLayoutX());
    		    animationLabel.setLayoutY(moneyLabel.getLayoutY() + 20); // Start 20px below
    		    
    		    // Add to the same parent as moneyLabel
    		    Parent parent = moneyLabel.getParent();
    		    if (parent instanceof Pane) {
    		        ((Pane)parent).getChildren().add(animationLabel);
    		    } else {
    		        return; // Can't add if parent isn't a Pane
    		    }
    		    
    		    // Set text and color
    		    if (won) {
    		        animationLabel.setText("+" + amountChange + "$");
    		        animationLabel.setTextFill(Color.GREEN);
    		    } else {
    		        animationLabel.setText("-" + amountChange + "$");
    		        animationLabel.setTextFill(Color.RED);
    		    }

    		    // Initial state (invisible)
    		    animationLabel.setOpacity(0);

    		    // Create animations
    		    TranslateTransition moveUp = new TranslateTransition(Duration.seconds(1), animationLabel);
    		    moveUp.setByY(-40); // Move up by 40px

    		    FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), animationLabel);
    		    fadeIn.setToValue(1.0);

    		    FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), animationLabel);
    		    fadeOut.setDelay(Duration.seconds(2));
    		    fadeOut.setToValue(0);

    		    // Combine animations
    		    ParallelTransition showUp = new ParallelTransition(moveUp, fadeIn);
    		    SequentialTransition sequence = new SequentialTransition(showUp, fadeOut);
    		    
    		    // Clean up after animation
    		    sequence.setOnFinished(e -> {
    		        ((Pane)parent).getChildren().remove(animationLabel);
    		    });
    		    
    		    sequence.play();
    		}
}
