package com.github.teocci.codesample.javafx.uisamples;

import javafx.application.*;
import javafx.beans.value.*;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Demonstrates placing multiple TitledPanes in a VBox so that each pane
 * can open and close independently of every other pane (unlike an Accordion,
 * which only allows a single pane to be open at a time).
 * <p>
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2017-Nov-23
 */
public class StackedPanes extends Application
{
    // image license: linkware - backlink to http://www.fasticon.com
    private static final Image BLUE_FISH = createImage("http://icons.iconarchive.com/icons/fasticon/fish-toys/128/Blue-Fish-icon.png");
    private static final Image RED_FISH = createImage("http://icons.iconarchive.com/icons/fasticon/fish-toys/128/Red-Fish-icon.png");
    private static final Image YELLOW_FISH = createImage("http://icons.iconarchive.com/icons/fasticon/fish-toys/128/Yellow-Fish-icon.png");
    private static final Image GREEN_FISH = createImage("http://icons.iconarchive.com/icons/fasticon/fish-toys/128/Green-Fish-icon.png");

    @Override
    public void start(Stage stage)
    {
        VBox stackedTitledPanes = createStackedTitledPanes();

        ScrollPane scroll = makeScrollable(stackedTitledPanes);
        scroll.getStyleClass().add("stacked-titled-panes-scroll-pane");
        scroll.setPrefSize(410, 480);

        stage.setTitle("Fishy, fishy");
        Scene scene = new Scene(scroll);
        scene.getStylesheets().add(getClass().getResource("/css/fishy-fishy.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    private VBox createStackedTitledPanes()
    {
        final VBox stackedTitledPanes = new VBox();
        stackedTitledPanes.getChildren().setAll(
                createTitledPane("One Fish", GREEN_FISH),
                createTitledPane("Two Fish", YELLOW_FISH, GREEN_FISH),
                createTitledPane("Red Fish", RED_FISH),
                createTitledPane("Blue Fish", BLUE_FISH)
        );
        ((TitledPane) stackedTitledPanes.getChildren().get(0)).setExpanded(true);
        stackedTitledPanes.getStyleClass().add("stacked-titled-panes");

        return stackedTitledPanes;
    }

    public TitledPane createTitledPane(String title, Image... images)
    {
        FlowPane content = new FlowPane();
        for (Image image : images) {
            ImageView imageView = new ImageView(image);
            content.getChildren().add(imageView);

            FlowPane.setMargin(imageView, new Insets(10));
        }
        content.setAlignment(Pos.TOP_CENTER);

        TitledPane pane = new TitledPane(title, content);
        pane.getStyleClass().add("stacked-titled-pane");
        pane.setExpanded(false);

        return pane;
    }

    private static Image createImage(String url)
    {
        try {
            URLConnection conn = new URL(url).openConnection();
            conn.setRequestProperty("User-Agent", "Wget/1.13.4 (linux-gnu)");
            InputStream stream = conn.getInputStream();

            return new Image(stream);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private ScrollPane makeScrollable(final VBox node)
    {
        final ScrollPane scroll = new ScrollPane();
        scroll.setContent(node);
        scroll.viewportBoundsProperty().addListener(new ChangeListener<Bounds>()
        {
            public void changed(ObservableValue<? extends Bounds> ov, Bounds oldBounds, Bounds bounds)
            {
                node.setPrefWidth(bounds.getWidth());
            }
        });
        return scroll;
    }

    public static void main(String[] args) { Application.launch(args); }
}