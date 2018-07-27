package com.github.teocci.codesample.javafx.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-27
 */
public class TreeViewController
{
    // The FXML annotation tells the loader to inject this variable before invoking initialize.
    @FXML
    private TreeView<String> locationTreeView;

    // The initialize method is automatically invoked by the FXMLLoader - it's magic
    public void initialize()
    {
        loadTreeItems("initial 1", "initial 2", "initial 3");
    }

    // Loads some strings into the tree in the application UI.
    public void loadTreeItems(String... rootItems)
    {
        TreeItem<String> root = new TreeItem<>("Root Node");
        root.setExpanded(true);
        for (String itemString : rootItems) {
            root.getChildren().add(new TreeItem<>(itemString));
        }

        locationTreeView.setRoot(root);
    }
}
