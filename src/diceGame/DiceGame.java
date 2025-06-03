package diceGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import diceGame.controllers.DiceGameController;
import gameChart.GameChart;
import main.*;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class DiceGame extends Game {
	
	private int currentMode;
    private final DiceGameController controller;
    private final Bot bot;
    private final Player player;
    private Dice3D[] playerDice3D = new Dice3D[4];
    private Dice3D[] botDice3D = new Dice3D[4];
    private MediaPlayer mediaPlayer;
    private  GameChart gameChart = new GameChart();
    private String isGameWon;
    Integer startMoney;

    public DiceGame(DiceGameController controller) {
        super(10); // Min bet: 10
        this.controller = controller;
        this.bot = Main.getBot();
        this.player = Main.getPlayer();
    }

    
    @Override
    public void startGame() {
        startRoll();
    }

    
    public void setupGame(int mode) {
        this.currentMode = mode;
        playerDice3D = new Dice3D[4];
        botDice3D = new Dice3D[4];

        for (int i = 0; i < 4; i++) {
            StackPane playerContainer = controller.getPlayerDiceContainer(i);
            StackPane botContainer = controller.getBotDiceContainer(i);
            
            // Skip if containers don't exist in FXML
            if (playerContainer == null || botContainer == null) continue;

            if (i < currentMode) {
                playerDice3D[i] = new Dice3D();
                botDice3D[i] = new Dice3D();
                
                playerContainer.getChildren().setAll(
                    playerDice3D[i].createScene(),
                    controller.getPlayerDiceImage(i)
                );
                botContainer.getChildren().setAll(
                    botDice3D[i].createScene(),
                    controller.getBotDiceImage(i)
                );
                
                playerContainer.setVisible(true);
                botContainer.setVisible(true);
            } else {
                playerContainer.setVisible(false);
                botContainer.setVisible(false);
            }
        }
        
        hideDiceImages();
        updateUI();
    }

    
    public void startRoll() {
        int betAmount = validateBet(controller.getBetInput().getText());
        if (betAmount == -1) return;

        controller.getRollButton().setDisable(true);
        hideDiceImages();

        // Roll only existing dice
        for (int i = 0; i < currentMode; i++) {
            if (playerDice3D[i] != null) {
                playerDice3D[i].roll3DDice();
            }
            if (botDice3D[i] != null) {
                botDice3D[i].roll3DDice();
            }
        }

        playSound();
        finalizeRoll(betAmount);
    }

    
    private void finalizeRoll(int betAmount) {
    	int[] playerDiceResults = new int[currentMode];
        int[] botDiceResults = new int[currentMode];

        for (int i = 0; i < currentMode; i++) {
            playerDiceResults[i] = (int) (Math.random() * 6) + 1; // Player's roll
            botDiceResults[i] = bot.rollDice(); // Bot's roll
        }

        new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            // Only update existing dice images
            for (int i = 0; i < currentMode; i++) {
                ImageView playerImg = controller.getPlayerDiceImage(i);
                ImageView botImg = controller.getBotDiceImage(i);
                
                if (playerImg != null) {
                    playerImg.setImage(getDiceImage(playerDiceResults[i]));
                }
                if (botImg != null) {
                    botImg.setImage(getDiceImage(botDiceResults[i]));
                }
            }

            /// Calculate totals
            int playerTotal = Arrays.stream(playerDiceResults).sum();
            int botTotal = Arrays.stream(botDiceResults).sum();

            // Determine outcome
            int margin = playerTotal - botTotal;
            int result = 0;

            player.incrementGamesPlayed();
            this.startMoney = player.getMoney(); //for final message calculation
            
            // WIN CASE
            if (margin > 0) {
            	if (margin >= 10){
            		result = (int)(betAmount * 4); // super profit
                    showMultiplier("+4X SUPER WIN!");
            	}
            	else if (margin >= 5) {
                    result = (int)(betAmount * 0.75); // modest profit
                    showMultiplier("+0.75X");
                } else if (margin >= 3) {
                    result = (int)(betAmount * 0.5); // small profit
                    showMultiplier("+0.5X");
                } else {
                    result = (int)(betAmount * 0.25); // tiny reward
                    showMultiplier("+0.25X");
                }

            	player.addMoney(result);
            	controller.showMoneyAnimation(result, true);
                player.incrementGamesWon();
                bot.incrementGamesLost();

            // LOSS CASE
            } else if (margin < 0) {
                int loss;
                
                if (margin <= -5) {
                    loss = (int)(betAmount); // full bet lost
                    showMultiplier("-1X");
                } else if (margin <= -3) {
                    loss = (int)(betAmount * 0.75); // lose 75% of bet
                    showMultiplier("-0.75X");
                } else {
                    loss = (int)(betAmount * 0.5); // lose 50% of bet
                    showMultiplier("-0.5X");
                }

                player.subtractMoney(loss);
                controller.showMoneyAnimation(loss, false);
                player.incrementGamesLost();
                bot.incrementGamesWon();
                
            // TIE CASE
            } else {
                showMultiplier("TIE!");
            }

            Integer finishMoney = player.getMoney();
            
            System.out.println(startMoney + " " + finishMoney);
          
            if (finishMoney.compareTo(startMoney) == 0) {
             isGameWon = null;
             System.out.println("Equal");
             controller.playerDraw();
            } else {
             if (finishMoney > startMoney) {
              System.out.println("PlayerWon");
              isGameWon = "true";
             } else {
              System.out.println("PlayerLost");
              isGameWon = "false";
             }
            }
             if ("true".equals(isGameWon)) {
              System.out.println("PlayerWon");
              controller.playerWon();
             } else if ("false".equals(isGameWon)){
              System.out.println("PlayerLost");
              controller.playerLost();
             } else {
              System.out.println("PlayerTIE");
             }
            
            startMoney = finishMoney;
            
            //update data in the chart
            gameChart.addData(player.getGamesPlayed(), player.getMoney());
            updateUI();

            // Fade-in animation for active dice
            List<KeyValue> keyValues = new ArrayList<>();
            for (int i = 0; i < currentMode; i++) {
                ImageView playerImg = controller.getPlayerDiceImage(i);
                ImageView botImg = controller.getBotDiceImage(i);
                
                if (playerImg != null) {
                    keyValues.add(new KeyValue(playerImg.opacityProperty(), 1, Interpolator.EASE_IN));
                }
                if (botImg != null) {
                    keyValues.add(new KeyValue(botImg.opacityProperty(), 1, Interpolator.EASE_IN));
                }
            }

            new Timeline(new KeyFrame(Duration.seconds(1), 
                keyValues.toArray(new KeyValue[0])
            )).play();
            
            Timeline fadeIn = new Timeline(new KeyFrame(Duration.seconds(1), keyValues.toArray(new KeyValue[0])));
            fadeIn.play();

            controller.getRollButton().setDisable(false);
        })).play();
        
    }

    
    private int validateBet(String betText) {
        try {
            int bet = Integer.parseInt(betText);
            if (bet < minBet || bet > player.getMoney()) {
                controller.updateResult("Invalid bet amount! Min: " + minBet);
                return -1;
            }
            return bet;
        } catch (NumberFormatException e) {
            controller.updateResult("Enter a valid number!");
            return -1;
        }
    }

    
    private void hideDiceImages() {
        for (int i = 0; i < currentMode; i++) { // Only loop through active dice
            ImageView playerImg = controller.getPlayerDiceImage(i);
            ImageView botImg = controller.getBotDiceImage(i);
            
            if (playerImg != null) playerImg.setOpacity(0);
            if (botImg != null) botImg.setOpacity(0);
        }
    }

    private void updateUI() {
        controller.updateMoneyLabel(player.getMoney() + "$");
    }

    
    private Image getDiceImage(int diceNumber) {
        return new Image(getClass().getResourceAsStream("/resources/img/dice" + diceNumber + ".png"));
    }

    
    private void playSound() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
        }

        String soundFile = getClass().getResource("/sounds/dice_sfx.mp3").toExternalForm();
        Media sound = new Media(soundFile);
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }
    
    private void showMultiplier(String mult) {
    	controller.multiplierOverlay.setText(mult);
        controller.multiplierOverlay.setVisible(true);

        // Add fade-out animation (optional)
        FadeTransition ft = new FadeTransition(Duration.seconds(2), controller.multiplierOverlay);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        ft.play();
    }
    
    
     public static int diceResult(int betAmount) {
        Random random = new Random();
        // Randomly choose the number of dice (1-4) for both player and bot
        int numberOfDice = random.nextInt(4) + 1;

        // Roll dice for player and bot
        int playerTotal = rollDiceForAuto(numberOfDice, random);
        int botTotal = rollDiceForAuto(numberOfDice, random);

        int margin = playerTotal - botTotal;
        int result = 0;

        // WIN CASE
        if (margin > 0) {
            if (margin >= 10) {
                result = (int)(betAmount * 4);
            } else if (margin >= 5) {
                result = (int)(betAmount * 0.75);
            } else if (margin >= 3) {
                result = (int)(betAmount * 0.5);
            } else {
                result = (int)(betAmount * 0.25);
            }

        } else if (margin < 0) {
            int loss;
            if (margin <= -5) {
                loss = (int)(betAmount);
            } else if (margin <= -3) {
                loss = (int)(betAmount * 0.75);
            } else {
                loss = (int)(betAmount * 0.5);
            }

            result = -loss;

        // TIE CASE
        } else {
            return 0;
        }

        return result;
    }

    // Helper method to roll a given number of dice
    private static int rollDiceForAuto(int numberOfDice, Random random) {
        int total = 0;
        for (int i = 0; i < numberOfDice; i++) {
            total += random.nextInt(6) + 1; // Dice roll (1-6)
        }
        return total;
    }
    
}
