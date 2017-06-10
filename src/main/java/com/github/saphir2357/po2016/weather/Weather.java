package com.github.saphir2357.po2016.weather;

import com.github.saphir2357.po2016.weather.datasources.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;



public class Weather extends Application {
    private Config config = new Config();
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private Timeline updateTimeline;
    private WeatherData weatherData = new WeatherData();
    private MainWindow mainWindow;
    private int successfulSubUpdates = 0;


    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {
        File configFile = new File("weather.properties");
        if (configFile.canRead()) {
            try {
                Properties properties = new Properties();
                properties.load(new FileInputStream(configFile));
                config.updateFromProperties(new Properties());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        mainWindow = new MainWindow(this);
        Logger.getGlobal().info("Starting application");
        primaryStage.setScene(mainWindow.getScene());
        primaryStage.setTitle("Weather (Warsaw/Poland)");
        primaryStage.getIcons().add(new Image("Icon.png"));
        primaryStage.show();

        updateAll();
        updateTimeline = new Timeline(new KeyFrame(Duration.seconds(config.getUpdateInterval()), (e) -> updateAll()));
        updateTimeline.setCycleCount(Timeline.INDEFINITE);
        updateTimeline.play();
    }


    @Override
    public void stop() throws Exception {
        Logger.getGlobal().info("Stopping application");
        super.stop();
        updateTimeline.stop();
        Logger.getGlobal().info("Shutting down executor");
        executor.shutdownNow();
    }


    void updateAll() {
        Logger.getGlobal().info("Updating all data");
        successfulSubUpdates = 0;
        weatherData.clearValues();
        mainWindow.setStatusText("Updating...");
        executor.execute(new WeatherUpdateTask(this));
        executor.execute(new AirUpdateTask(this));
    }


    void handleUpdate(WorkerStateEvent e) {
        try {
            IWeatherUpdate update = (IWeatherUpdate)((Task)e.getSource()).get();
            if (update == null) {
                Logger.getGlobal().info("Could not get update");
                if (e.getSource() instanceof AirUpdateTask)
                    mainWindow.setStatusText("Cannot update Air: probably no internet connection");
                else if (e.getSource() instanceof WeatherUpdateTask)
                    mainWindow.setStatusText("Cannot update Weather: no internet connection or wrong API key?");
                return;
            }

            weatherData.update(update, false);
            mainWindow.refresh();
            Logger.getGlobal().info("Successfully updated data");

            ++successfulSubUpdates;
            if (successfulSubUpdates == 2)
                mainWindow.setStatusText("Updated " + new Date());
        } catch (ExecutionException | InterruptedException err) {
            Logger.getGlobal().log(Level.SEVERE, "Something went wrong", err);
        }
    }


    public WeatherData getWeatherData() {
        return weatherData;
    }


    public Config getConfig() {
        return config;
    }
}
