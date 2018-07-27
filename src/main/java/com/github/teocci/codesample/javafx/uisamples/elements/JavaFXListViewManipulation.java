package com.github.teocci.codesample.javafx.uisamples.elements;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Removal button for removing selected items from a JavaFX ListView
 * <p>
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-26
 */

public class JavaFXListViewManipulation extends Application
{
    @Override
    public void start(final Stage stage)
    {
        final Label status = new Label();
        final Label changeReport = new Label();

        final ListView<String> listView = new ListView<>();
        initListView(listView);

        listView
                .getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> changeReport.setText("Selection changed from '" + oldValue + "' to '" + newValue + "'"));

        final Button removeButton = new Button("Remove Selected");
        removeButton.setOnAction(event -> {
            final int selectedIdx = listView.getSelectionModel().getSelectedIndex();
            if (selectedIdx != -1) {
                String itemToRemove = listView.getSelectionModel().getSelectedItem();

                final int newSelectedIdx =
                        (selectedIdx == listView.getItems().size() - 1)
                                ? selectedIdx - 1
                                : selectedIdx;

                listView.getItems().remove(selectedIdx);
                status.setText("Removed " + itemToRemove);
                listView.getSelectionModel().select(newSelectedIdx);
            }
        });
        final Button resetButton = new Button("Reset List");
        resetButton.setOnAction(event -> {
            initListView(listView);
            status.setText("List Reset");
        });
        final HBox controls = new HBox(10);
        controls.setAlignment(Pos.CENTER);
        controls.getChildren().addAll(removeButton, resetButton);

        final VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 10; -fx-background-color: cornsilk;");
        layout.getChildren().setAll(
                listView,
                controls,
                status,
                changeReport
        );
        layout.setPrefWidth(320);

        stage.setScene(new Scene(layout));
        stage.show();
    }

    private void initListView(ListView<String> listView)
    {
        listView.getItems().setAll("apples", "oranges", "peaches", "pears");
    }

    public static void main(String[] args) { launch(args); }
}
