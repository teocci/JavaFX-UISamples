package com.github.teocci.codesample.javafx.models.ticktock;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

/**
 * Handles events according to a tempo in beats per minute.
 * <p>
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-27
 */
public class Pulsar
{
    private final DoubleProperty tempo = new SimpleDoubleProperty(100);
    private final Timeline timeline = new Timeline();

    public Pulsar(final double initialTempo, final EventHandler<ActionEvent> pulseHandler)
    {
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.getKeyFrames().setAll(
                new KeyFrame(Duration.ZERO, pulseHandler),
                new KeyFrame(Duration.minutes(1), null)
        );
        timeline.rateProperty().bind(tempo);

        setTempo(initialTempo);
    }

    public DoubleProperty tempoProperty()
    {
        return tempo;
    }

    public double getTempo()
    {
        return tempo.get();
    }

    public void setTempo(double newTempo)
    {
        tempo.set(newTempo);
    }

    public void start()
    {
        timeline.play();
    }

    public void stop()
    {
        timeline.stop();
    }
}
