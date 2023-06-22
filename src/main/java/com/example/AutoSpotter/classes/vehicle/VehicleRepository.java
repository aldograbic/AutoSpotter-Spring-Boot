package com.example.AutoSpotter.classes.vehicle;

import java.util.List;

public interface VehicleRepository {

    int saveVehicle(Vehicle vehicle);

    List<String> getManufacturersByVehicleType(String vehicleType);

    int getVehicleTypeIdByListingType(String listingType);

    List<String> getAllVehicleTypes();
}

