package com.github.teocci.codesample.javafx.transitions;

import javafx.scene.Node;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-26
 */
public class MoveXTransition extends MoveTransition
{
    private double fromX;

    public MoveXTransition(final Node node)
    {
        super(node);
    }

    @Override
    protected void interpolate(double frac)
    {
        translate.setX(fromX * (1 - frac));
    }

    public void setFromX(double fromX)
    {
        translate.setX(fromX);
        this.fromX = fromX;
    }
}
