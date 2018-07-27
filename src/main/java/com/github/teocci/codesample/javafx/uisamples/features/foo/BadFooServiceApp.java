package com.github.teocci.codesample.javafx.uisamples.features.foo;

import com.github.teocci.codesample.javafx.threads.foo.BadFooGenerator;
import javafx.application.Application;
import javafx.concurrent.Worker;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Samples for returning user objects types and exception types from JavaFX service calls.
 * <p>
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-27
 */
public class BadFooServiceApp extends Application
{
    @Override
    public void start(final Stage stage) throws Exception
    {
        final BadFooGenerator badFooGenerator = new BadFooGenerator();
        final Label badFooDisplay = new Label();

        badFooDisplay.setMinWidth(250);
        badFooDisplay.setAlignment(Pos.CENTER);

        final Button badFooMaker = new Button("Fail Foo");

        badFooMaker.setOnAction(t -> {
            badFooDisplay.setText("wait...");
            if (badFooGenerator.getState() == Worker.State.READY) {
                badFooGenerator.start();
            } else {
                badFooGenerator.restart();
            }
        });
        badFooMaker.disableProperty().bind(badFooGenerator.runningProperty());

        badFooGenerator.setOnFailed(t -> {
            Throwable ouch = badFooGenerator.getException();
            badFooDisplay.setText(ouch.getClass().getName() + " -> " + ouch.getMessage());
        });

        final VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().setAll(badFooMaker, badFooDisplay);
        layout.setStyle("-fx-padding: 10px; -fx-background-color: cornsilk;");

        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) { Application.launch(args); }
}