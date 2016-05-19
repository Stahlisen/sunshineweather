package com.example.android.sunshine.app;

import android.text.format.Time;
import android.util.Log;

import com.example.android.sunshine.app.Model.WeatherObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by fst on 2016-05-19.
 */
public class WeatherDataParser {



    public static double getMaxTemperatureForDay(String weatherJsonStr, int dayIndex) throws JSONException {

        JSONObject response = new JSONObject(weatherJsonStr);
        JSONArray listOfDays = response.getJSONArray("list");
        JSONObject jsonDayObject = listOfDays.getJSONObject(dayIndex);
        JSONObject tempObject = jsonDayObject.getJSONObject("temp");
        return tempObject.getDouble("max");
    }

    public static ArrayList<WeatherObject> getWeatherData(String weatherJsonStr) throws JSONException {

        final String OWM_LIST = "list";
        final String OWM_WEATHER = "weather";
        final String OWM_TEMP = "temp";
        final String OWM_DAY = "day";
        final String OWM_MIN = "min";
        final String OWM_MAX = "max";
        final String OWM_NIGHT = "night";
        final String OWM_EVE = "eve";
        final String OWM_MORN = "morn";
        final String OWM_DESC = "main";
        final String OWM_HUMIDITY = "humidity";
        final String OWM_PRESSURE = "pressure";

        JSONObject forecastJson = new JSONObject(weatherJsonStr);
        JSONArray weatherArray = forecastJson.getJSONArray(OWM_LIST);

        Time dayTime = new Time();
        dayTime.setToNow();

        int julianStartDay = Time.getJulianDay(System.currentTimeMillis(), dayTime.gmtoff);

        dayTime = new Time();

        ArrayList<WeatherObject> forecasts = new ArrayList<WeatherObject>();

        for (int i = 0; i < weatherArray.length(); i++) {
            JSONObject dayForecast = weatherArray.getJSONObject(i);

            JSONObject weather = dayForecast.getJSONArray(OWM_WEATHER).getJSONObject(0);
            JSONObject temperature = dayForecast.getJSONObject(OWM_TEMP);

            WeatherObject weatherObject = new WeatherObject();
            weatherObject.setWeather(OWM_DESC, weather.getString(OWM_DESC));
            weatherObject.setTemperature(OWM_DAY, temperature.getDouble(OWM_DAY));
            weatherObject.setTemperature(OWM_MIN, temperature.getDouble(OWM_MIN));
            weatherObject.setTemperature(OWM_MAX, temperature.getDouble(OWM_MAX));
            weatherObject.setTemperature(OWM_NIGHT, temperature.getDouble(OWM_NIGHT));
            weatherObject.setTemperature(OWM_EVE, temperature.getDouble(OWM_EVE));
            weatherObject.setTemperature(OWM_MORN, temperature.getDouble(OWM_MORN));

            weatherObject.setHumidity(dayForecast.getDouble(OWM_PRESSURE));
            weatherObject.setHumidity(dayForecast.getDouble(OWM_HUMIDITY));

            long dateTime;
            dateTime = dayTime.setJulianDay(julianStartDay+i);
            SimpleDateFormat shortenedDateFormat = new SimpleDateFormat("EEE MMM dd");
            String dayDateString = shortenedDateFormat.format(dateTime);
            weatherObject.setWeatherDate(dayDateString);

            forecasts.add(weatherObject);

        }
        return forecasts;
    }

}
