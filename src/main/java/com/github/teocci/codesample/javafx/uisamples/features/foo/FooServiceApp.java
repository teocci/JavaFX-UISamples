package com.github.teocci.codesample.javafx.uisamples.features.foo;

import com.github.teocci.codesample.javafx.models.Foo;
import com.github.teocci.codesample.javafx.threads.foo.FooGenerator;
import javafx.application.Application;
import javafx.concurrent.Worker;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-27
 */

public class FooServiceApp extends Application
{
    @Override
    public void start(final Stage stage) throws Exception
    {
        final FooGenerator fooGenerator = new FooGenerator();

        final Label fooDisplay = new Label();
        final Button fooMaker = new Button("Make Foo");
        fooMaker.setOnAction(t -> {
            fooDisplay.setText("...");
            if (fooGenerator.getState() == Worker.State.READY) {
                fooGenerator.start();
            } else {
                fooGenerator.restart();
            }
        });
        fooMaker.disableProperty().bind(fooGenerator.runningProperty());

        fooGenerator.setOnSucceeded(t -> {
            Foo myLovelyNewFoo = fooGenerator.getValue();
            fooDisplay.setText(myLovelyNewFoo.getAnswer() + "");
        });

        final VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().setAll(fooMaker, fooDisplay);
        layout.setStyle("-fx-padding: 10px; -fx-background-color: cornsilk;");

        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.show();
    }




    public static void main(String[] args) { Application.launch(args); }
}
