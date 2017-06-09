package com.github.saphir2357.po2016.weather.data;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;


public class UpdateFromOpenWeatherMap implements IWeatherUpdate {
    private JSONObject weather = null;
    private JSONObject main = null;
    private JSONObject wind = null;
    private static final String ENDPOINT =
            "http://samples.openweathermap.org/data/2.5/weather?id=2172797&appid=b1b15e88fa797225412429c1c50c122a1";


    public UpdateFromOpenWeatherMap() throws IOException {
        try {
            Logger.getGlobal().info("Downloading update from OpenWeatherMap");
            URL url = new URL(ENDPOINT);
            JSONObject response = new JSONObject(new JSONTokener(url.openStream()));
            weather = response.getJSONArray("weather").getJSONObject(0);
            main = response.getJSONObject("main");
            wind = response.getJSONObject("wind");
        } catch (JSONException e) {
            Logger.getGlobal().log(Level.SEVERE, "JSON parse error", e);
            throw new IOException("JSON parse error");
        }
    }


    @Override
    public boolean hasData(WeatherUpdateDataKey data) {
        try {
            get(data);
            return true;
        } catch (JSONException | IllegalArgumentException e) {
            return false;
        }
    }


    @Override
    public double get(WeatherUpdateDataKey data) {
        try {
            switch (data) {
                case WEATHER_CODE:
                    return weather.getDouble("id");
                case TEMPERATURE:
                    return main.getDouble("temp");
                case TEMPERATURE_MIN:
                    return main.getDouble("temp_min");
                case TEMPERATURE_MAX:
                    return main.getDouble("temp_max");
                case PRESSURE:
                    return main.getDouble("pressure");
                case HUMIDITY:
                    return main.getDouble("humidity");
                case WIND_SPEED:
                    return wind.getDouble("speed");
                case WIND_DEGREE:
                    return wind.getDouble("deg");
                default:
                    throw new IllegalArgumentException("No such response in this object");
            }
        } catch (JSONException e) {
            Logger.getGlobal().log(Level.SEVERE, "Wrong response structure", e);
            throw new RuntimeException("Unexpected JSON structure");
        }
    }
}
