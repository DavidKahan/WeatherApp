package com.kahan.david.weatherapp.ui.cities;

import android.app.Activity;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.kahan.david.weatherapp.R;
import com.kahan.david.weatherapp.app.StringUtils;
import com.kahan.david.weatherapp.app.WeatherApp;
import com.kahan.david.weatherapp.model.CityItem;
import com.kahan.david.weatherapp.ui.city.CityWeatherActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by david on 28/07/2017.
 */
public class CitiesActivity extends Activity implements CitiesView {

  @BindView(R.id.activity_cities_recyclerView)
  RecyclerView citiesRecyclerView;

  @BindView(R.id.activity_cities_progressBar)
  ProgressBar progressBar;

  @Inject
  ICitiesPresenter presenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_cities);

    ((WeatherApp)getApplication()).getAppComponent().inject(this);
    ButterKnife.bind(this);

    citiesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    presenter.setView(this);
    presenter.getCities(this);
  }


  //region CitiesView
  @Override
  public void showLoading() {
    progressBar.setVisibility(View.VISIBLE);
  }

  @Override
  public void hideLoading() {
    progressBar.setVisibility(View.GONE);
  }

  @Override
  public void showCities(List<CityItem> cityItemList) {
    citiesRecyclerView.setAdapter(new CitiesAdapter(cityItemList));
    citiesRecyclerView.getAdapter().notifyDataSetChanged();
  }

  @Override
  public void showErrorMessage() {
    Toast.makeText(this, R.string.CitiesListError, Toast.LENGTH_SHORT).show();
  }

  @Override
  public void launchCityWeatherDetail(CityItem cityItem) {
    CityWeatherActivity.launch(this, cityItem);
  }
  //endregion


  class CitiesAdapter extends RecyclerView.Adapter<CityViewHolder> {

    private List<CityItem> citiesItemList;

    CitiesAdapter(List<CityItem> cityItemList) {
      this.citiesItemList = cityItemList;
    }

    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      LayoutInflater inflater = LayoutInflater.from(CitiesActivity.this);
      return new CityViewHolder(inflater.inflate(R.layout.list_item_city, parent, false));
    }

    @Override
    public void onBindViewHolder(CityViewHolder holder, int position) {
      CityItem cityItem = citiesItemList.get(position);
      holder.getCityName().setText(StringUtils.stripPrefix(cityItem.getName()));
      holder.getContainer().setOnClickListener(v -> launchCityWeatherDetail(cityItem));
    }

    @Override
    public int getItemCount() {
      return citiesItemList.size();
    }
  }
}
