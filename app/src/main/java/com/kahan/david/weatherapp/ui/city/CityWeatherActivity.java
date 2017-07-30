package com.kahan.david.weatherapp.ui.city;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kahan.david.weatherapp.R;
import com.kahan.david.weatherapp.app.WeatherApp;
import com.kahan.david.weatherapp.model.CityItem;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by david on 29/07/2017.
 */
public class CityWeatherActivity extends Activity implements CityWeatherView {

    @BindView(R.id.activity_city_progressBar)
    ProgressBar progressBar;

    @BindView(R.id.weather_layout)
    LinearLayout weatherLayout;

    @BindView(R.id.city_name)
    TextView cityName;

    @BindView(R.id.weather_description)
    TextView weatherDescription;

    @BindView(R.id.current_temperature)
    TextView currentTemperature;

    @BindView(R.id.current_weather_icon)
    ImageView currentWeatherIcon;

    @BindView(R.id.low_high_temperature)
    TextView lowHighTemperature;

    @BindView(R.id.celsius_fahrenheit_btn)
    Button celsiusFahrenheitBtn;

    @Inject
    ICityWeatherPresenter presenter;

    public static final String EXTRA_CITY_ID = "EXTRA_CITY_ID";

    public static void launch(Context context, CityItem cityItem) {
        Intent intent = new Intent(context, CityWeatherActivity.class);
        intent.putExtra(EXTRA_CITY_ID, cityItem.getId());
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_weather);

        ((WeatherApp) getApplication()).getAppComponent().inject(this);
        ButterKnife.bind(this);

        String foodId = getIntent().getStringExtra(EXTRA_CITY_ID);

        presenter.setView(this);
        presenter.getCityWeatherByID(this, foodId);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    //region CityWeatherView
    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        weatherLayout.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
        weatherLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showCityWeather(String name, String description, String iconUrl) {
        setTitle(name);
        cityName.setText(name);
        weatherDescription.setText(description);
        Picasso.with(this).load(iconUrl)
                .resize(200, 200)
                .centerCrop()
                .into(currentWeatherIcon);
        celsiusFahrenheitBtn.setOnClickListener(v -> presenter.changePresentation());
    }

    @Override
    public void showErrorMessage() {
        Toast.makeText(this, R.string.CityWeatherError, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setTemperatureValues(String temperature, String lowHighTmp) {
        currentTemperature.setText(temperature);
        lowHighTemperature.setText(lowHighTmp);
    }
    //endregion


}
