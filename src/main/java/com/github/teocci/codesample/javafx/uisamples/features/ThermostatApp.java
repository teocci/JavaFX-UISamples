package com.github.teocci.codesample.javafx.uisamples.features;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;

/**
 * Simulates reading a thermostat every couple of seconds and updating a label with the current temperature value.
 * <p>
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-26
 */

public class ThermostatApp extends Application
{
    @Override
    public void start(final Stage stage) throws Exception
    {
        final Thermostat thermostat = new Thermostat();
        final TemperatureLabel temperatureLabel = new TemperatureLabel(thermostat);

        VBox layout = new VBox(10);
        layout.getChildren().addAll(temperatureLabel);
        layout.setStyle("-fx-background-color: cornsilk; -fx-padding: 20; -fx-font-size: 20;");

        stage.setScene(new Scene(layout));
        stage.show();
    }

    public static void main(String[] args) throws Exception
    {
        launch(args);
    }
}

class TemperatureLabel extends Label
{
    public TemperatureLabel(final Thermostat thermostat)
    {
        textProperty().bind(
                Bindings.format(
                        "%3d \u00B0F",
                        thermostat.temperatureProperty()
                )
        );
    }
}

class Thermostat
{
    private static final Duration PROBE_FREQUENCY = Duration.seconds(2);

    private final ReadOnlyIntegerWrapper temperature;
    private final TemperatureProbe probe;
    private final Timeline timeline;

    public ReadOnlyIntegerProperty temperatureProperty()
    {
        return temperature.getReadOnlyProperty();
    }

    public Thermostat()
    {
        probe = new TemperatureProbe();
        temperature = new ReadOnlyIntegerWrapper(probe.readTemperature());

        timeline = new Timeline(
                new KeyFrame(
                        Duration.ZERO,
                        actionEvent -> temperature.set(probe.readTemperature())
                ),
                new KeyFrame(
                        PROBE_FREQUENCY
                )
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}

class TemperatureProbe
{
    private static final Random random = new Random();

    public int readTemperature()
    {
        return 72 + random.nextInt(6);
    }
}