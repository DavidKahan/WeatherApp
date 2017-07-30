package com.kahan.david.weatherapp.network;

import com.kahan.david.weatherapp.app.Constants;
import com.kahan.david.weatherapp.model.CityWeather;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by david on 28/07/2017.
 */

public interface OpenWeatherMapApi {

    @GET("weather?units=metric&APPID=" + Constants.OPEN_WEATHER_MAP_APP_ID)
    Observable<CityWeather> getCityItem(@Query("id") String cityId);

}
