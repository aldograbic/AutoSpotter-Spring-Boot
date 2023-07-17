package com.example.AutoSpotter.classes.listing;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import com.example.AutoSpotter.classes.user.UserRepository;
import com.example.AutoSpotter.classes.vehicle.VehicleRepository;

@Repository
public class JdbcListingRepository implements ListingRepository {

    private final JdbcTemplate jdbcTemplate;
    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;

    public JdbcListingRepository(JdbcTemplate jdbcTemplate, VehicleRepository vehicleRepository, UserRepository userRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.vehicleRepository = vehicleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void createListing(Listing listing) {
        String sql = "INSERT INTO listing (listing_description, listing_price, vehicle_id, user_id, status, created_at) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(
                sql,
                listing.getListingDescription(),
                listing.getListingPrice(),
                listing.getVehicleId(),
                listing.getUserId(),
                1,
                LocalDateTime.now()
        );
    }

    @Override
    public void updateListing(Listing listing) {
        String sql = "UPDATE listing SET listing_description = ?, listing_price = ?, vehicle_id = ?, user_id = ?, status = ?, created_at = ? WHERE id = ?";
        jdbcTemplate.update(
                sql,
                listing.getListingDescription(),
                listing.getListingPrice(),
                listing.getVehicleId(),
                listing.getUserId(),
                listing.getStatus(),
                listing.getCreatedAt(),
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
        String sql = "SELECT id, listing_description, listing_price, vehicle_id, user_id, status, created_at FROM listing WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new ListingRowMapper(vehicleRepository, userRepository), id);
    }

    @Override
    public List<Listing> getNewListings() {
        String sql = "SELECT l.id, l.listing_description, l.listing_price, l.vehicle_id, l.user_id, l.status, l.created_at, " +
                    "v.year, v.manufacturer, v.model, v.mileage, c.city_name, v.state, u.username " +
                    "FROM listing l " +
                    "INNER JOIN vehicle v ON l.vehicle_id = v.id " +
                    "INNER JOIN user u ON l.user_id = u.id " +
                    "INNER JOIN cities c ON v.city_id = c.id " +
                    "WHERE l.status = 1 " +
                    "ORDER BY l.created_at DESC";

        return jdbcTemplate.query(sql, new ListingRowMapper(vehicleRepository, userRepository));
    }


    @Scheduled(cron = "0 0 0 * * *") // Runs every day at midnight
    public void updateListingStatus() {
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);

        String sql = "UPDATE listing SET status = 0 WHERE created_at < ?";
        jdbcTemplate.update(sql, thirtyDaysAgo);
    }
}