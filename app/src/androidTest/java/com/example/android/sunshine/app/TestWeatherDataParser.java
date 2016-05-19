package com.example.android.sunshine.app;

import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;
/**
 * Created by fst on 2016-05-19.
 */
public class TestWeatherDataParser {

    @Test
    public void testFetchWeatherData() throws JSONException{
        String WEATHER_DATA_LODZ_MAY_19 = "{\"city\":{\"id\":3093133,\"name\":\"Lodz\",\"coord\":{\"lon\":19.466669,\"lat\":51.75},\"country\":\"PL\",\"population\":0},\"cod\":\"200\",\"message\":0.1396,\"cnt\":7,\"list\":[{\"dt\":1463652000,\"temp\":{\"day\":18.54,\"min\":13.61,\"max\":20.15,\"night\":13.61,\"eve\":20.15,\"morn\":15.15},\"pressure\":1013.87,\"humidity\":92,\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"scattered clouds\",\"icon\":\"03d\"}],\"speed\":3.1,\"deg\":154,\"clouds\":32},{\"dt\":1463738400,\"temp\":{\"day\":20.03,\"min\":9.6,\"max\":21.39,\"night\":15.68,\"eve\":21.21,\"morn\":9.6},\"pressure\":1016.08,\"humidity\":85,\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"speed\":2.06,\"deg\":326,\"clouds\":0,\"rain\":0.7},{\"dt\":1463824800,\"temp\":{\"day\":15.12,\"min\":12.02,\"max\":17.89,\"night\":12.02,\"eve\":17.89,\"morn\":12.15},\"pressure\":1015.68,\"humidity\":92,\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"speed\":5.57,\"deg\":303,\"clouds\":48,\"rain\":1.69},{\"dt\":1463911200,\"temp\":{\"day\":16.91,\"min\":7.36,\"max\":20.26,\"night\":13.86,\"eve\":20.26,\"morn\":7.36},\"pressure\":1017.38,\"humidity\":86,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"speed\":2.21,\"deg\":266,\"clouds\":0},{\"dt\":1463997600,\"temp\":{\"day\":24.84,\"min\":17.32,\"max\":24.84,\"night\":17.32,\"eve\":23.01,\"morn\":20.26},\"pressure\":1014.15,\"humidity\":0,\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"speed\":1.1,\"deg\":261,\"clouds\":61},{\"dt\":1464084000,\"temp\":{\"day\":19.28,\"min\":14.82,\"max\":19.28,\"night\":14.82,\"eve\":18.77,\"morn\":16.2},\"pressure\":1016.85,\"humidity\":0,\"weather\":[{\"id\":501,\"main\":\"Rain\",\"description\":\"moderate rain\",\"icon\":\"10d\"}],\"speed\":5.45,\"deg\":342,\"clouds\":36,\"rain\":4.43},{\"dt\":1464170400,\"temp\":{\"day\":19.22,\"min\":15.14,\"max\":19.22,\"night\":15.14,\"eve\":18.87,\"morn\":16.75},\"pressure\":1012.96,\"humidity\":0,\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"speed\":1.69,\"deg\":322,\"clouds\":80,\"rain\":2.96}]}";
        int dayIndex = 0;
        Double expectedMaxTemp = 20.15;
        Double actualMaxTemp = WeatherDataParser.getMaxTemperatureForDay(WEATHER_DATA_LODZ_MAY_19, dayIndex);
        Assert.assertEquals(actualMaxTemp, expectedMaxTemp);
    }
}
