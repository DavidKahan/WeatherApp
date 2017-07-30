package com.kahan.david.weatherapp.ui.cities;

import android.content.Context;

/**
 * Created by david on 28/07/2017.
 */
public interface ICitiesPresenter {
  void setView(CitiesView view);

  void getCities(Context context);
}
