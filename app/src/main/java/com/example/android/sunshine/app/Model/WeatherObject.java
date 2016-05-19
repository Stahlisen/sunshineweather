package com.example.android.sunshine.app.Model;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by fst on 2016-05-19.
 */
public class WeatherObject {
    String weatherDate;
    Double pressure;
    Double humidity;
    HashMap<String, Double> temperature = new HashMap<String, Double>();
    HashMap<String, String> weather = new HashMap<String, String>();

    public String getWeatherDate() {
        return weatherDate;
    }

    public void setWeatherDate(String weatherDate) {
        this.weatherDate = weatherDate;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public Double getTemperature(String key) {
        return temperature.get(key);
    }

    public void setTemperature(String key, Double value) {
        this.temperature.put(key, value);
    }

    public HashMap<String, String> getWeather() {
        return weather;
    }

    public void setWeather(String key, String value) {
        this.weather.put(key, value);
    }
}
