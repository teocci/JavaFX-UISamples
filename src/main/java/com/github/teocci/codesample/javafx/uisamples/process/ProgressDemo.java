package com.github.teocci.codesample.javafx.uisamples.process;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2017-Nov-23
 */
public class ProgressDemo extends Application
{
    @Override
    public void start(Stage stage)
    {

        ProgressBar progressBar = new ProgressBar();
        ProgressIndicator progressIndicator = new ProgressIndicator();

        FlowPane root = new FlowPane();
        root.setPadding(new Insets(10));
        root.setHgap(10);
        root.getChildren().addAll(progressBar, progressIndicator);

        Scene scene = new Scene(root, 400, 300);

        stage.setTitle("JavaFX ProgressBar & ProgressIndicator (o7planning.org)");

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
