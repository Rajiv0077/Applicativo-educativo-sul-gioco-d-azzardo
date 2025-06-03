package gameChart;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import main.Player;
import main.Alerter;
import main.Main;

import java.io.IOException;
import java.util.Map;

public class GameChart implements Alerter {
    
    private final ChartDataManager dataManager;
    private final XYChart.Series<Number, Number> series = new XYChart.Series<>();
    private final Player player;
    private Stage chartStage;

    public GameChart() {
        this.player = Main.getPlayer();
        this.dataManager = new ChartDataManager(player.getName());
        initializeChart();
    }

    private void initializeChart() {
        series.setName("Game Progress");
        series.setData(FXCollections.observableArrayList());
        
        try {
            dataManager.initializeFile(player.getMoney());
            Map<Integer, Integer> data = dataManager.loadData();
            data.forEach(this::addDataPoint);
        } catch (IOException e) {
            showError("Chart initialization failed: " + e.getMessage());
        }
    }

    public void showChartWindow() {
        Platform.runLater(() -> {
            if (chartStage == null) {
                chartStage = new Stage();
                chartStage.setTitle("Game Progress Chart");
                chartStage.setScene(createChartScene());
                chartStage.setOnCloseRequest(e -> chartStage = null);
            }
            chartStage.show();
        });
    }

    private Scene createChartScene() {
        NumberAxis xAxis = createAxis("Round");
        NumberAxis yAxis = createAxis("Money");
        
        LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);
        configureChart(chart);
        
        return new Scene(createMainLayout(chart), 800, 600, Color.web("#2b2b2b"));
    }

    private VBox createMainLayout(LineChart<Number, Number> chart) {
        VBox container = new VBox(10);
        container.getChildren().addAll(
            wrapChart(chart),
            new StatsPanel(player),
            new ColorLegend()
        );
        container.setStyle("-fx-background-color: #2b2b2b;");
        return container;
    }

    private void configureChart(LineChart<Number, Number> chart) {
        chart.getData().add(series);
        ChartInteractions.enablePanAndZoom(chart, 
            (NumberAxis) chart.getXAxis(), 
            (NumberAxis) chart.getYAxis());
        
        chart.setStyle("-fx-background-color: #2b2b2b;" + 
                      "-fx-chart-plot-background: #3b3b3b;" +
                      "-fx-legend-visible: false;" + 
                      "-fx-title-fill: white;");
    }

    public void addData(int round, int money) {
        Platform.runLater(() -> {
            addDataPoint(round, money);
            updateDataStore();
        });
    }

    private void addDataPoint(int x, int y) {
        XYChart.Data<Number, Number> point = new XYChart.Data<>(x, y);
        styleDataPoint(point);
        series.getData().add(point);
    }

    private void styleDataPoint(XYChart.Data<Number, Number> point) {
        point.nodeProperty().addListener((obs, oldNode, newNode) -> {
            if (newNode != null) {
                newNode.setStyle("-fx-background-color: " + 
                    getPointColor(point) + "; -fx-scale-x: 1.5; -fx-scale-y: 1.5;");
            }
        });
    }

    private String getPointColor(XYChart.Data<Number, Number> point) {
        int index = series.getData().indexOf(point);
        if (index == 0) return "#2196F3";
        
        int prevY = series.getData().get(index - 1).getYValue().intValue();
        int currentY = point.getYValue().intValue();
        
        return currentY > prevY ? "#4CAF50" : 
               currentY < prevY ? "#F44336" : "#2196F3";
    }

    private void updateDataStore() {
        try {
            Map<Integer, Integer> currentData = dataManager.loadData();
            series.getData().forEach(d -> 
                currentData.put(d.getXValue().intValue(), d.getYValue().intValue()));
            dataManager.saveData(currentData);
        } catch (IOException e) {
            showError("Failed to save chart data: " + e.getMessage());
        }
    }

    private NumberAxis createAxis(String label) {
        NumberAxis axis = new NumberAxis();
        axis.setLabel(label);
        axis.setTickUnit(1);
        axis.setMinorTickVisible(false);
        axis.setStyle("-fx-tick-label-fill: white; -fx-axis-label-fill: white;");
        return axis;
    }

    @Override
    public void showError(String message) {
        Platform.runLater(() -> {
            new Alert(Alert.AlertType.ERROR, message).show();
        });
    }

    // Nested UI Components
    private class StatsPanel extends GridPane {
        StatsPanel(Player player) {
            setHgap(20);
            setVgap(10);
            setStyle("-fx-padding: 15; -fx-background-color: #3b3b3b;");
            
            addRow(0, styledLabel("Current Money:"), styledValue("$" + player.getMoney()));
            addRow(1, styledLabel("Highest Earnings:"), styledValue("$" + player.getHighestEarning()));
            addRow(2, styledLabel("Net Profit:"), styledValue("$" + (player.getMoney() - player.getHighestEarning())));
            addRow(3, styledLabel("Win/Loss Ratio:"), styledValue(String.format("%.2f", 
                (double) player.getGamesWon() / Math.max(player.getGamesLost(), 1))));
            addRow(4, styledLabel("Games Played:"), styledValue(player.getGamesPlayed().toString()));
        }
        
        private Label styledLabel(String text) {
            Label label = new Label(text);
            label.setTextFill(Color.WHITE);
            return label;
        }
        
        private Label styledValue(String text) {
            Label label = new Label(text);
            label.setTextFill(Color.WHITE);
            return label;
        }
    }

    private class ColorLegend extends VBox { //inner class
        ColorLegend() {
            setSpacing(10);
            setStyle("-fx-padding: 15; -fx-background-color: #3b3b3b;");
            
            getChildren().addAll(
                createLegendItem("#4CAF50", "Money increased"),
                createLegendItem("#F44336", "Money decreased"),
                createLegendItem("#2196F3", "No change")
            );
        }
        
        private HBox createLegendItem(String color, String text) {
            Circle dot = new Circle(8, Color.web(color));
            Label label = new Label(text);
            label.setTextFill(Color.WHITE);
            return new HBox(10, dot, label);
        }
    }

    private StackPane wrapChart(LineChart<Number, Number> chart) {
        StackPane wrapper = new StackPane(chart);
        wrapper.setStyle("-fx-background-color: #2b2b2b;");
        chart.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        StackPane.setAlignment(chart, Pos.CENTER);
        return wrapper;
    }
    
    //methods to maintain backward compatibility
    public boolean isWindowOpen() {
        return chartStage != null && chartStage.isShowing();
    }

    public void showGraphAlreadyOpenAlert() {
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Graph Already Open");
            alert.setContentText("The chart window is already open. Close it before opening a new one.");
            alert.showAndWait();
        });
    }
}