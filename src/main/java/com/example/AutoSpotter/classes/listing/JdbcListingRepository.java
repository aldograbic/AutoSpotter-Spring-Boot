package com.example.AutoSpotter.classes.listing;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.AutoSpotter.classes.vehicle.VehicleRepository;

@Repository
public class JdbcListingRepository implements ListingRepository {

    private final JdbcTemplate jdbcTemplate;
    private final VehicleRepository vehicleRepository;

    public JdbcListingRepository(JdbcTemplate jdbcTemplate, VehicleRepository vehicleRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public void createListing(Listing listing) {
        String sql = "INSERT INTO listing (listing_description, listing_price, vehicle_id, user_id) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(
                sql,
                listing.getListingDescription(),
                listing.getListingPrice(),
                listing.getVehicleId(),
                listing.getUserId()
        );
    }

    @Override
    public void updateListing(Listing listing) {
        String sql = "UPDATE listing SET listing_description = ?, listing_price = ?, vehicle_id = ?, user_id = ? WHERE id = ?";
        jdbcTemplate.update(
                sql,
                listing.getListingDescription(),
                listing.getListingPrice(),
                listing.getVehicleId(),
                listing.getUserId(),
                listing.getId()
        );
    }

    @Override
    public void deleteListing(int id) {
        String sql = "DELETE FROM listing WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Listing getListingById(int id) {
        String sql = "SELECT id, listing_description, listing_price, vehicle_id, user_id FROM listing WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new ListingRowMapper(vehicleRepository), id);
    }

    @Override
    public List<Listing> getNewListings() {
        String sql = "SELECT l.id, l.listing_description, l.listing_price, l.vehicle_id, l.user_id, v.year, v.manufacturer, v.model FROM listing l INNER JOIN vehicle v ON l.vehicle_id = v.id ORDER BY l.created_at DESC LIMIT 10";
        return jdbcTemplate.query(sql, new ListingRowMapper(vehicleRepository));
    }
}