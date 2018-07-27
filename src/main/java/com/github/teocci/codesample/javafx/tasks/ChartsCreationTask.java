package com.github.teocci.codesample.javafx.tasks;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-27
 */
public class ChartsCreationTask extends Task<Void>
{
    private final int CHART_SIZE = 600;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.SSS");
    private final Random random = new Random();

    private final int nCharts;
    private final BlockingQueue<Parent> charts;

    public ChartsCreationTask(BlockingQueue<Parent> charts, final int nCharts)
    {
        this.charts = charts;
        this.nCharts = nCharts;
        updateProgress(0, nCharts);
    }

    @Override
    protected Void call() throws Exception
    {
        int i = nCharts;
        while (i > 0) {
            if (isCancelled()) {
                break;
            }
            charts.put(createChart());
            i--;
            updateProgress(nCharts - i, nCharts);
        }

        return null;
    }

    private Parent createChart()
    {
        // create a chart.
        final PieChart chart = new PieChart();
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Grapefruit", random.nextInt(30)),
                        new PieChart.Data("Oranges", random.nextInt(30)),
                        new PieChart.Data("Plums", random.nextInt(30)),
                        new PieChart.Data("Pears", random.nextInt(30)),
                        new PieChart.Data("Apples", random.nextInt(30))
                );
        chart.setData(pieChartData);
        chart.setTitle("Imported Fruits - " + dateFormat.format(new Date()));

        // Place the chart in a container pane.
        final Pane chartContainer = new Pane();
        chartContainer.getChildren().add(chart);
        chart.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        chart.setPrefSize(CHART_SIZE, CHART_SIZE);
        chart.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        chart.setStyle("-fx-font-size: 16px;");

        return chartContainer;
    }
}