package com.example.course_project;

public class MeasurementUnit {
    private int id;
    private String unit;

    public MeasurementUnit(int id, String unit) {
        this.id = id;
        this.unit = unit;
    }

    public int getId() {
        return id;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
