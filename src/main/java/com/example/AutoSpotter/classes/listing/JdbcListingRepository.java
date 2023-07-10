package com.example.AutoSpotter.classes.listing;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
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
                listing.getStatus(),
                listing.getCreatedAt()
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
                    "ORDER BY l.created_at DESC";

        return jdbcTemplate.query(sql, new ListingRowMapper(vehicleRepository, userRepository));
    }

   @Override
    public List<String> getManufacturersByVehicleType(String vehicleType) {
        String sql = "SELECT DISTINCT m.manufacturer_name " +
                    "FROM manufacturers m " +
                    "INNER JOIN vehicle_type vt ON m.vehicle_type_id = vt.id " +
                    "WHERE vt.name = ?";
        return jdbcTemplate.queryForList(sql, String.class, vehicleType);
    }

    @Override
    public List<String> getModelsByManufacturer(String manufacturer) {
        String sql = "SELECT DISTINCT mo.model_name " +
                    "FROM models mo " +
                    "INNER JOIN manufacturers ma ON mo.manufacturer_id = ma.id " +
                    "WHERE ma.manufacturer_name = ?";
        return jdbcTemplate.queryForList(sql, String.class, manufacturer);
    }

    @Override
    public List<String> getBodyTypes() {
        String sql = "SELECT body_type_name FROM body_type";
        return jdbcTemplate.queryForList(sql, String.class);
    }
}