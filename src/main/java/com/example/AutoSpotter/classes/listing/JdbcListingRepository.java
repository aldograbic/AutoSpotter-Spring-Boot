package com.example.AutoSpotter.classes.listing;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcListingRepository implements ListingRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcListingRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createListing(Listing listing) {
        String sql = "INSERT INTO listing (listing_title, listing_description, listing_price, vehicle_id, user_id) " +
                "VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(
                sql,
                listing.getListingTitle(),
                listing.getListingDescription(),
                listing.getListingPrice(),
                listing.getVehicleId(),
                listing.getUserId()
        );
    }

    @Override
    public void updateListing(Listing listing) {
        String sql = "UPDATE listing SET listing_title = ?, listing_description = ?, " +
                "listing_price = ?, vehicle_id = ?, user_id = ? WHERE id = ?";
        jdbcTemplate.update(
                sql,
                listing.getListingTitle(),
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
        String sql = "SELECT * FROM listing WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new ListingRowMapper());
    }
}

