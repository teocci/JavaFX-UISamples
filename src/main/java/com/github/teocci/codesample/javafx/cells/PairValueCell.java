package com.github.teocci.codesample.javafx.cells;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Pair;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-26
 */
public class PairValueCell extends TableCell<Pair<String, Object>, Object>
{
    @Override
    protected void updateItem(Object item, boolean empty)
    {
        super.updateItem(item, empty);

        if (item != null) {
            if (item instanceof String) {
                setText((String) item);
                setGraphic(null);
            } else if (item instanceof Integer) {
                setText(Integer.toString((Integer) item));
                setGraphic(null);
            } else if (item instanceof Boolean) {
                CheckBox checkBox = new CheckBox();
                checkBox.setSelected((boolean) item);
                setGraphic(checkBox);
            } else if (item instanceof Image) {
                setText(null);
                ImageView imageView = new ImageView((Image) item);
                imageView.setFitWidth(100);
                imageView.setPreserveRatio(true);
                imageView.setSmooth(true);
                setGraphic(imageView);
            } else {
                setText("N/A");
                setGraphic(null);
            }
        } else {
            setText(null);
            setGraphic(null);
        }
    }
}
