package com.example.AutoSpotter.classes.vehicle;

public class Vehicle {
    private int id;
    private String manufacturer;
    private String model;
    private String bodyType;
    private int mileage;
    private String state;
    private int year;
    private String engineType;
    private double engineDisplacement;
    private int enginePower;
    private double fuelConsumption;
    private String transmission;
    private int cityId;
    private int vehicleTypeId;

    public Vehicle(String manufacturer, String model, String bodyType, int mileage, String state, int year, String engineType,
                   double engineDisplacement, int enginePower, double fuelConsumption, String transmission, int cityId, int vehicleTypeId) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.bodyType = bodyType;
        this.mileage = mileage;
        this.state = state;
        this.year = year;
        this.engineType = engineType;
        this.engineDisplacement = engineDisplacement;
        this.enginePower = enginePower;
        this.fuelConsumption = fuelConsumption;
        this.transmission = transmission;
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

    // private String color;
    // private Date/boolean registered
    
    //ZA KAMION
    // private int numberOfWheels;
    // najveca dopustena masa

    //ZA ELEKTRICNI AUTO
    //kapacitetu baterije, doseg vozila, vrijeme punjenja

    // Safety Features
    // Infotainment System
}