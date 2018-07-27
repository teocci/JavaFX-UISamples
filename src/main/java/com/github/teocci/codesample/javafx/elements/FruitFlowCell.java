package com.github.teocci.codesample.javafx.elements;

import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.util.Map;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-27
 */
public class FruitFlowCell extends ListCell<String>
{
    static final String FRUIT_PLACEHOLDER = "%f";

    {
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    }

    private final Map<String, Image> fruitImages;

    public FruitFlowCell(Map<String, Image> fruitImages)
    {
        this.fruitImages = fruitImages;
    }

    @Override
    protected void updateItem(String s, boolean empty)
    {
        super.updateItem(s, empty);
        if (s != null && !"".equals(s) && !isEmpty()) {
            setGraphic(createFruitFlow(s));
        } else {
            setGraphic(null);
        }
    }

    private Node createFruitFlow(String s)
    {
        switch (s) {
            case "apple":
                return createTextFlow("Eat an ", FRUIT_PLACEHOLDER, s, " a day.");
            case "orange":
                return createTextFlow("An ", FRUIT_PLACEHOLDER, s, " has many vitamins.");
            case "pear":
                return createTextFlow("A ", FRUIT_PLACEHOLDER, s, " has a funny shape.");
            default:
                return null;
        }
    }

    private Node createTextFlow(String... msg)
    {
        FlowPane flow = new FlowPane();
        boolean isFruit = false;

        for (String s : msg) {
            if (FRUIT_PLACEHOLDER.equals(s)) {
                isFruit = true;
                continue;
            }

            Text text = new Text(s);
            if (isFruit) {
                flow.getChildren().addAll(new ImageView(fruitImages.get(s)), createSpacer(5));
                text.getStyleClass().addAll("fruit", s);
                isFruit = false;
            } else {
                text.getStyleClass().add("plain");
            }

            flow.getChildren().add(text);
        }

        return flow;
    }

    private Node createSpacer(int width)
    {
        HBox spacer = new HBox();
        spacer.setMinWidth(HBox.USE_PREF_SIZE);
        spacer.setPrefWidth(width);
        spacer.setMaxWidth(HBox.USE_PREF_SIZE);

        return spacer;
    }
}
