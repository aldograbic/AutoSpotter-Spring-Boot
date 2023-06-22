package com.example.AutoSpotter.classes.listing;

public interface ListingRepository {
    void createListing(Listing listing);
    void updateListing(Listing listing);
    void deleteListing(int id);
    Listing getListingById(int id);
}