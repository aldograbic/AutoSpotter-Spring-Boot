package com.example.AutoSpotter.classes.location;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CityRowMapper implements RowMapper<City>{

    @Override
    public City mapRow(ResultSet rs, int rowNum) throws SQLException {
        City city = new City();
        city.setId(rs.getInt("id"));
        city.setCityName(rs.getString("city_name"));
        return city;
    }
}