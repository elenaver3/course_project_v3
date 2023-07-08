package com.example.course_project;

import javafx.beans.property.SimpleStringProperty;

public class Ingredient {
    private int id;
    private String name;
    private String measurementUnit;
    private int amount;

    private int id_dish;

    public Ingredient() {
        id = -1;
        name = " ";
        measurementUnit = " ";
        amount = 0;
        id_dish = -1;
    }

    public Ingredient(int id, String name, String measurementUnit, int amount) {
        this.id = id;
        this.name = name;
        this.measurementUnit = measurementUnit;
        this.amount = amount;
        this.id_dish = -1;
    }

    public Ingredient(int id, String name, int amount, int id_dish) {
        this.id = id;
        this.name = name;
        this.measurementUnit = measurementUnit;
        this.amount = amount;
        this.id_dish = id_dish;
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

    public int getId_dish() {
        return id_dish;
    }

    public void setId_dish(int id_dish) {
        this.id_dish = id_dish;
    }
}
