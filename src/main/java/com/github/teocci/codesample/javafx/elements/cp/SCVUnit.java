package com.github.teocci.codesample.javafx.elements.cp;

import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-27
 */
public class SCVUnit extends Rectangle
{
    private static final Duration TRANSLATE_DURATION = Duration.seconds(.75);
    private static final Duration ROTATE_DURATION = Duration.seconds(.5);
    private static final Duration SCALE_DURATION = Duration.seconds(.2);
    private static final double SCV_WIDTH = 50, SCV_HEIGHT = 50;

    private TranslateTransition translateTransition;
    private RotateTransition rotateTransition;
    private ScaleTransition scaleTransition;

    public SCVUnit(double x, double y)
    {
        // init the Rectangle super class
        super(x - SCV_WIDTH / 2, y - SCV_HEIGHT / 2, SCV_WIDTH, SCV_HEIGHT);

        // create the classes that take care of the animations
        translateTransition = createTranslateTransition(this);
        rotateTransition = createRotateTransition(this);
        scaleTransition = createScaleTransition(this);

        // set rounded corners
        setArcHeight(15);
        setArcWidth(15);

        // display some debug info
        System.out.println("Creating SCV");
        System.out.println(this);
    }

    public void attack()
    {
        rotateTransition.play();
    }

    public void gather()
    {
        scaleTransition.play();
    }

    public void move(double targetX, double targetY)
    {
        translateTransition.setToX(targetX);
        translateTransition.setToY(targetY);
        translateTransition.playFromStart();
    }

    @Override
    public String toString()
    {
        return "SCVUnit{" +
                "x=" + getX() +
                ", y=" + getY() +
                ", width=" + getWidth() +
                ", height=" + getHeight() +
                '}';
    }

    private ScaleTransition createScaleTransition(SCVUnit scv)
    {
        ScaleTransition scaleTransition = new ScaleTransition(SCALE_DURATION, scv);
        scaleTransition.setByX(.5);
        scaleTransition.setByY(.5);
        scaleTransition.setCycleCount(2);
        scaleTransition.setAutoReverse(true);
        return scaleTransition;
    }

    private TranslateTransition createTranslateTransition(SCVUnit scv)
    {
        TranslateTransition transition = new TranslateTransition(TRANSLATE_DURATION, scv);
        transition.setOnFinished(t -> {
            scv.setX(scv.getTranslateX() + scv.getX());
            scv.setY(scv.getTranslateY() + scv.getY());
            scv.setTranslateX(0);
            scv.setTranslateY(0);
            System.out.println(scv);
        });
        return transition;
    }

    private RotateTransition createRotateTransition(SCVUnit scv)
    {
        RotateTransition transition = new RotateTransition(ROTATE_DURATION, scv);
        transition.setByAngle(180);
        transition.setCycleCount(2);
        transition.setAutoReverse(true);
        return transition;
    }
}
