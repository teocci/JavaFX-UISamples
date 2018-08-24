package com.github.teocci.codesample.javafx.uisamples.animation;

import com.github.teocci.codesample.javafx.elements.AnchorPoint;
import com.github.teocci.codesample.javafx.elements.BoundsDisplay;
import com.github.teocci.codesample.javafx.elements.ShapePair;
import com.github.teocci.codesample.javafx.enums.BoundsType;
import com.github.teocci.codesample.javafx.models.Delta;
import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import static com.github.teocci.codesample.javafx.enums.BoundsType.*;

public class BoundsPlayground extends Application {
    private final ObservableList<Shape> shapes = FXCollections.observableArrayList();
    private final ObservableList<ShapePair> intersections = FXCollections.observableArrayList();

    private ObjectProperty<BoundsType> selectedBoundsType = new SimpleObjectProperty<>(LAYOUT_BOUNDS);

    @Override
    public void start(Stage stage) {
        stage.setTitle("Bounds Playground");

        // define some objects to manipulate on the scene.
        Circle greenCircle = new Circle(100, 100, 50, Color.FORESTGREEN);
        greenCircle.setId("Green Circle");
        Circle redCircle = new Circle(300, 200, 50, Color.FIREBRICK);
        redCircle.setId("Red Circle");

        Line line = new Line(25, 300, 375, 200);
        line.setId("Line");
        line.setStrokeLineCap(StrokeLineCap.ROUND);
        line.setStroke(Color.MIDNIGHTBLUE);
        line.setStrokeWidth(5);

        final AnchorPoint anchor1 = new AnchorPoint("Anchor 1", line.startXProperty(), line.startYProperty());
        final AnchorPoint anchor2 = new AnchorPoint("Anchor 2", line.endXProperty(), line.endYProperty());

        final Group group = new Group(greenCircle, redCircle, line, anchor1, anchor2);

        // monitor intersections of shapes in the scene.
        for (Node node : group.getChildrenUnmodifiable()) {
            if (node instanceof Shape) {
                shapes.add((Shape) node);
            }
        }
        testIntersections();

        // Enable dragging for the scene objects.
        Circle[] circles = {greenCircle, redCircle, anchor1, anchor2};
        for (Circle circle : circles) {
            enableDrag(circle);
            circle.centerXProperty().addListener((observableValue, oldValue, newValue) -> testIntersections());
            circle.centerYProperty().addListener((observableValue, oldValue, newValue) -> testIntersections());
        }

        // Define an overlay to show the layout bounds of the scene's shapes.
        Group layoutBoundsOverlay = new Group();
        layoutBoundsOverlay.setMouseTransparent(true);
        for (Shape shape : shapes) {
            if (!(shape instanceof AnchorPoint)) {
                layoutBoundsOverlay.getChildren().add(new BoundsDisplay(shape, selectedBoundsType));
            }
        }

        // layout the scene.
        final StackPane background = new StackPane();
        background.setStyle("-fx-background-color: cornsilk;");
        final Scene scene = new Scene(new Group(background, group, layoutBoundsOverlay), 600, 500);
        background.prefHeightProperty().bind(scene.heightProperty());
        background.prefWidthProperty().bind(scene.widthProperty());
        stage.setScene(scene);
        stage.show();

        createUtilityWindow(stage, layoutBoundsOverlay, new Shape[]{greenCircle, redCircle});
    }

    // update the list of intersections.
    private void testIntersections() {
        intersections.clear();

        // for each shape test it's intersection with all other shapes.
        for (Shape src : shapes) {
            for (Shape dest : shapes) {
                ShapePair pair = new ShapePair(src, dest);
                if ((!(pair.getA() instanceof AnchorPoint) && !(pair.getB() instanceof AnchorPoint))
                        && !intersections.contains(pair)
                        && pair.intersects(selectedBoundsType.get())) {
                    intersections.add(pair);
                }
            }
        }
    }

