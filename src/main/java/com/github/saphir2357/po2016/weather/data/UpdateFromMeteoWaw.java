package com.github.saphir2357.po2016.weather.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateFromMeteoWaw implements IWeatherUpdate {
    private static final String SOURCE_URL = "http://www.meteo.waw.pl/";

    private String pageSource;

    public UpdateFromMeteoWaw() throws IOException {
        Logger.getGlobal().info("Downloading update from " + SOURCE_URL);
        URL source = new URL(SOURCE_URL);
        StringBuilder inputStringBuilder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(source.openStream()));
        String line = bufferedReader.readLine();
        while(line != null){
            inputStringBuilder.append(line);
            line = bufferedReader.readLine();
        }
        pageSource = inputStringBuilder.toString();
    }

    @Override
    public boolean hasData(WeatherUpdateDataKey data) {
        try {
            get(data);
            return true;
        } catch (IllegalStateException | IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public double get(WeatherUpdateDataKey data) {
        String searchStrongId;
        switch (data) {
            case TEMPERATURE:
                searchStrongId = "PARAM_TA";
                break;
            case PRESSURE:
                searchStrongId = "PARAM_PR";
                break;
            case HUMIDITY:
                searchStrongId = "PARAM_RH";
                break;
            case WIND_SPEED:
                searchStrongId = "PARAM_WV";
                break;
            case WIND_DEGREE:
                searchStrongId = "PARAM_WD";
                break;
            case RAINFALL_VOLUME:
                searchStrongId = "PARAM_RF3H";
                break;
            default:
                throw new IllegalArgumentException("no " + data + " in this update");
        }
        Pattern pattern = Pattern.compile("<strong id=\"" + searchStrongId + "\">(\\d+,\\d+)</strong>");
        Matcher matcher = pattern.matcher(pageSource);
        if (!matcher.find())
            throw new IllegalArgumentException("no " + data + " in this update");

        double result = Double.parseDouble(matcher.group(1).replace(",", "."));
        if (data != WeatherUpdateDataKey.TEMPERATURE)
            return result;
        return WeatherData.celciusToKelvin(result);
    }


    @Override
    public String description() {
        return "Weather";
    }
}
