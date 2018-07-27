package com.github.teocci.codesample.javafx.uisamples.chart;

import com.github.teocci.codesample.javafx.elements.LevelLegend;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Displays a bar with a single series whose bars are different colors depending upon the bar value.
 * A custom legend is created and displayed for the bar data.
 * Bars in the chart are customized to include a text label of the bar's data value above the bar.
 * <p>
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-27
 */
public class DynamicallyColoredBarChartWithLabel extends Application
{
    @Override
    public void start(Stage stage)
    {
        final CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Bars");
        final NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Value");

        final BarChart<String, Number> bc = new BarChart<>(xAxis, yAxis);
        bc.setLegendVisible(false);

        XYChart.Series series1 = new XYChart.Series();

        for (int i = 0; i < 10; i++) {
            final XYChart.Data<String, Number> data = new XYChart.Data<>("Value " + i, i);
            data.nodeProperty().addListener((ov, oldNode, node) -> {
                if (node != null) {
                    setNodeStyle(data);
                    displayLabelForData(data);
                }
            });
            series1.getData().add(data);
        }
        bc.getData().add(series1);

        LevelLegend legend = new LevelLegend();
        legend.setAlignment(Pos.CENTER);

        VBox chartWithLegend = new VBox();
        chartWithLegend.getChildren().setAll(bc, legend);
        VBox.setVgrow(bc, Priority.ALWAYS);

        chartWithLegend.getStylesheets().add(getClass().getResource("/css/colored-chart.css").toExternalForm());

        stage.setScene(new Scene(chartWithLegend));
        stage.setMinHeight(400);
        stage.setMinWidth(400);
        stage.show();
    }

    /**
     * Change color of bar if value of i is <5 then red, if >5 then green if i>8 then blue
     */
    private void setNodeStyle(XYChart.Data<String, Number> data)
    {
        Node node = data.getNode();
        if (data.getYValue().intValue() > 8) {
            node.setStyle("-fx-bar-fill: -fx-exceeded;");
        } else if (data.getYValue().intValue() > 5) {
            node.setStyle("-fx-bar-fill: -fx-achieved;");
        } else {
            node.setStyle("-fx-bar-fill: -fx-not-achieved;");
        }
    }

    /**
     * places a text label with a bar's value above a bar node for a given XYChart.Data
     */
    private void displayLabelForData(XYChart.Data<String, Number> data)
    {
        final Node node = data.getNode();
        final Text dataText = new Text(data.getYValue() + "");
        node.parentProperty().addListener((ov, oldParent, parent) -> {
            Group parentGroup = (Group) parent;
            parentGroup.getChildren().add(dataText);
        });

        node.boundsInParentProperty().addListener((ov, oldBounds, bounds) -> {
            dataText.setLayoutX(
                    Math.round(
                            bounds.getMinX() + bounds.getWidth() / 2 - dataText.prefWidth(-1) / 2
                    )
            );
            dataText.setLayoutY(
                    Math.round(
                            bounds.getMinY() - dataText.prefHeight(-1) * 0.5
                    )
            );
        });
    }



    public static void main(String[] args) { launch(args); }
}
