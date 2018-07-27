package com.github.teocci.codesample.javafx.uisamples.elements;

import com.github.teocci.codesample.javafx.elements.TabAndEnterIgnoringTextArea;
import com.github.teocci.codesample.javafx.handlers.ClearStatusListener;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Allow a Tab key press in a TextArea to focus on the next field and an Enter key press in a TextArea to trigger a default button.
 *
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-26
 */

public class TextAreaTabAndEnterHandler extends Application
{
    final Label status = new Label();

    public static void main(String[] args) { launch(args); }

    @Override
    public void start(final Stage stage)
    {
        final TextArea textArea1 = new TabAndEnterIgnoringTextArea();
        final TextArea textArea2 = new TabAndEnterIgnoringTextArea();

        final Button defaultButton = new Button("OK");
        defaultButton.setDefaultButton(true);
        defaultButton.setOnAction(event -> status.setText("Default Button Pressed"));

        textArea1.textProperty().addListener(new ClearStatusListener(status));
        textArea2.textProperty().addListener(new ClearStatusListener(status));

        VBox layout = new VBox(10);
        layout.setStyle("-fx-background-color: cornsilk; -fx-padding: 10px;");
        layout.getChildren().setAll(
                textArea1,
                textArea2,
                defaultButton,
                status
        );

        stage.setScene(
                new Scene(layout)
        );
        stage.show();
    }
}