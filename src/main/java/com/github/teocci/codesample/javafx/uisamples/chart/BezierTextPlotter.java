package com.github.teocci.codesample.javafx.uisamples.chart;

import com.github.teocci.codesample.javafx.elements.AnchorPoint;
import com.github.teocci.codesample.javafx.elements.BoundLine;
import com.github.teocci.codesample.javafx.handlers.PlotHandler;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.Glow;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Plots text along a Bézier curve in JavaFX. This sample of drawing text along a cubic curve.
 * Drag the anchors around to change the curve.
 * <p>
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-26
 */

public class BezierTextPlotter extends Application
{
    private static final String CURVED_TEXT = "Bézier Curve";

    public static void main(String[] args) throws Exception
    {
        launch(args);
    }

    @Override
    public void start(final Stage stage) throws Exception
    {
        final CubicCurve curve = createStartingCurve();

        Line controlLine1 = new BoundLine(curve.controlX1Property(), curve.controlY1Property(), curve.startXProperty(), curve.startYProperty());
        Line controlLine2 = new BoundLine(curve.controlX2Property(), curve.controlY2Property(), curve.endXProperty(), curve.endYProperty());

        AnchorPoint start = new AnchorPoint(Color.PALEGREEN, curve.startXProperty(), curve.startYProperty());
        AnchorPoint control1 = new AnchorPoint(Color.GOLD, curve.controlX1Property(), curve.controlY1Property());
        AnchorPoint control2 = new AnchorPoint(Color.GOLDENROD, curve.controlX2Property(), curve.controlY2Property());
        AnchorPoint end = new AnchorPoint(Color.TOMATO, curve.endXProperty(), curve.endYProperty());

        final Text text = new Text(CURVED_TEXT);
        text.setStyle("-fx-font-size: 40px");
        text.setEffect(new Glow());
        final ObservableList<Text> parts = FXCollections.observableArrayList();
        final ObservableList<PathTransition> transitions = FXCollections.observableArrayList();
        for (char character : text.textProperty().get().toCharArray()) {
            Text part = new Text(character + "");
            part.setEffect(text.getEffect());
            part.setStyle(text.getStyle());
            parts.add(part);
            part.setVisible(false);

            transitions.add(createPathTransition(curve, part));
        }

        final ObservableList<Node> controls = FXCollections.observableArrayList();
        controls.setAll(controlLine1, controlLine2, curve, start, control1, control2, end);

        final ToggleButton plot = new ToggleButton("Plot Text");
        plot.setOnAction(new PlotHandler(plot, parts, transitions, controls));

        Group content = new Group(controlLine1, controlLine2, curve, start, control1, control2, end, plot);
        content.getChildren().addAll(parts);

        stage.setTitle("Cubic Curve Manipulation Sample");
        stage.setScene(new Scene(content, 400, 400, Color.ALICEBLUE));
        stage.show();
    }

    private PathTransition createPathTransition(CubicCurve curve, Text text)
    {
        final PathTransition transition = new PathTransition(Duration.seconds(10), curve, text);

        transition.setAutoReverse(false);
        transition.setCycleCount(PathTransition.INDEFINITE);
        transition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        transition.setInterpolator(Interpolator.LINEAR);

        return transition;
    }

    private CubicCurve createStartingCurve()
    {
        CubicCurve curve = new CubicCurve();
        curve.setStartX(50);
        curve.setStartY(200);
        curve.setControlX1(150);
        curve.setControlY1(300);
        curve.setControlX2(250);
        curve.setControlY2(50);
        curve.setEndX(350);
        curve.setEndY(150);
        curve.setStroke(Color.FORESTGREEN);
        curve.setStrokeWidth(4);
        curve.setStrokeLineCap(StrokeLineCap.ROUND);
        curve.setFill(Color.CORNSILK.deriveColor(0, 1.2, 1, 0.6));

        return curve;
    }
}