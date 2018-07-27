package com.github.teocci.codesample.javafx.uisamples.features;

import com.github.teocci.codesample.javafx.controllers.MediaPlayerScene;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;

import javax.swing.*;

/**
 * Example of playing all mp3 audio files in a given directory
 * using a JavaFX MediaView launched from Swing
 * <p>
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-27
 */
public class JavaFXMusicPlayerLaunchedFromSwing
{
    private static void initAndShowGUI()
    {
        // This method is invoked on Swing thread
        JFrame frame = new JFrame("FX");
        final JFXPanel fxPanel = new JFXPanel();
        frame.add(fxPanel);
        frame.setBounds(200, 100, 800, 250);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

        Platform.runLater(() -> initFX(fxPanel));
    }

    private static void initFX(JFXPanel fxPanel)
    {
        // This method is invoked on JavaFX thread
        Scene scene = new MediaPlayerScene().createScene();
        fxPanel.setScene(scene);
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> initAndShowGUI());
    }
}
