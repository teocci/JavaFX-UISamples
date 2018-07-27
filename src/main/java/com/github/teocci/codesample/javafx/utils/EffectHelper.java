package com.github.teocci.codesample.javafx.utils;

import com.github.teocci.codesample.javafx.models.Delta;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.effect.Glow;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Various utilities for applying different effects to nodes.
 * <p>
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-27
 */

public class EffectHelper
{
    /**
     * Configures the node to fade when it is clicked on performed the onFinished handler when the fade is complete
     */
    public static void fadeOnClick(final Node node, final EventHandler<ActionEvent> onFinished)
    {
        node.setOnMouseClicked(mouseEvent -> {
            node.setMouseTransparent(true);
            FadeTransition fade = new FadeTransition(Duration.seconds(1.2), node);
            fade.setOnFinished(onFinished);
            fade.setFromValue(1);
            fade.setToValue(0);
            fade.play();
        });
    }

    /**
     * Adds a glow effect to a node when the mouse is hovered over the node
     */
    public static void addGlowOnHover(final Node node)
    {
        final Glow glow = new Glow();
        node.setOnMouseEntered(mouseEvent -> node.setEffect(glow));
        node.setOnMouseExited(mouseEvent -> node.setEffect(null));
    }

    /**
     * Makes a stage draggable using a given node
     */
    public static void makeDraggable(final Stage stage, final Node byNode)
    {
        final Delta dragDelta = new Delta();
        byNode.setOnMousePressed(mouseEvent -> {
            // Record a delta distance for the drag and drop operation.
            dragDelta.x = stage.getX() - mouseEvent.getScreenX();
            dragDelta.y = stage.getY() - mouseEvent.getScreenY();
            byNode.setCursor(Cursor.MOVE);
        });
        byNode.setOnMouseReleased(mouseEvent -> byNode.setCursor(Cursor.HAND));
        byNode.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX() + dragDelta.x);
            stage.setY(mouseEvent.getScreenY() + dragDelta.y);
        });
        byNode.setOnMouseEntered(mouseEvent -> {
            if (!mouseEvent.isPrimaryButtonDown()) {
                byNode.setCursor(Cursor.HAND);
            }
        });
        byNode.setOnMouseExited(mouseEvent -> {
            if (!mouseEvent.isPrimaryButtonDown()) {
                byNode.setCursor(Cursor.DEFAULT);
            }
        });
    }
}