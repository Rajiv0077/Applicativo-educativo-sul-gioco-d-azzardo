package autoMode;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import roulette.controllers.RouletteController;
import slot.SimulationSlot;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import coinflip.controllers.Coin1Controller;
import diceGame.DiceGame;
import fileManager.UserFileManager;
import guessNumber.GuessNumber;

public class AutoMode implements Runnable {
    
    public static String STATS_FILE_PATH = UserFileManager.getAutoModePath();
    private final int SMALLEST_BET = 10;
    
    // Configuration
    private final int initialMoney, maxBet;
    private final long roundTimeMillis, totalTimeMillis;
    private final boolean infinite;
    private final List<String> enabledGames;
    
    // State tracking
    private final AtomicInteger currentMoney; //Atomic Int to prevent thread errors and "race condition"
    private volatile boolean running = true;
    private int roundsPlayed, roundsWon, roundsLost, highestBet, highestEarning;
    private final Map<String, Integer> gameCounts = new HashMap<>(); //for final stats text, counter for each game
    
    // UI components
    private Stage stage;
    private LineChart<Number, Number> chart;
    private XYChart.Series<Number, Number> series;
    private Label gameStatusLabel;
    private final Random random = new Random();
    
    // Thread management
    private Thread simulationThread; //var that will contain main process with all its code in execution, interrupts if finished
    private final ScheduledExecutorService uiUpdateExecutor = Executors.newSingleThreadScheduledExecutor(); // needed to schedule the uniform stable updates of the window
    private final ConcurrentLinkedQueue<XYChart.Data<Number, Number>> chartDataBuffer = new ConcurrentLinkedQueue<>();
    private volatile String currentGame = "";

    public AutoMode(int initialMoney, int minBet, int maxBet, long roundTimeMillis,
                   long totalTimeMillis, boolean infinite, List<String> enabledGames) {
        this.initialMoney = initialMoney;
        this.maxBet = maxBet;
        this.roundTimeMillis = roundTimeMillis;
        this.totalTimeMillis = totalTimeMillis;
        this.infinite = infinite;
        this.enabledGames = enabledGames;
        this.currentMoney = new AtomicInteger(initialMoney);
    }

    @Override
    public void run() {
        Platform.runLater(() -> {
            setupWindow();
            simulationThread = new Thread(this::simulationLoop);
            simulationThread.start();
        });
    }

    private void setupWindow() {
        NumberAxis xAxis = new NumberAxis("Round", 0, 100, 10);
        NumberAxis yAxis = new NumberAxis("Money", 0, initialMoney * 2, initialMoney/10);
        
        chart = new LineChart<>(xAxis, yAxis);
        chart.setTitle("Casino Clamoroso Simulation");
        applyDarkTheme(xAxis, yAxis);
        
        series = new XYChart.Series<>();
        series.setName("Money Progress");
        chart.getData().add(series);
        
        gameStatusLabel = new Label("Starting simulation...");
        gameStatusLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-alignment: center;");
        
        VBox root = new VBox(10, createStatusLabel(), gameStatusLabel, chart);
        root.setStyle("-fx-background-color: #2b2b2b;");
        
        stage = new Stage();
        stage.setScene(new Scene(root, 800, 600));
        stage.setOnCloseRequest(e -> stopSimulation());
        stage.show();

        // Schedule UI updates every 100ms
        uiUpdateExecutor.scheduleAtFixedRate(this::processUiUpdates, 0, 100, TimeUnit.MILLISECONDS);
    }

    private void processUiUpdates() {
        // Update game status
        String gameSnapshot = currentGame;
        Platform.runLater(() -> 
            gameStatusLabel.setText("Current Game: " + gameSnapshot.toUpperCase())
        );
        
        // Process chart data
        if (!chartDataBuffer.isEmpty()) {
            List<XYChart.Data<Number, Number>> dataToAdd = new ArrayList<>();
            
            // Manually drain the queue
            XYChart.Data<Number, Number> data;
            while ((data = chartDataBuffer.poll()) != null) {
                dataToAdd.add(data);
            }
            
            Platform.runLater(() -> {
                series.getData().addAll(dataToAdd);
                
                // Keep only last 100 points
                if (series.getData().size() > 100) {
                    series.getData().remove(0, series.getData().size() - 100);
                }
                
                // Adjust viewport if there's new data
                if (!dataToAdd.isEmpty()) {
                    XYChart.Data<Number, Number> lastPoint = dataToAdd.get(dataToAdd.size() - 1);
                    adjustChartViewport(lastPoint.getXValue().intValue(), lastPoint.getYValue().intValue());
                }
            });
        }
    }

