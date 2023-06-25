package com.example.AutoSpotter.classes.listing;

import java.math.BigDecimal;

import com.example.AutoSpotter.classes.vehicle.Vehicle;

public class Listing {
    private int id;
    private String listingDescription;
    private BigDecimal listingPrice;
    private int vehicleId;
    private int userId;

    private Vehicle vehicle;

    public Listing() {}

    public Listing(int id, String listingDescription, BigDecimal listingPrice, int vehicleId, int userId) {
        this.id = id;
        this.listingDescription = listingDescription;
        this.listingPrice = listingPrice;
        this.vehicleId = vehicleId;
        this.userId = userId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
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
}