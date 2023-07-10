package com.example.AutoSpotter.classes.listing;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.example.AutoSpotter.classes.user.User;
import com.example.AutoSpotter.classes.vehicle.Vehicle;

public class Listing {
    private int id;
    private String listingDescription;
    private BigDecimal listingPrice;
    private int vehicleId;
    private int userId;
    private boolean status;
    private Timestamp createdAt;

    private Vehicle vehicle;
    private User user;

    public Listing() {}

    public Listing(int id, String listingDescription, BigDecimal listingPrice, int vehicleId, int userId, boolean status, Timestamp createdAt) {
        this.id = id;
        this.listingDescription = listingDescription;
        this.listingPrice = listingPrice;
        this.vehicleId = vehicleId;
        this.userId = userId;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getListingDescription() {
        return listingDescription;
    }

    public void setListingDescription(String listingDescription) {
        this.listingDescription = listingDescription;
    }

    public BigDecimal getListingPrice() {
        return listingPrice;
    }

    public void setListingPrice(BigDecimal listingPrice) {
        this.listingPrice = listingPrice;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}