package com.github.saphir2357.po2016.weather.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidParameterException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class UpdateFromGIOS implements IWeatherUpdate {
    private static final String ALL_DATA_ENDPOINT = "http://powietrze.gios.gov.pl/pjp/current/getAQIDetailsList?param=AQI";
    private static final int DEFAULT_STATION_ID = 530;


    private JSONObject stationData = null;


    public UpdateFromGIOS() throws IOException {
        this(DEFAULT_STATION_ID);
    }


    public UpdateFromGIOS(int stationId) throws IOException {
        Logger.getGlobal().info("Downloading update from GIOS station " + stationId);
        try {
            URL sensorsUrl = new URL(ALL_DATA_ENDPOINT);
            JSONArray stations = new JSONArray(new JSONTokener(sensorsUrl.openStream()));
            for (Object o : stations) {
                if (!(o instanceof JSONObject)) {
                    Logger.getGlobal().warning("Invalid object in array: " + o);
                    continue;
                }
                JSONObject data = (JSONObject)o;
                if (data.getInt("stationId") == stationId) {
                    stationData = data;
                    break;
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new IOException("This must be a bug");
        } catch (JSONException e) {
            Logger.getGlobal().log(Level.SEVERE, "response parse failed", e);
            throw new IOException("JSON parse error");
        }
    }


    @Override
    public boolean hasData(WeatherUpdateDataKey data) {
        if (stationData == null)
            return false;
        switch (data) {
            case AIR_QUALITY_INDEX:
                return stationData.has("aqIndex");
            case PM10:
                return stationData.has("values") && stationData.getJSONObject("values").has("PM10");
            case PM2_5:
                return stationData.has("values") && stationData.getJSONObject("values").has("PM2.5");
            default:
                return false;
        }
    }


    @Override
    public double get(WeatherUpdateDataKey data) {
        try {
            switch (data) {
                case AIR_QUALITY_INDEX:
                    return stationData.getDouble("aqIndex");
                case PM10:
                    return stationData.getJSONObject("values").getDouble("PM10");
                case PM2_5:
                    return stationData.getJSONObject("values").getDouble("PM2.5");
                default:
                    throw new InvalidParameterException("No such data int this update");
            }
        } catch (JSONException e) {
            throw new IllegalArgumentException("Has no data");
        }
    }


    @Override
    public String description() {
        return "Air";
    }
}
