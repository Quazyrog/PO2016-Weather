package com.github.saphir2357.po2016.weather;

import java.util.Properties;
import java.util.logging.Logger;

public class Config {
    private UpdateSource updateSource = UpdateSource.OPEN_WEATHER_MAP;
    private int giosStationID = 530;
    private int updateInterval = 1800;
    private String owmAPIKey = "f8b5d124cf7d13e0697128c5aedac55c";
    private boolean owmUseSample = false;
    private int owmCityID = 6695624;


    public UpdateSource getUpdateSource() {
        return updateSource;
    }


    public void setUpdateSource(UpdateSource updateSource) {
        this.updateSource = updateSource;
    }


    public int getGiosStationID() {
        return giosStationID;
    }


    public void setGiosStationID(int giosStationID) {
        this.giosStationID = giosStationID;
    }


    public int getUpdateInterval() {
        return updateInterval;
    }


    public void setUpdateInterval(int updateInterval) {
        this.updateInterval = updateInterval;
    }


    public String getOwmAPIKey() {
        return owmAPIKey;
    }


    public void setOwmAPIKey(String owmAPIKey) {
        this.owmAPIKey = owmAPIKey;
    }


    public boolean isOwmUseSample() {
        return owmUseSample;
    }


    public void setOwmUseSample(boolean owmUseSample) {
        this.owmUseSample = owmUseSample;
    }


    public int getOwmCityID() {
        return owmCityID;
    }


    public void setOwmCityID(int owmCityID) {
        this.owmCityID = owmCityID;
    }


    public void updateFromProperties(Properties prop) {
        Logger.getGlobal().info("Updating config from properties");
        if (prop.contains("updateSource")) {
            String source = prop.getProperty("updateSource");
            if (source.equals(UpdateSource.METEO_WAW_PL.toString())) {
                updateSource = UpdateSource.METEO_WAW_PL;
            } else if (source.equals(UpdateSource.OPEN_WEATHER_MAP.toString())) {
                updateSource = UpdateSource.OPEN_WEATHER_MAP;
            }
        }

        giosStationID = safeParseInt(giosStationID, "giosStationID", prop);
        updateInterval = safeParseInt(updateInterval, "updateInterval", prop);
        owmUseSample = safeParseInt(owmUseSample ? 1 : 0, "owmUseSample", prop) != 0;
        owmAPIKey = prop.getProperty("owmAPIKey", owmAPIKey);
        owmCityID = safeParseInt(owmCityID, "owmCityID", prop);
    }


    private int safeParseInt(int val, String key, Properties p) {
        try {
            val = Integer.parseInt(p.getProperty(key));
        } catch (NumberFormatException | NullPointerException ignored) {}
        return val;
    }
}
