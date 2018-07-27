package com.github.teocci.codesample.javafx.uisamples.image;

import com.github.teocci.codesample.javafx.controllers.WingClipper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-26
 */

public class ShadowedRoundedImage extends Application
{
    @Override
    public void start(Stage stage) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/batman-lost-in-the-mix.fxml"));
        loader.setController(new WingClipper());

        Pane batman = loader.load();

        stage.setTitle("Where's Batman?");
        stage.setScene(new Scene(batman));
        stage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
