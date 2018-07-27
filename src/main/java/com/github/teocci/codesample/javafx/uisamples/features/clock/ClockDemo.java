package com.github.teocci.codesample.javafx.uisamples.features.clock;

import com.github.teocci.codesample.javafx.elements.clock.AnalogueClock;
import com.github.teocci.codesample.javafx.elements.clock.DigitalClock;
import com.github.teocci.codesample.javafx.utils.EffectHelper;
import com.github.teocci.codesample.javafx.utils.FXUtilHelper;
import com.github.teocci.codesample.javafx.utils.UtilHelper;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Demonstrates drawing a clock in JavaFX
 * <p>
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-27
 */
public class ClockDemo extends Application
{
    private final String BRAND_NAME = "Teocci";
    private final double CLOCK_RADIUS = 100;

    public void start(final Stage stage) throws Exception
    {
        // create the scene elements.
        final Pane backdrop = makeBackdrop("backdrop", stage);

        // layout the scene.
        final Scene scene = createClockScene(FXUtilHelper.build(new StackPane(), stackPane -> {
                    stackPane.setId("layout");
                    stackPane.getChildren().setAll(
                            backdrop,
                            FXUtilHelper.build(new VBox(), vbox -> {
                                vbox.setId("clocks");
                                vbox.setPickOnBounds(false);
                                vbox.setAlignment(Pos.CENTER);
                                vbox.getChildren().setAll(makeAnalogueClock(stage), new DigitalClock());
                            })
                    );
                })
        );

        // size the backdrop to the scene.
        sizeToScene(backdrop, scene);

        // show the scene.
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    private AnalogueClock makeAnalogueClock(Stage stage)
    {
        final AnalogueClock analogueClock = new AnalogueClock(BRAND_NAME, CLOCK_RADIUS);
        EffectHelper.addGlowOnHover(analogueClock);
        EffectHelper.fadeOnClick(analogueClock, closeStageEventHandler(stage));
        return analogueClock;
    }

    private void sizeToScene(Pane pane, Scene scene)
    {
        pane.prefWidthProperty().bind(scene.widthProperty());
        pane.prefHeightProperty().bind(scene.heightProperty());
    }

    private Scene createClockScene(Parent layout)
    {
        final Scene scene = new Scene(layout, Color.TRANSPARENT);
        scene.getStylesheets().add(
                UtilHelper.getResourceFor(
                        ClockDemo.class,
                        "/css/clock-demo.css"
                )
        );
        return scene;
    }

    private EventHandler<ActionEvent> closeStageEventHandler(final Stage stage)
    {
        return actionEvent -> stage.close();
    }

    private Pane makeBackdrop(String id, Stage stage)
    {
        Pane backdrop = new Pane();
        backdrop.setId(id);
        EffectHelper.makeDraggable(stage, backdrop);
        return backdrop;
    }

    public static void main(String[] args) throws Exception
    {
        launch(args);
    }
}