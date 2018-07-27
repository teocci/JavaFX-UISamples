package com.github.teocci.codesample.javafx.utils;

import com.github.teocci.codesample.javafx.uisamples.features.H2app;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-27
 */
public class DBHelper
{
    private static final Logger logger = Logger.getLogger(DBHelper.class.getName());

    public static void createSchema(Connection con) throws SQLException
    {
        logger.info("Creating schema");
        Statement st = con.createStatement();
        String table = "create table employee(id integer, name varchar(64))";
        st.executeUpdate(table);
        logger.info("Created schema");
    }

    public static ObservableList<String> fetchNames(Connection con) throws SQLException
    {
        logger.info("Fetching names from database");
        ObservableList<String> names = FXCollections.observableArrayList();

        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("select name from employee");
        while (rs.next()) {
            names.add(rs.getString("name"));
        }

        logger.info("Found " + names.size() + " names");

        return names;
    }

    public static boolean schemaExists(Connection con)
    {
        logger.info("Checking for Schema existence");
        try {
            Statement st = con.createStatement();
            st.executeQuery("select count(*) from employee");
            logger.info("Schema exists");
        } catch (SQLException ex) {
            logger.info("Existing DB not found will create a new one");
            return false;
        }

        return true;
    }
}
