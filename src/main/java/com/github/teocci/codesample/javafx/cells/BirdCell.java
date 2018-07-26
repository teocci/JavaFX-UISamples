package com.github.teocci.codesample.javafx.cells;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-26
 */
public  class BirdCell extends ListCell<String>
{
    private final ImageView imageView = new ImageView();

    private final ObservableList<Image> birdImages;

    public BirdCell(ObservableList<Image> birdImages)
    {
        this.birdImages = birdImages;

        ListCell thisCell = this;

        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        setAlignment(Pos.CENTER);

        setOnDragDetected(event -> {
            if (getItem() == null) {
                return;
            }

            ObservableList<String> items = getListView().getItems();

            Dragboard dragboard = startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putString(getItem());
            dragboard.setDragView(
                    birdImages.get(items.indexOf(getItem()))
            );
            dragboard.setContent(content);

            event.consume();
        });

        setOnDragOver(event -> {
            if (event.getGestureSource() != thisCell &&
                    event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }

            event.consume();
        });

        setOnDragEntered(event -> {
            if (event.getGestureSource() != thisCell &&
                    event.getDragboard().hasString()) {
                setOpacity(0.3);
            }
        });

        setOnDragExited(event -> {
            if (event.getGestureSource() != thisCell &&
                    event.getDragboard().hasString()) {
                setOpacity(1);
            }
        });

        setOnDragDropped(event -> {
            if (getItem() == null) {
                return;
            }

            Dragboard db = event.getDragboard();
            boolean success = false;

            if (db.hasString()) {
                ObservableList<String> items = getListView().getItems();
                int draggedIdx = items.indexOf(db.getString());
                int thisIdx = items.indexOf(getItem());

                Image temp = birdImages.get(draggedIdx);
                birdImages.set(draggedIdx, birdImages.get(thisIdx));
                birdImages.set(thisIdx, temp);

                items.set(draggedIdx, getItem());
                items.set(thisIdx, db.getString());

                List<String> itemscopy = new ArrayList<>(getListView().getItems());
                getListView().getItems().setAll(itemscopy);

                success = true;
            }
            event.setDropCompleted(success);

            event.consume();
        });

        setOnDragDone(DragEvent::consume);
    }

    @Override
    protected void updateItem(String item, boolean empty)
    {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setGraphic(null);
        } else {
            imageView.setImage(
                    birdImages.get(
                            getListView().getItems().indexOf(item)
                    )
            );
            setGraphic(imageView);
        }
    }
}
