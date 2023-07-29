package com.example.AutoSpotter.classes.user;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.AutoSpotter.classes.location.LocationRepository;

@Repository
public class JdbcUserRepository implements UserRepository{

    private final JdbcTemplate jdbcTemplate;
    private final LocationRepository locationRepository;

    public JdbcUserRepository(JdbcTemplate jdbcTemplate, LocationRepository locationRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.locationRepository = locationRepository;
    }

    @Override
    public User getUserById(int id) {
        String sql = "SELECT id, username, password, first_name, last_name, company_name, company_oib, address, phone_number, email, email_verified, confirmation_token, city_id FROM user WHERE id = ?";
        User user = jdbcTemplate.queryForObject(sql, new UserRowMapper(locationRepository), id);

        return user;
    }

    @Override
    public boolean existsByUsername(String username) {
        String sql = "SELECT COUNT(*) FROM user WHERE username = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, username);
        return count != null && count > 0;
    }

    @Override
    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM user WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

    @Override
    public User findByUsername(String username) {
        String sql = "SELECT id, username, password, first_name, last_name, company_name, company_oib, address, phone_number, email, email_verified, confirmation_token, city_id FROM user WHERE username = ?";
        List<User> users = jdbcTemplate.query(sql, new UserRowMapper(locationRepository), username);

        if (users.isEmpty()) {
            return null; // Return null when no matching user is found
        } else if (users.size() == 1) {
            return users.get(0); // Return the single user when found
        } else {
            // Handle the unexpected case of multiple users with the same username (optional)
            // You can log an error or take appropriate action based on your application's logic.
            // For example, you might throw an exception here or return the first user found.
            return users.get(0);
        }
    }

    @Override
    public void save(User user) {
        String sql = "INSERT INTO user (username, password, first_name, last_name, company_name, company_oib, address, phone_number, email, email_verified, confirmation_token, city_id) " + 
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getFirstName(), user.getLastName(), user.getCompanyName(), user.getCompanyOIB(),
                            user.getAddress(), user.getPhoneNumber(), user.getEmail(), user.isEmailVerified(), user.getConfirmationToken(), user.getCityId());
    }

    @Override
    public User findByEmail(String email) {
        String sql = "SELECT id, username, password, first_name, last_name, company_name, company_oib, address, phone_number, email, city_id FROM user WHERE email = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new UserRowMapper(locationRepository), email);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        String sql = "SELECT id, username, password, first_name, last_name, company_name, company_oib, address, phone_number, email, city_id FROM user WHERE username = ? AND password = ?";
        return jdbcTemplate.queryForObject(sql, new UserRowMapper(locationRepository), username, password);
    }

    @Override
    public User findByConfirmationToken(String token) {
        String sql = "SELECT id, username, password, first_name, last_name, company_name, company_oib, address, phone_number, email, city_id, email_verified, confirmation_token " +
                    "FROM user WHERE confirmation_token = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new UserRowMapper(locationRepository), token);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public void updateEmailVerification(User user) {
        String sql = "UPDATE user SET email_verified = ? WHERE id = ?";
        jdbcTemplate.update(sql, user.isEmailVerified(), user.getId());
    }
}