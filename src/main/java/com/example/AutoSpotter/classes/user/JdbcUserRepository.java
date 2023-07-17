package com.example.AutoSpotter.classes.user;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcUserRepository implements UserRepository{

    private final JdbcTemplate jdbcTemplate;

    public JdbcUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User getUserById(int id) {
        String sql = "SELECT id, username, password, first_name, last_name, company_name, company_oib, address, phone_number, email, city_id FROM user WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new UserRowMapper(), id);
    }

    @Override
    public boolean existsByUsername(String username) {
        String sql = "SELECT COUNT(*) FROM user WHERE username = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, username);
        return count != null && count > 0;
    }

    @Override
    public User findByUsername(String username) {
        String sql = "SELECT id, username, password, first_name, last_name, company_name, company_oib, address, phone_number, email, city_id FROM user WHERE username = ?";
        return jdbcTemplate.queryForObject(sql, new UserRowMapper(), username);
    }

    @Override
    public void save(User user) {
        String sql = "INSERT INTO user (username, password, first_name, last_name, company_name, company_oib, address, phone_number, email, city_id) " + 
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getFirstName(), user.getLastName(), user.getCompanyName(), user.getCompanyOIB(),
                            user.getAddress(), user.getPhoneNumber(), user.getEmail(), user.getCityId());
    }

    @Override
    public User findByEmail(String email) {
        String sql = "SELECT id, username, password, first_name, last_name, company_name, company_oib, address, phone_number, email, city_id FROM user WHERE email = ?";
        return jdbcTemplate.queryForObject(sql, new UserRowMapper(), email);
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        String sql = "SELECT id, username, password, first_name, last_name, company_name, company_oib, address, phone_number, email, city_id FROM user WHERE username = ? AND password = ?";
        return jdbcTemplate.queryForObject(sql, new UserRowMapper(), username, password);
    }
}