    // make a node movable by dragging it around with the mouse.
    private void enableDrag(final Circle circle) {
        final Delta dragDelta = new Delta();
        circle.setOnMousePressed(mouseEvent -> {
            // record a delta distance for the drag and drop operation.
            dragDelta.x = circle.getCenterX() - mouseEvent.getX();
            dragDelta.y = circle.getCenterY() - mouseEvent.getY();
            circle.getScene().setCursor(Cursor.MOVE);
        });
        circle.setOnMouseReleased(mouseEvent -> circle.getScene().setCursor(Cursor.HAND));
        circle.setOnMouseDragged(mouseEvent -> {
            circle.setCenterX(mouseEvent.getX() + dragDelta.x);
            circle.setCenterY(mouseEvent.getY() + dragDelta.y);
        });
        circle.setOnMouseEntered(mouseEvent -> {
            if (!mouseEvent.isPrimaryButtonDown()) {
                circle.getScene().setCursor(Cursor.HAND);
            }
        });
        circle.setOnMouseExited(mouseEvent -> {
            if (!mouseEvent.isPrimaryButtonDown()) {
                circle.getScene().setCursor(Cursor.DEFAULT);
            }
        });
    }

    // define a utility stage for reporting intersections.
    private void createUtilityWindow(Stage stage, final Group boundsOverlay, final Shape[] transformableShapes) {
        final Stage reportingStage = new Stage();
        reportingStage.setTitle("Control Panel");
        reportingStage.initStyle(StageStyle.UTILITY);
        reportingStage.setX(stage.getX() + stage.getWidth());
        reportingStage.setY(stage.getY());

        // define content for the intersections utility panel.
        final ListView<ShapePair> intersectionView = new ListView<>(intersections);
        final Label instructions = new Label(
                "Click on any circle in the scene to the left to drag it around."
        );
        instructions.setMinSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
        instructions.setStyle("-fx-font-weight: bold; -fx-text-fill: darkgreen;");

        final Label intersectionInstructions = new Label(
                "Any intersecting bounds in the scene will be reported below."
        );
        instructions.setMinSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);

        // add the ability to set a translate value for the circles.
        final CheckBox translateNodes = new CheckBox("Translate circles");
        translateNodes.selectedProperty().addListener((observableValue, oldValue, doTranslate) -> {
            if (doTranslate) {
                for (Shape shape : transformableShapes) {
                    shape.setTranslateY(100);
                    testIntersections();
                }
            } else {
                for (Shape shape : transformableShapes) {
                    shape.setTranslateY(0);
                    testIntersections();
                }
            }
        });
        translateNodes.selectedProperty().set(false);

        // add the ability to add an effect to the circles.
        final Label modifyInstructions = new Label(
                "Modify visual display aspects."
        );
        modifyInstructions.setStyle("-fx-font-weight: bold;");
        modifyInstructions.setMinSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
        final CheckBox effectNodes = new CheckBox("Add an effect to circles");
        effectNodes.selectedProperty().addListener((observableValue, oldValue, doTranslate) -> {
            if (doTranslate) {
                for (Shape shape : transformableShapes) {
                    shape.setEffect(new DropShadow());
                    testIntersections();
                }
            } else {
                for (Shape shape : transformableShapes) {
                    shape.setEffect(null);
                    testIntersections();
                }
            }
        });
        effectNodes.selectedProperty().set(true);

        // add the ability to add a stroke to the circles.
        final CheckBox strokeNodes = new CheckBox("Add outside strokes to circles");
        strokeNodes.selectedProperty().addListener((observableValue, oldValue, doTranslate) -> {
            if (doTranslate) {
                for (Shape shape : transformableShapes) {
                    shape.setStroke(Color.LIGHTSEAGREEN);
                    shape.setStrokeWidth(10);
                    testIntersections();
                }
            } else {
                for (Shape shape : transformableShapes) {
                    shape.setStrokeWidth(0);
                    testIntersections();
                }
            }
        });
        strokeNodes.selectedProperty().set(true);

