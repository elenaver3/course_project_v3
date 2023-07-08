package com.example.course_project;

public class Dish {
    private int id;
    private String name;

    private int id_visit;

    private int amount;


    public Dish() {
        id = -1;
        name = " ";
        id_visit = -1;
        amount = 0;
    }

    public Dish(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Dish(int id, String name, int id_visit, int amount) {
        this.id = id;
        this.name = name;
        this.id_visit = id_visit;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId_visit() {
        return id_visit;
    }

    public void setId_visit(int id_visit) {
        this.id_visit = id_visit;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }
}
