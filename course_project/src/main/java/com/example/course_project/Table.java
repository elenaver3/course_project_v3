package com.example.course_project;

import javafx.beans.property.SimpleStringProperty;

public class Table {
    private int id;
    private String table_number;
    private int max_people;

    public Table() {
        id = -1;
        table_number = " ";
        max_people = 0;
    }

    public Table(int id, String table_number, int max_people) {
        this.id = id;
        this.table_number = table_number;
        this.max_people = max_people;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getTable_number() {
        return table_number;
    }

    public void setTableNumber(String name) {
        this.table_number = name;
    }


    public int getMax_people() {
        return max_people;
    }

    public void setMaxPeople(int max_people) {
        this.max_people = max_people;
    }
}
