package com.github.teocci.codesample.javafx.uisamples.features;

import com.github.teocci.codesample.javafx.utils.FXUtilHelper;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Sample using Java Lambdas to replace javafx builders
 * [Using Java Lambdas to replace javafx builders](https://stackoverflow.com/questions/25942999)
 * <p>
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-26
 */

public class JavaFXLambdaBuilder extends Application
{
    @Override
    public void start(Stage stage) throws IOException
    {
        Label node = FXUtilHelper.build(new Label(), label -> {
            label.setText("Label build with lambda.");
            label.setStyle("-fx-font-size: 20");
            label.setMinWidth(100);
        });

        stage.setTitle("Lambda builder");
        stage.setScene(new Scene(node, 300, 30));
        stage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
