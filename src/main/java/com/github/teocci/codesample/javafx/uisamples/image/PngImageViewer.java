package com.github.teocci.codesample.javafx.uisamples.image;

import com.github.teocci.codesample.javafx.utils.UtilHelper;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Small JavaFX application for viewing png files.
 * <p>
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-27
 */

public class PngImageViewer extends Application
{
    @Override
    public void start(Stage primaryStage)
    {
        final ImageView imageView = new ImageView();
        Button imageChooser = createImageChooserButton(imageView);
        ScrollPane scroll = UtilHelper.makeScrollable(imageView);

        VBox layout = new VBox(10);
        layout.setStyle("-fx-background-color: cornsilk; -fx-padding: 10;");
        layout.getChildren().addAll(
                imageChooser,
                scroll
        );
        VBox.setVgrow(scroll, Priority.ALWAYS);

        primaryStage.setScene(new Scene(layout, 800, 600));
        primaryStage.show();
    }

    private Image choosePng()
    {
        try {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                    "PNG files (*.png)",
                    "*.png"
            );
            fileChooser.getExtensionFilters().add(extFilter);

            File imageFile = fileChooser.showOpenDialog(null);
            if (imageFile == null) {
                return null;
            }

            String imageFileLoc = imageFile.toURI().toURL().toExternalForm();

            return new Image(imageFileLoc);
        } catch (MalformedURLException ex) {
            Logger.getLogger(PngImageViewer.class.getName()).log(Level.SEVERE, null, ex);

            return null;
        }
    }

    private Button createImageChooserButton(final ImageView imageView)
    {
        Button imageChooser = new Button("Choose Image");
        imageChooser.setOnAction(t -> {
            Image chosenImage = choosePng();
            if (chosenImage != null) {
                imageView.setImage(chosenImage);
            }
        });
        return imageChooser;
    }

    public static void main(String[] args) { Application.launch(args); }
}
