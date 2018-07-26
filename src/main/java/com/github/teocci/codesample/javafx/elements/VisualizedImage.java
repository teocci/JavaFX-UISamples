package com.github.teocci.codesample.javafx.elements;

import com.github.teocci.codesample.javafx.uisamples.features.OverlayIt;
import javafx.animation.FadeTransition;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-26
 */
public class VisualizedImage extends Group
{
    private final Image backgroundImage;
    private final Group visualizer;
    private final FadeTransition fadeIn, fadeOut;
    private final boolean doFade;

    public VisualizedImage(String location, double fadeDuration)
    {
        backgroundImage = new Image(OverlayIt.getResource(location));
        visualizer = new Group();

        getChildren().add(new ImageView(backgroundImage));
        getChildren().add(visualizer);
        setClip(new Rectangle(0, 0, backgroundImage.getWidth(), backgroundImage.getHeight()));

        doFade = fadeDuration > 0;

        fadeIn = new FadeTransition(Duration.seconds(fadeDuration), visualizer);
        fadeIn.setFromValue(0.4);
        fadeIn.setToValue(1.0);

        fadeOut = new FadeTransition(Duration.seconds(fadeDuration), visualizer);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.4);
    }

    public double getWidth()
    {
        return backgroundImage.getWidth();
    }

    public double getHeight()
    {
        return backgroundImage.getHeight();
    }

    public void replaceOverlay(final ObservableList<Shape> shapes)
    {
        if (!doFade) {
            visualizer.getChildren().setAll(shapes);
        } else {
            fadeIn.stop();
            fadeOut.play();
            fadeOut.setOnFinished(actionEvent -> {
                visualizer.getChildren().setAll(shapes);
                fadeIn.play();
            });
        }
    }
}
