package com.github.teocci.codesample.javafx.uisamples.image;

import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-26
 */
public class WingClipper
{
    @FXML
    private ImageView imageView;

    @FXML
    public void initialize()
    {
        // set a clip to apply rounded border to the original image.
        Rectangle clip = new Rectangle(imageView.getFitWidth(), imageView.getFitHeight());
        clip.setArcWidth(20);
        clip.setArcHeight(20);
        imageView.setClip(clip);

        // snapshot the rounded image.
        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        WritableImage image = imageView.snapshot(parameters, null);

        // remove the rounding clip so that our effect can show through.
        imageView.setClip(null);

        // apply a shadow effect.
        imageView.setEffect(new DropShadow(20, Color.BLACK));

        // store the rounded image in the imageView.
        imageView.setImage(image);
    }
}