    private void adjustChartViewport(int round, int money) {
        NumberAxis xAxis = (NumberAxis) chart.getXAxis();
        NumberAxis yAxis = (NumberAxis) chart.getYAxis();
        
        // Calculate dynamic padding
        double xPadding = Math.max(10, round * 0.1);
        double yPadding = Math.max(100, money * 0.1);
        
        // Update X-axis bounds
        xAxis.setLowerBound(Math.max(0, round - xPadding));
        xAxis.setUpperBound(round + xPadding);
        
        // Calculate Y-axis bounds from visible data
        double[] yBounds = calculateYBounds();
        yAxis.setLowerBound(Math.max(0, yBounds[0] - yPadding));
        yAxis.setUpperBound(yBounds[1] + yPadding);
    }

    private double[] calculateYBounds() {
        if (series.getData().isEmpty()) return new double[]{0, initialMoney};
        
        double minY = Double.MAX_VALUE;
        double maxY = Double.MIN_VALUE;
        
        for (XYChart.Data<Number, Number> point : series.getData()) {
            double val = point.getYValue().doubleValue();
            if (val < minY) minY = val;
            if (val > maxY) maxY = val;
        }
        
        return new double[]{minY, maxY};
    }

    private Label createStatusLabel() {
        Label label = new Label("Simulation in Progress...");
        label.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-alignment: center;");
        return label;
    }

    private void applyDarkTheme(NumberAxis xAxis, NumberAxis yAxis) {
        chart.setStyle("-fx-background-color: #2b2b2b;");
        chart.lookup(".chart-plot-background").setStyle("-fx-background-color: #1d1d1d;");
        
        for (NumberAxis axis : Arrays.asList(xAxis, yAxis)) {
            axis.setTickLabelFill(Color.WHITE);
            axis.lookup(".axis-label").setStyle("-fx-text-fill: white;");
        }
    }

    private void simulationLoop() {
        try {
            long startTime = System.currentTimeMillis();
            chartDataBuffer.add(new XYChart.Data<>(0, initialMoney)); // first point
            
            while (running && shouldContinue(startTime)) {
                int current = currentMoney.get();
                if (shouldStop(current)) {
                    showTerminationAlert(current);
                    break;
                }
                
                playGameRound();
                sleepBetweenRounds();
            }
        } finally { //executes in any case after any result
            Platform.runLater(() -> {
                if (stage != null) stage.close();
                showStatistics();
            });
            uiUpdateExecutor.shutdownNow();
        }
    }

    private void playGameRound() {
        int bet = calculateValidBet();
        if (bet == 0) return;
        
        String game = enabledGames.get(random.nextInt(enabledGames.size()));
        currentGame = game;
        
        trackGameStats(game, bet);
        int result = simulateGame(game, bet);
        
        updateMoneyAndStats(result);
        int newBalance = currentMoney.addAndGet(result);
        chartDataBuffer.add(new XYChart.Data<>(roundsPlayed, newBalance));
    }

    private void trackGameStats(String game, int bet) {
        gameCounts.put(game, gameCounts.getOrDefault(game, 0) + 1);
        highestBet = Math.max(highestBet, bet);
    }

    private void updateMoneyAndStats(int result) {
        if (result > 0) {
            roundsWon++;
            highestEarning = Math.max(highestEarning, result);
        } else {
            roundsLost++;
        }
        roundsPlayed++;
    }

    private boolean shouldContinue(long startTime) {
        return infinite || System.currentTimeMillis() - startTime < totalTimeMillis; //depending on the mode(inf or with time) make a calc with real sys time
    }

    private boolean shouldStop(int currentBalance) {
        return currentBalance < SMALLEST_BET || currentBalance <= 0;
    }

