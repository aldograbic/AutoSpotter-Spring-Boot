package com.example.AutoSpotter.classes.user;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.AutoSpotter.classes.location.City;
import com.example.AutoSpotter.classes.location.County;
import com.example.AutoSpotter.classes.location.LocationRepository;

public class UserRowMapper implements RowMapper<User> {

    private final LocationRepository locationRepository;
    private final RoleRepository roleRepository;

    public UserRowMapper(LocationRepository locationRepository, RoleRepository roleRepository) {
        this.locationRepository = locationRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setCompanyName(rs.getString("company_name"));
        user.setCompanyOIB(rs.getString("company_oib"));
        user.setAddress(rs.getString("address"));
        user.setPhoneNumber(rs.getString("phone_number"));
        user.setEmail(rs.getString("email"));
        user.setCityId(rs.getInt("city_id"));
        user.setRoleId(rs.getInt("role_id"));
        
        int cityId = rs.getInt("city_id");
        City city = locationRepository.getCityById(cityId);
        user.setCity(city);

        int countyId = city.getCountyId();
        County county = locationRepository.getCountyById(countyId);
        user.setCounty(county);

        int roleId = rs.getInt("role_id");
        Role role = roleRepository.getRoleById(roleId);
        user.setRole(role);

        return user;
    }
}