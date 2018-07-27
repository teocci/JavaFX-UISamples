package com.github.teocci.codesample.javafx.tasks;

import com.github.teocci.codesample.javafx.uisamples.chart.OffScreenOffThreadCharts;
import com.github.teocci.codesample.javafx.utils.UtilHelper;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.concurrent.Task;
import javafx.scene.Parent;

import java.awt.image.BufferedImage;
import java.util.concurrent.*;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-27
 */
public class SaveChartsTask<Void> extends Task
{
    private final BlockingQueue<Parent> charts = new ArrayBlockingQueue(10);
    private final BlockingQueue<BufferedImage> bufferedImages = new ArrayBlockingQueue(10);
    private final ExecutorService chartsCreationExecutor = UtilHelper.createExecutor("CreateCharts");
    private final ExecutorService chartsSnapshotExecutor = UtilHelper.createExecutor("TakeSnapshots");
    private final ExecutorService imagesExportExecutor = UtilHelper.createExecutor("ExportImages");
    private final ChartsCreationTask chartsCreationTask;
    private final ChartsSnapshotTask chartsSnapshotTask;
    private final PngsExportTask imagesExportTask;

    public SaveChartsTask(final int nCharts)
    {
        chartsCreationTask = new ChartsCreationTask(charts, nCharts);
        chartsSnapshotTask = new ChartsSnapshotTask(charts, bufferedImages, nCharts);
        imagesExportTask = new PngsExportTask(bufferedImages, nCharts);

        setOnCancelled(event -> {
            chartsCreationTask.cancel();
            chartsSnapshotTask.cancel();
            imagesExportTask.cancel();
        });

        imagesExportTask.workDoneProperty().addListener((observable, oldValue, workDone) -> updateProgress(workDone.intValue(), nCharts));
    }

    public ReadOnlyDoubleProperty chartsCreationProgressProperty()
    {
        return chartsCreationTask.progressProperty();
    }

    public ReadOnlyDoubleProperty chartsSnapshotProgressProperty()
    {
        return chartsSnapshotTask.progressProperty();
    }

    public ReadOnlyDoubleProperty imagesExportProgressProperty()
    {
        return imagesExportTask.progressProperty();
    }

    @Override
    protected Void call() throws Exception
    {
        chartsCreationExecutor.execute(chartsCreationTask);
        chartsSnapshotExecutor.execute(chartsSnapshotTask);
        imagesExportExecutor.execute(imagesExportTask);

        chartsCreationExecutor.shutdown();
        chartsSnapshotExecutor.shutdown();
        imagesExportExecutor.shutdown();

        try {
            imagesExportExecutor.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            /** no action required */
        }

        return null;
    }
}
