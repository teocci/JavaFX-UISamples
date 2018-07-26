package com.github.teocci.codesample.javafx.uisamples.features;

import com.github.teocci.codesample.javafx.enums.Location;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Demo of interaction of various concurrency aspects with JavaFX
 * <p>
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-26
 */
public class Greed extends Application
{
    private static final BlockingQueue<TravellingDwarf> dwarvesHeadingToCave = new ArrayBlockingQueue<>(3);
    private static final BlockingQueue<TravellingDwarf> dwarvesHeadingToTown = new ArrayBlockingQueue<>(3);
    private static final Random random = new Random();
    private final Log log = Log.getInstance();

    public static Random getRandom()
    {
        return random;
    }

    @Override
    public void start(final Stage stage) throws Exception
    {
        final DragonCave dragonCave = new DragonCave();

        final ProgressBar goldLeft = new ProgressBar();
        goldLeft.setStyle("-fx-accent: gold; -fx-color: lavender;");
        goldLeft.progressProperty().bind(
                dragonCave.goldProperty().divide(
                        dragonCave.MAX_GOLD * 1.0
                )
        );
        goldLeft.setMaxWidth(Double.MAX_VALUE);

        log.getView().setStyle("-fx-focus-color: transparent;");

        final ImageView dragonView = new ImageView(
                new Image(
                        "http://fc02.deviantart.net/fs70/i/2011/250/d/4/fire_dragon_from_a_volcano_by_wil_simpson-d497hc0.jpg",
                        200, 0, true, true
                )
        );
        final InnerShadow effect = new InnerShadow();
        effect.setColor(Color.LAVENDER);
        dragonView.setEffect(effect);
        dragonView.setMouseTransparent(true);
        dragonView.setOpacity(0.1);
        dragonView.visibleProperty().bind(
                dragonCave.getDragon().sleepingProperty().not()
        );
        dragonCave.getDragon().getDwarvesEaten().addListener(new InvalidationListener()
        {
            @Override
            public void invalidated(Observable observable)
            {
                if (dragonCave.getDragon().getDwarvesEaten().size() == Town.MAX_DWARVES) {
                    dragonView.visibleProperty().unbind();
                    dragonView.setOpacity(0.5);
                }
            }
        });

        StackPane logWithDragon = new StackPane();
        logWithDragon.getChildren().setAll(
                log.getView(),
                dragonView
        );

        final VBox layout = new VBox();
        layout.getChildren().setAll(
                goldLeft,
                logWithDragon
        );
        layout.getStylesheets().add(getClass().getResource("/css/greed.css").toExternalForm());

        stage.setTitle("Greed");
        stage.setScene(
                new Scene(
                        layout
                )
        );
        stage.show();

        final Thread eater = new Thread(
                new DwarvesEnteringCaveHandler(
                        dragonCave,
                        dwarvesHeadingToCave,
                        dwarvesHeadingToTown
                ),
                "eater"
        );
        eater.setDaemon(true);
        eater.start();
    }

    public static void main(String[] args) throws InterruptedException
    {
        final Town town = new Town();

        final Thread feeder = new Thread(
                new DwarvesLeavingTownHandler(town, dwarvesHeadingToCave),
                "feeder"
        );
        feeder.setDaemon(true);
        feeder.start();

        final Thread returner = new Thread(
                new DwarvesEnteringTownHandler(town, dwarvesHeadingToTown),
                "returner"
        );
        returner.setDaemon(true);
        returner.start();
        launch(args);
    }
}

class DwarvesEnteringCaveHandler extends Task<Void>
{
    final DragonCave cave;
    final BlockingQueue<TravellingDwarf> dwarvesHeadingToCave;
    final BlockingQueue<TravellingDwarf> dwarvesHeadingToTown;

    public DwarvesEnteringCaveHandler(
            DragonCave cave,
            BlockingQueue<TravellingDwarf> dwarvesHeadingToCave,
            BlockingQueue<TravellingDwarf> dwarvesHeadingToTown)
    {
        this.cave = cave;
        this.dwarvesHeadingToCave = dwarvesHeadingToCave;
        this.dwarvesHeadingToTown = dwarvesHeadingToTown;
    }

