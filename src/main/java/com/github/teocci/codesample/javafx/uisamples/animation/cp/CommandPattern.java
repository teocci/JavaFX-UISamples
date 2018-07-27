package com.github.teocci.codesample.javafx.uisamples.animation.cp;

import com.github.teocci.codesample.javafx.elements.cp.SCVUnit;
import com.github.teocci.codesample.javafx.handlers.cp.AttackCommand;
import com.github.teocci.codesample.javafx.handlers.cp.GatherCommand;
import com.github.teocci.codesample.javafx.handlers.cp.MoveCommand;
import com.github.teocci.codesample.javafx.interfaces.cp.ICommand;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-27
 */
public class CommandPattern extends Application
{
    private static final double WIDTH = 512, HEIGHT = 512;

    private SCVUnit scv;
    private Queue<ICommand> commandList;

    @Override
    public void start(Stage stage) throws Exception
    {
        stage.setTitle("Command Pattern - bad version");

        Group group = new Group();
        Scene scene = new Scene(group, WIDTH, HEIGHT, Color.WHITE);

        scv = new SCVUnit(scene.getWidth() / 2, scene.getHeight() / 2);

        group.getChildren().add(scv);

        commandList = new ArrayDeque<>();

        scene.setOnKeyPressed(createOnKeyDownEventHandler());
        scene.setOnMouseClicked(createOnMouseClickEventHandler());

        Timeline delayer = new Timeline(new KeyFrame(Duration.millis(10), event -> {
            if (commandList.size() > 0) {
                ICommand command = commandList.poll();
                if (command != null) {
                    command.execute();
                }
            }
        }));

        delayer.setCycleCount(Timeline.INDEFINITE);
        delayer.play();

        stage.setScene(scene);
        stage.show();
    }

    private EventHandler<? super MouseEvent> createOnMouseClickEventHandler()
    {
        return event -> {
            switch (event.getButton()) {
                case PRIMARY:
                    double targetX = 0;
                    double targetY = 0;
                    if (commandList.size() > 0) {
                        ICommand command = commandList.peek();
                        if (command.getClass() == MoveCommand.class) {
                            double sceneX = event.getSceneX();
                            double sceneY = event.getSceneY();
                            double oldTargetX = ((MoveCommand) command).getTargetX();
                            double oldTargetY = ((MoveCommand) command).getTargetY();

                            targetX = sceneX - oldTargetX - scv.getWidth() / 2;
                            targetY = sceneY - oldTargetY - scv.getHeight() / 2;
                            System.out.println("Target coords: " + targetX + ", " + targetY);
                        }
                    } else {
                        double sceneX = event.getSceneX();
                        double sceneY = event.getSceneY();
                        double oldTargetX = scv.getX();
                        double oldTargetY = scv.getY();

                        targetX = sceneX - oldTargetX - scv.getWidth() / 2;
                        targetY = sceneY - oldTargetY - scv.getHeight() / 2;
                        System.out.println("Target coords: " + targetX + ", " + targetY);
                    }
                    commandList.add(new MoveCommand(scv, targetX, targetY));
            }
        };
    }

    private EventHandler<? super KeyEvent> createOnKeyDownEventHandler()
    {
        return event -> {
            switch (event.getCode()) {
                case Q:
                    commandList.add(new AttackCommand(scv));
                    break;
                case W:
                    commandList.add(new GatherCommand(scv));
                    break;
            }
        };
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
