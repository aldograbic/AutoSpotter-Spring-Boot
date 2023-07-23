package com.example.AutoSpotter.classes.vehicle;

public class VehicleExtra {
    private String name;
    public boolean value;

    public VehicleExtra(String name, boolean value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return name;
    }
}
