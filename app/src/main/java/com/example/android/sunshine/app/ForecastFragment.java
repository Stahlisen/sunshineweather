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

    static final String OPENWEATHERMAP_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?q=11262,%20Stockholm&mode=json&units=metric&cnt=7&appid=";
    static final String OPENWEATHERMAP_API_KEY = "f680bbd4b65b83aca0182de93b82c56d";

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
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item_forecast, R.id.list_item_forecast_text, listOfData);

        ListView forecastListView = (ListView) rootView.findViewById(R.id.listview_forecast);
        forecastListView.setAdapter(arrayAdapter);
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
            new FetchWeatherTask().execute();
        }
        return super.onOptionsItemSelected(item);
    }
}

 class FetchWeatherTask extends AsyncTask<String, Void, String> {
     String logName = getClass().getName();

    @Override
    protected String doInBackground(String... params) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String forecastJsonStr = null;

        try {
            Uri.Builder uriBuilder = new Uri.Builder();


            //Construct the URL for the query
            URL url = new URL(ForecastFragment.OPENWEATHERMAP_URL + ForecastFragment.OPENWEATHERMAP_API_KEY);

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
            Log.v(logName, "ForecastJsonString = " + forecastJsonStr);
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
        }
        return forecastJsonStr;
    }
}