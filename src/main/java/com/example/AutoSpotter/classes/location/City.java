package com.example.AutoSpotter.classes.location;

public class City {
    
    private int id;
    private String cityName;
    private int countyId;

    public City() {}

    public City(int id, String cityName, int countyId) {
        this.id = id;
        this.cityName = cityName;
        this.countyId = countyId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getCountyId() {
        return countyId;
    }

    public void setCountyId(int countyId) {
        this.countyId = countyId;
    }

    public boolean isEmpty() {
        return false;
    }

    @Override
    public String toString() {
        return cityName;
    }
}
