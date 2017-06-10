package com.github.saphir2357.po2016.weather.datasources;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class GIOSStation {
    private static final String SENSOR_LIST_ENDPOINT = "http://api.gios.gov.pl/pjp-api/rest/station/sensors/";
    private static final String STATIONS_LIST_ENDPOINT = "http://api.gios.gov.pl/pjp-api/rest/station/findAll";


    public static List<GIOSStation> stationsInCity(String cityName) throws IOException {
        List<GIOSStation> list = new LinkedList<>();
        JSONArray allStations = new JSONArray(new JSONTokener(new URL(STATIONS_LIST_ENDPOINT).openStream()));

        for (Object o : allStations) {
            if (!(o instanceof JSONObject)) {
                Logger.getGlobal().warning("Unexpected element in sensors array: " + o);
                continue;
            }

            JSONObject stationData = (JSONObject)o;
            try {
                if (stationData.getJSONObject("city").getString("name").equals(cityName))
                    list.add(new GIOSStation(stationData.getInt("id"), stationData.getString("stationName")));
            } catch (NullPointerException | JSONException e) {
                Logger.getGlobal().warning("Failed parsing JSON station object: " + stationData);
            }
        }

        return list;
    }


    private String name;
    private int id;
    private List<String> paramNames;


    public GIOSStation(int id, String name) {
        this.id = id;
        this.name = name;
    }


    public String getName() {
        return name;
    }


    @Override
    public String toString() {
        return "[" + id + "] " + getName();
    }


    public int getId() {
        return id;
    }


    public List<String> getParamNames() throws IOException {
        if (paramNames == null) {
            paramNames = new LinkedList<>();
            URL infoURL = new URL(SENSOR_LIST_ENDPOINT + id);
            JSONArray sensors = new JSONArray(new JSONTokener(infoURL.openStream()));

            for (Object o : sensors) {
                if (!(o instanceof JSONObject)) {
                    Logger.getGlobal().warning("Unexpected element in sensors array: " + o);
                    continue;
                }

                JSONObject sensorData = (JSONObject)o;
                try {
                    paramNames.add(sensorData.getJSONObject("param").getString("paramName"));
                } catch (JSONException e) {
                    Logger.getGlobal().warning("Unexpected JSONObject contants. Exception: " + e);
                }
            }
        }

        return paramNames;
    }
}
