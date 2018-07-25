package com.github.teocci.codesample.javafx.uisamples;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-25
 */
public class CustomScrollBar extends Application
{
    private final int MAX_VIDEO_ITEMS = 20;

    private ListView<String> list = new ListView<>();

    private StackPane root = new StackPane();

    private Scene scene;

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        loadList();

        scene = new Scene(root, 640, 480);
        scene.getStylesheets().add("css/scrollbar-1.css");
//        scene.getStylesheets().add("css/scrollbar-2.css");
//        scene.getStylesheets().add("css/scrollbar-3.css");
//        scene.getStylesheets().add("css/scrollbar-4.css");

        primaryStage.setTitle("Custom Scrollbar");
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
    }

    private void loadList()
    {
        for (int i = 0; i < MAX_VIDEO_ITEMS; i++) {
            String letter = getCharForNumber(i + 1);

//            String id = "video_" + letter.toLowerCase();
            String item = "Item " +  letter.toUpperCase();
            list.getItems().add(item);
        }

        list.setId("custom-list");

        root.getChildren().add(list);
    }


    public String getCharForNumber(int i)
    {
        return i > 0 && i < 27 ? String.valueOf((char) (i + 'A' - 1)) : "";
    }
}
