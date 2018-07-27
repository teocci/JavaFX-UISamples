package com.github.teocci.codesample.javafx.models.ticktock;

import javafx.scene.media.AudioClip;

/**
 * Ticks according to a tempo in beats per minute controlled by the associated pulsar.
 * <p>
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-27
 */
public class Metronome
{
    private final AudioClip tick = new AudioClip("http://www.denhaku.com/r_box/sr16/sr16perc/losticks.wav");
    private final Pulsar pulsar;

    public Metronome(final double initialTempo)
    {
        // the first time the audioclip is played, there is a delay before you hear it,
        // so play with zero volume now as to make sure it is ready to play when straight away when needed.
        tick.play(0);

        pulsar = new Pulsar(initialTempo, actionEvent -> tick.play());
    }

    public Pulsar getPulsar()
    {
        return pulsar;
    }
}