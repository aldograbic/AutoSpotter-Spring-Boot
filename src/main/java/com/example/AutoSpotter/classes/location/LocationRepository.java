package com.example.AutoSpotter.classes.location;

import java.util.List;
import java.util.Map;

public interface LocationRepository {

    City getCityById(int cityId);
    
    County getCountyById(int id);

    int getCityIdByName(String cityName);

    List<String> getAllCities();

    List<String> getAllCounties();

    Map<String, List<String>> getCitiesByCounty();
}
