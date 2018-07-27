package com.github.teocci.codesample.javafx.uisamples.chart;

import com.github.teocci.codesample.javafx.elements.AnchorPoint;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.stage.Stage;

/**
 * Example of how a cubic curve works, drag the anchors around to change the curve. Demonstrates manipulation of
 * a CubicCurve in JavaFX by allowing the user to drag around the endpoints and control points of the curve.
 * <p>
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-27
 */

public class CubicCurveManipulator extends Application
{
    @Override
    public void start(final Stage stage) throws Exception
    {
        CubicCurve curve = createStartingCurve();

        Line controlLine1 = new BoundLine(curve.controlX1Property(), curve.controlY1Property(), curve.startXProperty(), curve.startYProperty());
        Line controlLine2 = new BoundLine(curve.controlX2Property(), curve.controlY2Property(), curve.endXProperty(), curve.endYProperty());

        AnchorPoint start = new AnchorPoint(Color.PALEGREEN, curve.startXProperty(), curve.startYProperty());
        AnchorPoint control1 = new AnchorPoint(Color.GOLD, curve.controlX1Property(), curve.controlY1Property());
        AnchorPoint control2 = new AnchorPoint(Color.GOLDENROD, curve.controlX2Property(), curve.controlY2Property());
        AnchorPoint end = new AnchorPoint(Color.TOMATO, curve.endXProperty(), curve.endYProperty());

        stage.setTitle("Cubic Curve Manipulation Sample");
        stage.setScene(new Scene(new Group(controlLine1, controlLine2, curve, start, control1, control2, end), 400, 400, Color.ALICEBLUE));
        stage.show();
    }

    private CubicCurve createStartingCurve()
    {
        CubicCurve curve = new CubicCurve();
        curve.setStartX(100);
        curve.setStartY(100);
        curve.setControlX1(150);
        curve.setControlY1(50);
        curve.setControlX2(250);
        curve.setControlY2(150);
        curve.setEndX(300);
        curve.setEndY(100);
        curve.setStroke(Color.FORESTGREEN);
        curve.setStrokeWidth(4);
        curve.setStrokeLineCap(StrokeLineCap.ROUND);
        curve.setFill(Color.CORNSILK.deriveColor(0, 1.2, 1, 0.8));

        return curve;
    }

    class BoundLine extends Line
    {
        BoundLine(DoubleProperty startX, DoubleProperty startY, DoubleProperty endX, DoubleProperty endY)
        {
            startXProperty().bind(startX);
            startYProperty().bind(startY);
            endXProperty().bind(endX);
            endYProperty().bind(endY);
            setStrokeWidth(2);
            setStroke(Color.GRAY.deriveColor(0, 1, 1, 0.5));
            setStrokeLineCap(StrokeLineCap.BUTT);
            getStrokeDashArray().setAll(10.0, 5.0);
        }
    }

    public static void main(String[] args) throws Exception { launch(args); }
}