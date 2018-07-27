package com.github.teocci.codesample.javafx.uisamples.elements;

import com.github.teocci.codesample.javafx.callbacks.PairKeyFactory;
import com.github.teocci.codesample.javafx.callbacks.PairValueFactory;
import com.github.teocci.codesample.javafx.cells.PairValueCell;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Pair;

/**
 * Creates a JavaFX table based on a list of pairs of objects whose values can be assorted different types.
 * <p>
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-26
 */

public class PairTable extends Application
{
    public static final String NAME_COLUMN_NAME = "Name";
    public static final String VALUE_COLUMN_NAME = "Value";

    final TableView<Pair<String, Object>> table = new TableView<>();

    public void start(final Stage stage) throws Exception
    {
        // model data
        ObservableList<Pair<String, Object>> data = FXCollections.observableArrayList(
                pair("Song", "Bach Cello Suite 2"),
                pair("Image", new Image("https://upload.wikimedia.org/wikipedia/en/9/99/Bach_Seal.jpg")),
                pair("Rating", 4),
                pair("Classic", true),
                pair("Song Data", new byte[]{})
        );

        table.getItems().setAll(data);
        table.setPrefHeight(275);

        // table definition
        TableColumn<Pair<String, Object>, String> nameColumn = new TableColumn<>(NAME_COLUMN_NAME);
        nameColumn.setPrefWidth(100);
        TableColumn<Pair<String, Object>, Object> valueColumn = new TableColumn<>(VALUE_COLUMN_NAME);
        valueColumn.setSortable(false);
        valueColumn.setPrefWidth(150);

        nameColumn.setCellValueFactory(new PairKeyFactory());
        valueColumn.setCellValueFactory(new PairValueFactory());

        table.getColumns().setAll(nameColumn, valueColumn);
        valueColumn.setCellFactory(column -> new PairValueCell());

        // layout the scene.
        final StackPane layout = new StackPane();
        layout.getChildren().setAll(table);
        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.show();
    }

    private Pair<String, Object> pair(String name, Object value)
    {
        return new Pair<>(name, value);
    }

    public static void main(String[] args) throws Exception
    {
        launch(args);
    }
}