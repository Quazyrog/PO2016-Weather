package com.github.saphir2357.po2016.weather;

import com.github.saphir2357.po2016.weather.data.*;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;



public class Weather extends Application {
    private WeatherData weatherData = new WeatherData();
    private MainWindow mainWindow = new MainWindow(weatherData);
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    private UpdateSource weatherSource = UpdateSource.METEO_WAW_PL;


    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {
        Logger.getGlobal().info("Starting application");
        primaryStage.setScene(mainWindow.getScene());
        primaryStage.setTitle("Weather (Warsaw/Poland)");
        primaryStage.show();

        updateAll();
    }


    @Override
    public void stop() throws Exception {
        Logger.getGlobal().info("Stopping application");
        super.stop();
        Logger.getGlobal().info("Shutting down executor");
        executor.shutdown();
        Logger.getGlobal().info("Bye!");
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
            Logger.getGlobal().info("Successfully updated weather data");
        } catch (ExecutionException | InterruptedException err) {
            Logger.getGlobal().log(Level.SEVERE, "Something went wrong", err);
        }
    }


    public UpdateSource getWeatherSource() {
        return weatherSource;
    }


    public void setWeatherSource(UpdateSource weatherSource) {
        this.weatherSource = weatherSource;
    }
}
