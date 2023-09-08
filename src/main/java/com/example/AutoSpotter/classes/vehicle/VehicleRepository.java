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

    String getVehicleTypeNameById(int vehicleTypeId);

    List<String> getAllBodyTypes();

    List<String> getAllEngineTypes();

    List<String> getAllMotorcycleEngineTypes();

    List<String> getAllTransmissionTypes();

    List<String>getAllEcoCategories();

    List<String> getAllDriveTrainTypes();

    List<String> getAllStates();

    int saveVehicleSafetyFeatures(List<String> safetyFeatures);

    int saveVehicleExtras(List<String> extras);

    List<SafetyFeature> getVehicleSafetyFeatures(int safetyFeaturesId);

    List<VehicleExtra> getVehicleExtras(int extrasId);
}