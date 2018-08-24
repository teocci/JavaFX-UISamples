package com.github.teocci.codesample.javafx.elements;

import com.github.teocci.codesample.javafx.enums.BoundsType;
import javafx.scene.shape.Shape;

/**
 * Records a pair of (possibly) intersecting shapes.
 */
public class ShapePair {
    private Shape a, b;

    public ShapePair(Shape src, Shape dest) {
        this.a = src;
        this.b = dest;
    }

    public boolean intersects(BoundsType boundsType) {
        if (a == b) return false;

        a.intersects(b.getBoundsInLocal());
        switch (boundsType) {
            case LAYOUT_BOUNDS:
                return a.getLayoutBounds().intersects(b.getLayoutBounds());
            case BOUNDS_IN_LOCAL:
                return a.getBoundsInLocal().intersects(b.getBoundsInLocal());
            case BOUNDS_IN_PARENT:
                return a.getBoundsInParent().intersects(b.getBoundsInParent());
            default:
                return false;
        }
    }

    @Override
    public String toString() {
        return a.getId() + " : " + b.getId();
    }

    @Override
    public boolean equals(Object other) {
        ShapePair o = (ShapePair) other;
        return o != null && ((a == o.a && b == o.b) || (a == o.b) && (b == o.a));
    }

    @Override
    public int hashCode() {
        int result = a != null ? a.hashCode() : 0;
        result = 31 * result + (b != null ? b.hashCode() : 0);
        return result;
    }

    public Shape getA() {
        return a;
    }

    public Shape getB() {
        return b;
    }
}
