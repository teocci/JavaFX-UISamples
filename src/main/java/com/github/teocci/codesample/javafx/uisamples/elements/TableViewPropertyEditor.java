package com.github.teocci.codesample.javafx.uisamples.elements;

import com.github.teocci.codesample.javafx.models.Person;
import javafx.application.Application;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Click in the value column (a couple of times) to edit the value in the column.
 * Property editors are defined only for String and Boolean properties.
 * Change focus to something else to commit the edit.
 * <p>
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-26
 */
public class TableViewPropertyEditor extends Application
{
    @Override
    public void start(Stage stage)
    {
        final Person aPerson = new Person("Fred", true);
        final Label currentObjectValue = new Label(aPerson.toString());

        TableView<NamedProperty> table = new TableView();
        table.setEditable(true);

        table.setItems(createNamedProperties(aPerson));

        TableColumn<NamedProperty, String> nameCol = new TableColumn("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<NamedProperty, Object> valueCol = new TableColumn("Value");
        valueCol.setCellValueFactory(new PropertyValueFactory<>("value"));
        valueCol.setCellFactory(param -> new EditingCell());

        valueCol.setOnEditCommit(
                t -> {
                    int row = t.getTablePosition().getRow();
                    NamedProperty property = t.getTableView().getItems().get(row);
                    property.setValue(t.getNewValue());

                    currentObjectValue.setText(aPerson.toString());
                }
        );

        table.getColumns().setAll(nameCol, valueCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox layout = new VBox(10);
        layout.setStyle("-fx-background-color: cornsilk; -fx-padding: 10;");
        layout.getChildren().setAll(
                currentObjectValue,
                table
        );
        VBox.setVgrow(table, Priority.ALWAYS);

        stage.setScene(new Scene(layout, 650, 600));
        stage.show();
    }

    private ObservableList<NamedProperty> createNamedProperties(Object object)
    {
        ObservableList<NamedProperty> properties = FXCollections.observableArrayList();

        for (Method method : object.getClass().getMethods()) {
            String name = method.getName();
            Class type = method.getReturnType();
            if (type.getName().endsWith("Property")) {
                try {
                    properties.add(new NamedProperty(name, (Property) method.invoke(object)));
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    Logger.getLogger(TableViewPropertyEditor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return properties;
    }

    public class NamedProperty
    {
        public NamedProperty(String name, Property value)
        {
            nameProperty.set(name);
            valueProperty = value;
        }

        private StringProperty nameProperty = new SimpleStringProperty();

        public StringProperty nameProperty() { return nameProperty; }

        public StringProperty getName() { return nameProperty; }

        public void setName(String name) { nameProperty.set(name); }

        private Property valueProperty;

        public Property valueProperty() { return valueProperty; }

        public Object getValue() { return valueProperty.getValue(); }

        public void setValue(Object value) { valueProperty.setValue(value); }
    }

    class EditingCell extends TableCell<NamedProperty, Object>
    {
        private TextField textField;
        private CheckBox checkBox;

        public EditingCell() {}

        @Override
        public void startEdit()
        {
            if (!isEmpty()) {
                super.startEdit();

                if (getItem() instanceof Boolean) {
                    createCheckBox();
                    setText(null);
                    setGraphic(checkBox);
                } else {
                    createTextField();
                    setText(null);
                    setGraphic(textField);
                    textField.selectAll();
                }
            }
        }

        @Override
        public void cancelEdit()
        {
            super.cancelEdit();

            if (getItem() instanceof Boolean) {
                setText(getItem().toString());
            } else {
                setText((String) getItem());
            }
            setGraphic(null);
        }

        @Override
        public void updateItem(Object item, boolean empty)
        {
            super.updateItem(item, empty);

            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (getItem() instanceof Boolean) {
                        if (checkBox != null) {
                            checkBox.setSelected(getBoolean());
                        }
                        setText(null);
                        setGraphic(checkBox);
                    } else {
                        if (textField != null) {
                            textField.setText(getString());
                        }
                        setText(null);
                        setGraphic(textField);
                    }
                } else {
                    setText(getString());
                    setGraphic(null);
                }
            }
        }

        private void createTextField()
        {
            textField = new TextField(getString());
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue) {
                    commitEdit(textField.getText());
                }
            });
        }

        private void createCheckBox()
        {
            checkBox = new CheckBox();
            checkBox.setSelected(getBoolean());
            checkBox.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            checkBox.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue) {
                    commitEdit(checkBox.isSelected());
                }
            });
        }

        private String getString()
        {
            return getItem() == null ? "" : getItem().toString();
        }

        private Boolean getBoolean()
        {
            return getItem() == null ? false : (Boolean) getItem();
        }
    }

    public static void main(String[] args) { launch(args); }
}
