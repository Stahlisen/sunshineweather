package com.example.android.sunshine.app;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.sunshine.app.Model.WeatherObject;

import java.util.ArrayList;

/**
 * Created by fst on 2016-05-24.
 */
public class ForecastAdapter extends ArrayAdapter<WeatherObject> {
    Context mContext;

    public ForecastAdapter(Context context, ArrayList<WeatherObject> weatherObjects) {
        super(context, 0, weatherObjects);
        mContext = context;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public WeatherObject getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        WeatherObject weatherObject = getItem(position);

        if (convertView == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(R.layout.list_item_forecast, parent, false);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.list_item_forecast_text);
        textView.setText(weatherObject.getWeatherDate() + " - " + weatherObject.getTemperature("min") + "/" + weatherObject.getTemperature("max"));
        return convertView;
    }
}
