package com.github.teocci.codesample.javafx.uisamples.features;

import com.github.teocci.codesample.javafx.tasks.TextDisplayCreationTask;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Variation of the ListViewTextHighlighter gist which displays a large amount of data (120 megabytes) in a searchable ListView.
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-26
 */
public class ListViewTextHighlighterWithLargeData extends Application
{
    public static void main(String[] args) throws Exception { launch(args); }

    private final StringProperty searchText = new SimpleStringProperty("");

    @Override
    public void start(final Stage stage) throws Exception
    {
        final TextDisplayCreationTask textDisplayCreationTask = new TextDisplayCreationTask(searchText);

        final Thread loadThread = new Thread(textDisplayCreationTask, "text-loader");
        loadThread.setDaemon(true);

        VBox loadProgressDisplay = createLoadProgressIndicator(textDisplayCreationTask);

        final StackPane textContentHolder = createTextContentHolder(loadProgressDisplay);

        final VBox layout = new VBox(10);
        layout.getChildren().setAll(
                createSearchControls(),
                textContentHolder
        );

        layout.getStylesheets().add(getClass().getResource("/css/highlighter.css").toExternalForm());

        stage.setTitle("Text Highlighter");
        stage.setScene(new Scene(layout));
        stage.show();

        textDisplayCreationTask.setOnSucceeded(workerStateEvent -> textContentHolder.getChildren().setAll(
                textDisplayCreationTask.getValue()
        ));

        loadThread.start();
    }

    private StackPane createTextContentHolder(VBox loadProgressDisplay)
    {
        final StackPane textContentHolder = new StackPane();
        textContentHolder.setPrefSize(480, 500);
        textContentHolder.getChildren().setAll(
                loadProgressDisplay
        );
        return textContentHolder;
    }

    private VBox createLoadProgressIndicator(TextDisplayCreationTask textDisplayCreationTask)
    {
        final ProgressIndicator loadProgress = new ProgressIndicator();
        loadProgress.progressProperty().bind(textDisplayCreationTask.progressProperty());
        loadProgress.setPrefSize(100, 100);
        VBox loadProgressDisplay = new VBox(10);
        loadProgressDisplay.getChildren().setAll(
                new Label("Loading Progress"),
                loadProgress
        );
        loadProgressDisplay.setAlignment(Pos.CENTER);
        loadProgressDisplay.setMaxSize(HBox.USE_PREF_SIZE, HBox.USE_PREF_SIZE);
        return loadProgressDisplay;
    }

    private HBox createSearchControls()
    {
        final TextField searchField = new TextField();
        searchField.setPromptText("Enter Search Text");
        final Button searchButton = new Button("Search");
        searchButton.setDefaultButton(true);

        searchButton.setOnAction(actionEvent -> searchText.setValue(searchField.getText()));

        HBox controls = new HBox(10);
        controls.getChildren().setAll(
                searchField,
                searchButton
        );
        HBox.setHgrow(searchField, Priority.ALWAYS);
        return controls;
    }
}



