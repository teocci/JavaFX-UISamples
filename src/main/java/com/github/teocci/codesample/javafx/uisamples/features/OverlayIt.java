package com.github.teocci.codesample.javafx.uisamples.features;

/**
 * Generates lots of shapes off of the JavaFX thread then composites them in a JavaFX Scene
 * <p>
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-26
 */

import com.github.teocci.codesample.javafx.elements.ShapeMachine;
import com.github.teocci.codesample.javafx.elements.VisualizedImage;
import com.github.teocci.codesample.javafx.handlers.ContinualShapeGenerator;
import com.github.teocci.codesample.javafx.threads.ParallelDimension;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

public class OverlayIt extends Application
{
    private static final int N_SHAPES = 10;
    private static final double PAUSE_SECS = 3;
    private static final double FADE_SECS = 0;
    private static final String IMAGE_URL = "http://farm9.staticflickr.com/8205/8264285440_f5617efb71_b.jpg";

    private ShapeMachine machine;
    private ParallelDimension parallel;
    private ContinualShapeGenerator generator;
    private VisualizedImage visualizedImage;

    @Override
    public void init() throws MalformedURLException, URISyntaxException
    {
        visualizedImage = new VisualizedImage(IMAGE_URL, FADE_SECS);
        double maxShapeSize = visualizedImage.getWidth() / 8;
        double minShapeSize = maxShapeSize / 2;
        machine = new ShapeMachine(visualizedImage.getWidth(), visualizedImage.getHeight(), maxShapeSize, minShapeSize);
        parallel = new ParallelDimension(machine, N_SHAPES);
        generator = new ContinualShapeGenerator(parallel, visualizedImage, PAUSE_SECS);
    }

    @Override
    public void start(final Stage stage) throws IOException, URISyntaxException
    {
        Scene scene = new Scene(visualizedImage);
        configureExitOnAnyKey(stage, scene);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        generator.generate();
    }

    private void configureExitOnAnyKey(final Stage stage, Scene scene)
    {
        scene.setOnKeyPressed(keyEvent -> stage.hide());
    }

    public static String getResource(String resourceName)
    {
        if (resourceName.matches("file:.*|http:.*|jar:.*"))
            return resourceName;

        try {
            return OverlayIt.class.getResource(resourceName).toURI().toURL().toExternalForm();
        } catch (Exception e) {
            System.out.println("Unable to retrieve resource: " + resourceName);
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) { launch(args); }
}




