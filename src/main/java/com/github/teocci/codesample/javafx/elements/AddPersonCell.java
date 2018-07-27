package com.github.teocci.codesample.javafx.elements;

import com.github.teocci.codesample.javafx.models.Person;
import com.github.teocci.codesample.javafx.utils.FXUtilHelper;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


/**
 * A table cell containing a button for adding a new person.
 * <p>
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-27
 */

public class AddPersonCell extends TableCell<Person, Boolean>
{
    // A button for adding a new person.
    private final Button addButton = new Button("Add");
    // Pads and centers the add button in the cell.
    private final StackPane paddedButton = new StackPane();
    // Records the y pos of the last button press so that the add person dialog can be shown next to the cell.
    private final DoubleProperty buttonY = new SimpleDoubleProperty();

    /**
     * AddPersonCell constructor
     *
     * @param stage the stage in which the table is placed.
     * @param table the table to which a new person can be added.
     */
    public AddPersonCell(final Stage stage, final TableView table)
    {
        paddedButton.setPadding(new Insets(3));
        paddedButton.getChildren().add(addButton);
        addButton.setOnMousePressed(mouseEvent -> buttonY.set(mouseEvent.getScreenY()));
        addButton.setOnAction(actionEvent -> {
            showAddPersonDialog(stage, table, buttonY.get());
            table.getSelectionModel().select(getTableRow().getIndex());
        });
    }

    /**
     * places an add button in the row only if the row is not empty.
     */
    @Override
    protected void updateItem(Boolean item, boolean empty)
    {
        super.updateItem(item, empty);
        if (!empty) {
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            setGraphic(paddedButton);
        } else {
            setGraphic(null);
        }
    }

    /**
     * shows a dialog which displays a UI for adding a person to a table.
     *
     * @param parent a parent stage to which this dialog will be modal and placed next to.
     * @param table  the table to which a person is to be added.
     * @param y      the y position of the top left corner of the dialog.
     */
    private void showAddPersonDialog(Stage parent, final TableView<Person> table, double y)
    {
        // initialize the dialog.
        final Stage dialog = new Stage();
        dialog.setTitle("New Person");
        dialog.initOwner(parent);
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.initStyle(StageStyle.UTILITY);
        dialog.setX(parent.getX() + parent.getWidth());
        dialog.setY(y);

        final TextField firstNameField = new TextField();
        final TextField lastNameField = new TextField();

        // create a grid for the data entry.
        GridPane grid = new GridPane();
        grid.addRow(0, new Label("First Name"), firstNameField);
        grid.addRow(1, new Label("Last Name"), lastNameField);
        grid.setHgap(10);
        grid.setVgap(10);
        GridPane.setHgrow(firstNameField, Priority.ALWAYS);
        GridPane.setHgrow(lastNameField, Priority.ALWAYS);

        // Create action buttons for the dialog.
        Button ok = new Button("OK");
        ok.setDefaultButton(true);
        // Only enable the ok button when there has been some text entered.
        ok.disableProperty().bind(firstNameField.textProperty().isEqualTo("").or(lastNameField.textProperty().isEqualTo("")));

        // Add action handlers for the dialog buttons.
        ok.setOnAction(actionEvent -> {
            int nextIndex = table.getSelectionModel().getSelectedIndex() + 1;
            table.getItems().add(nextIndex, new Person(firstNameField.getText(), lastNameField.getText()));
            table.getSelectionModel().select(nextIndex);
            dialog.close();
        });

        Button cancel = new Button("Cancel");
        cancel.setCancelButton(true);
        cancel.setOnAction(actionEvent -> dialog.close());

        // Layout the dialog.
        HBox buttons = FXUtilHelper.build(new HBox(), hbox -> {
            hbox.setSpacing(10);
            hbox.setAlignment(Pos.CENTER_RIGHT);
            hbox.getChildren().addAll(ok, cancel);
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(grid, buttons);
        layout.setPadding(new Insets(5));

        dialog.setScene(new Scene(layout));
        dialog.show();
    }
}
