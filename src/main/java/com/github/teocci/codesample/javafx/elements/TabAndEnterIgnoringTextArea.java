package com.github.teocci.codesample.javafx.elements;

import com.github.teocci.codesample.javafx.handlers.TabAndEnterHandler;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-26
 */
public class TabAndEnterIgnoringTextArea extends TextArea
{
    final TextArea myTextArea = this;

    public TabAndEnterIgnoringTextArea()
    {
        addEventFilter(KeyEvent.KEY_PRESSED, new TabAndEnterHandler(myTextArea));
    }
}
