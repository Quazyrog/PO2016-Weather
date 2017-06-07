package com.github.saphir2357.po2016.weather.ui;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;


public class MainWindow {
    private static final Font HUGE_FONT = new Font(72);
    private static final Font NORMAL_FONT = new Font(22);
    private static final Font SMALL_FONT = new Font(14);

    private GridPane mainPane = new GridPane();
    private Scene scene;

    private ImageView weatherIcon = new ImageView("SadSun.png");
    private Label temperatureLabel = new Label();
    private ImageView windArrow = new ImageView();
    private Label windLabel = new Label();
    private Label humidityLabel = new Label();

    
    public MainWindow() {
        setupScene();
    }


    private void setupScene() {
        mainPane.setHgap(15);
        mainPane.setVgap(15);
        ColumnConstraints cc = new ColumnConstraints();
        cc.setHalignment(HPos.CENTER);
        cc.setPercentWidth(33);
        mainPane.getColumnConstraints().addAll(cc, cc, cc);

        addLocationLabel(0);
        addTemperatureAndImage(1);
        addWindCell(0, 2);

        humidityLabel.setFont(NORMAL_FONT);
        mainPane.add(humidityLabel, 1, 2);

        StackPane mainLayoutWrapper;
        mainLayoutWrapper = new StackPane(mainPane);
        mainLayoutWrapper.setPrefSize(600, 250);
        mainPane.setPadding(new Insets(20, 40, 20, 40));
        scene = new Scene(mainLayoutWrapper);
    }


    private void addLocationLabel(int gridY) {
        Label ll = new Label("Warsaw, Poland");
        ll.setFont(NORMAL_FONT);
        mainPane.add(ll, 0, gridY, 3, 1);
    }


    private void addTemperatureAndImage(int gridY) {
        weatherIcon.setFitHeight(96);
        weatherIcon.setFitWidth(96);
        mainPane.add(weatherIcon, 0, gridY);
        mainPane.add(temperatureLabel, gridY, 1, 2, 1);
        temperatureLabel.setFont(HUGE_FONT);
    }

    private void addWindCell(int gridX, int gridY) {
        windArrow.setFitWidth(36);
        windArrow.setFitHeight(36);
        windArrow.setRotate(90);
        windLabel.setFont(NORMAL_FONT);

        HBox windBox = new HBox();
        windBox.setAlignment(Pos.CENTER);
        windBox.setSpacing(10);
        windBox.getChildren().addAll(windLabel, windArrow);

        addLabeledCell(gridX, gridY, "Wind", windBox);
    }

    private void addLabeledCell(int gridX, int gridY, String label, Node content) {
        Label lbl = new Label(label);
        lbl.setFont(SMALL_FONT);
        VBox vbox = new VBox(lbl, content);
        vbox.setAlignment(Pos.CENTER);
        mainPane.add(vbox, gridX, gridY);
    }


    public Scene getScene() {
        return scene;
    }
}
