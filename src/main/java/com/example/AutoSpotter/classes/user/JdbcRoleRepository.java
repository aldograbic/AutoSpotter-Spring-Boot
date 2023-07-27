package com.example.AutoSpotter.classes.user;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcRoleRepository implements RoleRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcRoleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Role getRoleById(int roleId) {
        String sql = "SELECT id, name FROM roles WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new RoleRowMapper(), roleId);
    }
}
