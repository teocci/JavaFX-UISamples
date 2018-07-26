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
public class PairValueFactory implements Callback<TableColumn.CellDataFeatures<Pair<String, Object>, Object>, ObservableValue<Object>>
{
    @SuppressWarnings("unchecked")
    @Override
    public ObservableValue<Object> call(TableColumn.CellDataFeatures<Pair<String, Object>, Object> data)
    {
        Object value = data.getValue().getValue();
        return (value instanceof ObservableValue)
                ? (ObservableValue) value
                : new ReadOnlyObjectWrapper<>(value);
    }
}
