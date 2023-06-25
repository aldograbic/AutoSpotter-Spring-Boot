package com.example.AutoSpotter.classes.vehicle;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class VehicleRowMapper implements RowMapper<Vehicle> {

    @Override
    public Vehicle mapRow(ResultSet rs, int rowNum) throws SQLException {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(rs.getInt("id"));
        vehicle.setManufacturer(rs.getString("manufacturer"));
        vehicle.setModel(rs.getString("model"));
        vehicle.setMileage(rs.getInt("mileage"));
        vehicle.setState(rs.getString("state"));
        vehicle.setYear(rs.getInt("year"));
        vehicle.setVehicleTypeId(rs.getInt("vehicle_type_id"));
        return vehicle;
    }
}