package com.github.saphir2357.po2016.weather;

import com.github.saphir2357.po2016.weather.data.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;



public class Weather extends Application {
    private UpdateSource weatherSource = UpdateSource.OPEN_WEATHER_MAP;
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private Timeline updateTimeline;
    private WeatherData weatherData = new WeatherData();
    private MainWindow mainWindow;


    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {
        mainWindow = new MainWindow(this);
        Logger.getGlobal().info("Starting application");
        primaryStage.setScene(mainWindow.getScene());
        primaryStage.setTitle("Weather (Warsaw/Poland)");
        primaryStage.show();

        updateAll();
        updateTimeline = new Timeline(new KeyFrame(Duration.seconds(1800), (e) -> updateAll()));
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
        executor.execute(new WeatherUpdateTask(this));
        executor.execute(new AirUpdateTask(this, 530));
    }


    void handleUpdate(WorkerStateEvent e) {
        try {
            IWeatherUpdate update = (IWeatherUpdate)((Task)e.getSource()).get();
            weatherData.update(update, false);
            mainWindow.refresh();
            Logger.getGlobal().info("Successfully updated data");
            mainWindow.setStatusText("Updated " + new Date() + " (" + update.description() + ")");
        } catch (ExecutionException | InterruptedException err) {
            Logger.getGlobal().log(Level.SEVERE, "Something went wrong", err);
        }
    }


    public UpdateSource getWeatherSource() {
        return weatherSource;
    }


    public void setWeatherSource(UpdateSource weatherSource) {
        Logger.getGlobal().info("Changing weather source to " + weatherSource);
        this.weatherSource = weatherSource;
    }


    public WeatherData getWeatherData() {
        return weatherData;
    }

}
