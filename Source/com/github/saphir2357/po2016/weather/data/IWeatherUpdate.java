package com.github.saphir2357.po2016.weather.data;

public interface IWeatherUpdate {
    boolean isReady();
    boolean hasData(WeatherUpdateDataKey data);
    double get(WeatherUpdateDataKey data);
}
