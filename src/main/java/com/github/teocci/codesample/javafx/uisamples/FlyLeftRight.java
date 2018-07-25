package com.github.teocci.codesample.javafx.uisamples;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2017-Nov-23
 */
public class FlyLeftRight extends Application
{
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Label label1 = new Label("Left");
        label1.setStyle("-fx-background-color: red; -fx-text-fill: white;");

        Label label2 = new Label("Center");
        label2.setStyle("-fx-background-color: green; -fx-text-fill: white;");

        Label label3 = new Label("Right");
        label3.setStyle("-fx-background-color: blue; -fx-text-fill: white;");

        Region region1 = new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);

        Region region2 = new Region();
        HBox.setHgrow(region2, Priority.ALWAYS);

        HBox hBox = new HBox(label1, region1, label2, region2, label3);

        primaryStage.setScene(new Scene(hBox, 640, 240));
        primaryStage.show();
    }
}
