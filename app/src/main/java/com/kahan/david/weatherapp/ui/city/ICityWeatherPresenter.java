package com.kahan.david.weatherapp.ui.city;

import android.content.Context;

/**
 * Created by david on 29/07/2017.
 */
public interface ICityWeatherPresenter {
    void setView(CityWeatherView view);

    void getCityWeatherByID(Context context, String foodId);

    void changePresentation();
}
