package com.github.teocci.codesample.javafx.callbacks;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.util.concurrent.Callable;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-27
 */
public class MissingTextPrompt implements Callable<String>
{
    final Window owner;

    public MissingTextPrompt(Window owner)
    {
        this.owner = owner;
    }

    @Override
    public String call() throws Exception
    {
        final Stage dialog = new Stage();
        dialog.setTitle("Enter Missing Text");
        dialog.initOwner(owner);
        dialog.initStyle(StageStyle.UTILITY);
        dialog.initModality(Modality.WINDOW_MODAL);

        final TextField textField = new TextField();
        final Button submitButton = new Button("Submit");
        submitButton.setDefaultButton(true);
        submitButton.setOnAction(t -> dialog.close());

        final VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER_RIGHT);
        layout.setStyle("-fx-background-color: azure; -fx-padding: 10;");
        layout.getChildren().setAll(textField, submitButton);

        dialog.setScene(new Scene(layout));
        dialog.showAndWait();

        return textField.getText();
    }
}
