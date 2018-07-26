package com.github.teocci.codesample.javafx.uisamples;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-26
 */
public class LayerClick extends Application
{
    @Override
    public void start(Stage stage) throws Exception
    {
        ToggleButton testButton = new ToggleButton("");

        VBox layer1 = new VBox();
        layer1.getChildren().add(testButton);

        Node layer2 = new Circle(100, 100, 100, Color.FIREBRICK);
        layer2.setOpacity(0.7);

        StackPane stack = new StackPane();
        stack.setStyle("-fx-background-color: azure;");
        stack.getChildren().setAll(layer1, layer2);

        VBox layout = new VBox();
        layout.getChildren().setAll(stack, createControls(testButton, layer2));

        stage.setScene(new Scene(layout));
        stage.show();
    }

    private VBox createControls(ToggleButton controlledButton, Node controlledNode)
    {
        controlledButton.textProperty().bind(Bindings
                .when(controlledNode.mouseTransparentProperty())
                .then("Completely Clickable")
                .otherwise(Bindings
                        .when(controlledNode.pickOnBoundsProperty())
                        .then("Not Clickable")
                        .otherwise("Partially clickable")
                )
        );

        CheckBox enableMouseTransparency = new CheckBox("Enable MouseTransparency");
        enableMouseTransparency.setSelected(controlledNode.isMouseTransparent());
        controlledNode.mouseTransparentProperty().bind(enableMouseTransparency.selectedProperty());

        CheckBox enablePickOnBounds = new CheckBox("Enable Pick On Bounds");
        enablePickOnBounds.setSelected(controlledNode.isPickOnBounds());
        controlledNode.pickOnBoundsProperty().bind(enablePickOnBounds.selectedProperty());

        VBox controls = new VBox(10);
        controls.setStyle("-fx-background-color: cornsilk; -fx-padding: 10;");
        controls.getChildren().addAll(enableMouseTransparency, enablePickOnBounds);

        return controls;
    }

    public static void main(String[] args) { launch(args); }
}