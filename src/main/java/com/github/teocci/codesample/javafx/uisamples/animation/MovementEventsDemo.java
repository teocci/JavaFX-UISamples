package com.github.teocci.codesample.javafx.uisamples.animation;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * JavaFX sample of moving a shape around on the screen in response to key and mouse presses.
 * <p>
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-27
 */

public class MovementEventsDemo extends Application
{
    private static final int KEYBOARD_MOVEMENT_DELTA = 5;
    private static final Duration TRANSLATE_DURATION = Duration.seconds(0.25);

    @Override
    public void start(Stage stage) throws Exception
    {
        final Circle circle = createCircle();
        final Group group = new Group(createInstructions(), circle);
        final TranslateTransition transition = createTranslateTransition(circle);

        final Scene scene = new Scene(group, 600, 400, Color.CORNSILK);
        moveCircleOnKeyPress(scene, circle);
        moveCircleOnMousePress(scene, circle, transition);

        stage.setScene(scene);
        stage.show();
    }

    private Label createInstructions()
    {
        Label instructions = new Label(
                "Use the arrow keys to move the circle in small increments\n" +
                        "Click the mouse to move the circle to a given location\n" +
                        "Ctrl + Click the mouse to slowly translate the circle to a given location"
        );
        instructions.setTextFill(Color.FORESTGREEN);
        return instructions;
    }

    private Circle createCircle()
    {
        final Circle circle = new Circle(200, 150, 50, Color.BLUEVIOLET);
        circle.setOpacity(0.7);
        return circle;
    }

    private TranslateTransition createTranslateTransition(final Circle circle)
    {
        final TranslateTransition transition = new TranslateTransition(TRANSLATE_DURATION, circle);
        transition.setOnFinished(t -> {
            circle.setCenterX(circle.getTranslateX() + circle.getCenterX());
            circle.setCenterY(circle.getTranslateY() + circle.getCenterY());
            circle.setTranslateX(0);
            circle.setTranslateY(0);
        });
        return transition;
    }

    private void moveCircleOnKeyPress(Scene scene, final Circle circle)
    {
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:
                    circle.setCenterY(circle.getCenterY() - KEYBOARD_MOVEMENT_DELTA);
                    break;
                case RIGHT:
                    circle.setCenterX(circle.getCenterX() + KEYBOARD_MOVEMENT_DELTA);
                    break;
                case DOWN:
                    circle.setCenterY(circle.getCenterY() + KEYBOARD_MOVEMENT_DELTA);
                    break;
                case LEFT:
                    circle.setCenterX(circle.getCenterX() - KEYBOARD_MOVEMENT_DELTA);
                    break;
            }
        });
    }

    private void moveCircleOnMousePress(Scene scene, final Circle circle, final TranslateTransition transition)
    {
        scene.setOnMousePressed(event -> {
            if (!event.isControlDown()) {
                circle.setCenterX(event.getSceneX());
                circle.setCenterY(event.getSceneY());
            } else {
                transition.setToX(event.getSceneX() - circle.getCenterX());
                transition.setToY(event.getSceneY() - circle.getCenterY());
                transition.playFromStart();
            }
        });
    }

    public static void main(String[] args) { launch(args); }
}
