package com.github.teocci.codesample.javafx.uisamples.features;

import com.github.teocci.codesample.javafx.managers.tictactoe.GameManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static com.github.teocci.codesample.javafx.elements.tictactoe.SquareSkin.CROSS_IMAGE;

/**
 * A simple Tic-Tac-Toe game in JavaFX
 * <p>
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-27
 */

public class TicTacToe extends Application
{
    @Override
    public void start(Stage stage) throws Exception
    {
        GameManager gameManager = new GameManager();

        Scene scene = gameManager.getGameScene();
        scene.getStylesheets().add(getResource("/css/tictactoe-blueskin.css"));

        stage.setTitle("Tic-Tac-Toe");
        stage.getIcons().add(CROSS_IMAGE);
        stage.setScene(scene);
        stage.show();
    }

    private String getResource(String resourceName)
    {
        return getClass().getResource(resourceName).toExternalForm();
    }

    public static void main(String[] args)
    {
        Application.launch(TicTacToe.class);
    }
}















