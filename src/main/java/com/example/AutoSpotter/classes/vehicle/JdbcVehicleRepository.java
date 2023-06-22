package com.example.AutoSpotter.classes.vehicle;

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
        String sql = "INSERT INTO vehicle (manufacturer, model, state) VALUES (?, ?, ?)";
        jdbcTemplate.update(
                sql,
                vehicle.getManufacturer(),
                vehicle.getModel(),
                vehicle.getState()
        );

        // Retrieve the generated vehicle ID
        String idSql = "SELECT last_insert_id()";
        return jdbcTemplate.queryForObject(idSql, Integer.class);
    }
}
