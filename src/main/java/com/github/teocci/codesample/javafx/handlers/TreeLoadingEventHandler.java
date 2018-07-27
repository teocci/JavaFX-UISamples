package com.github.teocci.codesample.javafx.handlers;

import com.github.teocci.codesample.javafx.controllers.TreeViewController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Small helper class for handling tree loading events.
 * <p>
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-27
 */
public class TreeLoadingEventHandler implements EventHandler<ActionEvent>
{
    private TreeViewController controller;
    private int idx = 0;

    public TreeLoadingEventHandler(TreeViewController controller)
    {
        this.controller = controller;
    }

    @Override
    public void handle(ActionEvent t)
    {
        controller.loadTreeItems("Loaded " + idx, "Loaded " + (idx + 1), "Loaded " + (idx + 2));
        idx += 3;
    }
}
