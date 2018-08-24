package com.github.teocci.codesample.javafx.elements;

import com.github.teocci.codesample.javafx.enums.BoundsType;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeType;

import static com.github.teocci.codesample.javafx.enums.BoundsType.*;

/**
 * A translucent overlay display rectangle to show the bounds of a Shape.
 */
public class BoundsDisplay  extends Rectangle {
    // the shape to which the bounds display has been type.
    private final Shape monitoredShape;
    private ChangeListener<Bounds> boundsChangeListener;

    public BoundsDisplay(final Shape shape, ObjectProperty<BoundsType> selectedBoundsType) {
        setFill(Color.LIGHTGRAY.deriveColor(1, 1, 1, 0.35));
        setStroke(Color.LIGHTGRAY.deriveColor(1, 1, 1, 0.5));
        setStrokeType(StrokeType.INSIDE);
        setStrokeWidth(3);

        monitoredShape = shape;

        monitorBounds(LAYOUT_BOUNDS, selectedBoundsType);
    }

    // set the type of the shape's bounds to monitor for the bounds display.
    public void monitorBounds(final BoundsType boundsType, ObjectProperty<BoundsType> selectedBoundsType) {
        // remove the shape's previous boundsType.
        if (boundsChangeListener != null) {
            final ReadOnlyObjectProperty<Bounds> oldBounds = getBounds(selectedBoundsType.get());
            if (oldBounds != null) {
                oldBounds.removeListener(boundsChangeListener);
            }
        }

        // determine the shape's bounds for the given boundsType.
        final ReadOnlyObjectProperty<Bounds> bounds  = getBounds(boundsType);

        // set the visual bounds display based upon the new bounds and keep it in sync.
        updateBoundsDisplay(bounds.get());

        // keep the visual bounds display based upon the new bounds and keep it in sync.
        boundsChangeListener = (observableValue, oldBounds, newBounds) -> updateBoundsDisplay(newBounds);
        bounds.addListener(boundsChangeListener);
    }

    private ReadOnlyObjectProperty<Bounds> getBounds(BoundsType boundsType) {
        switch (boundsType) {
            case LAYOUT_BOUNDS:
                 return monitoredShape.layoutBoundsProperty();
            case BOUNDS_IN_LOCAL:
                return monitoredShape.boundsInLocalProperty();
            case BOUNDS_IN_PARENT:
                return monitoredShape.boundsInParentProperty();
            default:
                return null;
        }
    }

    // update this bounds display to match a new set of bounds.
    private void updateBoundsDisplay(Bounds newBounds) {
        setX(newBounds.getMinX());
        setY(newBounds.getMinY());
        setWidth(newBounds.getWidth());
        setHeight(newBounds.getHeight());
    }
}