package com.kahan.david.weatherapp.ui.cities;

import com.kahan.david.weatherapp.model.CityItem;

import java.util.List;

/**
 * Created by david on 28/07/2017.
 */

public interface CitiesView {

    void showLoading();

    void hideLoading();

    void showCities(List<CityItem> cityItemList);

    void showErrorMessage();

    void launchCityWeatherDetail(CityItem cityItem);
}
