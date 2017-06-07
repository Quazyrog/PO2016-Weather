package com.github.saphir2357.po2016.weather;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;


public class MainWindowControler {
    private static final Font HUGE_FONT = new Font(72);
    private static final Font NORMAL_FONT = new Font(24);
    private static final Font SMALL_FONT = new Font(14);

    private GridPane mainPane = new GridPane();
    private Scene scene;

    private Label locationLabel = new Label("Warsaw, Poland");
    private ImageView weatherIcon = new ImageView("SadSun.png");
    private Label temperatureLabel = new Label("22℃");
    private ImageView windArrow = new ImageView("Arrow.png");
    private Label windLabel = new Label("22 km/h");
    private Label humidityLabel = new Label("very wet");

    public MainWindowControler() {
        mainPane.setHgap(15);
        mainPane.setVgap(10);
        ColumnConstraints cc = new ColumnConstraints();
        cc.setHalignment(HPos.CENTER);
        cc.setPercentWidth(33);
        mainPane.getColumnConstraints().addAll(cc, cc, cc);

        locationLabel.setId("LocationLabel");
        locationLabel.setFont(SMALL_FONT);
        mainPane.add(locationLabel, 0, 0, 3, 1);

        weatherIcon.setFitHeight(96);
        weatherIcon.setFitWidth(96);
        mainPane.add(weatherIcon, 0, 1);
        mainPane.add(temperatureLabel, 1, 1, 2, 1);
        temperatureLabel.setFont(HUGE_FONT);

        HBox windBox = new HBox();
        windArrow.setFitWidth(36);
        windArrow.setFitHeight(36);
        windArrow.setRotate(90);
        windLabel.setFont(NORMAL_FONT);
        windBox.setAlignment(Pos.CENTER);
        windBox.setSpacing(10);
        windBox.getChildren().addAll(windLabel, windArrow);
        mainPane.add(windBox, 0, 2);

        humidityLabel.setFont(NORMAL_FONT);
        mainPane.add(humidityLabel, 1, 2);

        StackPane mainLayoutWrapper;
        mainLayoutWrapper = new StackPane(mainPane);
        mainLayoutWrapper.setPrefSize(600, 250);
        mainPane.setPadding(new Insets(20, 40, 20, 40));
        scene = new Scene(mainLayoutWrapper);
    }

    public Scene getScene() {
        return scene;
    }
}
