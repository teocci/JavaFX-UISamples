package com.github.teocci.codesample.javafx.uisamples.features;

import com.github.teocci.codesample.javafx.controllers.RolePlayController;
import com.github.teocci.codesample.javafx.enums.Role;
import com.github.teocci.codesample.javafx.managers.RoleManager;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Sample for creating a role based UI using JavaFX FXML
 * <p>
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-26
 */

public class RoleVisibility extends Application
{
    private RoleManager roleManager = new RoleManager();

    @Override
    public void start(Stage stage) throws IOException
    {
        VBox layout = new VBox(10);
        layout.getChildren().setAll(
                getRoleChooser(),
                createContent()
        );
        layout.setStyle("-fx-padding: 10px; -fx-background-color: cornsilk;");

        roleManager.showActiveNodes();

        stage.setTitle("Role Selector");
        stage.setScene(new Scene(layout));
        stage.show();
    }

    private Node getRoleChooser()
    {
        ObservableList<Role> activeRoles = FXCollections.observableArrayList();

        VBox roleChooser = new VBox(10);
        for (final Role role : Role.values()) {
            CheckBox checkBox = new CheckBox(role.toString().toLowerCase());
            checkBox.selectedProperty().addListener((observable, wasSelected, isSelected) -> {
                if (isSelected) {
                    activeRoles.add(role);
                } else {
                    activeRoles.remove(role);
                }
            });

            roleChooser.getChildren().add(checkBox);
        }

        roleManager.setActiveRoles(
                activeRoles
        );

        return roleChooser;
    }

    private Pane createContent() throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setController(new RolePlayController(roleManager));

        return loader.load(
                getClass().getResourceAsStream("/fxml/roleplay.fxml")
        );
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
