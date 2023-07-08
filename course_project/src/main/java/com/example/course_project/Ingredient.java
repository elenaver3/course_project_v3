package com.example.course_project;

import javafx.beans.property.SimpleStringProperty;

public class Ingredient {
    private int id;
    private String name;
    private String measurementUnit;
    private int amount;

    public Ingredient() {
        id = -1;
        name = " ";
        measurementUnit = " ";
        amount = 0;
    }

    public Ingredient(int id, String name, String measurementUnit, int amount) {
        this.id = id;
        this.name = name;
        this.measurementUnit = measurementUnit;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getMeasurementUnit() {
        return measurementUnit;
    }

    public void setMeasurementUnit(String measurementUnit) {
        this.measurementUnit = measurementUnit;
    }
}
