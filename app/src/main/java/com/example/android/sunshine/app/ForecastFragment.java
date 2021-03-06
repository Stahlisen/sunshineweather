package com.example.android.sunshine.app;


import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.android.sunshine.app.Model.WeatherObject;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A placeholder fragment containing a simple view.
 */
public class ForecastFragment extends Fragment {

    private ArrayAdapter<String> mForecastAdapter;

    public ForecastFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set up to handle menu events
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        //ArrayList<WeatherObject> weaterDataList = new FetchWeatherTask().execute("94043");
        String[] forecastDummyData = {
                "Today - Sunny - 12 Degrees",
                "Tomorrow - Cloudy - 9 Degrees",
                "Friday - Sunny - 13 Degrees",
                "Today - Sunny - 12 Degrees",
                "Tomorrow - Cloudy - 9 Degrees",
                "Friday - Sunny - 13 Degrees",
                "Today - Sunny - 12 Degrees",
                "Tomorrow - Cloudy - 9 Degrees",
                "Friday - Sunny - 13 Degrees",
                "Today - Sunny - 12 Degrees",
                "Tomorrow - Cloudy - 9 Degrees",
                "Friday - Sunny - 13 Degrees",
                "Today - Sunny - 12 Degrees",
                "Tomorrow - Cloudy - 9 Degrees",
                "Friday - Sunny - 13 Degrees",
                "Today - Sunny - 12 Degrees",
                "Tomorrow - Cloudy - 9 Degrees",
                "Friday - Sunny - 13 Degrees",
                "Today - Sunny - 12 Degrees",
                "Tomorrow - Cloudy - 9 Degrees",
                "Friday - Sunny - 13 Degrees"
        };
        ArrayList<String> listOfData = new ArrayList<String>(Arrays.asList(forecastDummyData));
        mForecastAdapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item_forecast, R.id.list_item_forecast_text);

        ListView forecastListView = (ListView) rootView.findViewById(R.id.listview_forecast);
        forecastListView.setAdapter(mForecastAdapter);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.forecastfragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            new FetchWeatherTask().execute("94043");
        }
        return super.onOptionsItemSelected(item);
    }

    private class FetchWeatherTask extends AsyncTask<String, Void, ArrayList<WeatherObject>> {
        String logName = getClass().getName();

        @Override
        protected ArrayList<WeatherObject> doInBackground(String... params) {

            if (params.length == 0) {
                return null;
            }
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String forecastJsonStr = null;

            String format = "json";
            String units = "metric";
            int numOfDays = 7;

            ArrayList<WeatherObject> forecasts = null;

            try {
                final String FORECAST_BASE_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?";
                final String QUERY_PARAM = "q";
                final String FORMAT_PARAM = "mode";
                final String UNITS_PARAM = "units";
                final String DAYS_PARAM = "cnt";
                final String APPID_PARAM = "APPID";

                Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                        .appendQueryParameter(QUERY_PARAM, params[0])
                        .appendQueryParameter(FORMAT_PARAM, format)
                        .appendQueryParameter(UNITS_PARAM, units)
                        .appendQueryParameter(DAYS_PARAM, Integer.toString(numOfDays))
                        .appendQueryParameter(APPID_PARAM, "f680bbd4b65b83aca0182de93b82c56d")
                        .build();

                URL url = new URL(builtUri.toString());
                //Create the request
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                //Read the input stream into a string
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer stringBuffer = new StringBuffer();

                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuffer.append(line + "\n");
                }

                if (stringBuffer.length() == 0) {
                    return null;
                }
                forecastJsonStr = stringBuffer.toString();
            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);
            } finally {
                Log.v("FetchWeatherTask", "ForecastJsonString = " + forecastJsonStr);
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        Log.e("PlaceholderFragment", "Error ", e);
                    }
                }
                try {
                    forecasts = WeatherDataParser.getWeatherData(forecastJsonStr);

                    for (WeatherObject wo : forecasts) {
                        Log.v("FetchWeatherTask", wo.getWeatherDate());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return forecasts;

        }

        @Override
        protected void onPostExecute(ArrayList<WeatherObject> weatherObjects) {
            if (weatherObjects != null) {
                mForecastAdapter.clear();
                for (WeatherObject wo : weatherObjects){
                    mForecastAdapter.add(wo.getWeatherDate() + " - " + wo.getWeather().get("main"));
                }
            }
        }
    }
}

