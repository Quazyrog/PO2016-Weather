package com.github.saphir2357.po2016.weather.data;

public class WeatherData {
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


    protected static double kelvinToCelcius(double kelvin){
        return kelvin - 273.15;
    }


    public WeatherData() {
        clearValues();
    }


    public double pm25() throws NoDataException {
        if (pm25 < 0)
            throw new NoDataException("PM2.5");
        return pm25;
    }


    public double pm10() throws NoDataException {
        if (pm10 < 0)
            throw new NoDataException("PM10");
        return pm10;
    }


    public double celciusMin() throws NoDataException {
        if (kelvinTemperatureMin < 0)
            throw new NoDataException("calcius minimal temperature");
        return kelvinToCelcius(kelvinTemperatureMin);
    }


    public double celciusMax() throws NoDataException {
        if (kelvinTemperatureMin < 0)
            throw new NoDataException("calcius maximal temperature");
        return kelvinToCelcius(kelvinTemperatureMax);
    }


    public double celcius() throws NoDataException {
        return (celciusMin() + celciusMax()) / 2.0;
    }


    public double hPaPressure() throws NoDataException {
        if (hPaPressure < 0)
            throw new NoDataException("pressure");
        return hPaPressure;
    }


    public double humidityPercent() throws NoDataException {
        if (humidity < 0)
            throw new NoDataException("humidity percent");
        return humidity;
    }


    public double windSpeedKmh() throws NoDataException {
        if (windSpeed < 0)
            throw new NoDataException("wind speed");
        return windSpeed * 3.6;
    }


    public double windDirectionDegree() throws NoDataException {
        if (windDegree < 0)
            throw new NoDataException("wind direction degree");
        return windDegree;
    }


    public double airQualityIndex() throws NoDataException {
        if (aqIndex < 0)
            throw new NoDataException("air quality index");
        return aqIndex;
    }


    public double rainVolume3h() throws NoDataException {
        if (rain < 0)
            throw new NoDataException("rain volume in 3h");
        return rain;
    }


    public double cloudinessPercent() throws  NoDataException {
        if (clouds < 0)
            throw new NoDataException("cloudiness percent");
        return clouds;
    }


    public String weatherImageName() {
        return "SadSun.png";
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
}
