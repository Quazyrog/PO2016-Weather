package com.github.saphir2357.po2016.weather;

import com.github.saphir2357.po2016.weather.data.WeatherData;
import com.github.saphir2357.po2016.weather.ui.MainWindow;
import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {
    private WeatherData weatherData = new WeatherData();
    private MainWindow mainWindow = new MainWindow(weatherData);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setScene(mainWindow.getScene());
        primaryStage.setTitle("Weather (Warsaw/Poland)");
        primaryStage.show();
    }

}
