package com.github.teocci.codesample.javafx.controllers;

import com.github.teocci.codesample.javafx.managers.VistaNavigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-26
 */
public class View2Controller
{
    /**
     * Event handler fired when the user requests a previous vista.
     *
     * @param event the event that triggered the handler.
     */
    @FXML
    void previousPane(ActionEvent event)
    {
        VistaNavigator.loadVista(VistaNavigator.VIEW_1);
    }
}