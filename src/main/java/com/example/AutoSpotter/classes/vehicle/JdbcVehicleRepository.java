package com.example.AutoSpotter.classes.vehicle;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcVehicleRepository implements VehicleRepository {
    
    private final JdbcTemplate jdbcTemplate;

    public JdbcVehicleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int saveVehicle(Vehicle vehicle) {
        String sql = "INSERT INTO vehicle (manufacturer, model, body_type, color, registered, mileage, state, year, number_of_wheels, " +
                    "maximum_allowable_weight, engine_type, engine_displacement, engine_power, fuel_consumption, transmission, drive_train, battery_capacity, " +
                    "charging_time, vehicle_range, city_id, vehicle_type_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(
                sql,
                vehicle.getManufacturer(),
                vehicle.getModel(),
                vehicle.getBodyType(),
                vehicle.getColor(),
                vehicle.getRegistered(),
                vehicle.getMileage(),
                vehicle.getState(),
                vehicle.getYear(),
                vehicle.getNumberOfWheels(),
                vehicle.getMaximumAllowableWeight(),
                vehicle.getEngineType(),
                vehicle.getEngineDisplacement(),
                vehicle.getEnginePower(),
                vehicle.getFuelConsumption(),
                vehicle.getTransmission(),
                vehicle.getDriveTrain(),
                vehicle.getBatteryCapacity(),
                vehicle.getChargingTime(),
                vehicle.getVehicleRange(),
                vehicle.getCityId(),
                vehicle.getVehicleTypeId()
        );
        String idSql = "SELECT last_insert_id()";
        return jdbcTemplate.queryForObject(idSql, Integer.class);
    }

    @Override
    public int getVehicleTypeId(String vehicleType) {
        String sql = "SELECT id FROM vehicle_type WHERE name = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, vehicleType);
    }

    public int getCityIdByName(String cityName) {
        String sql = "SELECT id FROM cities WHERE city_name = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, cityName);
    }

    @Override
    public List<String> getAllVehicleTypes() {
        String sql = "SELECT name FROM vehicle_type";
        return jdbcTemplate.queryForList(sql, String.class);
    }

    @Override
    public Vehicle getVehicleById(int id) {
        String sql = "SELECT id, manufacturer, model, body_type, color, registered, mileage, state, year, number_of_wheels, maximum_allowable_weight, " +
                    "engine_type, engine_displacement, engine_power, fuel_consumption, transmission, drive_train, battery_capacity, charging_time, vehicle_range, " +
                    "city_id, vehicle_type_id FROM vehicle WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new VehicleRowMapper(), id);
    }

    @Override
    public List<String> getManufacturersByVehicleType(int vehicleTypeId) {
        String sql = "SELECT manufacturer_name FROM manufacturers WHERE vehicle_type_id = ? ";
        return jdbcTemplate.queryForList(sql, String.class, vehicleTypeId);
    }

    @Override
    public List<String> getAllBodyTypes() {
        String sql = "SELECT body_type_name FROM body_type";
        return jdbcTemplate.queryForList(sql, String.class);
    }

    @Override
    public List<String> getAllEngineTypes() {
        String sql = "SELECT engine_type_name FROM engine_type";
        return jdbcTemplate.queryForList(sql, String.class);
    }

    @Override
    public List<String> getAllTransmissionTypes() {
        String sql = "SELECT transmission_name FROM transmission_type";
        return jdbcTemplate.queryForList(sql, String.class);
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
    public List<String> getAllStates() {
        String sql = "SELECT name FROM states";
        return jdbcTemplate.queryForList(sql, String.class);
    }
}