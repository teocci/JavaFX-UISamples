package com.github.teocci.codesample.javafx.uisamples.web;

import com.github.teocci.codesample.javafx.utils.UtilHelper;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.converter.NumberStringConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-26
 */
public class WebViewCaptureDemo extends Application
{
    private static final String HOME_LOC = "http://docs.oracle.com/javafx/2/get_started/animation.htm";

    private WebView webView;

    private File captureFile = new File("cap.png");

    @Override
    public void start(Stage stage) throws Exception
    {
        webView = new WebView();
        webView.setPrefSize(1000, 8000);

        final TextField location = new TextField();
        location.setOnAction(event -> {
            if (!location.getText().startsWith("http")) {
                location.setText("http://" + location.getText());
            }
            webView.getEngine().load(location.getText());
        });
        webView.getEngine().locationProperty().addListener((observable, oldLocation, newLocation) -> location.setText(newLocation));

        ScrollPane webViewScroll = new ScrollPane();
        webViewScroll.setContent(webView);
        webViewScroll.setPrefSize(800, 300);

        final Button capture = new Button("Capture");

        final ProgressIndicator progress = new ProgressIndicator();
        progress.setVisible(false);

        final TextField prefWidth = new TextField("1000");
        final TextField prefHeight = new TextField("8000");

        HBox controls = new HBox(10);
        controls.getChildren().addAll(capture, progress, prefWidth, prefHeight);

        final ImageView imageView = new ImageView();
        ScrollPane imageViewScroll = UtilHelper.makeScrollable(imageView);
        imageViewScroll.setPrefSize(800, 300);

        final PauseTransition pt = new PauseTransition();
        pt.setDuration(Duration.millis(500));
        pt.setOnFinished(actionEvent -> {
            WritableImage image = webView.snapshot(null, null);
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
            try {
                ImageIO.write(bufferedImage, "png", captureFile);
                imageView.setImage(new Image(captureFile.toURI().toURL().toExternalForm()));
                System.out.println("Captured WebView to: " + captureFile.getAbsoluteFile());
                progress.setVisible(false);
                capture.setDisable(false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        capture.setOnAction(actionEvent -> {
            NumberStringConverter converter = new NumberStringConverter();
            double W = converter.fromString(prefWidth.getText()).doubleValue();
            double H = converter.fromString(prefHeight.getText()).doubleValue();

            // ensure that the capture size has a reasonable min size and is within the limits of what JavaFX is capable of processing.
            if (W < 100) {
                W = 100;
                prefWidth.setText("100");
            }

            if (W > 2000) {
                W = 2000;
                prefWidth.setText("2000");
            }

            if (H < 100) {
                H = 100;
                prefHeight.setText("100");
            }

            if (H > 16000) {
                H = 16000;
                prefHeight.setText("16000");
            }

            webView.setPrefWidth(W);
            webView.setPrefHeight(H);

            pt.play();
            capture.setDisable(true);
            progress.setVisible(true);
        });

        webView.getEngine().load(HOME_LOC);

        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 10; -fx-background-color: cornsilk;");
        layout.getChildren().setAll(
                location,
                webViewScroll,
                controls,
                imageViewScroll,
                new Label("Capture File: " + captureFile.getAbsolutePath())
        );
        VBox.setVgrow(imageViewScroll, Priority.ALWAYS);

        stage.setScene(new Scene(layout));
        stage.show();
    }

    public static void main(String[] args) { Application.launch(WebViewCaptureDemo.class); }
}
