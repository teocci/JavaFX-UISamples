package com.github.teocci.codesample.javafx.uisamples.web;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Demonstrates adding a basic alert handler to a JavaFX WebView.
 * <p>
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-26
 */

public class WebViewWithPromptHandler extends Application
{
    public static void main(String[] args) { launch(args); }

    @Override
    public void start(final Stage primaryStage)
    {
        WebView webView = new WebView();
        webView.getEngine().loadContent("<button onclick='alert(\"Alerted\"); alert(\"Alerted 2\");'>Popup</button>");
        webView.getEngine().setOnAlert(event -> {
            Stage popup = new Stage();
            popup.initOwner(primaryStage);
            popup.initStyle(StageStyle.UTILITY);
            popup.initModality(Modality.WINDOW_MODAL);

            StackPane content = new StackPane();
            content.getChildren().setAll(
                    new Label(event.getData())
            );
            content.setPrefSize(200, 100);

            popup.setScene(new Scene(content));
            popup.showAndWait();
        });

        final Scene scene = new Scene(webView);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}