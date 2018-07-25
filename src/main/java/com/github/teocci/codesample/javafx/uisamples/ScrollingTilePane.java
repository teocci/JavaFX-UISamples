package com.github.teocci.codesample.javafx.uisamples;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2017-Nov-22
 */
public class ScrollingTilePane extends Application {

    @Override
    public void start(Stage stage) {

        TilePane tilePane = new TilePane();
        tilePane.setPadding(new Insets(5));
        tilePane.setVgap(4);
        tilePane.setHgap(4);
        tilePane.setPrefColumns(4);
        tilePane.setStyle("-fx-background-color: lightblue;");

        // dont grow more than the preferred number of columns:
        tilePane.setMaxWidth(Region.USE_PREF_SIZE);

        HBox tiles[] = new HBox[9];
        for (int i = 0; i < 9; i++) {
            tiles[i] = new HBox(new Label("This is node #" + i));
            tiles[i].setStyle("-fx-border-color: black;");
            tiles[i].setPadding(new Insets(50));
            tilePane.getChildren().add(tiles[i]);
        }

        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.setStyle("-fx-background-color: blue;");
        hbox.getChildren().add(tilePane);

//        StackPane stack = new StackPane();
//        stack.getChildren().add(tilePane);
//        stack.setStyle("-fx-background-color: blue;");

        ScrollPane sp = new ScrollPane();
//        sp.setFitToHeight(true);
        sp.setFitToWidth(true);
        sp.setContent(hbox);

        Scene mainScene = new Scene(sp, 800, 600);
        mainScene.getStylesheets().add("css/style.css");
        stage.setScene(mainScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
