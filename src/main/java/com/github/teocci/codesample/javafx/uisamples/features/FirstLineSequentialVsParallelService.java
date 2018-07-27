package com.github.teocci.codesample.javafx.uisamples.features;

import java.util.concurrent.*;

import com.github.teocci.codesample.javafx.threads.FirstLineService;
import com.github.teocci.codesample.javafx.threads.FirstLineThreadFactory;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * JavaFX service examples for executing service tasks in parallel or sequentially.
 * <p>
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-27
 */
public class FirstLineSequentialVsParallelService extends Application
{
    private static final String[] URLs = {
            "http://www.google.com",
            "http://www.yahoo.com",
            "http://www.microsoft.com",
            "http://www.oracle.com"
    };

    private ExecutorService sequentialExecutor;
    private ExecutorService parallelExecutor;

    @Override
    public void init() throws Exception
    {
        sequentialExecutor = Executors.newFixedThreadPool(1, new FirstLineThreadFactory("sequential"));
        parallelExecutor = Executors.newFixedThreadPool(URLs.length, new FirstLineThreadFactory("parallel"));
    }

    @Override
    public void stop() throws Exception
    {
        parallelExecutor.shutdown();
        parallelExecutor.awaitTermination(3, TimeUnit.SECONDS);

        sequentialExecutor.shutdown();
        sequentialExecutor.awaitTermination(3, TimeUnit.SECONDS);
    }

    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage stage) throws Exception
    {
        final VBox messages = new VBox();
        messages.setStyle("-fx-background-color: cornsilk; -fx-padding: 10;");

        messages.getChildren().addAll(
                new Label("Parallel Execution"),
                new Label("------------------"),
                new Label()
        );
        fetchFirstLines(messages, parallelExecutor);

        messages.getChildren().addAll(
                new Label("Sequential Execution"),
                new Label("--------------------"),
                new Label()
        );
        fetchFirstLines(messages, sequentialExecutor);

        messages.setStyle("-fx-font-family: monospace");

        stage.setScene(new Scene(messages, 600, 800));
        stage.show();
    }

    private void fetchFirstLines(final VBox monitoredLabels, ExecutorService executorService)
    {
        for (final String url : URLs) {
            final FirstLineService service = new FirstLineService();
            service.setExecutor(executorService);
            service.setUrl(url);

            final ProgressMonitoredLabel monitoredLabel = new ProgressMonitoredLabel(url);
            monitoredLabels.getChildren().add(monitoredLabel);
            monitoredLabel.progress.progressProperty().bind(service.progressProperty());

            service.setOnSucceeded(t -> monitoredLabel.addStrings(
                    service.getMessage(),
                    service.getValue()
            ));
            service.start();
        }
    }

    public class ProgressMonitoredLabel extends HBox
    {
        final ProgressBar progress;
        final VBox labels;

        public ProgressMonitoredLabel(String initialString)
        {
            super(20);

            progress = new ProgressBar();
            labels = new VBox();
            labels.getChildren().addAll(new Label(initialString), new Label());

            progress.setPrefWidth(100);
            progress.setMinWidth(ProgressBar.USE_PREF_SIZE);
            HBox.setHgrow(labels, Priority.ALWAYS);
            setMinHeight(80);

            getChildren().addAll(progress, labels);
        }

        public void addStrings(String... strings)
        {
            for (String string : strings) {
                labels.getChildren().add(
                        labels.getChildren().size() - 1,
                        new Label(string)
                );
            }
        }
    }
}
