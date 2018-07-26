package com.github.teocci.codesample.javafx.uisamples;

import com.github.teocci.codesample.javafx.controllers.HolderController;
import com.github.teocci.codesample.javafx.managers.VistaNavigator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

import static com.github.teocci.codesample.javafx.managers.VistaNavigator.HOLDER;
import static com.github.teocci.codesample.javafx.managers.VistaNavigator.VIEW_1;

/**
 * Small JavaFX framework for swapping in and out child panes in a main FXML container. Code is for Java 8+.
 * <p>
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-26
 */
public class ViewSwapping extends Application
{
    @Override
    public void start(Stage stage) throws Exception
    {
        stage.setTitle("View Shower");

        stage.setScene(createScene(loadMainPane()));

        stage.show();
    }

    /**
     * Loads the main fxml layout.
     * Sets up the vista switching VistaNavigator.
     * Loads the first vista into the fxml layout.
     *
     * @return the loaded pane.
     * @throws IOException if the pane could not be loaded.
     */
    private Pane loadMainPane() throws IOException
    {
        FXMLLoader loader = new FXMLLoader();

        Pane mainPane = loader.load(getClass().getResourceAsStream(HOLDER));

        HolderController controller = loader.getController();

        VistaNavigator.setController(controller);
        VistaNavigator.loadVista(VIEW_1);

        return mainPane;
    }

    /**
     * Creates the main application scene.
     *
     * @param mainPane the main application layout.
     * @return the created scene.
     */
    private Scene createScene(Pane mainPane)
    {
        Scene scene = new Scene(mainPane, 200, 50);

        scene.getStylesheets().setAll(getClass().getResource("/css/views.css").toExternalForm());

        return scene;
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}