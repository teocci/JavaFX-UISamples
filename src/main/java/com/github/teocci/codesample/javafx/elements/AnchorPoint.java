package com.github.teocci.codesample.javafx.elements;

import com.github.teocci.codesample.javafx.models.Delta;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Cursor;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;

/**
 * A draggable anchor displayed around a point.
 * <p>
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-26
 */
public class AnchorPoint extends Circle
{
    public AnchorPoint(Color color, DoubleProperty x, DoubleProperty y)
    {
        super(x.get(), y.get(), 5);
        setFill(color.deriveColor(1, 1, 1, 0.5));
        setStroke(color);
        setStrokeWidth(2);
        setStrokeType(StrokeType.OUTSIDE);

        x.bind(centerXProperty());
        y.bind(centerYProperty());
        enableDrag();
    }

    // make a node movable by dragging it around with the mouse.
    private void enableDrag()
    {
        final Delta dragDelta = new Delta();
        setOnMousePressed(mouseEvent -> {
            // record a delta distance for the drag and drop operation.
            dragDelta.x = getCenterX() - mouseEvent.getX();
            dragDelta.y = getCenterY() - mouseEvent.getY();
            getScene().setCursor(Cursor.MOVE);
        });
        setOnMouseReleased(mouseEvent -> getScene().setCursor(Cursor.HAND));
        setOnMouseDragged(mouseEvent -> {
            double newX = mouseEvent.getX() + dragDelta.x;
            if (newX > 0 && newX < getScene().getWidth()) {
                setCenterX(newX);
            }
            double newY = mouseEvent.getY() + dragDelta.y;
            if (newY > 0 && newY < getScene().getHeight()) {
                setCenterY(newY);
            }
        });
        setOnMouseEntered(mouseEvent -> {
            if (!mouseEvent.isPrimaryButtonDown()) {
                getScene().setCursor(Cursor.HAND);
            }
        });
        setOnMouseExited(mouseEvent -> {
            if (!mouseEvent.isPrimaryButtonDown()) {
                getScene().setCursor(Cursor.DEFAULT);
            }
        });
    }
}
