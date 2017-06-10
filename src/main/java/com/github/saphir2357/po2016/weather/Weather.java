package com.github.saphir2357.po2016.weather;

import com.github.saphir2357.po2016.weather.data.UpdateFromGIOS;
import com.github.saphir2357.po2016.weather.data.UpdateFromOpenWeatherMap;
import com.github.saphir2357.po2016.weather.data.WeatherData;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;


public class Weather extends Application {
    private WeatherData weatherData = new WeatherData();
    private MainWindow mainWindow = new MainWindow(weatherData);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            weatherData.update(new UpdateFromOpenWeatherMap(), false);
            weatherData.update(new UpdateFromGIOS(), false);
            mainWindow.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setScene(mainWindow.getScene());
        primaryStage.setTitle("Weather (Warsaw/Poland)");
        primaryStage.show();
    }

}
