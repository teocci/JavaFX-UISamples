package com.github.teocci.codesample.javafx.transitions;

import javafx.scene.Node;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-26
 */
public class MoveYTransition extends MoveTransition
{
    private double fromY;

    public MoveYTransition(final Node node)
    {
        super(node);
    }

    @Override
    protected void interpolate(double frac)
    {
        translate.setY(fromY * (1 - frac));
    }

    public void setFromY(double fromY)
    {
        translate.setY(fromY);
        this.fromY = fromY;
    }
}
