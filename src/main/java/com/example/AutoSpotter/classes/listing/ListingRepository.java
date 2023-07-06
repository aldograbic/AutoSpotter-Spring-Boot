package com.example.AutoSpotter.classes.listing;

import java.util.List;

public interface ListingRepository {
    void createListing(Listing listing);
    void updateListing(Listing listing);
    void deleteListing(int id);
    Listing getListingById(int id);
    List<Listing> getNewListings();
    List<String> getManufacturersByVehicleType(String vehicleType);
    List<String> getModelsByManufacturer(String manufacturer);
    List<String> getBodyTypes();
}