package com.github.teocci.codesample.javafx.handlers;

import javafx.animation.AnimationTimer;
import javafx.animation.PathTransition;
import javafx.animation.Transition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * Plots text along a path defined by provided bezier control points.
 * <p>
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-26
 */
public class PlotHandler implements EventHandler<ActionEvent>
{
    private final ToggleButton plot;
    private final ObservableList<Text> parts;
    private final ObservableList<PathTransition> transitions;
    private final ObservableList<Node> controls;

    public PlotHandler(ToggleButton plot, ObservableList<Text> parts, ObservableList<PathTransition> transitions, ObservableList<Node> controls)
    {
        this.plot = plot;
        this.parts = parts;
        this.transitions = transitions;
        this.controls = controls;
    }

    @Override
    public void handle(ActionEvent actionEvent)
    {
        if (plot.isSelected()) {
            for (int i = 0; i < parts.size(); i++) {
                parts.get(i).setVisible(true);
                final Transition transition = transitions.get(i);
                transition.stop();
                transition.jumpTo(Duration.seconds(10).multiply((i + 0.5) * 1.0 / parts.size()));
                // just play a single animation frame to display the curved text, then stop
                AnimationTimer timer = new AnimationTimer()
                {
                    int frameCounter = 0;

                    @Override
                    public void handle(long l)
                    {
                        frameCounter++;
                        if (frameCounter == 1) {
                            transition.stop();
                            stop();
                        }
                    }
                };
                timer.start();
                transition.play();
            }
            plot.setText("Show Controls");
        } else {
            plot.setText("Plot Text");
        }

        for (Node control : controls) {
            control.setVisible(!plot.isSelected());
        }

        for (Node part : parts) {
            part.setVisible(plot.isSelected());
        }
    }
}
