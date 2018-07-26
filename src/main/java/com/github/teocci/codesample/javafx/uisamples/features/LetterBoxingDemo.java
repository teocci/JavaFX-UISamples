package com.github.teocci.codesample.javafx.uisamples.features;

import com.github.teocci.codesample.javafx.elements.ShapeMachine;
import com.github.teocci.codesample.javafx.handlers.SceneSizeChangeListener;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Demonstrates sizing content in JavaFX by dynamically scaling it, but still keeping the content in the correct aspect ratio by placing it in a letterbox.
 * <p>
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-26
 */
public class LetterBoxingDemo extends Application
{
    private static final String IMAGE_URL = "http://farm9.staticflickr.com/8205/8264285440_f5617efb71_b.jpg";
    private Image image;

    public static void main(String[] args) { launch(args); }

    @Override
    public void init() throws Exception
    {
        image = new Image(IMAGE_URL);
    }

    @Override
    public void start(final Stage stage) throws IOException
    {
        Pane root = createPane();

        Scene scene = new Scene(new Group(root));
        stage.setScene(scene);
        stage.show();

        letterbox(scene, root);
        stage.setFullScreen(true);
    }

    private StackPane createPane()
    {
        final int MAX_HEIGHT = 400;

        StackPane stack = new StackPane();

        Pane content = new Pane();

        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(MAX_HEIGHT);
        double width = imageView.getLayoutBounds().getWidth();
        content.getChildren().add(imageView);

        ShapeMachine machine = new ShapeMachine(width, MAX_HEIGHT, MAX_HEIGHT / 8, MAX_HEIGHT / 16);
        for (int i = 0; i < 200; i++) {
            content.getChildren().add(machine.randomShape());
        }
        content.setMaxSize(width, MAX_HEIGHT);
        content.setClip(new Rectangle(width, MAX_HEIGHT));

        stack.getChildren().add(content);
        stack.setStyle("-fx-background-color: black");

        return stack;
    }

    private void letterbox(final Scene scene, final Pane contentPane)
    {
        final double initWidth = scene.getWidth();
        final double initHeight = scene.getHeight();
        final double ratio = initWidth / initHeight;

        SceneSizeChangeListener sizeListener = new SceneSizeChangeListener(scene, ratio, initHeight, initWidth, contentPane);
        scene.widthProperty().addListener(sizeListener);
        scene.heightProperty().addListener(sizeListener);
    }
}