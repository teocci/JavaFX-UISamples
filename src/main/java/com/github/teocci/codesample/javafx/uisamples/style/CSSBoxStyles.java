package com.github.teocci.codesample.javafx.uisamples.style;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Applies CSS to a JavaFX pane to create a page turn style shadow effect.
 * <p>
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-27
 */

public class CSSBoxStyles extends Application
{
    @Override
    public void start(Stage stage)
    {
        StackPane shadowPane = new StackPane();
        shadowPane.getStyleClass().add("right-page-turn");

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getStyleClass().add("content-pane");
        anchorPane.setMinSize(400, 200);

        StackPane layout = new StackPane();
        layout.getChildren().setAll(
                shadowPane,
                anchorPane
        );

        stage.setScene(new Scene(layout));
        stage.getScene().getStylesheets().add(
                getClass().getResource("/css/shadow-styles.css").toExternalForm()
        );
        stage.show();
    }

    public static void main(String[] args) { launch(args); }
}
