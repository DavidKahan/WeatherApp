package com.kahan.david.weatherapp.model;

/**
 * Created by david on 28/07/2017.
 */
public class CityItem {

    private String id;

    private String name;

    public CityItem(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
