package com.github.teocci.codesample.javafx.uisamples.chart;

import com.github.teocci.codesample.javafx.tasks.SaveChartsTask;
import com.github.teocci.codesample.javafx.utils.UtilHelper;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberExpression;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import static javafx.concurrent.Worker.State.SUCCEEDED;

/**
 * Render 300 charts off screen and save them to files in JavaFX.
 * <p>
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-27
 */
public class OffScreenOffThreadCharts extends Application
{
    public static final String CHART_FILE_PREFIX = "chart_";
    public static final String WORKING_DIR = System.getProperty("user.dir");

    private final int N_CHARTS = 300;
    private final int PREVIEW_SIZE = 600;

    private final ExecutorService saveChartsExecutor = UtilHelper.createExecutor("SaveCharts");

    @Override
    public void start(Stage stage) throws IOException
    {
        final SaveChartsTask saveChartsTask = new SaveChartsTask(N_CHARTS);

        final VBox layout = new VBox(10);
        layout.getChildren().addAll(
                createProgressPane(saveChartsTask),
                createChartImagePagination(saveChartsTask)
        );
        layout.setStyle("-fx-background-color: cornsilk; -fx-padding: 15;");

        stage.setTitle("Chart Export Sample");
        stage.setOnCloseRequest(event -> saveChartsTask.cancel());

        stage.setScene(new Scene(layout));
        stage.show();

        saveChartsExecutor.execute(saveChartsTask);
    }

    @Override
    public void stop() throws Exception
    {
        saveChartsExecutor.shutdown();
        saveChartsExecutor.awaitTermination(5, TimeUnit.SECONDS);
    }

    private Pagination createChartImagePagination(final SaveChartsTask saveChartsTask)
    {
        final Pagination pagination = new Pagination(N_CHARTS);
        pagination.setMinSize(PREVIEW_SIZE + 100, PREVIEW_SIZE + 100);
        pagination.setPageFactory(new Callback<Integer, Node>()
        {
            @Override
            public Node call(final Integer chartNumber)
            {
                final StackPane page = new StackPane();
                page.setStyle("-fx-background-color: antiquewhite;");

                if (chartNumber < saveChartsTask.getWorkDone()) {
                    page.getChildren().setAll(createImageViewForChartFile(chartNumber));
                } else {
                    ProgressIndicator progressIndicator = new ProgressIndicator();
                    progressIndicator.setMaxSize(PREVIEW_SIZE * 1 / 4, PREVIEW_SIZE * 1 / 4);
                    page.getChildren().setAll(progressIndicator);

                    final ChangeListener<Number> WORK_DONE_LISTENER = new ChangeListener<Number>()
                    {
                        @Override
                        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
                        {
                            if (chartNumber < saveChartsTask.getWorkDone()) {
                                page.getChildren().setAll(createImageViewForChartFile(chartNumber));
                                saveChartsTask.workDoneProperty().removeListener(this);
                            }
                        }
                    };

                    saveChartsTask.workDoneProperty().addListener(WORK_DONE_LISTENER);
                }

                return page;
            }
        });

        return pagination;
    }

    private ImageView createImageViewForChartFile(Integer chartNumber)
    {
        ImageView imageView = new ImageView(new Image("file:///" + UtilHelper.getChartFilePath(chartNumber)));
        imageView.setFitWidth(PREVIEW_SIZE);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    private Pane createProgressPane(SaveChartsTask saveChartsTask)
    {
        GridPane progressPane = new GridPane();

        progressPane.setHgap(5);
        progressPane.setVgap(5);
        progressPane.addRow(0, new Label("Create:"), createBoundProgressBar(saveChartsTask.chartsCreationProgressProperty()));
        progressPane.addRow(1, new Label("Snapshot:"), createBoundProgressBar(saveChartsTask.chartsSnapshotProgressProperty()));
        progressPane.addRow(2, new Label("Save:"), createBoundProgressBar(saveChartsTask.imagesExportProgressProperty()));
        progressPane.addRow(3, new Label("Processing:"),
                createBoundProgressBar(Bindings
                        .when(saveChartsTask.stateProperty().isEqualTo(SUCCEEDED))
                        .then(new SimpleDoubleProperty(1))
                        .otherwise(new SimpleDoubleProperty(ProgressBar.INDETERMINATE_PROGRESS))
                )
        );

        return progressPane;
    }

    private ProgressBar createBoundProgressBar(NumberExpression progressProperty)
    {
        ProgressBar progressBar = new ProgressBar();
        progressBar.setMaxWidth(Double.MAX_VALUE);
        progressBar.progressProperty().bind(progressProperty);
        GridPane.setHgrow(progressBar, Priority.ALWAYS);
        return progressBar;
    }

    public static void main(String[] args) { launch(args); }
}
