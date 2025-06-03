package gameChart;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.input.ScrollEvent;

public final class ChartInteractions {
    
    public static void enablePanAndZoom(LineChart<Number, Number> chart, 
                                      NumberAxis xAxis, 
                                      NumberAxis yAxis) {
        enablePan(chart, xAxis, yAxis);
        enableZoom(chart, xAxis, yAxis);
    }

    private static void enablePan(LineChart<Number, Number> chart, 
                                NumberAxis xAxis, 
                                NumberAxis yAxis) {
        final double[] dragStart = new double[2];
        final double[] initialBounds = new double[4];

        chart.setOnMousePressed(e -> {
            if (e.isPrimaryButtonDown()) {
                dragStart[0] = e.getX();
                dragStart[1] = e.getY();
                storeAxisBounds(xAxis, yAxis, initialBounds);
            }
        });

        chart.setOnMouseDragged(e -> {
            if (e.isPrimaryButtonDown()) {
                updateAxisBounds(chart, xAxis, yAxis, dragStart, initialBounds, e);
            }
        });
    }

    private static void enableZoom(LineChart<Number, Number> chart,
                                 NumberAxis xAxis,
                                 NumberAxis yAxis) {
        chart.setOnScroll(e -> {
            if (e.isControlDown()) {
                handleZoom(e, xAxis, yAxis, e.getDeltaY() > 0 ? 0.9 : 1.1);
                e.consume();
            }
        });
    }

    private static void handleZoom(ScrollEvent e, 
                                 NumberAxis xAxis, 
                                 NumberAxis yAxis, 
                                 double zoomFactor) {
        double xValue = xAxis.getValueForDisplay(e.getX()).doubleValue();
        double yValue = yAxis.getValueForDisplay(e.getY()).doubleValue();

        updateAxis(xAxis, 
            xValue - (xValue - xAxis.getLowerBound()) * zoomFactor,
            xValue + (xAxis.getUpperBound() - xValue) * zoomFactor
        );
        
        updateAxis(yAxis,
            yValue - (yValue - yAxis.getLowerBound()) * zoomFactor,
            yValue + (yAxis.getUpperBound() - yValue) * zoomFactor
        );
    }

    private static void storeAxisBounds(NumberAxis xAxis, 
                                      NumberAxis yAxis, 
                                      double[] bounds) {
        bounds[0] = xAxis.getLowerBound();
        bounds[1] = xAxis.getUpperBound();
        bounds[2] = yAxis.getLowerBound();
        bounds[3] = yAxis.getUpperBound();
    }

    private static void updateAxisBounds(LineChart<Number, Number> chart,
                                       NumberAxis xAxis,
                                       NumberAxis yAxis,
                                       double[] dragStart,
                                       double[] initialBounds,
                                       javafx.scene.input.MouseEvent e) {
        double deltaX = e.getX() - dragStart[0];
        double deltaY = e.getY() - dragStart[1];
        
        double xScale = (initialBounds[1] - initialBounds[0]) / chart.getBoundsInLocal().getWidth();
        double yScale = (initialBounds[3] - initialBounds[2]) / chart.getBoundsInLocal().getHeight();

        updateAxis(xAxis, 
            initialBounds[0] - deltaX * xScale,
            initialBounds[1] - deltaX * xScale
        );
        
        updateAxis(yAxis,
            initialBounds[2] + deltaY * yScale,
            initialBounds[3] + deltaY * yScale
        );
    }

    private static void updateAxis(NumberAxis axis, double lower, double upper) {
        axis.setAutoRanging(false);
        axis.setLowerBound(Math.max(lower, 0));
        axis.setUpperBound(Math.max(upper, axis.getLowerBound() + 1));
    }
}