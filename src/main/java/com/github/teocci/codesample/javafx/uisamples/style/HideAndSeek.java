package com.github.teocci.codesample.javafx.uisamples.style;

import javafx.animation.*;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Demonstrates fading in and out a label while at the same time gradually resizing it to nothing and restoring it.
 * <p>
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-27
 */
public class HideAndSeek extends Application
{
    @Override
    public void start(Stage stage)
    {
        Label label = new Label(TEXT);

        ToggleButton visibilityControl = new ToggleButton("Hide");
        visibilityControl.textProperty().bind(
                Bindings.when(
                        visibilityControl.selectedProperty()
                ).then("Show")
                        .otherwise("Hide")
        );

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: cornsilk; -fx-padding: 10;");
        layout.getChildren().setAll(label, visibilityControl);

        stage.setScene(new Scene(layout));
        stage.show();

        final double standardWidth = label.getWidth();
        final double standardHeight = label.getHeight();
        label.setMaxSize(standardWidth, standardHeight);

        final Timeline hide = new Timeline(
                new KeyFrame(
                        Duration.seconds(3),
                        new KeyValue(label.opacityProperty(), 0),
                        new KeyValue(label.maxWidthProperty(), 0),
                        new KeyValue(label.maxHeightProperty(), 0)
                )
        );

        final Timeline show = new Timeline(
                new KeyFrame(
                        Duration.seconds(3),
                        new KeyValue(label.opacityProperty(), 1),
                        new KeyValue(label.maxWidthProperty(), standardWidth),
                        new KeyValue(label.maxHeightProperty(), standardHeight)
                )
        );

        visibilityControl.selectedProperty().addListener((ov, wasSelected, selected) -> {
            if (selected) {
                hide.play();
            } else {
                show.play();
            }
        });
    }

    private static final String TEXT =
            "  WITCH.  Fillet of a fenny snake,\n" +
                    "In the caldron boil and bake;\n" +
                    "Eye of newt, and toe of frog,\n" +
                    "Wool of bat, and tongue of dog,\n" +
                    "Adder's fork, and blind-worm's sting,\n" +
                    "Lizard's leg, and owlet's wing,—\n" +
                    "For a charm of powerful trouble,\n" +
                    "Like a hell-broth boil and bubble.\n\n" +
                    "  ALL.  Double, double toil and trouble;\n" +
                    "Fire burn, and caldron bubble. ";

    public static void main(String[] args) { Application.launch(HideAndSeek.class, args); }
}
