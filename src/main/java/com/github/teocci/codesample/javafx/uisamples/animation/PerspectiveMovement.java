package com.github.teocci.codesample.javafx.uisamples.animation;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.PerspectiveTransform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Demonstrates use of a JavaFX PerspectiveTransform - toggling or morphing the transform on and off.
 * <p>
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-26
 */

public class PerspectiveMovement extends Application
{
    // perspective transformed group width and height.
    private final int W = 280;
    private final int H = 96;

    // upper right and lower right co-ordinates of perspective transformed group.
    private final int URY = 35;
    private final int LRY = 65;

    @Override
    public void start(Stage stage)
    {
        final PerspectiveTransform perspectiveTransform = createPerspectiveTransform();

        final Group group = new Group();
        group.setCache(true);
        setContent(group);

        final ToggleButton perspectiveToggle = createToggle(
                group,
                perspectiveTransform
        );

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().setAll(
                perspectiveToggle,
                createMorph(perspectiveToggle, group, perspectiveTransform),
                group
        );
        layout.setStyle("-fx-padding: 10px; -fx-background-color: rgb(17, 20, 25);");

        stage.setScene(new Scene(layout));
        stage.show();
    }

    private void setContent(Group group)
    {
        Rectangle rect = new Rectangle(0, 5, W, 80);
        rect.setFill(Color.web("0x3b596d"));

        Text text = new Text();
        text.setX(4.0);
        text.setY(60.0);
        text.setText("A long time ago");
        text.setFill(Color.ALICEBLUE);
        text.setFont(Font.font(null, FontWeight.BOLD, 36));

        Image image = new Image("http://icons.iconarchive.com/icons/danrabbit/elementary/96/Star-icon.png");
        ImageView imageView = new ImageView(image);
        imageView.setX(50);

        group.getChildren().addAll(rect, imageView, text);
    }

    private PerspectiveTransform createPerspectiveTransform()
    {
        PerspectiveTransform perspectiveTransform = new PerspectiveTransform();

        perspectiveTransform.setUlx(0.0);
        perspectiveTransform.setUly(0.0);
        perspectiveTransform.setUrx(W);
        perspectiveTransform.setUry(URY);
        perspectiveTransform.setLrx(W);
        perspectiveTransform.setLry(LRY);
        perspectiveTransform.setLlx(0.0);
        perspectiveTransform.setLly(H);

        return perspectiveTransform;
    }

    private ToggleButton createToggle(final Group group, final PerspectiveTransform perspectiveTransform)
    {
        final ToggleButton toggle = new ToggleButton("Toggle Perspective");
        toggle.selectedProperty().addListener((observable, wasSelected, selected) -> {
            if (selected) {
                perspectiveTransform.setUry(URY);
                perspectiveTransform.setLry(LRY);
                group.setEffect(perspectiveTransform);
            } else {
                group.setEffect(null);
            }
        });

        return toggle;
    }

    private ToggleButton createMorph(final ToggleButton perspectiveToggle, final Group group, final PerspectiveTransform perspectiveTransform)
    {
        final Timeline distorter = new Timeline(
                new KeyFrame(
                        Duration.seconds(0),
                        new KeyValue(perspectiveTransform.uryProperty(), 0, Interpolator.LINEAR),
                        new KeyValue(perspectiveTransform.lryProperty(), H, Interpolator.LINEAR)
                ),
                new KeyFrame(
                        Duration.seconds(3),
                        new KeyValue(perspectiveTransform.uryProperty(), URY, Interpolator.LINEAR),
                        new KeyValue(perspectiveTransform.lryProperty(), LRY, Interpolator.LINEAR)
                )
        );

        final ToggleButton morphToggle = new ToggleButton("Morph Perspective");
        morphToggle.selectedProperty().addListener((observable, wasSelected, selected) -> {
            if (!perspectiveToggle.isSelected()) {
                perspectiveToggle.fire();
            }
            if (selected) {
                distorter.setRate(1);
                distorter.play();
            } else {
                distorter.setRate(-1);
                distorter.play();
            }
        });

        return morphToggle;
    }
}
