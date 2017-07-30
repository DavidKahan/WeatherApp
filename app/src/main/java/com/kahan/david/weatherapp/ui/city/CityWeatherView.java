package com.kahan.david.weatherapp.ui.city;

/**
 * Created by david on 29/07/2017.
 */
public interface CityWeatherView {
  void showLoading();

  void hideLoading();

  void showCityWeather(String name, String description,  String iconUrl);

  void showErrorMessage();

  void setTemperatureValues(String temperature, String lowHighTmp);

}
