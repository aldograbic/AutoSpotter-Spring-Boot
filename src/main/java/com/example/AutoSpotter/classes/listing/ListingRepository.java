package com.example.AutoSpotter.classes.listing;

import java.util.List;

public interface ListingRepository {
    void createListing(Listing listing);
    void updateListing(Listing listing);
    void editListing(Listing listing);
    void deleteListing(int id);
    Listing getListingById(int id);
    List<Listing> getNewListings();
    List<Listing> getFilteredListings(String vehicleType, String manufacturer, String model,
                                            String bodyType, String engineType, String motorcycleEngineType, String transmission,
                                            String county, Integer mileageFrom, Integer mileageTo,
                                            Integer yearFrom, Integer yearTo, Integer priceFrom,
                                            Integer priceTo, String userType);
    int getListingsCountByUserId(int userId);
    int getLikedListingsCountByUserId(int userId);
    List<Listing> getListingsByUserId(int userId);

    void likeListing(int userId, int listingId);
    void dislikeListing(int userId, int listingId);
    List<Listing> getListingsLikedByUser(int userId);
    boolean hasUserLikedListing(int userId, int listingId);
    void saveImageUrlsForVehicle(ListingImage listingImage);
    List<String> getImageUrlsForVehicle(int vehicleId);
    String getFirstImageUrlForVehicle(int vehicleId);
    List<Listing> getNewestListings();
    List<Listing> getSimilarListings(int currentListingId, String vehicleType, String manufacturer, String model);
    List<Listing> getSimilarListingsOfFilteredListings(String vehicleType, String manufacturer, String model);
}