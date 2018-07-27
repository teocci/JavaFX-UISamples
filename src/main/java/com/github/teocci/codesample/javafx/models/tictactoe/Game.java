package com.github.teocci.codesample.javafx.models.tictactoe;

import com.github.teocci.codesample.javafx.elements.tictactoe.GameSkin;
import com.github.teocci.codesample.javafx.enums.tictactoe.State;
import com.github.teocci.codesample.javafx.managers.tictactoe.GameManager;
import com.github.teocci.codesample.javafx.managers.tictactoe.WinningStrategy;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.Parent;

import static com.github.teocci.codesample.javafx.enums.tictactoe.State.CROSS;
import static com.github.teocci.codesample.javafx.enums.tictactoe.State.EMPTY;
import static com.github.teocci.codesample.javafx.enums.tictactoe.State.NOUGHT;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-27
 */
public class Game
{
    private GameSkin skin;
    private Board board = new Board(this);
    private WinningStrategy winningStrategy = new WinningStrategy(board);

    private ReadOnlyObjectWrapper<State> currentPlayer = new ReadOnlyObjectWrapper<>(CROSS);

    public ReadOnlyObjectProperty<State> currentPlayerProperty()
    {
        return currentPlayer.getReadOnlyProperty();
    }

    public State getCurrentPlayer()
    {
        return currentPlayer.get();
    }

    private ReadOnlyObjectWrapper<State> winner = new ReadOnlyObjectWrapper<>(EMPTY);

    public ReadOnlyObjectProperty<State> winnerProperty()
    {
        return winner.getReadOnlyProperty();
    }

    private ReadOnlyBooleanWrapper drawn = new ReadOnlyBooleanWrapper(false);

    public ReadOnlyBooleanProperty drawnProperty()
    {
        return drawn.getReadOnlyProperty();
    }

    public boolean isDrawn()
    {
        return drawn.get();
    }

    private ReadOnlyBooleanWrapper gameOver = new ReadOnlyBooleanWrapper(false);

    public ReadOnlyBooleanProperty gameOverProperty()
    {
        return gameOver.getReadOnlyProperty();
    }

    public boolean isGameOver()
    {
        return gameOver.get();
    }

    public Game(GameManager gameManager)
    {
        gameOver.bind(
                winnerProperty().isNotEqualTo(EMPTY)
                        .or(drawnProperty())
        );

        skin = new GameSkin(gameManager, this);
    }

    public Board getBoard()
    {
        return board;
    }

    public void nextTurn()
    {
        if (isGameOver()) return;

        switch (currentPlayer.get()) {
            case EMPTY:
            case NOUGHT:
                currentPlayer.set(CROSS);
                break;
            case CROSS:
                currentPlayer.set(NOUGHT);
                break;
        }
    }

    private void checkForWinner()
    {
        winner.set(winningStrategy.getWinner());
        drawn.set(winningStrategy.isDrawn());

        if (isDrawn()) {
            currentPlayer.set(EMPTY);
        }
    }

    public void boardUpdated()
    {
        checkForWinner();
    }

    public Parent getSkin()
    {
        return skin;
    }
}
