package com.github.teocci.codesample.javafx.transitions;

import javafx.animation.Transition;
import javafx.scene.Node;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-26
 */
public abstract class MoveTransition extends Transition
{
    private final Duration MOVEMENT_ANIMATION_DURATION = new Duration(1000);

    protected final Translate translate;

    public MoveTransition(final Node node)
    {
        setCycleDuration(MOVEMENT_ANIMATION_DURATION);
        translate = new Translate();

        node.getTransforms().add(translate);
    }

    public double getTranslateX()
    {
        return translate.getX();
    }

    public double getTranslateY()
    {
        return translate.getY();
    }
}
