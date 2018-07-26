package com.github.teocci.codesample.javafx.elements;

import com.github.teocci.codesample.javafx.enums.Shapes;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import java.util.Random;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-26
 */
public class ShapeMachine
{
    private static final Random random = new Random();
    private final double canvasWidth, canvasHeight, maxShapeSize, minShapeSize;

    public ShapeMachine(double canvasWidth, double canvasHeight, double maxShapeSize, double minShapeSize)
    {
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
        this.maxShapeSize = maxShapeSize;
        this.minShapeSize = minShapeSize;
    }

    private Color randomColor()
    {
        return Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256), 0.1 + random.nextDouble() * 0.5);
    }

    public Shape randomShape()
    {
        Shape shape = null;

        switch (Shapes.values()[random.nextInt(Shapes.values().length)]) {
            case Circle:
                shape = randomCircle();
                break;
            case Rectangle:
                shape = randomRectangle();
                break;
            case Line:
                shape = randomLine();
                break;
            default:
                System.out.println("Unknown Shape");
                System.exit(1);
        }

        Color fill = randomColor();
        shape.setFill(fill);
        shape.setStroke(deriveStroke(fill));
        shape.setStrokeWidth(deriveStrokeWidth(shape));
        shape.setStrokeLineCap(StrokeLineCap.ROUND);
        shape.relocate(randomShapeX(), randomShapeY());

        return shape;
    }

    private double deriveStrokeWidth(Shape shape)
    {
        return Math.max(shape.getLayoutBounds().getWidth() / 10, shape.getLayoutBounds().getHeight() / 10);
    }

    private Color deriveStroke(Color fill)
    {
        return fill.desaturate();
    }

    private double randomShapeSize()
    {
        double range = maxShapeSize - minShapeSize;
        return random.nextDouble() * range + minShapeSize;
    }

    private double randomShapeX()
    {
        return random.nextDouble() * (canvasWidth + maxShapeSize) - maxShapeSize / 2;
    }

    private double randomShapeY()
    {
        return random.nextDouble() * (canvasHeight + maxShapeSize) - maxShapeSize / 2;
    }

    private Shape randomLine()
    {
        int xZero = random.nextBoolean() ? 1 : 0;
        int yZero = random.nextBoolean() || xZero == 0 ? 1 : 0;

        int xSign = random.nextBoolean() ? 1 : -1;
        int ySign = random.nextBoolean() ? 1 : -1;

        return new Line(0, 0, xZero * xSign * randomShapeSize(), yZero * ySign * randomShapeSize());
    }

    private Shape randomRectangle()
    {
        return new Rectangle(0, 0, randomShapeSize(), randomShapeSize());
    }

    private Shape randomCircle()
    {
        double radius = randomShapeSize() / 2;
        return new Circle(radius, radius, radius);
    }
}