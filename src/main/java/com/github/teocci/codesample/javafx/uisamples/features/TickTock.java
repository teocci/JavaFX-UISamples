package com.github.teocci.codesample.javafx.uisamples.features;

import com.github.teocci.codesample.javafx.elements.ticktock.MetronomeView;
import com.github.teocci.codesample.javafx.models.ticktock.Metronome;
import com.github.teocci.codesample.javafx.models.ticktock.Pulsar;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-27
 */

/** Creates a metronome with start/stop and tempo controls */
public class TickTock extends Application {
    private static final double INITIAL_TEMPO = 100;

    @Override public void start(final Stage stage) {
        stage.setTitle("Metronome");
        stage.setScene(
                new Scene(new MetronomeView(new Metronome(INITIAL_TEMPO)))
        );
        stage.show();
    }

    public static void main(String[] args) { launch(args); }
}







