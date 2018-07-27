package com.github.teocci.codesample.javafx.uisamples.features;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * JavaFX Room Reservation system - demonstrates switching between scenes in a stage.
 * <p>
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-27
 */

public class RoomReservationNavigator extends Application
{
    private Scene mainScene;
    private Scene optionsScene;
    private Stage stage;

    @Override
    public void start(Stage stage)
    {
        this.stage = stage;
        mainScene = createMainScene();
        optionsScene = createOptionsScene();

        stage.setScene(mainScene);
        stage.show();
    }

    private Scene createMainScene()
    {
        VBox layout = new VBox(10);
        layout.setStyle("-fx-background-color: cornsilk; " +
                "-fx-padding: 10;"
        );
        layout.getChildren().setAll(
                LabelBuilder.create()
                        .text("Room Reservation System")
                        .style("-fx-font-weight: bold;")
                        .build(),
                HBoxBuilder.create()
                        .spacing(5)
                        .children(
                                new Label("First Name:"),
                                new TextField("Peter")
                        )
                        .build(),
                HBoxBuilder.create()
                        .spacing(5)
                        .children(
                                new Label("Last Name:"),
                                new TextField("Parker")
                        )
                        .build(),
                new Label("Property:"),
                ChoiceBoxBuilder.<String>create()
                        .items(FXCollections.observableArrayList(
                                "The Waldorf-Astoria",
                                "The Plaza",
                                "The Algonquin Hotel"
                        ))
                        .build(),
                ButtonBuilder.create()
                        .text("Reservation Options  >>")
                        .onAction((EventHandler<ActionEvent>) t -> stage.setScene(optionsScene))
                        .build(),
                ButtonBuilder.create()
                        .text("Reserve")
                        .defaultButton(true)
                        .onAction((EventHandler<ActionEvent>) t -> stage.hide())
                        .build()
        );

        return new Scene(layout);
    }

    private Scene createOptionsScene()
    {
        VBox layout = new VBox(10);
        layout.setStyle("-fx-background-color: azure; " +
                "-fx-padding: 10;"
        );

        ChoiceBox<String> newsPapers = new ChoiceBox<>(FXCollections.observableArrayList(
                "New York Times",
                "Wall Street Journal",
                "The Daily Bugle"
        ));

        Button confirmBtn = new Button("Confirm Options");
        confirmBtn.setOnAction(t -> stage.setScene(mainScene));

        layout.getChildren().setAll(
                new CheckBox("Breakfast"),
                new Label("Paper:"),
                newsPapers,
                confirmBtn
        );

        return new Scene(layout);
    }

    public static void main(String[] args) { Application.launch(args); }
}
