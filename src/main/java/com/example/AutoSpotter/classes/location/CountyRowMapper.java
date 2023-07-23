package com.example.AutoSpotter.classes.location;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CountyRowMapper implements RowMapper<County> {

    @Override
    public County mapRow(ResultSet rs, int rowNum) throws SQLException {
        County county = new County();
        county.setId(rs.getInt("id"));
        county.setCountyName(rs.getString("county_name"));
        return county;
    }
}