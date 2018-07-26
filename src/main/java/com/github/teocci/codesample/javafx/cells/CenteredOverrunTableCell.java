package com.github.teocci.codesample.javafx.cells;

import com.github.teocci.codesample.javafx.models.Person;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.TableCell;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-26
 */
public class CenteredOverrunTableCell extends TableCell<Person, String>
{
    public CenteredOverrunTableCell()
    {
        this(null);
    }

    public CenteredOverrunTableCell(String ellipsisString)
    {
        super();
        setTextOverrun(OverrunStyle.CENTER_WORD_ELLIPSIS);
        if (ellipsisString != null) {
            setEllipsisString(ellipsisString);
        }
    }

    @Override
    protected void updateItem(String item, boolean empty)
    {
        super.updateItem(item, empty);
        setText(item == null ? "" : item);
    }
}
