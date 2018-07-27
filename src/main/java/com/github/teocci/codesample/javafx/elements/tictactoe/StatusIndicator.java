package com.github.teocci.codesample.javafx.elements.tictactoe;

import com.github.teocci.codesample.javafx.models.tictactoe.Game;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import static com.github.teocci.codesample.javafx.enums.tictactoe.State.CROSS;
import static com.github.teocci.codesample.javafx.enums.tictactoe.State.EMPTY;
import static com.github.teocci.codesample.javafx.enums.tictactoe.State.NOUGHT;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-27
 */
public class StatusIndicator extends HBox
{
    private final ImageView playerToken = new ImageView();
    private final Label playerLabel = new Label("Current Player: ");

    StatusIndicator(Game game)
    {
        getStyleClass().add("status-indicator");

        bindIndicatorFieldsToGame(game);

        playerToken.setFitHeight(32);
        playerToken.setPreserveRatio(true);

        playerLabel.getStyleClass().add("info");

        getChildren().addAll(playerLabel, playerToken);
    }

    private void bindIndicatorFieldsToGame(Game game)
    {
        playerToken.imageProperty().bind(Bindings
                .when(game.currentPlayerProperty().isEqualTo(NOUGHT))
                .then(SquareSkin.NOUGHT_IMAGE)
                .otherwise(Bindings
                        .when(game.currentPlayerProperty().isEqualTo(CROSS))
                        .then(SquareSkin.CROSS_IMAGE)
                        .otherwise((Image) null)
                )
        );

        playerLabel.textProperty().bind(Bindings
                .when(game.gameOverProperty().not())
                .then("Current Player: ")
                .otherwise(Bindings
                        .when(game.winnerProperty().isEqualTo(EMPTY))
                        .then("Draw")
                        .otherwise("Winning Player: ")
                )
        );
    }
}
