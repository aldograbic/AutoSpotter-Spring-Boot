package com.example.AutoSpotter.classes.location;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CityConverter implements Converter<String, City> {
    private final LocationRepository locationRepository;

    public CityConverter(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public City convert(String cityName) {
        int cityId = locationRepository.getCityIdByName(cityName);
        return new City(cityId, cityName, 0); // 0 for countyId, you may adjust it according to your data
    }
}