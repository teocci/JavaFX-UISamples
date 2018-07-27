package com.github.teocci.codesample.javafx.uisamples.features.foo;

import com.github.teocci.codesample.javafx.models.Foo;
import com.github.teocci.codesample.javafx.threads.foo.FooModifier;
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

public class FooModifierServiceApp extends Application
{
    @Override
    public void start(final Stage stage) throws Exception
    {
        final FooModifier fooModifier = new FooModifier();

        final Label fooBeforeDisplay = new Label();
        fooBeforeDisplay.setMinWidth(300);
        fooBeforeDisplay.setAlignment(Pos.CENTER);
        final Label fooAfterDisplay = new Label();
        fooAfterDisplay.setMinWidth(300);
        fooAfterDisplay.setAlignment(Pos.CENTER);

        final Button fooMaker = new Button("Make Foo");
        fooMaker.setOnAction(t -> {
            fooModifier.reset();
            Foo foo = new Foo();
            fooModifier.setFoo(foo);
            fooBeforeDisplay.setText("Before Modification: " + foo.getAnswer());
            fooAfterDisplay.setText("...");
            if (fooModifier.getState() == Worker.State.READY) {
                fooModifier.start();
            } else {
                fooModifier.restart();
            }
        });
        fooMaker.disableProperty().bind(fooModifier.runningProperty());

        fooModifier.setOnSucceeded(t -> {
            Foo myModifiedFoo = fooModifier.getValue();
            fooAfterDisplay.setText("After Modification: " + myModifiedFoo.getAnswer());
        });

        final VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().setAll(fooMaker, fooBeforeDisplay, fooAfterDisplay);
        layout.setStyle("-fx-padding: 10px; -fx-background-color: cornsilk;");

        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) { Application.launch(args); }
}
