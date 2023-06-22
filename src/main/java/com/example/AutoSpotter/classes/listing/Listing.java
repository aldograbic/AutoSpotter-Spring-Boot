package com.example.AutoSpotter.classes.listing;

import java.math.BigDecimal;

public class Listing {
    private int id;
    private String listingTitle;
    private String listingDescription;
    private BigDecimal listingPrice;
    private int vehicleId;
    private int userId;

    public Listing() {}

    public Listing(int id, String listingTitle, String listingDescription, BigDecimal listingPrice, int vehicleId, int userId) {
        this.id = id;
        this.listingTitle = listingTitle;
        this.listingDescription = listingDescription;
        this.listingPrice = listingPrice;
        this.vehicleId = vehicleId;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getListingTitle() {
        return listingTitle;
    }

    public void setListingTitle(String listingTitle) {
        this.listingTitle = listingTitle;
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
