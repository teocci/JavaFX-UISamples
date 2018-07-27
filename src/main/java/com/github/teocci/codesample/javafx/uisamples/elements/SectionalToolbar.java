package com.github.teocci.codesample.javafx.uisamples.elements;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2017-Nov-23
 */
public class SectionalToolbar extends Application
{
    @Override
    public void start(Stage stage) throws Exception
    {
        final Pane leftSpacer = new Pane();
        HBox.setHgrow(leftSpacer, Priority.SOMETIMES);

        final Pane rightSpacer = new Pane();
        HBox.setHgrow(rightSpacer, Priority.SOMETIMES);

        final ToolBar toolBar = new ToolBar(
                new Button("Good"),
                new Button("Boys"),
                leftSpacer,
                new Button("Deserve"),
                new Button("Fruit"),
                rightSpacer,
                new Button("Always")
        );
        toolBar.setPrefWidth(400);

        BorderPane layout = new BorderPane();
        layout.setTop(toolBar);

        stage.setScene(new Scene(layout));
        stage.show();
    }

    public static void main(String[] args) { launch(); }
}
