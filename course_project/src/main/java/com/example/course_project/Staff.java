package com.example.course_project;

import javafx.beans.property.SimpleStringProperty;

public class Staff extends Human {
    private String phone_number;
    private int id;

    public Staff() {
        phone_number = " ";
        id = -1;
    }
    public Staff(int id, String last_name, String first_name, String second_name, String address, String phone_number) {
        this.phone_number = phone_number;
        this.id = id;
        this.setFirst_name(first_name);
        this.setLast_name(last_name);
        this.setSecond_name(second_name);
        this.setAddress(address);
        this.setPhone_number(phone_number);
    }


    public String getPhone_number() {
        return phone_number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
