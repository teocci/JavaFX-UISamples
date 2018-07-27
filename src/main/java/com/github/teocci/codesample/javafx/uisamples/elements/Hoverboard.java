package com.github.teocci.codesample.javafx.uisamples.elements;

import com.github.teocci.codesample.javafx.elements.TextChooser;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-26
 */
public class Hoverboard extends Application
{
    @Override
    public void start(Stage stage) throws Exception
    {
        TextChooser textChooser = new TextChooser("Option 01", "Option 02", "Option 03", "Option 04");

        VBox layout = new VBox(textChooser);
        layout.setPadding(new Insets(10));

        stage.setScene(new Scene(layout));
        stage.show();
    }

    public static void main(String[] args)
    {
        launch(Hoverboard.class);
    }
}
