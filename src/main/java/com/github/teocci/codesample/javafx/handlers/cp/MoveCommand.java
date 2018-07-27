package com.github.teocci.codesample.javafx.handlers.cp;

import com.github.teocci.codesample.javafx.elements.cp.SCVUnit;
import com.github.teocci.codesample.javafx.interfaces.cp.ICommand;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-27
 */
public class MoveCommand implements ICommand
{
    private final double targetX;
    private final double targetY;
    private SCVUnit scv;

    public MoveCommand(SCVUnit scv, double targetX, double targetY)
    {
        this.scv = scv;
        this.targetX = targetX;
        this.targetY = targetY;
        System.out.println("Creating MoveCommand: " + targetX + ", " + targetY);
    }

    @Override
    public void execute()
    {
        scv.move(targetX, targetY);
    }

    public double getTargetX()
    {
        return targetX;
    }

    public double getTargetY()
    {
        return targetY;
    }
}
