package com.github.teocci.codesample.javafx.tasks;

import com.github.teocci.codesample.javafx.callbacks.MissingTextPrompt;
import com.github.teocci.codesample.javafx.threads.AddNodeLater;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.util.concurrent.FutureTask;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-27
 */
public class LoadTextTask extends Task<Void>
{
    private final String[] lines;
    private final Pane container;
    private final IntegerProperty idx = new SimpleIntegerProperty(0);

    public LoadTextTask(final String[] lines, final Pane container)
    {
        this.lines = lines;
        this.container = container;
    }

    @Override
    protected Void call() throws Exception
    {
        try {
            updateProgress(0, lines.length);

            while (idx.get() < lines.length) {
                final Label nextLabel = new Label();
                final int curIdx = idx.get();
                updateMessage("Reading Line: " + curIdx);
                String nextText = lines[curIdx];

                if ("MISSING".equals(nextText)) {
                    updateMessage("Prompting for missing text for line: " + curIdx);
                    FutureTask<String> futureTask = new FutureTask<>(
                            new MissingTextPrompt(
                                    container.getScene().getWindow()
                            )
                    );
                    Platform.runLater(futureTask);
                    nextText = futureTask.get();

                    nextLabel.setStyle("-fx-background-color: palegreen;");
                }
                nextLabel.setText(nextText);

                Platform.runLater(
                        new AddNodeLater(
                                container,
                                curIdx,
                                nextLabel
                        )
                );
                idx.set(curIdx + 1);

                updateProgress(curIdx + 1, lines.length);

                Thread.sleep(200);
            }

            updateMessage("Loading Text Completed: " + idx.get() + " lines loaded.");
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }
}