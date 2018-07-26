package com.github.teocci.codesample.javafx.uisamples;

import com.github.teocci.codesample.javafx.cells.BirdCell;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Use Drag and Drop to reorder items in a JavaFX ListView
 * <p>
 * Disclose: Iconset homepage: http://jozef89.deviantart.com/art/Origami-Birds-400642253
 * License: CC Attribution-Noncommercial-No Derivate 3.0
 * Commercial usage: Not allowed
 *
 * @author teocci@yandex.com on 2018-Jul-26
 */

public class ListOrganizer extends Application
{
    private static final String PREFIX = "http://icons.iconarchive.com/icons/jozef89/origami-birds/72/bird";

    private static final String SUFFIX = "-icon.png";

    private static final ObservableList<String> birds = FXCollections.observableArrayList(
            "-black",
            "-blue",
            "-red",
            "-red-2",
            "-yellow",
            "s-green",
            "s-green-2"
    );

    private static final ObservableList<Image> birdImages = FXCollections.observableArrayList();

    @Override
    public void start(Stage stage) throws Exception
    {
        birds.forEach(bird -> birdImages.add(new Image(PREFIX + bird + SUFFIX)));

        ListView<String> birdList = new ListView<>(birds);
        birdList.setCellFactory(param -> new BirdCell(birdImages));
        birdList.setPrefWidth(180);

        VBox layout = new VBox(birdList);
        layout.setPadding(new Insets(10));

        stage.setScene(new Scene(layout));
        stage.show();
    }

    public static void main(String[] args)
    {
        launch(ListOrganizer.class);
    }
}
