package com.github.teocci.codesample.javafx.uisamples;

import com.github.teocci.codesample.javafx.elements.DockDialog;
import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-26
 */

public class SimpleDocking extends Application
{
    public void start(final Stage stage) throws Exception
    {
        final SplitPane rootPane = new SplitPane();
        rootPane.setOrientation(Orientation.VERTICAL);

        final FlowPane dockedArea = new FlowPane();
        dockedArea.getChildren().add(new Label("Some docked content"));

        final FlowPane centerArea = new FlowPane();
        final Button undockButton = new Button("Undock");
        centerArea.getChildren().add(undockButton);

        rootPane.getItems().addAll(centerArea, dockedArea);

        stage.setScene(new Scene(rootPane, 300, 300));
        stage.show();

        final DockDialog dockDialog = new DockDialog(stage);
        undockButton.disableProperty().bind(dockDialog.showingProperty());
        undockButton.setOnAction(actionEvent -> {
            rootPane.getItems().remove(dockedArea);

            dockDialog.setOnHidden(windowEvent -> {
                rootPane.getItems().add(dockedArea);
            });
            dockDialog.setContent(dockedArea);
            dockDialog.show(stage);
        });
    }

    public static void main(String[] args) throws Exception
    {
        launch(args);
    }
}
