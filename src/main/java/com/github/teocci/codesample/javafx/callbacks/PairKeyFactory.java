package com.github.teocci.codesample.javafx.callbacks;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import javafx.util.Pair;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-26
 */
public class PairKeyFactory implements Callback<TableColumn.CellDataFeatures<Pair<String, Object>, String>, ObservableValue<String>>
{
    @Override
    public ObservableValue<String> call(TableColumn.CellDataFeatures<Pair<String, Object>, String> data)
    {
        return new ReadOnlyObjectWrapper<>(data.getValue().getKey());
    }
}
