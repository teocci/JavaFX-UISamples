package com.github.teocci.codesample.javafx.uisamples.image;

import javafx.application.*;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * Demonstrates different methods of scaling images in JavaFX and the effects of scaling with each.
 * <p>
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-26
 */
public class ImageScaler extends Application
{
    private static final String IMAGE_LOC = "https://i.imgur.com/ydmnWRt.png";

    private static final int SCALE_FACTOR = 6;

    private Image image;
    private int scaledImageSize;

    @Override
    public void init()
    {
        image = new Image(IMAGE_LOC);

        scaledImageSize = (int) image.getWidth() * SCALE_FACTOR;
    }

    @Override
    public void start(Stage stage)
    {
        GridPane layout = new GridPane();
        layout.setHgap(10);
        layout.setVgap(10);

        ImageView originalImageView = new ImageView(image);
        StackPane originalImageViewStack = new StackPane();
        originalImageViewStack.getChildren().add(originalImageView);
        originalImageViewStack.setMinWidth(scaledImageSize);

        ImageView fittedImageView = new ImageView(image);
        fittedImageView.setSmooth(false);
        fittedImageView.setFitWidth(scaledImageSize);
        fittedImageView.setFitHeight(scaledImageSize);

        ImageView scaledImageView = new ImageView(image);
        scaledImageView.setSmooth(false);
        scaledImageView.setScaleX(SCALE_FACTOR);
        scaledImageView.setScaleY(SCALE_FACTOR);
        Group scaledImageViewGroup = new Group(scaledImageView);

        ImageView sizedImageInView = new ImageView(
                new Image(
                        IMAGE_LOC,
                        scaledImageSize,
                        scaledImageSize,
                        false,
                        false
                )
        );

        ImageView resampledImageView = new ImageView(
                resample(
                        image,
                        SCALE_FACTOR
                )
        );

        layout.addRow(
                0,
                withTooltip(
                        originalImageViewStack,
                        "Unmodified image"
                ),
                withTooltip(
                        sizedImageInView,
                        "Image sized in Image constructor - Image smoothing false"
                ),
                withTooltip(
                        fittedImageView,
                        "Image fitted using ImageView fitWidth/fitHeight - ImageView smoothing false"
                ),
                withTooltip(
                        scaledImageViewGroup,
                        "ImageView scaled with Node scaleX/scaleY - ImageView smoothing false"
                ),
                withTooltip(
                        resampledImageView,
                        "Image manually recreated as a new WritableImage using a PixelWriter"
                )
        );

        layout.addRow(
                1,
                centeredLabel("Original"),
                centeredLabel("Sized"),
                centeredLabel("Fitted"),
                centeredLabel("Scaled"),
                centeredLabel("Resampled")
        );
        layout.setAlignment(Pos.CENTER);

        layout.setStyle("-fx-background-color: cornsilk; -fx-padding: 10;");
        stage.setScene(
                new Scene(layout)
        );
        stage.show();
    }

    private Node withTooltip(Node node, String text)
    {
        Tooltip.install(node, new Tooltip(text));
        return node;
    }

    private Label centeredLabel(String text)
    {
        Label label = new Label(text);
        GridPane.setHalignment(label, HPos.CENTER);

        return label;
    }

    private Image resample(Image input, int scaleFactor)
    {
        final int W = (int) input.getWidth();
        final int H = (int) input.getHeight();
        final int S = scaleFactor;

        WritableImage output = new WritableImage(
                W * S,
                H * S
        );

        PixelReader reader = input.getPixelReader();
        PixelWriter writer = output.getPixelWriter();

        for (int y = 0; y < H; y++) {
            for (int x = 0; x < W; x++) {
                final int argb = reader.getArgb(x, y);
                for (int dy = 0; dy < S; dy++) {
                    for (int dx = 0; dx < S; dx++) {
                        writer.setArgb(x * S + dx, y * S + dy, argb);
                    }
                }
            }
        }

        return output;
    }

    public static void main(String[] args) { Application.launch(args); }
}
