package com.github.teocci.codesample.javafx.uisamples.elements;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * Displays a translucent pane with solid children in JavaFX.
 * <p>
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-27
 */

public class TranslucentPane extends Application
{
    @Override
    public void start(final Stage stage) throws Exception
    {
        final ImageView imageView = new ImageView(
                new Image("https://upload.wikimedia.org/wikipedia/commons/b/b7/Idylls_of_the_King_3.jpg")
        );
        imageView.setFitHeight(300);
        imageView.setFitWidth(228);

        final Label label = new Label("Camelot");
        label.setStyle("-fx-text-fill: white; " +
                "-fx-font-style: italic; " +
                "-fx-font-weight: bold; " +
                "-fx-padding: 0 0 20 0;"
        );

        StackPane glass = new StackPane();
        StackPane.setAlignment(label, Pos.BOTTOM_CENTER);
        glass.getChildren().addAll(label);
        glass.setStyle("-fx-background-color: rgba(247, 220, 111, 0.25); " +
                "-fx-background-radius: 10;"
        );
        glass.setMaxWidth(imageView.getFitWidth() - 40);
        glass.setMaxHeight(imageView.getFitHeight() - 40);

        final StackPane layout = new StackPane();
        layout.getChildren().addAll(imageView, glass);
        layout.setStyle("-fx-background-color: silver; " +
                "-fx-font-size: 20; " +
                "-fx-padding: 10;"
        );

        stage.setScene(new Scene(layout));
        stage.show();
    }

    public static void main(String[] args) { launch(args); }
}
