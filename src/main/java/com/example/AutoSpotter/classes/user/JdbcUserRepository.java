package com.example.AutoSpotter.classes.user;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.AutoSpotter.classes.location.LocationRepository;

@Repository
public class JdbcUserRepository implements UserRepository{

    private final JdbcTemplate jdbcTemplate;
    private final LocationRepository locationRepository;
    private final RoleRepository roleRepository;

    public JdbcUserRepository(JdbcTemplate jdbcTemplate, LocationRepository locationRepository, RoleRepository roleRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.locationRepository = locationRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public User getUserById(int id) {
        String sql = "SELECT id, username, password, first_name, last_name, company_name, company_oib, address, phone_number, email, city_id, role_id FROM user WHERE id = ?";
        User user = jdbcTemplate.queryForObject(sql, new UserRowMapper(locationRepository, roleRepository), id);

        

        return user;
    }

    @Override
    public boolean existsByUsername(String username) {
        String sql = "SELECT COUNT(*) FROM user WHERE username = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, username);
        return count != null && count > 0;
    }

    @Override
    public User findByUsername(String username) {
        String sql = "SELECT id, username, password, first_name, last_name, company_name, company_oib, address, phone_number, email, city_id, role_id FROM user WHERE username = ?";
        return jdbcTemplate.queryForObject(sql, new UserRowMapper(locationRepository, roleRepository), username);
    }

    @Override
    public void save(User user) {
        String sql = "INSERT INTO user (username, password, first_name, last_name, company_name, company_oib, address, phone_number, email, city_id, role_id) " + 
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getFirstName(), user.getLastName(), user.getCompanyName(), user.getCompanyOIB(),
                            user.getAddress(), user.getPhoneNumber(), user.getEmail(), user.getCityId(), user.getRoleId());
    }

    @Override
    public User findByEmail(String email) {
        String sql = "SELECT id, username, password, first_name, last_name, company_name, company_oib, address, phone_number, email, city_id, role_id FROM user WHERE email = ?";
        return jdbcTemplate.queryForObject(sql, new UserRowMapper(locationRepository, roleRepository), email);
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        String sql = "SELECT id, username, password, first_name, last_name, company_name, company_oib, address, phone_number, email, city_id, role_id FROM user WHERE username = ? AND password = ?";
        return jdbcTemplate.queryForObject(sql, new UserRowMapper(locationRepository, roleRepository), username, password);
    }
}