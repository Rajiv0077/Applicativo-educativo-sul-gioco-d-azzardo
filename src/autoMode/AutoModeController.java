package autoMode;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import diceGame.Dice3D;
import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import main.Main;
import main.SceneController;

public class AutoModeController extends SceneController {

    @FXML private TextField moneyField;
    @FXML private RadioButton radioDice, radioRoulette, radioGuessNumber, radioCoin, radioSlot;
    @FXML private Button goBackBtn, startBTN;
    @FXML private CheckBox infiniteCheck;
    @FXML private HBox hboxInf;
    @FXML private Slider RoundTSlider, TotalTSlider;
    @FXML private Label lbl1, lbl2, lblNessunLimite;
    @FXML private ChoiceBox<String> choiceBox1, choiceBox2;
    @FXML private ImageView graphImg;
    @FXML private VBox maxBetBox;
    @FXML private ChoiceBox<Integer> minBet, maxBet;

    private final Integer smallestBet = 10;
    private Boolean isInfinite = false;
    private String timeUnit1 = "seconds"; //as default
    private String timeUnit2 = "minutes"; //as default
    
    // Animation node references
    private Dice3D dice3D1;
    
    @FXML private StackPane diceContainer1;
    @FXML private Group groupRoulette;
    @FXML private Group groupLeva;
    @FXML private Group groupMonete;
    @FXML private Group groupIndovina;
    @FXML private Label lblNum;
    @FXML private Label lblX;

    @FXML
    private void initialize() {
        setupMoneyField();
        setupSliders();
        setupChoiceBoxes();
        setupInitialVisibility();
        
        // Initialize 3D dice
        initialize3DDice();
    }
    
    private void initialize3DDice() {
        dice3D1 = new Dice3D();
        // Add SubScenes to containers
        diceContainer1.getChildren().add(dice3D1.createScene());
    }

    private void resetDiceTransforms() {
        // Reset dice transforms through Dice3D class
        dice3D1.resetPosition();
    }


    private void setupInitialVisibility() {
        maxBetBox.setVisible(false);
        graphImg.setVisible(false);
        lblNessunLimite.setVisible(false);
    }

