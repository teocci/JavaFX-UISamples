package com.github.teocci.codesample.javafx.uisamples.elements;

import javafx.application.Application;
import javafx.beans.value.*;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * A basic JavaFX Slider value change logger
 * <p>
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-26
 */

public class SliderChangeLog extends Application
{
    private final ListView<String> startLog = new ListView<>();
    private final ListView<String> endLog = new ListView<>();

    @Override
    public void start(Stage stage) throws Exception
    {
        Pane logsPane = createLogsPane();
        Slider slider = createMonitoredSlider();

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(10));
        layout.getChildren().setAll(
                slider,
                logsPane
        );
        VBox.setVgrow(logsPane, Priority.ALWAYS);

        stage.setTitle("Slider Value Change Logger");
        stage.setScene(new Scene(layout));
        stage.show();
    }

    private Slider createMonitoredSlider()
    {
        final Slider slider = new Slider(0, 1, 0.25);
        slider.setMajorTickUnit(0.5);
        slider.setMinorTickCount(0);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.setMinHeight(Slider.USE_PREF_SIZE);

        slider.valueChangingProperty().addListener((observableValue, wasChanging, changing) -> {
            String valueString = String.format("%1$.3f", slider.getValue());

            if (changing) {
                startLog.getItems().add(
                        valueString
                );
            } else {
                endLog.getItems().add(
                        valueString
                );
            }
        });

        return slider;
    }

    private HBox createLogsPane()
    {
        HBox logs = new HBox(10);
        logs.getChildren().addAll(
                createLabeledLog("Start", startLog),
                createLabeledLog("End", endLog)
        );

        return logs;
    }

    private Pane createLabeledLog(String logName, ListView<String> log)
    {
        Label label = new Label(logName);
        label.setLabelFor(log);

        VBox logPane = new VBox(5);
        logPane.getChildren().setAll(
                label,
                log
        );

        logPane.setAlignment(Pos.TOP_LEFT);
        VBox.setVgrow(log, Priority.ALWAYS);

        return logPane;
    }

    public static void main(String[] args) { launch(args); }
}
