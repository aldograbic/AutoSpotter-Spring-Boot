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
        vehicle.setBodyType(rs.getString("body_type"));
        vehicle.setColor(rs.getString("color"));
        vehicle.setRegistered(rs.getDate("registered"));
        vehicle.setMileage(rs.getInt("mileage"));
        vehicle.setState(rs.getString("state"));
        vehicle.setYear(rs.getInt("year"));
        vehicle.setNumberOfWheels(rs.getInt("number_of_wheels"));
        vehicle.setMaximumAllowableWeight(rs.getDouble("maximum_allowable_weight"));
        vehicle.setEngineType(rs.getString("engine_type"));
        vehicle.setEngineDisplacement(rs.getDouble("engine_displacement"));
        vehicle.setEnginePower(rs.getInt("engine_power"));
        vehicle.setFuelConsumption(rs.getDouble("fuel_consumption"));
        vehicle.setTransmission(rs.getString("transmission"));
        vehicle.setDriveTrain(rs.getString("drive_train"));
        vehicle.setBatteryCapacity(rs.getDouble("battery_capacity"));
        vehicle.setChargingTime(rs.getDouble("charging_time"));
        vehicle.setVehicleRange(rs.getDouble("vehicle_range"));
        vehicle.setCityId(rs.getInt("city_id"));
        vehicle.setVehicleTypeId(rs.getInt("vehicle_type_id"));
        return vehicle;
    }
}