package com.github.teocci.codesample.javafx.uisamples.elements;

import com.github.teocci.codesample.javafx.controllers.TreeViewController;
import com.github.teocci.codesample.javafx.handlers.TreeLoadingEventHandler;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * JavaFX TreeView item dynamic loading demo with FXML.
 * <p>
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-27
 */

public class DynamicTreeView extends Application
{
    @Override
    public void start(final Stage stage) throws Exception
    {
        // Load the scene fxml UI.
        // Grabs the UI scenegraph view from the loader.
        // Grabs the UI controller for the view from the loader.
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dynamic-tree-view.fxml"));
        final Parent root = loader.load();
        final TreeViewController controller = loader.getController();

        // continuously refresh the TreeItems.
        // demonstrates using controller methods to manipulate the controlled UI.
        final Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.seconds(3),
                        new TreeLoadingEventHandler(controller)
                )
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        // close the app if the user clicks on anywhere on the window.
        // just provides a simple way to kill the demo app.
        root.addEventFilter(MouseEvent.MOUSE_CLICKED, t -> stage.hide());

        // initialize the stage.
        stage.setScene(new Scene(root));
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
    }

    // main method is only for legacy support - java 8 won't call it for a javafx application.
    public static void main(String[] args) { launch(args); }
}