    @Override
    protected Void call()
    {
        while (true) {
            try {
                final TravellingDwarf enteringDwarf = dwarvesHeadingToCave.take();

                final FutureTask<TravellingDwarf> caveVoyage = new FutureTask<>(
                        () -> cave.dwarfEnters(enteringDwarf)
                );

                Platform.runLater(caveVoyage);

                final TravellingDwarf returningDwarf = caveVoyage.get();
                if (returningDwarf != null) {
                    dwarvesHeadingToTown.put(returningDwarf);
                }
            } catch (InterruptedException ex) {
                System.out.println("TravellingDwarfHandler interrupted");
                return null;
            } catch (ExecutionException e) {
                System.out.println("Unexpected exception encountered entering the cave: " + e);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

class DragonCave
{
    public static final int MAX_GOLD = 100;

    private Log log = Log.getInstance();

    private Dragon dragon = new Dragon();

    Dragon getDragon()
    {
        return dragon;
    }

    private ReadOnlyIntegerWrapper gold = new ReadOnlyIntegerWrapper(MAX_GOLD);

    public ReadOnlyIntegerProperty goldProperty()
    {
        return gold.getReadOnlyProperty();
    }

    public TravellingDwarf dwarfEnters(TravellingDwarf dwarf)
    {
        if (dragon.isSleeping()) {
            int goldTaken = Math.min(
                    dwarf.getGoldCarryingCapacity(),
                    gold.get()
            );
            gold.set(gold.get() - goldTaken);

            log.info(dwarf.getName() + " took " + goldTaken + " gold");

            return new TravellingDwarf(
                    dwarf.getName(),
                    Location.TOWN,
                    dwarf.getGoldCarryingCapacity(),
                    goldTaken
            );
        } else {
            dragon.eat(dwarf);
            log.warn(dwarf.getName() + " was eaten");
            return null; // dragon is awake, dwarf is eaten and never returns.
        }
    }
}

class Dragon
{
    private static final Random random = Greed.getRandom();
    private static final int MIN_SLEEP_TIME = 1600;
    private static final int MAX_SLEEP_TIME = 3200 + MIN_SLEEP_TIME;
    private final ReadOnlyBooleanWrapper sleeping = new ReadOnlyBooleanWrapper(random.nextBoolean());
    private final ObservableList<TravellingDwarf> dwarvesEaten = FXCollections.observableArrayList();

    public Dragon()
    {
        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.millis(random.nextInt(MAX_SLEEP_TIME - MIN_SLEEP_TIME) + MIN_SLEEP_TIME),
                        event -> sleeping.set(!sleeping.get())
                )
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public ReadOnlyBooleanProperty sleepingProperty()
    {
        return sleeping.getReadOnlyProperty();
    }

    public boolean isSleeping()
    {
        return sleeping.get();
    }

    public void eat(TravellingDwarf dwarf)
    {
        dwarvesEaten.add(dwarf);
    }

    public ObservableList<TravellingDwarf> getDwarvesEaten()
    {
        return FXCollections.unmodifiableObservableList(dwarvesEaten);
    }
}

class DwarvesLeavingTownHandler implements Runnable
{
    private static final int MIN_PAUSE_INTERVAL = 800;
    private static final int MAX_PAUSE_INTERVAL = 4000 - MIN_PAUSE_INTERVAL;
    private final Random random = Greed.getRandom();
    private final Town town;
    private final BlockingQueue<TravellingDwarf> travellingDwarves;

    public DwarvesLeavingTownHandler(Town town, BlockingQueue<TravellingDwarf> travellingDwarves)
    {
        this.town = town;
        this.travellingDwarves = travellingDwarves;
    }

    @Override
    public void run()
    {
        while (true) {
            try {
                Thread.sleep(random.nextInt(MAX_PAUSE_INTERVAL - MIN_PAUSE_INTERVAL) + MIN_PAUSE_INTERVAL);

                final TravellingDwarf dwarf = town.createTravellingDwarf(
                        Location.DRAGON_CAVE
                );

                if (dwarf != null) {
                    travellingDwarves.put(dwarf);
                }
            } catch (InterruptedException e) {
                System.out.println("Simulation Interrupted");
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

class DwarvesEnteringTownHandler implements Runnable
{
    private final BlockingQueue<TravellingDwarf> travellingDwarves;
    private final Town town;

    public DwarvesEnteringTownHandler(Town town, BlockingQueue<TravellingDwarf> travellingDwarves)
    {
        this.town = town;
        this.travellingDwarves = travellingDwarves;
    }

    @Override
    public void run()
    {
        while (true) {
            try {
                final TravellingDwarf enteringDwarf = travellingDwarves.take();
                town.returnToTown(enteringDwarf);
            } catch (InterruptedException ex) {
                System.out.println("TravellingDwarfHandler interrupted");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

class Town
{
    private static final Random random = Greed.getRandom();
    private static final String[] NAMES = {
            "Bofur", "Bifur", "Balin", "Dwalin", "Fili", "Kili", "Gloin", "Oin", "Nori", "Ori", "Dori", "Bombur", "Thorin"
    };
    public static final int MAX_DWARVES = NAMES.length;
    private static final Map<String, Integer> carryingCapacity = new HashMap<>();

    static {
        for (final String name : NAMES) {
            carryingCapacity.put(name, (random.nextInt(8) + 2));
        }
    }

    private final Location location = Location.TOWN;
    private final List<String> dwarvesInTown = new ArrayList<>(Arrays.asList(NAMES));

    private int gold = 0;

    public synchronized boolean hasDwarves()
    {
        return !dwarvesInTown.isEmpty();
    }

    public synchronized TravellingDwarf createTravellingDwarf(Location destination)
    {
        if (destination == this.location) {
            return null;
        }

        if (!hasDwarves()) {
            return null;
        }

        String dwarfName = dwarvesInTown.remove(0);

        return new TravellingDwarf(
                dwarfName,
                destination,
                carryingCapacity.get(dwarfName),
                0
        );
    }

    public synchronized void returnToTown(TravellingDwarf enteringDwarf)
    {
        gold += enteringDwarf.getGoldCarried();
        dwarvesInTown.add(enteringDwarf.getName());
        System.out.println("Town has " + gold + " gold");
    }
}

class TravellingDwarf
{
    private final String name;
    private final Location destination;
    private final int goldCarryingCapacity;
    private final int goldCarried;

    public TravellingDwarf(String name, Location destination, int goldCarryingCapacity, int goldCarried)
    {
        this.name = name;
        this.destination = destination;
        this.goldCarryingCapacity = goldCarryingCapacity;
        this.goldCarried = goldCarried;
    }

    public String getName()
    {
        return name;
    }

    public Location getDestination()
    {
        return destination;
    }

    public int getGoldCarried()
    {
        return goldCarried;
    }

    public int getGoldCarryingCapacity()
    {
        return goldCarryingCapacity;
    }

    @Override
    public String toString()
    {
        return "TravellingDwarf{" +
                "name='" + name + '\'' +
                ", destination=" + destination +
                ", goldCarryingCapacity=" + goldCarryingCapacity +
                ", goldCarried=" + goldCarried +
                '}';
    }
}

class Log
{
    private static final int MAX_LENGTH = 10000;

    private enum Level
    {
        INFO, WARN
    }

    private final ListView<LogItem> log = new ListView();

    private class LogItem
    {
        private final Level level;
        private final String msg;

        private LogItem(Level level, String msg)
        {
            this.level = level;
            this.msg = msg;
        }

        private Level getLevel()
        {
            return level;
        }

        private String getMsg()
        {
            return msg;
        }
    }

    private static final Log instance = new Log();

    public static Log getInstance()
    {
        return instance;
    }

    private final StyleManager logStyles = new StyleManager(
            "info", "warn"
    );

    public Log()
    {
        log.setCellFactory(new Callback<ListView<LogItem>, ListCell<LogItem>>()
        {
            @Override
            public ListCell<LogItem> call(ListView<LogItem> logItemListView)
            {
                return new ListCell<LogItem>()
                {
                    @Override
                    protected void updateItem(LogItem logItem, boolean empty)
                    {
                        super.updateItem(logItem, empty);

                        if (!getStyleClass().contains("log-entry")) {
                            getStyleClass().add("log-entry");
                        }
                        if (logItem != null) {
                            setText(logItem.getMsg());
                            switch (logItem.getLevel()) {
                                case INFO:
                                    logStyles.selectStyle("info", getStyleClass());
                                    break;

                                case WARN:
                                    logStyles.selectStyle("warn", getStyleClass());
                                    break;

                                default:
                                    logStyles.selectStyle(null, getStyleClass());
                            }
                        } else {
                            setText("");
                            logStyles.selectStyle(null, getStyleClass());
                        }
                    }
                };
            }
        });
    }

    public ListView getView()
    {
        return log;
    }

    public void info(String msg)
    {
        log(Level.INFO, msg);
    }

    public void warn(String msg)
    {
        log(Level.WARN, msg);
    }

    private void log(Level level, String msg)
    {
        log.scrollTo(log.getItems().size() - 1);
        if (log.getItems().size() >= MAX_LENGTH) {
            log.getItems().remove(log.getItems().size() - 1);
        }
        log.getItems().add(new LogItem(level, msg));
    }
}

class StyleManager
{
    private Set managedClasses = new TreeSet();

    public StyleManager(String... styleClasses)
    {
        Collections.addAll(managedClasses, styleClasses);
    }

    public void selectStyle(String styleClass, ObservableList<String> styleClasses)
    {
        if (styleClass == null) {
            styleClasses.removeAll(managedClasses);
            return;
        }

        if (!styleClasses.contains(styleClass)) {
            styleClasses.removeAll(managedClasses);
            styleClasses.add(styleClass);
        }
    }
}