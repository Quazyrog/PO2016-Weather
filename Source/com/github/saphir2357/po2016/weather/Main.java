package com.github.saphir2357.po2016.weather;

import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {
    private MainWindowControler mainWindow = new MainWindowControler();

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
