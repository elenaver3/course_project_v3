package com.example.course_project;

public abstract class Human {

    private String last_name;
    private String first_name;
    private String second_name;
    private String address;

    public Human() {
        last_name = " ";
        first_name = " ";
        second_name = " ";
        address = " ";
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getSecond_name() {
        return second_name;
    }

    public String getAddress() {
        return address;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setSecond_name(String second_name) {
        this.second_name = second_name;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
