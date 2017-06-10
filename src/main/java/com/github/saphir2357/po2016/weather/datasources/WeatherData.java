package com.github.saphir2357.po2016.weather.datasources;

import javafx.scene.image.Image;

public class WeatherData {
    private static final Image BROKEN_CLOUDS_IMAGE = new Image("BrokenClouds.png");
    private static final Image FOG_IMAGE = new Image("Fog.png");
    private static final Image HEAVY_RAIN_IMAGE = new Image("HeavyRain.png");
    private static final Image LIGHT_RAIN_IMAGE = new Image("LightRain.png");
    private static final Image SCATTERED_CLOUDS_IMAGE = new Image("ScatteredClouds.png");
    private static final Image SNOW_IMAGE = new Image("Snow.png");
    private static final Image SUN_AND_CLOUDS_IMAGE = new Image("SunAndCloud.png");
    private static final Image SUNNY_IMAGE = new Image("Sunny.png");
    private static final Image THUNDERSTORM_IMAGE = new Image("Thunderstorm.png");
    private static final Image NO_DATA_IMAGE = new Image("SadSun.png");

    private int precisionPower = 10;
    private int weatherCode;
    private double kelvinTemperatureMin;
    private double kelvinTemperatureMax;
    private double hPaPressure;
    private double humidity;
    private double windSpeed;
    private double windDegree;
    private double aqIndex;
    private double pm25;
    private double pm10;
    private double clouds;
    private double rain;


    public static double kelvinToCelcius(double kelvin){
        return kelvin - 273.15;
    }


    public static double celciusToKelvin(double kelvin){
        return kelvin + 273.15;
    }


    public WeatherData() {
        clearValues();
    }


    public double pm25() throws NoDataException {
        if (pm25 < 0)
            throw new NoDataException("PM2.5");
        return round(pm25);
    }


    public double pm10() throws NoDataException {
        if (pm10 < 0)
            throw new NoDataException("PM10");
        return round(pm10);
    }


    public double celciusMin() throws NoDataException {
        if (kelvinTemperatureMin < 0)
            throw new NoDataException("calcius minimal temperature");
        return round(kelvinToCelcius(kelvinTemperatureMin));
    }


    public double celciusMax() throws NoDataException {
        if (kelvinTemperatureMin < 0)
            throw new NoDataException("calcius maximal temperature");
        return round(kelvinToCelcius(kelvinTemperatureMax));
    }


    public double celcius() throws NoDataException {
        return round((celciusMin() + celciusMax()) / 2.0);
    }


    public double hPaPressure() throws NoDataException {
        if (hPaPressure < 0)
            throw new NoDataException("pressure");
        return round(hPaPressure);
    }


    public double humidityPercent() throws NoDataException {
        if (humidity < 0)
            throw new NoDataException("humidity percent");
        return round(humidity);
    }


    public double windSpeedKmh() throws NoDataException {
        if (windSpeed < 0)
            throw new NoDataException("wind speed");
        return round(windSpeed * 3.6);
    }


    public double windDirectionDegree() throws NoDataException {
        if (windDegree < 0)
            throw new NoDataException("wind direction degree");
        return round(windDegree);
    }


    public double airQualityIndex() throws NoDataException {
        if (aqIndex < 0)
            throw new NoDataException("air quality index");
        return aqIndex;
    }


    public double rainVolume3h() throws NoDataException {
        if (rain < 0)
            throw new NoDataException("rain volume in 3h");
        return round(rain);
    }


    public double cloudinessPercent() throws  NoDataException {
        if (clouds < 0)
            throw new NoDataException("cloudiness percent");
        return round(clouds);
    }


