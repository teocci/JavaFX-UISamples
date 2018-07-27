package com.github.teocci.codesample.javafx.elements;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Shape;

/**
 * A simple custom legend for a three valued chart.
 * <p>
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-27
 */
public class LevelLegend extends HBox
{
    public LevelLegend()
    {
        setSpacing(10);
        getChildren().add(new HBox(10, createSymbol("-fx-exceeded"), new Label("Exceeded")));
        getChildren().add(new HBox(10, createSymbol("-fx-achieved"), new Label("Achieved")));
        getChildren().add(new HBox(10, createSymbol("-fx-not-achieved"), new Label("Not Achieved")));

        getStyleClass().add("level-legend");
    }

    /**
     * Create a custom symbol for a custom chart legend with the given fillStyle style string.
     */
    private Node createSymbol(String fillStyle)
    {
        Shape symbol = new Ellipse(10, 5, 10, 5);
        symbol.setStyle("-fx-fill: " + fillStyle);
        symbol.setStroke(Color.BLACK);
        symbol.setStrokeWidth(1);

        return symbol;
    }
}