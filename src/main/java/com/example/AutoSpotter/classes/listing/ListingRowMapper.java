package com.example.AutoSpotter.classes.listing;

import org.springframework.jdbc.core.RowMapper;
import com.example.AutoSpotter.classes.vehicle.Vehicle;
import com.example.AutoSpotter.classes.vehicle.VehicleRepository;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ListingRowMapper implements RowMapper<Listing> {
    private final VehicleRepository vehicleRepository;

    public ListingRowMapper(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public Listing mapRow(ResultSet rs, int rowNum) throws SQLException {
        Listing listing = new Listing();
        listing.setId(rs.getInt("id"));
        listing.setListingDescription(rs.getString("listing_description"));
        listing.setListingPrice(rs.getBigDecimal("listing_price"));
        listing.setVehicleId(rs.getInt("vehicle_id"));
        listing.setUserId(rs.getInt("user_id"));

        int vehicleId = rs.getInt("vehicle_id");
        Vehicle vehicle = vehicleRepository.getVehicleById(vehicleId);
        listing.setVehicle(vehicle);

        return listing;
    }
}
