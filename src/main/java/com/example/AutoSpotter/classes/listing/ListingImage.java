package com.example.AutoSpotter.classes.listing;

import com.example.AutoSpotter.classes.vehicle.Vehicle;

public class ListingImage {
    private Long id;
    private Vehicle vehicle;
    private String imageUrl;

    public ListingImage() {
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}