package com.github.teocci.codesample.javafx.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-26
 */
public class HolderController
{
    /**
     * Holder of a switchable view.
     */
    @FXML
    private StackPane viewHolder;

    /**
     * Replaces a view displayed in the view holder with a new vista.
     *
     * @param node the view node to be swapped in.
     */
    public void setVista(Node node)
    {
        viewHolder.getChildren().setAll(node);
    }

}
