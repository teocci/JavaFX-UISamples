package com.github.teocci.codesample.javafx.utils;

import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.File;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import static com.github.teocci.codesample.javafx.uisamples.chart.OffScreenOffThreadCharts.CHART_FILE_PREFIX;
import static com.github.teocci.codesample.javafx.uisamples.chart.OffScreenOffThreadCharts.WORKING_DIR;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-27
 */
public class UtilHelper
{
    /**
     * Retrieves an absolute resource path from a path relative to the location of the specified class.
     * The requested resource must exist otherwise this method will throw an IllegalArgumentException.
     *
     * @param clazz a path relative to the location of this class.
     * @param path  a path relative to the location of this class.
     * @return the absolute resource path.
     * @throws IllegalArgumentException if a resource at the specified path does not exist.
     */
    public static String getResourceFor(Class clazz, String path)
    {
        URL resourceURL = clazz.getResource(path);
        if (resourceURL == null) {
            throw new IllegalArgumentException("No resource exists at: " + path + " relative to " + clazz.getName());
        }

        return resourceURL.toExternalForm();
    }

    public static ScrollPane makeScrollable(final ImageView imageView)
    {
        final ScrollPane scroll = new ScrollPane();
        final StackPane centeredImageView = new StackPane();

        centeredImageView.getChildren().add(imageView);
        scroll.viewportBoundsProperty().addListener((ov, oldBounds, bounds) -> centeredImageView.setPrefSize(
                Math.max(imageView.prefWidth(bounds.getHeight()), bounds.getWidth()),
                Math.max(imageView.prefHeight(bounds.getWidth()), bounds.getHeight())
        ));
        scroll.setContent(centeredImageView);

        return scroll;
    }

    public static ExecutorService createExecutor(final String name)
    {
        ThreadFactory factory = r -> {
            Thread t = new Thread(r);
            t.setName(name);
            t.setDaemon(true);
            return t;
        };

        return Executors.newSingleThreadExecutor(factory);
    }

    public static String getChartFilePath(int chartNumber)
    {
        return new File(WORKING_DIR, CHART_FILE_PREFIX + chartNumber + ".png").getPath();
    }
}
