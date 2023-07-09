package com.example.AutoSpotter.classes.vehicle;

import java.sql.Date;

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
    private int numberOfWheels;
    private double maximumAllowableWeight;
    private String engineType;
    private double engineDisplacement;
    private int enginePower;
    private double fuelConsumption;
    private String transmission;
    private String driveTrain;
    private double batteryCapacity;
    private double chargingTime;
    private double vehicleRange;
    private int cityId;
    private int vehicleTypeId;

    public Vehicle(String manufacturer, String model, String bodyType, String color, Date registered, int mileage,
                   String state, int year, int numberOfWheels, double maximumAllowableWeight, String engineType, double engineDisplacement,
                   int enginePower, double fuelConsumption, String transmission, String driveTrain, double batteryCapacity, double chargingTime,
                   double vehicleRange, int cityId, int vehicleTypeId) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.bodyType = bodyType;
        this.color = color;
        this.registered = registered;
        this.mileage = mileage;
        this.state = state;
        this.year = year;
        this.numberOfWheels = numberOfWheels;
        this.maximumAllowableWeight = maximumAllowableWeight;
        this.engineType = engineType;
        this.engineDisplacement = engineDisplacement;
        this.enginePower = enginePower;
        this.fuelConsumption = fuelConsumption;
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

    public double getEngineDisplacement() {
        return engineDisplacement;
    }

    public void setEngineDisplacement(double engineDisplacement) {
        this.engineDisplacement = engineDisplacement;
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

    // Safety Features
    // Infotainment System
}