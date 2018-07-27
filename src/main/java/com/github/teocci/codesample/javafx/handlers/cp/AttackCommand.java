package com.github.teocci.codesample.javafx.handlers.cp;

import com.github.teocci.codesample.javafx.elements.cp.SCVUnit;
import com.github.teocci.codesample.javafx.interfaces.cp.ICommand;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-27
 */
public class AttackCommand implements ICommand
{
    private SCVUnit scv;

    public AttackCommand(SCVUnit scv) {
        this.scv = scv;
    }

    public void execute() {
        scv.attack();
    }
}
