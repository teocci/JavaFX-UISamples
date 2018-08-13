package com.github.teocci.codesample.javafx.uisamples.style;

import com.github.teocci.codesample.javafx.utils.FXUtilHelper;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.Control;
import javafx.scene.control.ToolBar;
import javafx.scene.control.ToolBarBuilder;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderPaneBuilder;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Aug-13
 */
public class ButtonStyles extends Application
{
    @Override
    public void start(Stage primaryStage)
    {
        primaryStage.setTitle("JavaFX CSS Buttons");

        Node[] buttons = new Node[]{
                FXUtilHelper.build(new Button("Green"), btn -> btn.setId("green")),
                FXUtilHelper.build(new Button("Round Red"), btn -> btn.setId("round-red")),
                FXUtilHelper.build(new Button("Bevel Grey"), btn -> btn.setId("bevel-grey")),
                FXUtilHelper.build(new Button("Glass Grey"), btn -> btn.setId("glass-grey")),
                FXUtilHelper.build(new Button("Shiny Orange"), btn -> btn.setId("shiny-orange")),
                FXUtilHelper.build(new Button("Dark Blue"), btn -> btn.setId("dark-blue")),
                FXUtilHelper.build(new Button("Record Sales"), btn -> btn.setId("record-sales")),
                FXUtilHelper.build(new Button("Rich Blue"), btn -> btn.setId("rich-blue")),
                FXUtilHelper.build(new Button("Big Yellow"), btn -> btn.setId("big-yellow")),
                FXUtilHelper.build(new ToolBar(), bar -> {
                    bar.setId("iphone-toolbar");
                    bar.getItems().addAll(
                            FXUtilHelper.build(new Button("iPhone"), btn -> btn.setId("iphone"))
                    );
                }),
                FXUtilHelper.build(new Button("Large iPad Dark Grey"), btn -> btn.setId("ipad-dark-grey")),
                FXUtilHelper.build(new Button("Large iPad Grey"), btn -> btn.setId("ipad-grey")),
                FXUtilHelper.build(new Button("OSX Lion (Default)"), btn -> btn.setId("lion-default")),
                FXUtilHelper.build(new Button("OSX Lion"), btn -> btn.setId("lion")),
                FXUtilHelper.build(new Button("Windows 7 (Default)"), btn -> btn.setId("windows7-default")),
                FXUtilHelper.build(new Button("Windows 7"), btn -> btn.setId("windows7"))
        };


        VBox leftPane = FXUtilHelper.build(new VBox(10), pane -> {
            pane.setPadding(new Insets(20));
            pane.setAlignment(Pos.CENTER);
            pane.getChildren().addAll(buttons);
        });

        VBox centerPane = FXUtilHelper.build(new VBox(10), pane -> {
            pane.setPadding(new Insets(20));
            pane.setAlignment(Pos.CENTER);
            pane.setStyle("-fx-background-color: #373737;");
            pane.getChildren().addAll(buttons);
        });

        BorderPane root = FXUtilHelper.build(new BorderPane(), borderPane -> {
            borderPane.setLeft(leftPane);
            borderPane.setCenter(centerPane);
        });

        primaryStage.setScene(
                FXUtilHelper.build(new Scene(root), scene -> {
                    scene.getStylesheets().add(getResource("/css/buttons.css"));
                    scene.setFill(Color.gray(0.9));
                })
        );

        primaryStage.show();
    }

    private String getResource(String resourceName)
    {
        return getClass().getResource(resourceName).toExternalForm();
    }
    
    public static void main(String[] args) { launch(args); }
}