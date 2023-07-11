package com.example.AutoSpotter.classes.location;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcLocationRepository implements LocationRepository{

    private final JdbcTemplate jdbcTemplate;

    public JdbcLocationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public City getCityById(int id) {
        String sql = "SELECT id, city_name, county_id FROM cities WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            City city = new City();
            city.setId(rs.getInt("id"));
            city.setCityName(rs.getString("city_name"));
            city.setCountyId(rs.getInt("county_id"));
            return city;
        }, id);
    }

    @Override
    public County getCountyById(int id) {
        String sql = "SELECT id, county_name, country_id FROM counties WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            County county = new County();
            county.setId(rs.getInt("id"));
            county.setCountyName(rs.getString("county_name"));
            county.setCountryId(rs.getInt("country_id"));
            return county;
        }, id);
    }
}