    private void setupMoneyField() {
        moneyField.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().matches("\\d*")) {
                return change;
            }
            return null;
        }));
        
        moneyField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.isEmpty()) {
                updateBetControls();
            } else {
                clearBetControls();
            }
        });
    }

    private void updateBetControls() {
        int moneyValue = getMoneyValue();
        if (moneyValue < smallestBet) {
            clearBetControls();
            return;
        }

        List<Integer> minOptions = calculateMinBetOptions(moneyValue);
        minBet.getItems().setAll(minOptions);
        
        if (!minOptions.isEmpty()) {
            minBet.setValue(minOptions.get(0));
            updateMaxBetOptions(minOptions.get(0), moneyValue);
            setBetControlsVisible(true);
        }
    }

    private List<Integer> calculateMinBetOptions(int moneyValue) {
        List<Integer> options = new ArrayList<>();
        options.add(smallestBet);
        
        for (int i = 1; i <= 9; i++) {
            int percentageValue = (int) (moneyValue * (i * 0.1));
            if (percentageValue >= smallestBet) {
                options.add(percentageValue);
            }
        }
        
        if (moneyValue > smallestBet) {
            options.add(moneyValue);
        }
        
        return options;
    }

    private void updateMaxBetOptions(Integer minVal, int moneyValue) {
        maxBet.getItems().clear();
        if (minVal > moneyValue) {
            graphImg.setVisible(false);
            maxBetBox.setVisible(false);
            lblNessunLimite.setVisible(false);
            return;
        }

        Set<Integer> options = new LinkedHashSet<>();
        int step = Math.max((moneyValue - minVal) / 10, 1);
        
        // Create stepped options
        for (int i = 0; i <= 10; i++) {
            int value = minVal + (i * step);
            if (value > moneyValue) value = moneyValue;
            options.add(value);
        }
        
        // Add special options
        options.add(moneyValue);
        options.add(999999); // "No limit" marker

        maxBet.getItems().addAll(options);
        maxBet.setValue(minVal);
        
        // Make sure the ChoiceBox is interactive
        maxBet.setDisable(false);
        maxBet.setMouseTransparent(false);
        maxBet.setFocusTraversable(true);
        
        // Show the appropriate controls
        graphImg.setVisible(true);
        maxBetBox.setVisible(true);
        lblNessunLimite.setVisible(false);
    }

    private void setBetControlsVisible(boolean visible) {
        maxBetBox.setVisible(visible);
        graphImg.setVisible(visible);
    }

    private void clearBetControls() {
        minBet.getItems().clear();
        maxBet.getItems().clear();
        setBetControlsVisible(false);
    }

    private int getMoneyValue() {
        try {
            return Integer.parseInt(moneyField.getText());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private void setupSliders() {
    	RoundTSlider.setMin(1);
        RoundTSlider.setMax(60);
        TotalTSlider.setMin(1);
        TotalTSlider.setMax(120);
        
        RoundTSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            // Ensure minimum value of 1 for round time
            if (newVal.intValue() < 1) {
                RoundTSlider.setValue(1);
                return;
            }
            
            int value = newVal.intValue();
            lbl1.setText(String.valueOf(value));
            updateLabelColor(lbl1, value);
        });

        TotalTSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            int value = newVal.intValue();
            lbl2.setText(String.valueOf(value));
            updateLabelColor(lbl2, value);
        });
    }

    private void updateLabelColor(Label label, int value) {
        int red, green;
        if (value <= 20) {
            red = (int) (value * 8);
            green = 255;
        } else if (value <= 25) {
            red = 220 + (int) ((value - 20) * 7);
            green = 220 - (int) ((value - 20) * 4);
        } else if (value <= 40) {
            red = 255;
            green = 200 - (int) ((value - 25) * 8);
        } else {
            red = 255;
            green = 60 - (int) ((value - 40) * 3);
        }
        label.setTextFill(Color.rgb(
            Math.min(255, Math.max(0, red)),
            Math.min(255, Math.max(0, green)),
            0
        ));
    }

    private void setupChoiceBoxes() {
        choiceBox1.getItems().addAll("milliseconds", "seconds", "minutes", "hours");
        choiceBox1.setValue("seconds");
        choiceBox1.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldVal, newVal) -> timeUnit1 = newVal);

        choiceBox2.getItems().addAll("milliseconds", "seconds", "minutes", "hours");
        choiceBox2.setValue("minutes");
        choiceBox2.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldVal, newVal) -> timeUnit2 = newVal);
    }

    @FXML
    private void toggleInfinite(ActionEvent event) {
        isInfinite = !isInfinite;
        if (hboxInf != null) {  // Add null check for safety
            hboxInf.setVisible(!isInfinite);
        }
    }
    
    private List<String> getSelectedGames() {
        List<String> games = new ArrayList<>();
        if (radioDice.isSelected()) games.add("DICE");
        if (radioRoulette.isSelected()) games.add("ROULETTE");
        if (radioGuessNumber.isSelected()) games.add("GUESS_NUMBER");
        if (radioCoin.isSelected()) games.add("COIN");
        if (radioSlot.isSelected()) games.add("SLOT");
        return games;
    }

    @FXML
    private void lancia(ActionEvent event) {
        if (!validateInput()) return;
        
        int moneyValue = getMoneyValue();
        if (moneyValue < smallestBet) {
            showAlert("Invalid Input", "Minimum money required: " + smallestBet);
            return;
        }
        
        // Ensure minimum round time of 1 millisecond
        double roundTimeValue = RoundTSlider.getValue();
        if (roundTimeValue < 1) {
            roundTimeValue = 1;
            RoundTSlider.setValue(1);
        }
        
        // Convert times with enforced minimum
        long roundTime = convertToMillis(roundTimeValue, timeUnit1);
        long totalTime = isInfinite ? 0 : convertToMillis(TotalTSlider.getValue(), timeUnit2);

        new Thread(new AutoMode(
            moneyValue,
            minBet.getValue(),
            maxBet.getValue() == 999999 ? Integer.MAX_VALUE : maxBet.getValue(),
            roundTime,
            totalTime,
            isInfinite,
            getSelectedGames()
        )).start();
    }

    private boolean validateInput() {
        StringBuilder errors = new StringBuilder();
        
        if (!radioDice.isSelected() && !radioRoulette.isSelected() &&
            !radioGuessNumber.isSelected() && !radioCoin.isSelected() && 
            !radioSlot.isSelected()) {
            errors.append("- Select at least one game\n");
        }

        try {
            int money = Integer.parseInt(moneyField.getText());
            if (money <= 0) errors.append("- Invalid initial money\n");
        } catch (NumberFormatException e) {
            errors.append("- Invalid money format\n");
        }

        if (errors.length() > 0) {
            showAlert("Input Error", errors.toString());
            return false;
        }
        
        // Get time values in milliseconds
        long roundTime = convertToMillis(RoundTSlider.getValue(), timeUnit1);
        long totalTime = convertToMillis(TotalTSlider.getValue(), timeUnit2);
        
        // Ensure minimum round time of 1 millisecond
        if (roundTime < 1) {
            roundTime = 1;
        }
        
        // Check if round time exceeds total time (only when not infinite)
        if (!isInfinite && totalTime > 0 && roundTime > totalTime) {
            errors.append("- Round time cannot exceed total duration\n");
        }
        
        if (errors.length() > 0) {
            showAlert("Input Error", errors.toString());
            return false;
        }
        return true;
    }


    private long convertToMillis(double value, String unit) {
        switch (unit.toLowerCase()) {
            case "hours": return (long)(value * 3600 * 1000);
            case "minutes": return (long)(value * 60 * 1000);
            case "seconds": return (long)(value * 1000);
            default: return (long)value;
        }
    }

    private void showAlert(String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    // Game animation methods remain similar with proper translations
    @FXML
    private void rDadi(ActionEvent event) {
        if (radioDice.isSelected()) {
            animateDice();
        }
    }
    
    private void stopDiceAnimations() {
        // Use ParallelTransition type
        if (dice3D1 != null && dice3D1.getTimeline() != null) {
            dice3D1.getTimeline().stop();
        }
    }

    private void animateDice() {
        // Ensure dice are initialized
        if (dice3D1 == null) {
            initialize3DDice();
        }
        
        stopDiceAnimations();
        resetDiceTransforms();
        
        // Start animations on JavaFX thread
        Platform.runLater(() -> {
            dice3D1.roll3DDicePin();
        });
    }

    @FXML
    private void rRoulette(ActionEvent event) {
        if (radioRoulette.isSelected()) {
            RotateTransition rt = new RotateTransition(Duration.seconds(3), groupRoulette);
            rt.setByAngle(1440); // 4 full rotations
            rt.play();
        }
    }

    @FXML 
    private void rSlot(ActionEvent event) {
        if (radioSlot.isSelected()) {
            RotateTransition rt = new RotateTransition(Duration.seconds(0.5), groupLeva);
            rt.setAxis(Rotate.X_AXIS);
            rt.setFromAngle(-30);
            rt.setToAngle(-90);
            rt.setAutoReverse(true);
            rt.setCycleCount(2);
            rt.play();
        }
    }

    @FXML
    private void rMonete(ActionEvent event) {
        if (radioCoin.isSelected()) {
            RotateTransition rt = new RotateTransition(Duration.seconds(1), groupMonete);
            rt.setByAngle(720);
            rt.play();
        }
    }

    @FXML
    private void rIndovina(ActionEvent event) {
        if (radioGuessNumber.isSelected()) {
            Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, e -> lblNum.setText("?")),
                new KeyFrame(Duration.seconds(0.3), e -> lblNum.setText("7")),
                new KeyFrame(Duration.seconds(0.6), e -> lblNum.setText("3")),
                new KeyFrame(Duration.seconds(0.9), e -> lblNum.setText("X"))
            );
            timeline.setCycleCount(2);
            timeline.play();
        }
    }

    @FXML
    private void goBack(ActionEvent event) {
        switchScene(event, "/menu/fxml/ModeMenu.fxml");
    }
    
}