package com.github.teocci.codesample.javafx.elements;

import com.github.teocci.codesample.javafx.models.Delta;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Popup;
import javafx.stage.Window;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-26
 */
public class DockDialog extends Popup
{
    private BorderPane root;

    public DockDialog(Window parent)
    {
        root = new BorderPane();
        root.setPrefSize(200, 200);
        root.setStyle("-fx-border-width: 1; -fx-border-color: gray");
        root.setTop(buildTitleBar());
        setX(parent.getX() + 50);
        setY(parent.getY() + 50);
        getContent().add(root);
    }

    public void setContent(Node content)
    {
        root.setCenter(content);
    }

    private Node buildTitleBar()
    {
        BorderPane pane = new BorderPane();
        pane.setStyle("-fx-background-color: burlywood; -fx-padding: 5");

        final Delta dragDelta = new Delta();
        pane.setOnMousePressed(mouseEvent -> {
            dragDelta.x = getX() - mouseEvent.getScreenX();
            dragDelta.y = getY() - mouseEvent.getScreenY();
        });
        pane.setOnMouseDragged(mouseEvent -> {
            setX(mouseEvent.getScreenX() + dragDelta.x);
            setY(mouseEvent.getScreenY() + dragDelta.y);
        });

        Label title = new Label("My Dialog");
        title.setStyle("-fx-text-fill: midnightblue;");
        pane.setLeft(title);

        Button closeButton = new Button("X");
        closeButton.setOnAction(actionEvent -> hide());
        pane.setRight(closeButton);

        return pane;
    }
}
