package com.example.AutoSpotter.classes.location;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    public int getCityIdByName(String cityName) {
        String sql = "SELECT id FROM cities WHERE city_name = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, cityName);
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

    @Override
    public List<String> getAllCities() {
        String sql = "SELECT city_name FROM cities";
        return jdbcTemplate.queryForList(sql, String.class);
    }

    @Override
    public List<String> getAllCounties() {
        String sql = "SELECT county_name FROM counties";
        return jdbcTemplate.queryForList(sql, String.class);
    }

    @Override
    public Map<String, List<String>> getCitiesByCounty() {
        String sql = "SELECT c.county_name, ct.city_name " +
                    "FROM cities ct " +
                    "INNER JOIN counties c ON ct.county_id = c.id " +
                    "ORDER BY c.county_name";

        return jdbcTemplate.query(sql, (rs) -> {
            Map<String, List<String>> citiesByCounty = new LinkedHashMap<>();

            while (rs.next()) {
                String countyName = rs.getString("county_name");
                String cityName = rs.getString("city_name");

                if (!citiesByCounty.containsKey(countyName)) {
                    citiesByCounty.put(countyName, new ArrayList<>());
                }

                citiesByCounty.get(countyName).add(cityName);
            }

            return citiesByCounty;
        });
    }
}