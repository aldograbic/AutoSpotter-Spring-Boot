package com.example.AutoSpotter.classes.vehicle;

import java.util.List;

public interface VehicleRepository {

    int saveVehicle(Vehicle vehicle);

    List<String> getManufacturersByVehicleType(int vehicleTypeId);

    List<String> getManufacturersByVehicleTypeName(String vehicleType);

    List<String> getModelsByManufacturer(String manufacturer);

    int getVehicleTypeId(String vehicleType);

    List<String> getAllVehicleTypes();

    Vehicle getVehicleById(int id);

    List<String> getAllBodyTypes();

    List<String> getAllEngineTypes();

    List<String> getAllTransmissionTypes();

    List<String> getAllDriveTrainTypes();

    List<String> getAllStates();

    int saveVehicleSafetyFeatures(List<String> safetyFeatures);

    int saveVehicleExtras(List<String> extras);

    List<String> getVehicleSafetyFeatures(int safetyFeaturesId);

    List<String> getVehicleExtras(int extrasId);
}