package com.github.saphir2357.po2016.weather.datasources;

public interface IWeatherUpdate {
    boolean hasData(WeatherUpdateDataKey data);
    double get(WeatherUpdateDataKey data);
    String description();
}
