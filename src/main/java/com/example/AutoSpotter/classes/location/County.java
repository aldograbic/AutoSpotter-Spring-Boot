package com.example.AutoSpotter.classes.location;

public class County {

    private int id;
    private String countyName;
    private int countryId;

    public County() {}

    public County(int id, String countyName, int countryId) {
        this.id = id;
        this.countyName = countyName;
        this.countryId = countryId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }
}
