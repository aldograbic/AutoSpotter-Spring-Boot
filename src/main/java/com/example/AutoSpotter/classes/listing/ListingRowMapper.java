package com.example.AutoSpotter.classes.listing;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ListingRowMapper implements RowMapper<Listing> {
    @Override
    public Listing mapRow(ResultSet rs, int rowNum) throws SQLException {
        Listing listing = new Listing();
        listing.setId(rs.getInt("id"));
        listing.setListingTitle(rs.getString("listing_title"));
        listing.setListingDescription(rs.getString("listing_description"));
        listing.setListingPrice(rs.getBigDecimal("listing_price"));
        listing.setVehicleId(rs.getInt("vehicle_id"));
        listing.setUserId(rs.getInt("user_id"));
        return listing;
    }
}
