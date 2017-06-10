package com.github.saphir2357.po2016.weather;

import com.github.saphir2357.po2016.weather.data.IWeatherUpdate;
import com.github.saphir2357.po2016.weather.data.UpdateFromMeteoWaw;
import com.github.saphir2357.po2016.weather.data.UpdateFromOpenWeatherMap;
import javafx.concurrent.Task;

import java.io.IOException;
import java.util.logging.Logger;

public class WeatherUpdateTask extends Task<IWeatherUpdate> {
    UpdateSource source;

    public WeatherUpdateTask(Weather application) {
        setOnSucceeded(application::handleUpdate);
        source = application.getWeatherSource();
    }

    @Override
    protected IWeatherUpdate call() throws Exception {
        Logger.getGlobal().info("Started weather network update task (source: " + source + ")");
        IWeatherUpdate result = null;
        try {
            switch (source) {
                case METEO_WAW_PL:
                    result = new UpdateFromMeteoWaw();
                    break;
                case OPEN_WEATHER_MAP:
                    result = new UpdateFromOpenWeatherMap();
                    break;
            }
            Logger.getGlobal().info("Completed weather network update task");
        } catch (IOException e) {
            Logger.getGlobal().info("Failed weather network update task (IOException: " + e + ")");
        }
        return result;
    }
}
