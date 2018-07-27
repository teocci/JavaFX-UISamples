package com.github.teocci.codesample.javafx.uisamples.image;

import com.github.teocci.codesample.javafx.elements.FruitFlowCell;
import de.codecentric.centerdevice.javafxsvg.SvgImageLoaderFactory;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

/**
 * Sample of using a SvgImageLoaderFactory to add SVG images into a styled text in JavaFX
 * <p>
 * Icons made by <a href="https://www.flaticon.com/authors/smashicons" title="Smashicons">Smashicons</a> from
 * <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a> is licensed by
 * <a href="http://creativecommons.org/licenses/by/3.0/" title="Creative Commons BY 3.0" target="_blank">CC 3.0 BY</a>
 * <p>
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-27
 */
public class FruitsDisplay extends Application
{
    private static final String[] fruits = {"apple", "orange", "pear"};
    private static final String[] fruitImageLocs = {
            "https://image.flaticon.com/icons/svg/135/135728.svg",
            "https://image.flaticon.com/icons/svg/135/135620.svg",
            "https://image.flaticon.com/icons/svg/135/135576.svg"
    };

    private Map<String, Image> fruitImages = new HashMap<>();

    @Override
    public void start(Stage stage) throws Exception
    {

        stage.setTitle("Fruit Tales");

        for (int i = 0; i < fruits.length; i++) {
            Image image = new Image(fruitImageLocs[i], 30, 30, true, true);
            fruitImages.put(fruits[i], image);
        }

        ListView<String> list = new ListView<>(FXCollections.observableArrayList(fruits));
        list.setCellFactory(listView -> new FruitFlowCell(fruitImages));
        list.setPrefSize(440, 180);

        Scene scene = new Scene(list);
        scene.getStylesheets().add(getResource("/css/fruits.css"));
        stage.setScene(scene);
        stage.show();
    }

    private String getResource(String resourceName)
    {
        return getClass().getResource(resourceName).toExternalForm();
    }


    public static void main(String[] args)
    {
        SvgImageLoaderFactory.install();
        Application.launch(FruitsDisplay.class);
    }
}
