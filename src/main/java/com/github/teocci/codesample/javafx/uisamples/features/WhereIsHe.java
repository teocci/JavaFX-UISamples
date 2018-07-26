package com.github.teocci.codesample.javafx.uisamples.features;

import com.github.teocci.codesample.javafx.models.Delta;
import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

/**
 * A very basic JavaFX viewfinder application
 *
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-26
 */
public class WhereIsHe extends Application
{
    private static final String IMAGE_URL = "http://collider.com/wp-content/uploads/wheres-waldo2.jpg";

    private static final double VIEWFINDER_WIDTH = 40;
    private static final double VIEWFINDER_HEIGHT = 90;

    public static final Color LENS_TINT = Color.BLUE.deriveColor(0, 1, 1, 0.10);
    public static final Color MASK_TINT = Color.GRAY.deriveColor(0, 1, 1, 0.97);

    @Override
    public void start(Stage stage)
    {
        Image image = new Image(IMAGE_URL);

        ImageView background = new ImageView(image);
        StackPane layout = applyViewfinder(
                background, image.getWidth(), image.getHeight()
        );

        stage.setScene(new Scene(layout));
        stage.show();
    }

    private StackPane applyViewfinder(Node background, double width, double height)
    {
        Rectangle mask = new Rectangle(
                width,
                height
        );

        Rectangle viewfinder = new Rectangle(
                VIEWFINDER_WIDTH,
                VIEWFINDER_HEIGHT,
                LENS_TINT
        );
        makeDraggable(viewfinder);

        Pane viewPane = new Pane();
        viewPane.getChildren().addAll(
                new Group(),
                viewfinder
        );

        viewfinder.boundsInParentProperty().addListener((observableValue, bounds, bounds2) -> applyStencil(
                cutStencil(mask, viewfinder),
                viewPane
        ));

        viewfinder.relocate(
                width / 2 - VIEWFINDER_WIDTH / 2,
                height / 2 - VIEWFINDER_HEIGHT / 2
        );

        StackPane layout = new StackPane();
        layout.getChildren().setAll(
                background,
                viewPane
        );
        return layout;
    }

    private void applyStencil(Node stencil, Pane viewPane)
    {
        viewPane.getChildren().set(0, stencil);
    }

    private Node cutStencil(Rectangle mask, Rectangle viewfinder)
    {
        Shape stencil = Shape.subtract(mask, viewfinder);
        stencil.setFill(MASK_TINT);

        return stencil;
    }

    public void makeDraggable(final Node node)
    {
        final Delta dragDelta = new Delta();
        node.setOnMousePressed(mouseEvent -> {
            // record a delta distance for the drag and drop operation.
            dragDelta.x = mouseEvent.getX();
            dragDelta.y = mouseEvent.getY();
        });
        node.setOnMouseReleased(mouseEvent -> node.setCursor(Cursor.MOVE));
        node.setOnMouseDragged(mouseEvent -> {
            node.relocate(
                    mouseEvent.getSceneX() - dragDelta.x,
                    mouseEvent.getSceneY() - dragDelta.y
            );
            node.setCursor(Cursor.NONE);
        });
        node.setOnMouseEntered(mouseEvent -> {
            if (!mouseEvent.isPrimaryButtonDown()) {
                node.setCursor(Cursor.MOVE);
            }
        });
        node.setOnMouseExited(mouseEvent -> {
            if (!mouseEvent.isPrimaryButtonDown()) {
                node.setCursor(Cursor.DEFAULT);
            }
        });
    }

    public static void main(String[] args) { launch(args); }
}
