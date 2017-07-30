package com.kahan.david.weatherapp.dagger;

import com.kahan.david.weatherapp.ui.cities.CitiesActivity;
import com.kahan.david.weatherapp.ui.city.CityWeatherActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by david on 28/07/2017.
 */
@Singleton
@Component(modules = {AppModule.class, PresenterModule.class})
public interface AppComponent {

    void inject(CitiesActivity target);

    void inject(CityWeatherActivity target);

}
