package com.kahan.david.weatherapp.ui.city;

import android.content.Context;
import android.net.ConnectivityManager;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.kahan.david.weatherapp.app.Constants;
import com.kahan.david.weatherapp.app.StringUtils;
import com.kahan.david.weatherapp.model.CityWeather;
import com.kahan.david.weatherapp.network.OpenWeatherMapApi;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by david on 29/07/2017.
 */
public class CityWeatherPresenterImpl implements ICityWeatherPresenter {

    private CityWeatherView view;
    private boolean showCelsius = true;
    private double temp;
    private double minTmp;
    private double maxTmp;

    @Override
    public void setView(CityWeatherView view) {
        this.view = view;
    }

    @Override
    public void getCityWeatherByID(Context context, String cityId) {
        view.showLoading();

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting()){
            Converter.Factory converter = GsonConverterFactory.create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(converter)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

            OpenWeatherMapApi openWeatherMapApi = retrofit.create(OpenWeatherMapApi.class);

            openWeatherMapApi.getCityItem(cityId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(cityWeather -> {
                        if (cityWeather != null) {
                            String name = StringUtils.stripPrefix(cityWeather.getName());
                            String description = StringUtils.stripPrefix(cityWeather.getWeather().get(0).getDescription());
                            temp = cityWeather.getMain().getTemp();
                            minTmp = cityWeather.getMain().getTemp_min();
                            maxTmp = cityWeather.getMain().getTemp_max();
                            recalculateValues();
                            String icon = cityWeather.getWeather().get(0).getIcon();
                            String iconUrl = Constants.OPEN_WEATHER_IMAGE_BASE_URL + icon + ".png";
                            view.showCityWeather(name, description, iconUrl);
                        } else {
                            showError();
                        }

                    });
        } else {
            showError();
        }
        view.hideLoading();
//        openWeatherMapApi.getCityItem(cityId).enqueue(new Callback<CityWeather>() {
//            @Override
//            public void onResponse(Call<CityWeather> call, Response<CityWeather> response) {
//                if (response.isSuccessful()){
//                    cityWeather = response.body();
//                    String name = StringUtils.stripPrefix(cityWeather.getName());
//                    String description = StringUtils.stripPrefix(cityWeather.getWeather().get(0).getDescription());
//                    String temperature = "Currently " + String.valueOf(cityWeather.getMain().getTemp()) + " \u2103";
//                    String lowHighTmp = String.valueOf(cityWeather.getMain().getTemp_min()) + "\u00B0 - " + String.valueOf(cityWeather.getMain().getTemp_max()) + "\u00B0";
//                    String icon = cityWeather.getWeather().get(0).getIcon();
//                    String iconUrl = Constants.OPEN_WEATHER_IMAGE_BASE_URL + icon + ".png";
//                    view.showCityWeather(name, description, temperature, lowHighTmp, iconUrl);
//                } else {
//                    showError();
//                }
//                view.hideLoading();
//            }
//
//            @Override
//            public void onFailure(Call<CityWeather> call, Throwable t) {
//                showError();
//                view.hideLoading();
//            }
//        });
    }

    @Override
    public void changePresentation() {
        showCelsius = !showCelsius;
        recalculateValues();
    }

    private void recalculateValues() {
        String tempSign;
        double tmpTemp, tmpMinTmp, tmpMaxTmp;
        if (showCelsius) {
            tmpTemp = temp;
            tmpMinTmp = minTmp;
            tmpMaxTmp = maxTmp;
            tempSign = " \u2103";
        } else {
            tmpTemp = convertCelciusToFahrenheit((float) temp);
            tmpMinTmp = convertCelciusToFahrenheit((float) minTmp);
            tmpMaxTmp = convertCelciusToFahrenheit((float) maxTmp);
            tempSign = " \u2109";
        }
        String temperature = "Currently " + String.valueOf(String.format("%.2f", tmpTemp)) + tempSign;
        String lowHighTmp = String.valueOf(String.format("%.2f", tmpMinTmp)) + "\u00B0 -  " + String.valueOf(String.format("%.2f", tmpMaxTmp)) + "\u00B0";
        view.setTemperatureValues(temperature, lowHighTmp);
    }

    private void showError() {
        view.showErrorMessage();
    }

    // Converts to fahrenheit
    private float convertCelciusToFahrenheit(float celsius) {
        return ((celsius * 9) / 5) + 32;
    }
}
