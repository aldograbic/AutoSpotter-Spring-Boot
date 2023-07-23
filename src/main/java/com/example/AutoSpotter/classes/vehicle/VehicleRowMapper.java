package com.example.AutoSpotter.classes.vehicle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.example.AutoSpotter.classes.location.City;
import com.example.AutoSpotter.classes.location.County;
import com.example.AutoSpotter.classes.location.LocationRepository;

public class VehicleRowMapper implements RowMapper<Vehicle> {

    private final LocationRepository locationRepository;
    private final VehicleRepository vehicleRepository;

    public VehicleRowMapper(LocationRepository locationRepository, VehicleRepository vehicleRepository) {
        this.locationRepository = locationRepository;
        this.vehicleRepository = vehicleRepository;
    }

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
        vehicle.setVehicleSafetyFeaturesId(rs.getInt("vehicle_safety_features_id"));
        vehicle.setVehicleExtrasId(rs.getInt("vehicle_extras_id"));

        int cityId = rs.getInt("city_id");
        City city = locationRepository.getCityById(cityId);
        vehicle.setCity(city);

        int countyId = city.getCountyId();
        County county = locationRepository.getCountyById(countyId);
        vehicle.setCounty(county);

        int safetyFeaturesId = rs.getInt("vehicle_safety_features_id");
        List<SafetyFeature> safetyFeatures = vehicleRepository.getVehicleSafetyFeatures(safetyFeaturesId);
        vehicle.setSafetyFeatures(safetyFeatures);

        int extrasId = rs.getInt("vehicle_extras_id");
        List<VehicleExtra> extras = vehicleRepository.getVehicleExtras(extrasId);
        vehicle.setExtras(extras);

        return vehicle;
    }
}