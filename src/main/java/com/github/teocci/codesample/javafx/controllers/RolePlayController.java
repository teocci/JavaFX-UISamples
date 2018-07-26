package com.github.teocci.codesample.javafx.controllers;

import com.github.teocci.codesample.javafx.managers.RoleManager;
import javafx.fxml.FXML;
import javafx.scene.Node;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-26
 */

public class RolePlayController
{
    @FXML
    private Node root;

    private RoleManager roleManager;

    public RolePlayController(RoleManager roleManager)
    {
        this.roleManager = roleManager;
    }

    @FXML
    public void initialize()
    {
        roleManager.assignRolesToNodeTree(root);
    }
}

