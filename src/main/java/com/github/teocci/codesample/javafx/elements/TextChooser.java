package com.github.teocci.codesample.javafx.elements;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-26
 */
public class TextChooser extends StackPane
{
    private Label label = new Label();
    private ComboBox<String> combo = new ComboBox<>();

    public TextChooser(String... options)
    {
        StackPane.setAlignment(label, Pos.CENTER_LEFT);
        StackPane.setAlignment(combo, Pos.CENTER_LEFT);

        label.textProperty().bind(
                combo.getSelectionModel().selectedItemProperty()
        );
        label.visibleProperty().bind(
                combo.visibleProperty().not()
        );
        label.setPadding(new Insets(0, 0, 0, 9));

        combo.getItems().setAll(options);
        combo.getSelectionModel().select(0);
        combo.setVisible(false);

        label.setOnMouseEntered(event -> combo.setVisible(true));
        combo.showingProperty().addListener(observable -> {
            if (!combo.isShowing()) {
                combo.setVisible(false);
            }
        });
        combo.setOnMouseExited(event -> {
            if (!combo.isShowing()) {
                combo.setVisible(false);
            }
        });

        getChildren().setAll(label, combo);
    }
}
