package com.github.teocci.codesample.javafx.uisamples.elements;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Determines the relative position (index in child list) of a node clicked in a JavaFX HBox.
 * <p>
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-26
 */

public class HBoxPositions extends Application
{

    private static final String[] ICON_LOCS =
            {
                    "http://icons.iconarchive.com/icons/bevel-and-emboss/car/256/car-orange-icon.png",
                    "http://icons.iconarchive.com/icons/bevel-and-emboss/car/256/car-purple-icon.png",
                    "http://icons.iconarchive.com/icons/bevel-and-emboss/car/256/lorry-icon.png",
                    "http://icons.iconarchive.com/icons/bevel-and-emboss/car/256/van-bus-icon.png"
            };
    private Image[] icons = new Image[ICON_LOCS.length];

    @Override
    public void init()
    {
        int i = 0;
        for (String loc : ICON_LOCS) {
            icons[i] = new Image(loc);
            i++;
        }
    }

    @Override
    public void start(final Stage stage)
    {
        final Label label = new Label();
        label.setStyle("-fx-font-family: 'Comic Sans MS'; -fx-font-size: 20;");

        final HBox images = new HBox(10);
        for (Image icon : icons) {
            final ImageView imageView = new ImageView(icon);
            images.getChildren().add(imageView);

            final DropShadow shadow = new DropShadow();
            shadow.setColor(Color.FORESTGREEN);
            final Glow glow = new Glow();
            glow.setInput(shadow);
            imageView.setOnMouseEntered(mouseEvent -> imageView.setEffect(glow));
            imageView.setOnMouseExited(mouseEvent -> imageView.setEffect(null));

            imageView.setOnMousePressed(mouseEvent -> {
                final Object selectedNode = mouseEvent.getSource();
                final int selectedIdx = images.getChildren().indexOf(selectedNode);

                label.setText(
                        "Selected Vehicle: " + (selectedIdx + 1)
                );
            });
        }

        final VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 10; -fx-background-color: cornsilk;");
        layout.getChildren().addAll(images, label);

        stage.setScene(new Scene(layout));
        stage.show();
    }

    public static void main(String[] args) { launch(args); }
}
