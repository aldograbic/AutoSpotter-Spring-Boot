package com.example.AutoSpotter.classes.user;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class UserRowMapper implements RowMapper<User> {

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
        return user;
    }
}