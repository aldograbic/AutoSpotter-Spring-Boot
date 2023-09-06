package com.example.AutoSpotter.classes.user;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.AutoSpotter.classes.location.LocationRepository;
import com.example.AutoSpotter.config.AuthenticationType;

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
        String sql = "SELECT id, username, password, first_name, last_name, company_name, company_oib, address, phone_number, email, profile_image, email_verified, confirmation_token, city_id FROM user WHERE id = ?";
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
        String sql = "SELECT id, username, password, first_name, last_name, company_name, company_oib, address, phone_number, email, profile_image, email_verified, confirmation_token, city_id FROM user WHERE username = ?";
        List<User> users = jdbcTemplate.query(sql, new UserRowMapper(locationRepository), username);

        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public void save(User user) {
        String sql = "INSERT INTO user (username, password, first_name, last_name, company_name, company_oib, address, phone_number, email, profile_image, email_verified, confirmation_token, city_id) " + 
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getFirstName(), user.getLastName(), user.getCompanyName(), user.getCompanyOIB(),
                            user.getAddress(), user.getPhoneNumber(), user.getEmail(), user.getProfileImage(), user.isEmailVerified(), user.getConfirmationToken(), user.getCityId());
    }

    @Override
    public void saveOAuth2(User user) {
        String sql = "INSERT INTO user (username, first_name, last_name, phone_number, address, email, email_verified) " + 
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getUsername(),user.getFirstName(), user.getLastName(), user.getPhoneNumber(), user.getAddress(), user.getEmail(), user.isEmailVerified());
    }

    @Override
    public void deleteUser(int userId) {
        String sql = "DELETE FROM user WHERE id = ?";
        jdbcTemplate.update(sql, userId);
    }

    @Override
    public void updateUser(User user) {
        String sql = "UPDATE user SET username = ?, password = ?, first_name = ?, last_name = ?, " +
                "company_name = ?, company_oib = ?, address = ?, phone_number = ?, email = ?, profile_image = ?, " +
                "email_verified = ?, confirmation_token = ?, city_id = ? WHERE id = ?";
        jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getFirstName(),
                user.getLastName(), user.getCompanyName(), user.getCompanyOIB(), user.getAddress(),
                user.getPhoneNumber(), user.getEmail(), user.getProfileImage(), user.isEmailVerified(), user.getConfirmationToken(),
                user.getCityId(), user.getId());
    }

    @Override
    public User findByEmail(String email) {
        String sql = "SELECT id, username, password, first_name, last_name, company_name, company_oib, address, phone_number, email, profile_image, email_verified, confirmation_token, city_id FROM user WHERE email = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new UserRowMapper(locationRepository), email);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        String sql = "SELECT id, username, password, first_name, last_name, company_name, company_oib, address, phone_number, email, profile_image, city_id FROM user WHERE username = ? AND password = ?";
        return jdbcTemplate.queryForObject(sql, new UserRowMapper(locationRepository), username, password);
    }

    @Override
    public User findByConfirmationToken(String token) {
        String sql = "SELECT id, username, password, first_name, last_name, company_name, company_oib, address, phone_number, email, profile_image, city_id, email_verified, confirmation_token " +
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

    @Override
    public void updatePassword(User user) {
        String sql = "UPDATE user SET password = ? WHERE id = ?";
        jdbcTemplate.update(sql, user.getPassword(), user.getId());
    }

    @Override
    public void updateAuthenticationType(String username, AuthenticationType authType) {
        String sql = "UPDATE user SET auth_type = ? WHERE username = ?";
        jdbcTemplate.update(sql, authType.toString(), username);
    }
}