package com.github.teocci.codesample.javafx.handlers;

import com.github.teocci.codesample.javafx.elements.VisualizedImage;
import com.github.teocci.codesample.javafx.threads.ParallelDimension;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Service;
import javafx.util.Duration;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-26
 */
public class ContinualShapeGenerator
{
    private Timeline updater = new Timeline();

    public ContinualShapeGenerator(final ParallelDimension parallel, final VisualizedImage visualizer, final double pauseSecs)
    {
        updater.getKeyFrames().addAll(
                new KeyFrame(
                        Duration.seconds(pauseSecs),
                        actionEvent -> {
                            if (Service.State.READY == parallel.getState()) {
                                parallel.start();
                            } else {
                                System.out.println("Frame skipped");
                            }
                        }
                )
        );

        parallel.setOnSucceeded(workerStateEvent -> {
            visualizer.replaceOverlay(parallel.getValue());
            parallel.reset();
            updater.play();
        });

        parallel.setOnFailed(workerStateEvent -> {
            System.out.println("Parallel task failed");
            parallel.getException().printStackTrace();
            updater.play();
        });
    }

    public void generate()
    {
        updater.play();
    }
}

