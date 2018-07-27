package com.github.teocci.codesample.javafx.uisamples.elements;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Create a pannable map background in JavaFX
 * <p>
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-27
 */

public class PannableView extends Application
{
    private Image backgroundImage;

    @Override
    public void init()
    {
        backgroundImage = new Image("https://i.imgur.com/KQuLska.jpg");
    }

    @Override
    public void start(Stage stage)
    {
        stage.setTitle("Drag the mouse to pan the map");

        // Construct the scene contents over a stacked background.
        StackPane layout = new StackPane();
        layout.getChildren().setAll(
                new ImageView(backgroundImage),
                createKillButton()
        );

        // Wrap the scene contents in a pannable scroll pane.
        ScrollPane scroll = createScrollPane(layout);

        // Show the scene.
        Scene scene = new Scene(scroll);
        stage.setScene(scene);
        stage.show();

        // Bind the preferred size of the scroll area to the size of the scene.
        scroll.prefWidthProperty().bind(scene.widthProperty());
        scroll.prefHeightProperty().bind(scene.widthProperty());

        // Center the scroll contents.
        scroll.setHvalue(scroll.getHmin() + (scroll.getHmax() - scroll.getHmin()) / 2);
        scroll.setVvalue(scroll.getVmin() + (scroll.getVmax() - scroll.getVmin()) / 2);
    }

    /**
     * @return a control to place on the scene.
     */
    private Button createKillButton()
    {
        final Button killButton = new Button("Kill the evil witch");
        killButton.setStyle("-fx-base: firebrick;");
        killButton.setTranslateX(65);
        killButton.setTranslateY(-130);
        killButton.setOnAction(t -> {
            killButton.setStyle("-fx-base: forestgreen;");
            killButton.setText("Ding-Dong! The Witch is Dead");
        });
        return killButton;
    }

    /**
     * @return a ScrollPane which scrolls the layout.
     */
    private ScrollPane createScrollPane(Pane layout)
    {
        ScrollPane scroll = new ScrollPane();
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setPannable(true);
        scroll.setPrefSize(800, 600);
        scroll.setContent(layout);

        return scroll;
    }

    public static void main(String[] args) { launch(args); }
}
