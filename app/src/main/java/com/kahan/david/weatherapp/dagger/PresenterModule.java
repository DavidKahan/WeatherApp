package com.kahan.david.weatherapp.dagger;

import com.kahan.david.weatherapp.ui.cities.ICitiesPresenter;
import com.kahan.david.weatherapp.ui.cities.CitiesPresenterImpl;
import com.kahan.david.weatherapp.ui.city.ICityWeatherPresenter;
import com.kahan.david.weatherapp.ui.city.CityWeatherPresenterImpl;


import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

/**
 * Created by david on 28/07/2017.
 */
@Module
public class PresenterModule {
    @Provides
    @Singleton
    ICitiesPresenter provideCitiesPresenter() {
        return new CitiesPresenterImpl();
    }

    @Provides
    @Singleton
    ICityWeatherPresenter provideCityWeatherPresenter() {
        return new CityWeatherPresenterImpl();
    }
}
