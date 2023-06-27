package com.example.AutoSpotter.classes.vehicle;

public class Vehicle {
    int id;
    private String model;
    private String manufacturer;
    private int mileage;
    private String location;
    private String state;
    private int year;
    private int vehicleTypeId;
    // private String fuelType;
    // private String transmission;
    // private String bodyShape;

    public Vehicle(String model, String manufacturer, int mileage, String location, String state, int year, int vehicleTypeId) {
        this.model = model;
        this.manufacturer = manufacturer;
        this.mileage = mileage;
        this.location = location;
        this.state = state;
        this.year = year;
        this.vehicleTypeId = vehicleTypeId;
    }

    public Vehicle() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
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

    public int getVehicleTypeId() {
        return vehicleTypeId;
    }

    public void setVehicleTypeId(int vehicleTypeId) {
        this.vehicleTypeId = vehicleTypeId;
    }

 
    // private String vin;
    // private String color;
    // private String engineType;
    
    
    // private String seatingCapacity;
    // private double curbWeight;
    // private double maximumPayloadCapacity;
    // private double width;
    // private double length;
    // private double height;
    // private double maximumSpeed;
    // private double acceleration;
    // private double fuelEfficiency;

    //Safety Features, Infotainment System, numberOfWheels, registered,
    // powerEngine, location (promijenit malo)
}