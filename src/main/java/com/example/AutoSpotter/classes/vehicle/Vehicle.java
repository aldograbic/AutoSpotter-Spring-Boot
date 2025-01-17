package com.example.AutoSpotter.classes.vehicle;

import java.sql.Date;
import java.util.List;

import com.example.AutoSpotter.classes.location.City;
import com.example.AutoSpotter.classes.location.County;

public class Vehicle {
    private int id;
    private String manufacturer;
    private String model;
    private String bodyType;
    private String color;
    private Date registered;
    private int mileage;
    private String state;
    private int year;
    private int numberOfDoors;
    private int numberOfWheels;
    private double maximumAllowableWeight;
    private String engineType;
    private String motorcycleEngineType;
    private double engineDisplacement;
    private int engineDisplacementCcm3;
    private int enginePower;
    private double fuelConsumption;
    private String ecoCategory;
    private String transmission;
    private String driveTrain;
    private double batteryCapacity;
    private double chargingTime;
    private double vehicleRange;
    private int cityId;
    private int vehicleTypeId;
    private int vehicleSafetyFeaturesId;
    private int vehicleExtrasId;

    private String vehicleType;
    private City city;
    private County county;
    private List<SafetyFeature> safetyFeatures;
    private List<VehicleExtra> extras;

    public Vehicle(String manufacturer, String model, String bodyType, String color, Date registered, int mileage,
                String state, int year, int numberOfDoors, int numberOfWheels, double maximumAllowableWeight, String engineType,
                String motorcycleEngineType, double engineDisplacement, int engineDisplacementCcm3,
                int enginePower, double fuelConsumption, String ecoCategory, String transmission, String driveTrain,
                double batteryCapacity, double chargingTime, double vehicleRange, int cityId, int vehicleTypeId) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.bodyType = bodyType;
        this.color = color;
        this.registered = registered;
        this.mileage = mileage;
        this.state = state;
        this.year = year;
        this.numberOfDoors = numberOfDoors;
        this.numberOfWheels = numberOfWheels;
        this.maximumAllowableWeight = maximumAllowableWeight;
        this.engineType = engineType;
        this.engineDisplacement = engineDisplacement;
        this.engineDisplacementCcm3 = engineDisplacementCcm3;
        this.enginePower = enginePower;
        this.fuelConsumption = fuelConsumption;
        this.ecoCategory = ecoCategory;
        this.transmission = transmission;
        this.driveTrain = driveTrain;
        this.batteryCapacity = batteryCapacity;
        this.chargingTime = chargingTime;
        this.vehicleRange = vehicleRange;
        this.cityId = cityId;
        this.vehicleTypeId = vehicleTypeId;
    }

    public Vehicle() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBodyType() {
        return bodyType;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Date getRegistered() {
        return registered;
    }

    public void setRegistered(Date registered) {
        this.registered = registered;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getNumberOfDoors() {
        return numberOfDoors;
    }

    public void setNumberOfDoors(int numberOfDoors) {
        this.numberOfDoors = numberOfDoors;
    }

    public int getNumberOfWheels() {
        return numberOfWheels;
    }

    public void setNumberOfWheels(int numberOfWheels) {
        this.numberOfWheels = numberOfWheels;
    }

    public double getMaximumAllowableWeight() {
        return maximumAllowableWeight;
    }

    public void setMaximumAllowableWeight(double maximumAllowableWeight) {
        this.maximumAllowableWeight = maximumAllowableWeight;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public String getMotorcycleEngineType() {
        return motorcycleEngineType;
    }

    public void setMotorcycleEngineType(String motorcycleEngineType) {
        this.motorcycleEngineType = motorcycleEngineType;
    }

    public double getEngineDisplacement() {
        return engineDisplacement;
    }

    public void setEngineDisplacement(double engineDisplacement) {
        this.engineDisplacement = engineDisplacement;
    }

    public int getEngineDisplacementCcm3() {
        return engineDisplacementCcm3;
    }

    public void setEngineDisplacementCcm3(int engineDisplacementCcm3) {
        this.engineDisplacementCcm3 = engineDisplacementCcm3;
    }

    public int getEnginePower() {
        return enginePower;
    }

    public void setEnginePower(int enginePower) {
        this.enginePower = enginePower;
    }

    public double getFuelConsumption() {
        return fuelConsumption;
    }

    public void setFuelConsumption(double fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
    }

    public String getEcoCategory() {
        return ecoCategory;
    }

    public void setEcoCategory(String ecoCategory) {
        this.ecoCategory = ecoCategory;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public String getDriveTrain() {
        return driveTrain;
    }

    public void setDriveTrain(String driveTrain) {
        this.driveTrain = driveTrain;
    }

    public double getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(double batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    public double getChargingTime() {
        return chargingTime;
    }

    public void setChargingTime(double chargingTime) {
        this.chargingTime = chargingTime;
    }

    public double getVehicleRange() {
        return vehicleRange;
    }

    public void setVehicleRange(double vehicleRange) {
        this.vehicleRange = vehicleRange;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getVehicleTypeId() {
        return vehicleTypeId;
    }

    public void setVehicleTypeId(int vehicleTypeId) {
        this.vehicleTypeId = vehicleTypeId;
    }

    public int getVehicleSafetyFeaturesId() {
        return vehicleSafetyFeaturesId;
    }

    public void setVehicleSafetyFeaturesId(int vehicleSafetyFeaturesId) {
        this.vehicleSafetyFeaturesId = vehicleSafetyFeaturesId;
    }

    public int getVehicleExtrasId() {
        return vehicleExtrasId;
    }

    public void setVehicleExtrasId(int vehicleExtrasId) {
        this.vehicleExtrasId = vehicleExtrasId;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public County getCounty() {
        return county;
    }

    public void setCounty(County county) {
        this.county = county;
    }

    public List<SafetyFeature> getSafetyFeatures() {
        return safetyFeatures;
    }

    public void setSafetyFeatures(List<SafetyFeature> safetyFeatures) {
        this.safetyFeatures = safetyFeatures;
    }

    public List<VehicleExtra> getExtras() {
        return extras;
    }

    public void setExtras(List<VehicleExtra> extras) {
        this.extras = extras;
    }
}