        // add the ability to show or hide the layout bounds overlay.
        final Label showBoundsInstructions = new Label(
                "The gray squares represent layout bounds."
        );
        showBoundsInstructions.setStyle("-fx-font-weight: bold;");
        showBoundsInstructions.setMinSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
        final CheckBox showBounds = new CheckBox("Show Bounds");
        boundsOverlay.visibleProperty().bind(showBounds.selectedProperty());
        showBounds.selectedProperty().set(true);

        // create a container for the display control checkboxes.
        VBox displayChecks = new VBox(10);
        displayChecks.getChildren().addAll(modifyInstructions, translateNodes, effectNodes, strokeNodes, showBoundsInstructions, showBounds);

        // create a toggle group for the bounds type to use.
        ToggleGroup boundsToggleGroup = new ToggleGroup();
        final RadioButton useLayoutBounds = new RadioButton("Use Layout Bounds");
        final RadioButton useBoundsInLocal = new RadioButton("Use Bounds in Local");
        final RadioButton useBoundsInParent = new RadioButton("Use Bounds in Parent");

        useLayoutBounds.setToggleGroup(boundsToggleGroup);
        useBoundsInLocal.setToggleGroup(boundsToggleGroup);
        useBoundsInParent.setToggleGroup(boundsToggleGroup);

        VBox boundsToggles = new VBox(10);
        boundsToggles.getChildren().addAll(useLayoutBounds, useBoundsInLocal, useBoundsInParent);

        // change the layout bounds display depending on which bounds type has been selected.
        useLayoutBounds.selectedProperty().addListener(
                (observableValue, aBoolean, isSelected) -> changeBoundsDisplay(isSelected, boundsOverlay, LAYOUT_BOUNDS)
        );
        useBoundsInLocal.selectedProperty().addListener(
                (observableValue, aBoolean, isSelected) -> changeBoundsDisplay(isSelected, boundsOverlay, BOUNDS_IN_LOCAL)
        );
        useBoundsInParent.selectedProperty().addListener(
                (observableValue, aBoolean, isSelected) -> changeBoundsDisplay(isSelected, boundsOverlay, BOUNDS_IN_PARENT)
        );
        useLayoutBounds.selectedProperty().set(true);

        WebView boundsExplanation = new WebView();
        boundsExplanation.getEngine().loadContent(
                "<html><body bgcolor='darkseagreen' fgcolor='lightgrey' style='font-size:12px'><dl>" +
                        "<dt><b>Layout Bounds</b></dt><dd>The boundary of the shape.</dd><br/>" +
                        "<dt><b>Bounds in Local</b></dt><dd>The boundary of the shape and effect.</dd><br/>" +
                        "<dt><b>Bounds in Parent</b></dt><dd>The boundary of the shape, effect and transforms.<br/>The co-ordinates of what you see.</dd>" +
                        "</dl></body></html>"
        );
        boundsExplanation.setPrefWidth(100);
        boundsExplanation.setMinHeight(130);
        boundsExplanation.setMaxHeight(130);
        boundsExplanation.setStyle("-fx-background-color: transparent");

        // layout the utility pane.
        VBox utilityLayout = new VBox(10);
        utilityLayout.setStyle("-fx-padding:10; -fx-background-color: linear-gradient(to bottom, lightblue, derive(lightblue, 20%));");
        utilityLayout.getChildren().addAll(instructions, intersectionInstructions, intersectionView, displayChecks, boundsToggles, boundsExplanation);
        utilityLayout.setPrefHeight(530);
        reportingStage.setScene(new Scene(utilityLayout));
        reportingStage.show();

        // ensure the utility window closes when the main app window closes.
        stage.setOnCloseRequest(windowEvent -> reportingStage.close());
    }

    private void changeBoundsDisplay(boolean isSelected, Group boundsOverlay, BoundsType boundsInLocal) {
        if (isSelected) {
            for (Node overlay : boundsOverlay.getChildren()) {
                ((BoundsDisplay) overlay).monitorBounds(boundsInLocal, selectedBoundsType);
            }
            selectedBoundsType.set(boundsInLocal);
            testIntersections();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
