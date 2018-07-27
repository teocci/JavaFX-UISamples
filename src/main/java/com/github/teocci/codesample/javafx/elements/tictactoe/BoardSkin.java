package com.github.teocci.codesample.javafx.elements.tictactoe;

import com.github.teocci.codesample.javafx.models.tictactoe.Board;
import javafx.scene.layout.GridPane;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-27
 */
public class BoardSkin extends GridPane
{
    public BoardSkin(Board board)
    {
        getStyleClass().add("board");

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                add(board.getSquare(i, j).getSkin(), i, j);
            }
        }
    }
}
