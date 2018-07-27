package com.github.teocci.codesample.javafx.uisamples.chart;

import javafx.application.Application;
import javafx.collections.*;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.chart.*;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-27
 */

public class PieChartWithCustomColors extends Application
{
    @Override
    public void start(Stage stage)
    {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Grapefruit", 13),
                new PieChart.Data("Oranges", 25),
                new PieChart.Data("Plums", 10),
                new PieChart.Data("Pears", 22),
                new PieChart.Data("Apples", 30)
        );

        final PieChart chart = new PieChart(pieChartData);
        chart.setLegendVisible(true);

        Scene scene = new Scene(chart);
        scene.getStylesheets().add("/css/pie-chart-custom-colors.css");

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) { launch(args); }
}