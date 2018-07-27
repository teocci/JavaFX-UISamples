package com.github.teocci.codesample.javafx.uisamples.chart;

import com.github.teocci.codesample.javafx.elements.AnchorPoint;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeLineCap;
import javafx.stage.Stage;

/**
 * Drag the anchors around to change a polygon's points.
 * <p>
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-27
 */
public class TriangleManipulator extends Application
{
    // main application layout logic.
    @Override
    public void start(final Stage stage) throws Exception
    {
        Polygon triangle = createStartingTriangle();

        Group root = new Group();
        root.getChildren().add(triangle);
        root.getChildren().addAll(createControlAnchorsFor(triangle.getPoints()));

        stage.setTitle("Triangle Manipulation Sample");
        stage.setScene(
                new Scene(
                        root,
                        400, 400, Color.ALICEBLUE
                )
        );
        stage.show();
    }

    // creates a triangle.
    private Polygon createStartingTriangle()
    {
        Polygon triangle = new Polygon();

        triangle.getPoints().setAll(
                100d, 100d,
                150d, 50d,
                250d, 150d
        );

        triangle.setStroke(Color.FORESTGREEN);
        triangle.setStrokeWidth(4);
        triangle.setStrokeLineCap(StrokeLineCap.ROUND);
        triangle.setFill(Color.CORNSILK.deriveColor(0, 1.2, 1, 0.6));

        return triangle;
    }

    // @return a list of anchors which can be dragged around to modify points in the format [x1, y1, x2, y2...]
    private ObservableList<AnchorPoint> createControlAnchorsFor(final ObservableList<Double> points)
    {
        ObservableList<AnchorPoint> anchors = FXCollections.observableArrayList();

        for (int i = 0; i < points.size(); i += 2) {
            final int idx = i;

            DoubleProperty xProperty = new SimpleDoubleProperty(points.get(i));
            DoubleProperty yProperty = new SimpleDoubleProperty(points.get(i + 1));

            xProperty.addListener((ov, oldX, x) -> points.set(idx, (double) x));

            yProperty.addListener((ov, oldY, y) -> points.set(idx + 1, (double) y));

            anchors.add(new AnchorPoint(Color.GOLD, xProperty, yProperty));
        }

        return anchors;
    }

    public static void main(String[] args) throws Exception { launch(args); }
}