    public Image weatherImageName() {
        int code100 = weatherCode / 100;
        switch (code100) {
            case 2:
                return THUNDERSTORM_IMAGE;
            case 3:
                return LIGHT_RAIN_IMAGE;
            case 5:
                if (weatherCode < 511)
                    return LIGHT_RAIN_IMAGE;
                if (weatherCode == 511)
                    return SNOW_IMAGE;
                return HEAVY_RAIN_IMAGE;
            case 6:
                return SNOW_IMAGE;
            case 7:
                return FOG_IMAGE;
            case 8:
                if (weatherCode == 800)
                    return SUNNY_IMAGE;
                if (weatherCode == 801)
                    return SUN_AND_CLOUDS_IMAGE;
                if (weatherCode == 802)
                    return SCATTERED_CLOUDS_IMAGE;
                return BROKEN_CLOUDS_IMAGE;
        }
        return NO_DATA_IMAGE;
    }


    public void clearValues() {
        weatherCode = -1;
        kelvinTemperatureMin = -1;
        kelvinTemperatureMax = -1;
        hPaPressure = -1;
        humidity = -1;
        windSpeed = -1;
        windDegree = -1;
        clouds = -1;
        rain = -1;
        aqIndex = -1;
        pm25 = -1;
        pm10 = -1;
    }


    public void update(IWeatherUpdate update, boolean clear) {
        if (clear)
            clearValues();

        weatherCode = update.hasData(WeatherUpdateDataKey.WEATHER_CODE)
                ? (int)update.get(WeatherUpdateDataKey.WEATHER_CODE) : weatherCode;
        hPaPressure = update.hasData(WeatherUpdateDataKey.PRESSURE)
                ? update.get(WeatherUpdateDataKey.PRESSURE) : hPaPressure;
        humidity = update.hasData(WeatherUpdateDataKey.HUMIDITY)
                ? update.get(WeatherUpdateDataKey.HUMIDITY) : humidity;
        clouds = update.hasData(WeatherUpdateDataKey.CLOUDINESS_PERCENT)
                ? update.get(WeatherUpdateDataKey.CLOUDINESS_PERCENT) : clouds;
        rain = update.hasData(WeatherUpdateDataKey.RAINFALL_VOLUME)
                ? update.get(WeatherUpdateDataKey.RAINFALL_VOLUME) : rain;
        aqIndex = update.hasData(WeatherUpdateDataKey.AIR_QUALITY_INDEX)
                ? update.get(WeatherUpdateDataKey.AIR_QUALITY_INDEX) : aqIndex;
        pm25 = update.hasData(WeatherUpdateDataKey.PM2_5)
                ? update.get(WeatherUpdateDataKey.PM2_5) : pm25;
        pm10 = update.hasData(WeatherUpdateDataKey.PM10)
                ? update.get(WeatherUpdateDataKey.PM10) : pm10;

        if (update.hasData(WeatherUpdateDataKey.WIND_SPEED) && update.hasData(WeatherUpdateDataKey.WIND_DEGREE)) {
            windSpeed = update.get(WeatherUpdateDataKey.WIND_SPEED);
            windDegree = update.get(WeatherUpdateDataKey.WIND_DEGREE);
        }

        if (update.hasData(WeatherUpdateDataKey.TEMPERATURE_MAX) && update.hasData(WeatherUpdateDataKey.TEMPERATURE_MIN)) {
            kelvinTemperatureMin = update.get(WeatherUpdateDataKey.TEMPERATURE_MIN);
            kelvinTemperatureMax = update.get(WeatherUpdateDataKey.TEMPERATURE_MAX);
        } else if (update.hasData(WeatherUpdateDataKey.TEMPERATURE)) {
            kelvinTemperatureMin = kelvinTemperatureMax = update.get(WeatherUpdateDataKey.TEMPERATURE);
        }
    }


    public int precision() {
        return (int)Math.round(Math.log10(precisionPower));
    }


    public void setPrecision(int precision) {
        if (precision < 0)
            throw new IllegalArgumentException("negative precision is invalid precision");
        precisionPower = (int)Math.pow(10, precision);
    }


    private double round(double val) {
        return (double)Math.round(val * precisionPower) / (double)precisionPower;
    }
}
