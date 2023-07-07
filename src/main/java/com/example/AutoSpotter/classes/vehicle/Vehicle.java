package com.example.AutoSpotter.classes.vehicle;

public class Vehicle {
    private int id;
    private String manufacturer;
    private String model;
    private String bodyType;
    private int mileage;
    private String location;
    private String state;
    private int year;
    private String engineType;
    private String engineDisplacement;
    private int enginePower;
    private String fuelConsumption;
    private String transmission;
    private int vehicleTypeId;

    public Vehicle(String manufacturer, String model, String bodyType, int mileage, String location, String state, int year, String engineType,
                   String engineDisplacement, int enginePower, String fuelConsumption, String transmission, int vehicleTypeId) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.bodyType = bodyType;
        this.mileage = mileage;
        this.location = location;
        this.state = state;
        this.year = year;
        this.engineType = engineType;
        this.engineDisplacement = engineDisplacement;
        this.enginePower = enginePower;
        this.fuelConsumption = fuelConsumption;
        this.transmission = transmission;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public String getEngineDisplacement() {
        return engineDisplacement;
    }

    public void setEngineDisplacement(String engineDisplacement) {
        this.engineDisplacement = engineDisplacement;
    }

    public int getEnginePower() {
        return enginePower;
    }

    public void setEnginePower(int enginePower) {
        this.enginePower = enginePower;
    }

    public String getFuelConsumption() {
        return fuelConsumption;
    }

    public void setFuelConsumption(String fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
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


    // location (promijenit malo), fuelEfficiency i fuelConsumtion u double
}