package com.example.AutoSpotter.classes.location;

public interface LocationRepository {

    City getCityById(int cityId);
    
    County getCountyById(int id);
}
