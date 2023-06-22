package com.example.AutoSpotter.classes.vehicle;

import java.util.List;

public interface VehicleRepository {

    int saveVehicle(Vehicle vehicle);

    List<String> getManufacturersByVehicleType(int vehicleTypeId);

    int getVehicleTypeId(String vehicleType);

    List<String> getAllVehicleTypes();

    List<String> getAllStates();
}