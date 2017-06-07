package com.github.saphir2357.po2016.weather.data;

public class NoDataException extends Exception {
    NoDataException(String dataName) {
        super("No data about: " + dataName);
    }
}
