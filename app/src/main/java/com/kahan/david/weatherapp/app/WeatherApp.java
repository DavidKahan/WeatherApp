package com.kahan.david.weatherapp.app;

import android.app.Application;

import com.kahan.david.weatherapp.dagger.AppComponent;
import com.kahan.david.weatherapp.dagger.AppModule;
import com.kahan.david.weatherapp.dagger.DaggerAppComponent;
/**
 * Created by david on 28/07/2017.
 */
public class WeatherApp extends Application {

  private AppComponent appComponent;

  public AppComponent getAppComponent() {
    return appComponent;
  }
  @Override
  public void onCreate() {
    super.onCreate();
    appComponent = initDagger(this);
  }

  protected AppComponent initDagger(WeatherApp application) {
    return DaggerAppComponent.builder()
            .appModule(new AppModule(application))
            .build();
  }
}
