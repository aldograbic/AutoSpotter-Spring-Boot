package com.example.AutoSpotter.classes.vehicle;

import java.util.Date;

public class Vehicle {
    int id;
    private String model;
    private String manufacturer;
    private String state;

    public Vehicle(String model, String manufacturer, String state) {
        this.model = model;
        this.manufacturer = manufacturer;
        this.state = state;
    }

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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    // private String vin;
    // private Date year;
    // private String color;
    // private String engineType;
    // private String transmission;
    // private String fuelType;
    // private String seatingCapacity;
    // private double curbWeight;
    // private double maximumPayloadCapacity;
    // private double width;
    // private double length;
    // private double height;
    // private double maximumSpeed;
    // private double acceleration;
    // private double fuelEfficiency;

    //Safety Features, Infotainment System, numberOfWheels, registered, price,
    // kilometers, powerEngine, location, ownerID
}
