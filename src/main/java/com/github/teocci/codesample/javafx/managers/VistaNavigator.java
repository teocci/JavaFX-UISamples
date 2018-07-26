package com.github.teocci.codesample.javafx.managers;

import com.github.teocci.codesample.javafx.controllers.HolderController;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-26
 */
public class VistaNavigator
{
    /**
     * Convenience constants for fxml layouts managed by the navigator.
     */
    public static final String HOLDER = "/fxml/holder.fxml";
    public static final String VIEW_1 = "/fxml/view1.fxml";
    public static final String VIEW_2 = "/fxml/view2.fxml";

    /**
     * The main application layout controller.
     */
    private static HolderController controller;

    /**
     * Stores the main controller for later use in navigation tasks.
     *
     * @param controller the main application layout controller.
     */
    public static void setController(HolderController controller)
    {
        VistaNavigator.controller = controller;
    }

    /**
     * Loads the vista specified by the fxml file into the
     * viewHolder pane of the main application layout.
     * <p>
     * Previously loaded vista for the same fxml file are not cached.
     * The fxml is loaded anew and a new vista node hierarchy generated
     * every time this method is invoked.
     * <p>
     * A more sophisticated load function could potentially add some
     * enhancements or optimizations, for example:
     * cache FXMLLoaders
     * cache loaded vista nodes, so they can be recalled or reused
     * allow a user to specify vista node reuse or new creation
     * allow back and forward history like a browser
     *
     * @param fxml the fxml file to be loaded.
     */
    public static void loadVista(String fxml)
    {
        try {
            controller.setVista(
                    FXMLLoader.load(
                            VistaNavigator.class.getResource(
                                    fxml
                            )
                    )
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
