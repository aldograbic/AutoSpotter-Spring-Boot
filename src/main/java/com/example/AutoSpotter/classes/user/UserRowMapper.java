package com.example.AutoSpotter.classes.user;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.AutoSpotter.classes.location.City;
import com.example.AutoSpotter.classes.location.County;
import com.example.AutoSpotter.classes.location.LocationRepository;

public class UserRowMapper implements RowMapper<User> {

    private final LocationRepository locationRepository;

    public UserRowMapper(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setDisplayUsername(rs.getString("display_username"));
        user.setPassword(rs.getString("password"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setCompanyName(rs.getString("company_name"));
        user.setCompanyOIB(rs.getString("company_oib"));
        user.setAddress(rs.getString("address"));
        user.setPhoneNumber(rs.getString("phone_number"));
        user.setEmail(rs.getString("email"));
        user.setProfileImage(rs.getString("profile_image"));
        user.setEmailVerified(rs.getBoolean("email_verified"));
        user.setConfirmationToken(rs.getString("confirmation_token"));
        user.setCityId(rs.getInt("city_id"));
        
        int cityId = rs.getInt("city_id");
        City city = locationRepository.getCityById(cityId);
        if (city != null) {
            user.setCity(city);
            int countyId = city.getCountyId();
            County county = locationRepository.getCountyById(countyId);
    
            if (county != null) {
                user.setCounty(county);
            } else {
                // Handle the case where the county is not found
                // You can log a message or take appropriate action
            }
        } else {
            // Handle the case where the city is not found
            // You can log a message or take appropriate action
        }

        return user;
    }
}