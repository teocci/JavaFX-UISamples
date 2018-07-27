package com.github.teocci.codesample.javafx.uisamples.features;

import java.sql.*;
import java.util.logging.*;

import com.github.teocci.codesample.javafx.utils.DBHelper;
import javafx.application.Application;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-27
 */

public class H2app extends Application
{
    private static final Logger logger = Logger.getLogger(H2app.class.getName());
    private static final String[] SAMPLE_NAME_DATA = {"John", "Jill", "Jack", "Jerry"};

    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage stage)
    {
        final ListView<String> nameView = new ListView();

        final Button fetchNames = new Button("Fetch names from the database");
        fetchNames.setOnAction(event -> fetchNamesFromDatabaseToListView(nameView));

        final Button clearNameList = new Button("Clear the name list");
        clearNameList.setOnAction(event -> nameView.getItems().clear());

        VBox layout = new VBox(10);
        layout.setStyle("-fx-background-color: cornsilk; -fx-padding: 15;");
        layout.getChildren().setAll(
                HBoxBuilder.create().spacing(10).children(
                        fetchNames,
                        clearNameList
                ).build(),
                nameView
        );
        layout.setPrefHeight(200);

        stage.setScene(new Scene(layout));
        stage.show();
    }

    private void fetchNamesFromDatabaseToListView(ListView listView)
    {
        try (Connection con = getConnection()) {
            if (!DBHelper.schemaExists(con)) {
                DBHelper.createSchema(con);
                populateDatabase(con);
            }
            listView.setItems(DBHelper.fetchNames(con));
        } catch (SQLException | ClassNotFoundException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    private Connection getConnection() throws ClassNotFoundException, SQLException
    {
        logger.info("Getting a database connection");
        Class.forName("org.h2.Driver");
        return DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
    }

    private void populateDatabase(Connection con) throws SQLException
    {
        logger.info("Populating database");
        Statement st = con.createStatement();
        int i = 1;
        for (String name : SAMPLE_NAME_DATA) {
            st.executeUpdate("insert into employee values(i,'" + name + "')");
            i++;
        }
        logger.info("Populated database");
    }
}
