package com.github.teocci.codesample.javafx.uisamples;

import com.github.teocci.codesample.javafx.cells.CenteredOverrunTableCell;
import com.github.teocci.codesample.javafx.models.Person;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-26
 */

public class ElidedTableViewSample extends Application
{
    private TableView<Person> table = new TableView<>();
    private final ObservableList<Person> data = FXCollections.observableArrayList(
            new Person("Jacob", "Smith", "jacob.smith@example.com"),
            new Person("Isabella", "Johnson", "isabella.johnson@example.com"),
            new Person("Ethangorovichswavlowskikaliantayaprodoralisk",
                    "Llanfairpwllgwyngyllgogerychwyrndrobwyll-llantysiliogogogoch",
                    "ethan@llanfairpwllgwyngyllgogerychwyrndrobwyll-llantysiliogogogoch.com"
            ),
            new Person("Emma", "Jones", "emma.jones@example.com"),
            new Person("Michael", "Brown", "michael.brown@example.com")
    );

    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage stage)
    {
        stage.setTitle("Table View Sample");
        stage.setWidth(470);
        stage.setHeight(500);

        final Label label = new Label("Address Book");
        label.setFont(new Font("Arial", 20));

        TableColumn firstNameCol = new TableColumn("First Name");
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<Person, String>("firstName"));
        firstNameCol.setCellFactory(new Callback<TableColumn<Person, String>, TableCell<Person, String>>()
        {
            @Override
            public TableCell<Person, String> call(TableColumn<Person, String> p)
            {
                return new CenteredOverrunTableCell("<--->");
            }
        });

        TableColumn lastNameCol = new TableColumn("Last Name");
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<Person, String>("lastName"));
        lastNameCol.setCellFactory(new Callback<TableColumn<Person, String>, TableCell<Person, String>>()
        {
            @Override
            public TableCell<Person, String> call(TableColumn<Person, String> p)
            {
                return new CenteredOverrunTableCell();
            }
        });

        TableColumn emailCol = new TableColumn("Email");
        emailCol.setCellValueFactory(
                new PropertyValueFactory<Person, String>("email"));

        table.setItems(data);
        table.getColumns().addAll(
                firstNameCol,
                lastNameCol,
                emailCol
        );

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(label, table);

        table.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY
        );

        Scene scene = new Scene(vbox);
        stage.setScene(scene);
        stage.show();
    }
}
