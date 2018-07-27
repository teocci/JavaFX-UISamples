package com.github.teocci.codesample.javafx.elements.tictactoe;

import com.github.teocci.codesample.javafx.managers.tictactoe.GameManager;
import com.github.teocci.codesample.javafx.models.tictactoe.Game;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-27
 */
public class GameControls extends HBox
{
    public GameControls(final GameManager gameManager, final Game game)
    {
        getStyleClass().add("game-controls");

        visibleProperty().bind(game.gameOverProperty());

        Label playAgainLabel = new Label("Play Again?");
        playAgainLabel.getStyleClass().add("info");

        Button playAgainButton = new Button("Yes");
        playAgainButton.getStyleClass().add("play-again");
        playAgainButton.setDefaultButton(true);
        playAgainButton.setOnAction(actionEvent -> gameManager.newGame());

        Button exitButton = new Button("No");
        playAgainButton.getStyleClass().add("exit");
        exitButton.setCancelButton(true);
        exitButton.setOnAction(actionEvent -> gameManager.quit());

        getChildren().setAll(
                playAgainLabel,
                playAgainButton,
                exitButton
        );
    }
}
