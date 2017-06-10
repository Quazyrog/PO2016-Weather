package com.github.saphir2357.po2016.weather;

import com.github.saphir2357.po2016.weather.datasources.IWeatherUpdate;
import com.github.saphir2357.po2016.weather.datasources.UpdateFromGIOS;
import javafx.concurrent.Task;

import java.io.IOException;
import java.util.logging.Logger;



public class AirUpdateTask extends Task<IWeatherUpdate> {
    int stationID;


    public AirUpdateTask(Weather application) {
        setOnSucceeded(application::handleUpdate);
        stationID = application.getConfig().getGiosStationID();
    }


    @Override
    protected IWeatherUpdate call() throws Exception {
        Logger.getGlobal().info("Started air network update task from station " + stationID);
        IWeatherUpdate result = null;
        try {
            result = new UpdateFromGIOS(stationID);
            Logger.getGlobal().info("Completed weather network update task");
        } catch (IOException e) {
            Logger.getGlobal().info("Failed weather network update task (IOException: " + e + ")");
        }
        return result;
    }
}
