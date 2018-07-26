package com.github.teocci.codesample.javafx.handlers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-26
 */
public class ClearStatusListener implements ChangeListener<String>
{
    final Label status;

    public ClearStatusListener(Label status)
    {
        this.status = status;
    }

    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
    {
        status.setText("");
    }
}
