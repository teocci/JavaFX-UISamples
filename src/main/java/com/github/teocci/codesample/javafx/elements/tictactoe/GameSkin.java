package com.github.teocci.codesample.javafx.elements.tictactoe;

import com.github.teocci.codesample.javafx.managers.tictactoe.GameManager;
import com.github.teocci.codesample.javafx.models.tictactoe.Game;
import javafx.scene.layout.VBox;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-27
 */
public class GameSkin extends VBox
{
    public GameSkin(GameManager gameManager, Game game)
    {
        getChildren().addAll(
                game.getBoard().getSkin(),
                new StatusIndicator(game),
                new GameControls(gameManager, game)
        );
    }
}
