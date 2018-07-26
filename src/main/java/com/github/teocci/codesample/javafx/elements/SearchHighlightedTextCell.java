package com.github.teocci.codesample.javafx.elements;

import javafx.beans.property.StringProperty;
import javafx.scene.control.ListCell;

/**
 * Sample for highlighting text displayed in a JavaFX ListView based on search results.
 * <p>
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-26
 */
public class SearchHighlightedTextCell extends ListCell<String>
{
    private static final String HIGHLIGHT_CLASS = "search-highlight";

    private final StringProperty searchText;

    public SearchHighlightedTextCell(StringProperty searchText)
    {
        this.searchText = searchText;
    }

    @Override
    protected void updateItem(String text, boolean empty)
    {
        super.updateItem(text, empty);

        setText(text == null ? "" : text);

        updateStyleClass();

        searchText.addListener(observable -> updateStyleClass());
    }

    private void updateStyleClass()
    {
        if (isEmptyString(searchText.get()) || isEmptyString(getText()) || !getText().contains(searchText.get())) {
            getStyleClass().remove(HIGHLIGHT_CLASS);
        } else {
            getStyleClass().add(HIGHLIGHT_CLASS);
        }
    }

    private boolean isEmptyString(String text)
    {
        return text == null || text.equals("");
    }
}