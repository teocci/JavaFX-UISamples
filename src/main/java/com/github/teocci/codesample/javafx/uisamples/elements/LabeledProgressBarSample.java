package com.github.teocci.codesample.javafx.uisamples.elements;

import com.github.teocci.codesample.javafx.elements.ProgressIndicatorBar;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Sample of overlaying a text label indicating progress on top of a JavaFX ProgressBar.
 * <p>
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-27
 */


public class LabeledProgressBarSample extends Application
{
    @Override
    public void start(final Stage stage)
    {
        stage.setScene(new Scene(createResettableProgressIndicatorBar()));
        stage.show();
    }

    private VBox createResettableProgressIndicatorBar()
    {
        final int TOTAL_WORK = 18;
        final String WORK_DONE_LABEL_FORMAT = "%.0f";

        final ReadOnlyDoubleWrapper workDone = new ReadOnlyDoubleWrapper();

        final ProgressIndicatorBar bar = new ProgressIndicatorBar(
                workDone.getReadOnlyProperty(),
                TOTAL_WORK,
                WORK_DONE_LABEL_FORMAT
        );

        final Timeline countDown = new Timeline(
                new KeyFrame(Duration.seconds(0), new KeyValue(workDone, TOTAL_WORK)),
                new KeyFrame(Duration.seconds(10), new KeyValue(workDone, 0))
        );
        countDown.play();

        final Button resetButton = new Button("Reset");
        resetButton.setOnAction(actionEvent -> countDown.playFromStart());

        final VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: cornsilk; -fx-padding: 20px;");
        layout.getChildren().addAll(bar, resetButton);

        return layout;
    }

    public static void main(String[] args) { launch(args); }
}
