package com.github.teocci.codesample.javafx.models.tictactoe;

import com.github.teocci.codesample.javafx.elements.tictactoe.SquareSkin;
import com.github.teocci.codesample.javafx.enums.tictactoe.State;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.Node;

import static com.github.teocci.codesample.javafx.enums.tictactoe.State.EMPTY;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-27
 */
public class Square
{
    private final SquareSkin skin;

    private ReadOnlyObjectWrapper<State> state = new ReadOnlyObjectWrapper<>(EMPTY);

    public ReadOnlyObjectProperty<State> stateProperty()
    {
        return state.getReadOnlyProperty();
    }

    public State getState()
    {
        return state.get();
    }

    private final Game game;

    public Square(Game game)
    {
        this.game = game;

        skin = new SquareSkin(this);
    }

    public void pressed()
    {
        if (!game.isGameOver() && state.get() == EMPTY) {
            state.set(game.getCurrentPlayer());
            game.boardUpdated();
            game.nextTurn();
        }
    }

    public Node getSkin()
    {
        return skin;
    }
}
