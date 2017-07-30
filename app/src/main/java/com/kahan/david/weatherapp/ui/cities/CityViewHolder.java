package com.kahan.david.weatherapp.ui.cities;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kahan.david.weatherapp.R;

/**
 * Created by david on 28/07/2017.
 */
public class CityViewHolder extends RecyclerView.ViewHolder {

    private ViewGroup container;
    private TextView cityName;

    CityViewHolder(View view) {
        super(view);
        container = (ViewGroup) view.findViewById(R.id.list_item_city_container);
        cityName = (TextView) view.findViewById(R.id.list_item_city_name);
    }

    public ViewGroup getContainer() {
        return container;
    }

    public TextView getCityName() {
        return cityName;
    }
}
