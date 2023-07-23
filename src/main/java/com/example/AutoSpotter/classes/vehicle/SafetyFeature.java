package com.example.AutoSpotter.classes.vehicle;

public class SafetyFeature {
    private String name;
    public boolean value;

    public SafetyFeature(String name, boolean value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return name;
    }
}
