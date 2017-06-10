package com.github.saphir2357.po2016.weather.data;

public interface IWeatherUpdate {
    boolean hasData(WeatherUpdateDataKey data);
    double get(WeatherUpdateDataKey data);
    String description();
}
