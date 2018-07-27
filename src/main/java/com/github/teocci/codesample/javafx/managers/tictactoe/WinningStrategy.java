package com.github.teocci.codesample.javafx.managers.tictactoe;

import com.github.teocci.codesample.javafx.enums.tictactoe.State;
import com.github.teocci.codesample.javafx.models.tictactoe.Board;

import java.util.HashMap;
import java.util.Map;

import static com.github.teocci.codesample.javafx.enums.tictactoe.State.CROSS;
import static com.github.teocci.codesample.javafx.enums.tictactoe.State.EMPTY;
import static com.github.teocci.codesample.javafx.enums.tictactoe.State.NOUGHT;
/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-27
 */
public class WinningStrategy
{
    private final Board board;

    private static final int COUNTS = 3;
    private static final int NOUGHT_WON = 3;
    private static final int CROSS_WON = 30;

    private static final Map<State, Integer> values = new HashMap<>();

    static {
        values.put(EMPTY, 0);
        values.put(NOUGHT, 1);
        values.put(CROSS, 10);
    }

    public WinningStrategy(Board board)
    {
        this.board = board;
    }

    public State getWinner()
    {
        State state = hasWon(false);
        if (state != EMPTY) return state;
        state = hasWon(true);
        if (state != EMPTY) return state;

        int score = 0;
        score += valueOf(0, 0);
        score += valueOf(1, 1);
        score += valueOf(2, 2);
        if (isWinning(score)) {
            return winner(score);
        }

        score = 0;
        score += valueOf(2, 0);
        score += valueOf(1, 1);
        score += valueOf(0, 2);
        if (isWinning(score)) {
            return winner(score);
        }

        return EMPTY;
    }

    public State hasWon(boolean invert)
    {
        for (int i = 0; i < COUNTS; i++) {
            int score = 0;
            for (int j = 0; j < COUNTS; j++) {
                score += invert ? valueOf(j, i) : valueOf(i, j);
            }
            if (isWinning(score)) {
                return winner(score);
            }
        }

        return EMPTY;
    }

    public boolean isDrawn()
    {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board.getSquare(i, j).getState() == EMPTY) {
                    return false;
                }
            }
        }

        return getWinner() == EMPTY;
    }

    private Integer valueOf(int i, int j)
    {
        return values.get(board.getSquare(i, j).getState());
    }

    private boolean isWinning(int score)
    {
        return score == NOUGHT_WON || score == CROSS_WON;
    }

    private State winner(int score)
    {
        if (score == NOUGHT_WON) return NOUGHT;
        if (score == CROSS_WON) return CROSS;

        return EMPTY;
    }
}
