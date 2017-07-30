package com.kahan.david.weatherapp.ui.cities;

import android.content.Context;

import com.kahan.david.weatherapp.model.CityItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by david on 28/07/2017.
 */
public class CitiesPresenterImpl implements ICitiesPresenter {

    private CitiesView view;

    @Override
    public void setView(CitiesView view) {
        this.view = view;
    }

    @Override
    public void getCities(Context context) {
        view.showLoading();
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset(context));
            JSONArray jsonArray = obj.getJSONArray("cities");
            List<CityItem> cityList = new ArrayList<CityItem>();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jo_inside = jsonArray.getJSONObject(i);
                String city_value = jo_inside.getString("city");
                String id_value = jo_inside.getString("id");
                CityItem cityItem = new CityItem(id_value, city_value);
                cityList.add(cityItem);
            }
            cityList.sort((o1, o2) -> o1.getName().compareTo(o2.getName()));
            view.showCities(cityList);
            view.hideLoading();
        } catch (JSONException e) {
            e.printStackTrace();
            view.showErrorMessage();
            view.hideLoading();
        }
    }

    public String loadJSONFromAsset(Context context) {
        String json;
        try {
            InputStream is = context.getAssets().open("cities.txt");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}