    private void showTerminationAlert(int currentBalance) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(currentBalance <= 0 ? "Bankrupt!" : "Insufficient funds for a minBet!");
            styleAlert(alert.getDialogPane());
            alert.showAndWait();
        });
    }

    private void styleAlert(DialogPane pane) {
        pane.setStyle("-fx-background-color: #2b2b2b;");
        pane.lookupAll(".label").forEach(node -> 
            node.setStyle("-fx-text-fill: white;"));
    }

    private int calculateValidBet() {
        int current = currentMoney.get();
        if (current < SMALLEST_BET) return 0;
        
        int effectiveMax = (maxBet == Integer.MAX_VALUE) ? current : Math.min(maxBet, current);
        effectiveMax = Math.max(effectiveMax, SMALLEST_BET);
        
        return SMALLEST_BET + random.nextInt(effectiveMax - SMALLEST_BET + 1);
    }

    private void sleepBetweenRounds() {
        try {
            long sleepStart = System.currentTimeMillis();
            long remaining = roundTimeMillis;
            
            while (remaining > 0 && running) {
                Thread.sleep(Math.min(remaining, 50));
                remaining = roundTimeMillis - (System.currentTimeMillis() - sleepStart);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void stopSimulation() {
        running = false;
        if (simulationThread != null) {
            simulationThread.interrupt();
        }
        uiUpdateExecutor.shutdownNow();
        Platform.runLater(() -> {
            if (stage != null) {
                stage.close();
                stage = null;
            }
        });
    }

    private int simulateGame(String gameType, int bet) {
        switch (gameType.toUpperCase()) {
            case "DICE": 
                return DiceGame.diceResult(bet);
            case "ROULETTE": 
                return RouletteController.rouletteResult(bet);
            case "GUESS_NUMBER": 
            	GuessNumber gn = new GuessNumber();
                return gn.autoGuessNumber(bet);
            case "COIN": 
                return Coin1Controller.coinFlipResult(bet);
            case "SLOT": 
                return SimulationSlot.slotResult(bet);
            default: 
                return 0;
        }
    }

    private void showStatistics() {
        Platform.runLater(() -> {
            Stage statsStage = new Stage();
            VBox layout = new VBox(20);
            layout.setStyle("-fx-background-color: #2b2b2b; -fx-padding: 20;");

            TextArea statsArea = createStatsTextArea();
            Button saveButton = createSaveButton(statsStage);

            layout.getChildren().addAll(createStatsTitle(), statsArea, saveButton);
            layout.setAlignment(Pos.CENTER);

            statsStage.setScene(new Scene(layout, 500, 400));
            statsStage.show();
        });
    }

    private TextArea createStatsTextArea() {
        TextArea area = new TextArea(buildStatsText());
        area.setStyle("-fx-control-inner-background: #1d1d1d; -fx-text-fill: white;");
        area.setEditable(false);
        return area;
    }

    private Button createSaveButton(Stage statsStage) {
        Button button = new Button("Save & Exit");
        button.setStyle("-fx-base: #3c3c3c; -fx-text-fill: white;");
        button.setOnAction(e -> {
            saveStats();
            statsStage.close();
        });
        return button;
    }

    private Label createStatsTitle() {
        Label title = new Label("Final Statistics");
        title.setStyle("-fx-text-fill: white; -fx-font-size: 20; -fx-font-weight: bold;");
        return title;
    }

    private String buildStatsText() {
        return String.format(
            "Rounds Played: %d\nWins: %d\nLosses: %d\nInitial: $%,d\nFinal: $%,d\nMax Bet: $%,d\nPeak Earned: $%,d\n\nGames:\n%s",
            roundsPlayed, roundsWon, roundsLost, initialMoney, currentMoney.get(),
            highestBet, highestEarning, formatGameCounts()
        );
    }

    private String formatGameCounts() {
        StringBuilder sb = new StringBuilder();
        gameCounts.forEach((game, count) -> 
            sb.append(String.format("- %s: %d rounds\n", game, count)));
        return sb.toString();
    }

    private void saveStats() {
        String fileName = "automode_" + LocalDateTime.now().format(
            DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".txt";
        File file = new File(STATS_FILE_PATH + "\\" + fileName);
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(buildStatsText());
            Platform.runLater(() -> showSaveConfirmation(file.getAbsolutePath()));
        } catch (IOException e) {
            Platform.runLater(() -> showSaveError(e.getMessage()));
        }
    }

    private void showSaveConfirmation(String filePath) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Statistics Saved");
        alert.setContentText("Data saved to:\n" + filePath);
        styleAlert(alert.getDialogPane());
        alert.showAndWait();
    }

    private void showSaveError(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Save Failed");
        alert.setContentText("Could not save statistics:\n" + error);
        styleAlert(alert.getDialogPane());
        alert.showAndWait();
    }
}