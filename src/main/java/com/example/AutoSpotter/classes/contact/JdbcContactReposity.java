package com.example.AutoSpotter.classes.contact;

import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcContactReposity implements ContactRepository{

    private final JdbcTemplate jdbcTemplate;

    public JdbcContactReposity(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveContact(Contact contact) {
        String sql = "INSERT INTO contact_messages (name, email, message) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, contact.getName(), contact.getEmail(), contact.getMessage());
    }
}