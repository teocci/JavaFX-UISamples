package com.github.teocci.codesample.javafx.uisamples.elements;

import com.github.teocci.codesample.javafx.elements.AddPersonCell;
import com.github.teocci.codesample.javafx.models.Person;
import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Disclaimer: Icon license: Linkware (Backlink to http://www.icons-land.com required)
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-27
 */
public class TableViewWithAddButtonExample extends Application
{
    @Override
    public void start(final Stage stage)
    {
        stage.setTitle("People");
        stage.getIcons().add(new Image("http://icons.iconarchive.com/icons/icons-land/vista-people/72/Historical-Viking-Female-icon.png"));

        // create a table.
        final TableView<Person> table = new TableView<>(
                FXCollections.observableArrayList(
                        new Person("Jacob", "Smith"),
                        new Person("Isabella", "Johnson"),
                        new Person("Ethan", "Williams"),
                        new Person("Emma", "Jones"),
                        new Person("Michael", "Brown")
                )
        );

        // define the table columns.
        TableColumn<Person, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        TableColumn<Person, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        TableColumn<Person, Boolean> actionCol = new TableColumn<>("Action");
        actionCol.setSortable(false);

        // Define a simple boolean cell value for the action column so that the column will only be shown for non-empty rows.
        actionCol.setCellValueFactory(features -> new SimpleBooleanProperty(features.getValue() != null));

        // Create a cell value factory with an add button for each row in the table.
        actionCol.setCellFactory(personBooleanTableColumn -> new AddPersonCell(stage, table));

        table.getColumns().setAll(firstNameCol, lastNameCol, actionCol);

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        stage.setScene(new Scene(table));
        stage.show();
    }

    public static void main(String[] args) { launch(args); }
}