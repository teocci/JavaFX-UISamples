package com.github.teocci.codesample.javafx.tasks;

import com.github.teocci.codesample.javafx.uisamples.chart.OffScreenOffThreadCharts;
import com.github.teocci.codesample.javafx.utils.UtilHelper;
import javafx.concurrent.Task;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-27
 */
public class PngsExportTask extends Task<Void>
{
    private final int nImages;
    private final BlockingQueue<BufferedImage> images;

    public PngsExportTask(BlockingQueue<BufferedImage> images, final int nImages)
    {
        this.images = images;
        this.nImages = nImages;
        updateProgress(0, nImages);
    }

    @Override
    protected Void call() throws Exception
    {
        int i = nImages;
        while (i > 0) {
            if (isCancelled()) {
                break;
            }
            exportPng(images.take(), UtilHelper.getChartFilePath(nImages - i));
            i--;
            updateProgress(nImages - i, nImages);
        }

        return null;
    }

    private void exportPng(BufferedImage image, String filename)
    {
        try {
            ImageIO.write(image, "png", new File(filename));
        } catch (IOException ex) {
            Logger.getLogger(OffScreenOffThreadCharts.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}