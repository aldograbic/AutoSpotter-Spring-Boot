package com.example.AutoSpotter.classes.location;

import java.util.List;
import java.util.Map;

public interface LocationRepository {

    City getCityById(int cityId);
    
    County getCountyById(int countyId);

    int getCityIdByName(String cityName);

    List<City> getAllCities();

    List<County> getAllCounties();

    Map<String, List<String>> getCitiesByCounty();
}