package com.github.teocci.codesample.javafx.uisamples.features;

import com.github.teocci.codesample.javafx.elements.HoveredThresholdNode;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

/**
 * Displays a LineChart which displays the value of a plotted Node when you hover over the Node.
 * <p>
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-27
 */

public class LineChartWithHover extends Application
{
    @SuppressWarnings("unchecked")
    @Override
    public void start(Stage stage)
    {
        final LineChart lineChart = new LineChart(
                new NumberAxis(),
                new NumberAxis(),
                FXCollections.observableArrayList(
                        new XYChart.Series<>(
                                "My portfolio",
                                FXCollections.observableArrayList(
                                        plot(23, 14, 15, 24, 34, 36, 22, 45, 43, 17, 29, 25)
                                )
                        )
                )
        );
        lineChart.setCursor(Cursor.CROSSHAIR);

        lineChart.setTitle("Stock Monitoring, 2013");

        stage.setScene(new Scene(lineChart, 500, 400));
        stage.show();
    }

    /**
     * @return plotted y values for monotonically increasing integer x values, starting from x=1
     */
    public ObservableList<XYChart.Data<Integer, Integer>> plot(int... y)
    {
        final ObservableList<XYChart.Data<Integer, Integer>> dataset = FXCollections.observableArrayList();
        int i = 0;
        while (i < y.length) {
            final XYChart.Data<Integer, Integer> data = new XYChart.Data<>(i + 1, y[i]);
            data.setNode(new HoveredThresholdNode((i == 0) ? 0 : y[i - 1], y[i]));

            dataset.add(data);
            i++;
        }

        return dataset;
    }


    public static void main(String[] args) { launch(args); }
}
