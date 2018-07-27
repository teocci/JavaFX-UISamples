package com.github.teocci.codesample.javafx.uisamples.elements;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.Random;

/**
 * Shows how to make a small context sensitive dialog which popups on pressing a button.
 * <p>
 * Icon source: Linkware (Backlink to http://www.aha-soft.com required)
 * <p>
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-27
 */
public class PopupButton extends Application
{
    private final String IMAGE_WIZ_URL = "http://icons.iconarchive.com/icons/aha-soft/free-large-boss/128/Wizard-icon.png";

    private final Random random = new Random();

    private final String[] animals = {"dog", "cat", "rhino", "hippo", "toad"};

    private final ImageView wiz = new ImageView(new Image(IMAGE_WIZ_URL));

    @Override
    public void start(final Stage stage) throws Exception
    {
        // create a popup trigger menu button and it's associated popup content.
        final MenuItem wizPopup = new MenuItem();

        wizPopup.setGraphic(createPopupContent(wiz));

        final MenuButton popupButton = new MenuButton("Wizard");
        popupButton.getItems().setAll(
                wizPopup
        );

        // show the scene.
        final VBox layout = new VBox(20);
        layout.setStyle("-fx-background-color: cornsilk; " +
                "-fx-padding: 10;"
        );
        layout.getChildren().addAll(popupButton);
        Scene scene = new Scene(layout);
        scene.getStylesheets().add(getClass().getResource("/css/context-color.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    private VBox createPopupContent(final ImageView wiz)
    {
        final Label unfortunateEvent = new Label();
        unfortunateEvent.setWrapText(true);
        unfortunateEvent.setTextAlignment(TextAlignment.CENTER);
        unfortunateEvent.setMaxWidth(wiz.getImage().getWidth());

        final Button wand = new Button("Cast spell");

        final VBox wizBox = new VBox(5);
        wizBox.setAlignment(Pos.CENTER);
        wizBox.getChildren().setAll(
                wiz,
                wand,
                unfortunateEvent
        );

        wand.setOnAction(t -> unfortunateEvent.setText(
                "Zap! The wizard has turned you into a " + animals[random.nextInt(animals.length)] + "."
        ));

        return wizBox;
    }

    public static void main(String[] args) throws Exception { launch(args); }
}
