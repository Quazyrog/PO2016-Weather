package com.github.saphir2357.po2016.weather;


public enum UpdateSource {
    METEO_WAW_PL,
    OPEN_WEATHER_MAP;


    @Override
    public String toString() {
        switch (this) {
            case METEO_WAW_PL:
                return "www.meteo.waw.pl";
            case OPEN_WEATHER_MAP:
                return "OpenWeatherMap";
        }
        return "Święty Mikołaj";
    }
}
