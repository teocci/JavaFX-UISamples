package com.github.teocci.codesample.javafx.managers.tictactoe;

import com.github.teocci.codesample.javafx.models.tictactoe.Game;
import javafx.scene.Scene;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-27
 */
public class GameManager
{
    private Scene gameScene;
    private Game game;

    public GameManager()
    {
        newGame();
    }

    public void newGame()
    {
        game = new Game(this);

        if (gameScene == null) {
            gameScene = new Scene(game.getSkin());
        } else {
            gameScene.setRoot(game.getSkin());
        }
    }

    public void quit()
    {
        gameScene.getWindow().hide();
    }

    public Game getGame()
    {
        return game;
    }

    public Scene getGameScene()
    {
        return gameScene;
    }
}

