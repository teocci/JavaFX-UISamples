package com.github.teocci.codesample.javafx.elements.tictactoe;

import com.github.teocci.codesample.javafx.models.tictactoe.Square;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-27
 */
public class SquareSkin extends StackPane
{
    public static final Image NOUGHT_IMAGE = new Image("http://icons.iconarchive.com/icons/double-j-design/origami-colored-pencil/128/green-cd-icon.png");
    public static final Image CROSS_IMAGE = new Image("http://icons.iconarchive.com/icons/double-j-design/origami-colored-pencil/128/blue-cross-icon.png");

    private final ImageView imageView = new ImageView();

    public SquareSkin(final Square square)
    {
        getStyleClass().add("square");

        imageView.setMouseTransparent(true);

        getChildren().setAll(imageView);
        setPrefSize(CROSS_IMAGE.getHeight() + 20, CROSS_IMAGE.getHeight() + 20);

        setOnMousePressed(mouseEvent -> square.pressed());

        square.stateProperty().addListener((observableValue, oldState, state) -> {
            switch (state) {
                case EMPTY:
                    imageView.setImage(null);
                    break;
                case NOUGHT:
                    imageView.setImage(NOUGHT_IMAGE);
                    break;
                case CROSS:
                    imageView.setImage(CROSS_IMAGE);
                    break;
            }
        });
    }
}
