package com.github.teocci.codesample.javafx.uisamples.elements;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Aug-01
 */
public class CustomWidthTilePane extends Application
{
    private final int NO_OF_COLUMNS = 13;

    private IntegerProperty prefColumns;
    public final void setPrefColumns(int value) { prefColumnsProperty().set(value); }
    public final int getPrefColumns() { return prefColumns == null ? 5 : prefColumns.get(); }

    public final IntegerProperty prefColumnsProperty() {
        if (prefColumns == null) {
            prefColumns = new SimpleIntegerProperty(5) {
                @Override
                public String getName() {
                    return "prefColumns";
                }
            };
        }
        return prefColumns;
    }

    /**
     * The width of this {@code Scene}
     */
    private DoubleProperty width;
    private final void setWidth(double value) {
        widthProperty().set(value);
    }
    public final double getWidth() {
        return width == null ? 0.0 : width.get();
    }

    public final DoubleProperty widthProperty() {
        if (width == null) {
            width = new SimpleDoubleProperty(30) {
                @Override
                public String getName() {
                    return "width";
                }
            };
        }
        return width;
    }

    @Override
    public void start(Stage primaryStage)
    {
        try {
            TilePane pane = new TilePane();
            Scene scene = new Scene(pane, 400, 400);

            pane.setPadding(new Insets(10, 10, 10, 10));
            pane.setVgap(5);
            pane.setHgap(5);
            pane.setPrefColumns(13);
//            pane.setMaxWidth(Region.USE_PREF_SIZE);
            ObservableList<Node> list = pane.getChildren();

            scene.widthProperty().addListener(e -> {
                int newWidth = (int) ((scene.widthProperty().doubleValue() / NO_OF_COLUMNS) - 30);
                if (newWidth > 30) {
                    setWidth(newWidth);
                }
                System.err.println("Scene width: " + scene.widthProperty().doubleValue() +
                        " View width: " + scene.widthProperty().doubleValue() / NO_OF_COLUMNS);
            });

            for (int i = 0; i < 200; i++) {
                Label view = new Label();

                view.minWidthProperty().bind(widthProperty());
                view.maxWidthProperty().bind(widthProperty());

                view.setText("" + (i + 1));
                list.add(view);
            }

            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}