package com.github.teocci.codesample.javafx.tasks;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.paint.Color;

import java.awt.image.BufferedImage;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-27
 */
public class ChartsSnapshotTask extends Task<Void>
{
    private final int nCharts;
    private final BlockingQueue<Parent> charts;
    private final BlockingQueue<BufferedImage> images;

    public ChartsSnapshotTask(BlockingQueue<Parent> charts, BlockingQueue<BufferedImage> images, final int nCharts)
    {
        this.charts = charts;
        this.images = images;
        this.nCharts = nCharts;
        updateProgress(0, nCharts);
    }

    @Override
    protected Void call() throws Exception
    {
        int i = nCharts;
        while (i > 0) {
            if (isCancelled()) {
                break;
            }
            images.put(snapshotChart(charts.take()));
            i--;
            updateProgress(nCharts - i, nCharts);
        }

        return null;
    }

    private BufferedImage snapshotChart(final Parent chartContainer) throws InterruptedException
    {
        final CountDownLatch latch = new CountDownLatch(1);
        // render the chart in an offscreen scene (scene is used to allow css processing) and snapshot it to an image.
        // the snapshot is done in runlater as it must occur on the javafx application thread.
        final SimpleObjectProperty<BufferedImage> imageProperty = new SimpleObjectProperty<>();
        Platform.runLater(() -> {
            Scene snapshotScene = new Scene(chartContainer);
            final SnapshotParameters params = new SnapshotParameters();
            params.setFill(Color.ALICEBLUE);
            chartContainer.snapshot(
                    result -> {
                        imageProperty.set(SwingFXUtils.fromFXImage(result.getImage(), null));
                        latch.countDown();
                        return null;
                    },
                    params,
                    null
            );
        });

        latch.await();

        return imageProperty.get();
    }
}
