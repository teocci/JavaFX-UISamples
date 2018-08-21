package com.github.teocci.codesample.javafx.uisamples.features.traybar;

import dorkbox.systemTray.Checkbox;
import dorkbox.systemTray.Menu;
import dorkbox.systemTray.MenuItem;
import dorkbox.systemTray.Separator;
import dorkbox.systemTray.SystemTray;
import dorkbox.util.CacheUtil;
import dorkbox.util.Desktop;
import dorkbox.util.JavaFX;
import dorkbox.util.OS;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;


/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Aug-20
 */
public class TestTrayJavaFX
{
    public static final URL BLUE_CAMPING = TestTrayJavaFX.class.getResource("/images/accommodation_camping.glow.0092DA.32.png");
    public static final URL BLACK_FIRE = TestTrayJavaFX.class.getResource("/images/amenity_firestation_black.png");

    public static final URL BLACK_MAIL = TestTrayJavaFX.class.getResource("/images/amenity_post_box_black.png");
    public static final URL GREEN_MAIL = TestTrayJavaFX.class.getResource("/images/amenity_post_box_gree.png");

    public static final URL BLACK_BUS = TestTrayJavaFX.class.getResource("/images/transport_bus_station_black.png");
    public static final URL LT_GRAY_BUS = TestTrayJavaFX.class.getResource("/images/transport_bus_station_light_gray.png");

    public static final URL BLACK_TRAIN = TestTrayJavaFX.class.getResource("/images/transport_train_station_black.png");
    public static final URL GREEN_TRAIN = TestTrayJavaFX.class.getResource("/images/transport_train_station_green.png");
    public static final URL LT_GRAY_TRAIN = TestTrayJavaFX.class.getResource("/images/transport_train_station_dark_gray.png");

    private static TestTrayJavaFX testTrayJavaFX;

    public static
    class MyApplication extends Application
    {
        public MyApplication()
        {
        }

        @Override
        public void start(final Stage stage) throws Exception
        {
            if (testTrayJavaFX == null) {
                testTrayJavaFX = new TestTrayJavaFX();
            }

            testTrayJavaFX.doJavaFxStuff(stage);
        }
    }

    private SystemTray systemTray;
    private ActionListener callbackGray;

    public TestTrayJavaFX() {}

    public void doJavaFxStuff(final Stage primaryStage) throws Exception
    {
        primaryStage.setTitle("Hello World!");
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(event -> System.out.println("Hello World!"));

        StackPane root = new StackPane();
        root.getChildren().add(btn);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();

        SystemTray.DEBUG = true; // for test apps, we always want to run in debug mode
        CacheUtil.clear(); // for test apps, make sure the cache is always reset. You should never do this in production.

        // SwingUtil.setLookAndFeel(null); // set Native L&F (this is the System L&F instead of CrossPlatform L&F)
        // SystemTray.SWING_UI = new CustomSwingUI();

        this.systemTray = SystemTray.get();
        if (systemTray == null) {
            throw new RuntimeException("Unable to load SystemTray!");
        }

        systemTray.setTooltip("Mail Checker");
        systemTray.setImage(LT_GRAY_TRAIN);
        systemTray.setStatus("No Mail");

        callbackGray = e -> {
            final MenuItem entry = (MenuItem) e.getSource();
            systemTray.setStatus(null);
            systemTray.setImage(BLACK_TRAIN);

            entry.setCallback(null);
//                systemTray.setStatus("Mail Empty");
            systemTray.getMenu().remove(entry);
            System.err.println("POW");
        };


        Menu mainMenu = systemTray.getMenu();

        MenuItem greenEntry = new MenuItem("Green Mail", e -> {
            final MenuItem entry = (MenuItem) e.getSource();
            systemTray.setStatus("Some Mail!");
            systemTray.setImage(GREEN_TRAIN);

            entry.setCallback(callbackGray);
            entry.setImage(BLACK_MAIL);
            entry.setText("Delete Mail");
            entry.setTooltip(null); // remove the tooltip
//                systemTray.remove(menuEntry);
        });

        greenEntry.setImage(GREEN_MAIL);
        // case does not matter
        greenEntry.setShortcut('G');
        greenEntry.setTooltip("This means you have green mail!");
        mainMenu.add(greenEntry);


        Checkbox checkbox = new Checkbox("Euro € Mail", e -> System.err.println("Am i checked? " + ((Checkbox) e.getSource()).getChecked()));
        checkbox.setShortcut('€');
        mainMenu.add(checkbox);

        mainMenu.add(new Separator());

        mainMenu.add(new MenuItem("About", e -> {
            try {
                Desktop.browseURL("https://github.com/dorkbox/SystemTray");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }));

        mainMenu.add(new MenuItem("Temp Directory", e -> {
            try {
                Desktop.browseDirectory(OS.TEMP_DIR.getAbsolutePath());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }));

        Menu submenu = new Menu("Options", BLUE_CAMPING);
        submenu.setShortcut('t');


        MenuItem disableMenu = new MenuItem("Disable menu", BLACK_BUS, e -> {
            MenuItem source = (MenuItem) e.getSource();
            source.getParent().setEnabled(false);
        });
        submenu.add(disableMenu);


        submenu.add(new MenuItem("Hide tray", LT_GRAY_BUS, e -> systemTray.setEnabled(false)));
        submenu.add(new MenuItem("Remove menu", BLACK_FIRE, e -> {
            MenuItem source = (MenuItem) e.getSource();
            source.getParent().remove();
        }));
        mainMenu.add(submenu);

        systemTray.getMenu().add(new MenuItem("Quit", e -> {
            systemTray.shutdown();

            if (!JavaFX.isEventThread()) {
                JavaFX.dispatch(() -> {
                    primaryStage.hide(); // must do this BEFORE Platform.exit() otherwise odd errors show up
                    Platform.exit();  // necessary to close javaFx
                });
            } else {
                primaryStage.hide(); // must do this BEFORE Platform.exit() otherwise odd errors show up
                Platform.exit();  // necessary to close javaFx
            }

            //System.exit(0);  not necessary if all non-daemon threads have stopped.
        })).setShortcut('q'); // case does not matter
    }

    public static void main(String[] args)
    {
        if (OS.isMacOsX() && OS.javaVersion <= 7) {
            System.setProperty("javafx.macosx.embedded", "true");
            java.awt.Toolkit.getDefaultToolkit();
        }

        testTrayJavaFX = new TestTrayJavaFX();

        Application application = new MyApplication();

        // make sure JNA jar is on the classpath!
        application.launch(MyApplication.class);
    }
}
