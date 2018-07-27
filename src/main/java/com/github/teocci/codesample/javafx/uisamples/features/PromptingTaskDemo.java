package com.github.teocci.codesample.javafx.uisamples.features;

import com.github.teocci.codesample.javafx.tasks.LoadTextTask;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Demonstrates a JavaFX task which runs in the background reading data and, when required,
 * pauses from reading data to prompt the user for extra input before continuing processing.
 * <p>
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-27
 */

public class PromptingTaskDemo extends Application
{
    private static final String[] SAMPLE_TEXT = "MISSING Lorem ipsum dolor sit amet MISSING consectetur adipisicing elit sed do eiusmod tempor incididunt MISSING ut labore et dolore magna aliqua".split(" ");

    @Override
    public void start(Stage primaryStage)
    {
        Label status = new Label();
        ProgressBar progress = new ProgressBar();

        VBox textContainer = new VBox(10);
        textContainer.setStyle("-fx-background-color: burlywood; -fx-padding: 10;");

        LoadTextTask task = new LoadTextTask(SAMPLE_TEXT, textContainer);
        status.textProperty().bind(task.messageProperty());
        progress.progressProperty().bind(task.progressProperty());

        final Thread taskThread = new Thread(task, "label-generator");
        taskThread.setDaemon(true);

        VBox layout = new VBox(10);
        layout.getChildren().addAll(status, progress, textContainer);
        layout.setStyle("-fx-background-color: cornsilk; -fx-padding: 10;");

        primaryStage.setScene(new Scene(layout, 300, 700));
        primaryStage.show();

        taskThread.start();
    }

    public static void main(String[] args) { launch(args); }
}


