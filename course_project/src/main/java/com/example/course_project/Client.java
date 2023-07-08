package com.example.course_project;

public class Client extends Human  {

    private String inn;
    private int id;

    private int id_visit;

    public Client() {
        inn = " ";
        id = -1;
        id_visit = -1;
    }
    public Client(int id, String last_name, String first_name, String second_name, String address, String inn) {
        this.inn = inn;
        this.id = id;
        this.setFirst_name(first_name);
        this.setLast_name(last_name);
        this.setSecond_name(second_name);
        this.setAddress(address);
        this.setInn(inn);
    }

    public Client(int id, String last_name, String first_name, String second_name, int id_visit) {
        this.id = id;
        this.setFirst_name(first_name);
        this.setLast_name(last_name);
        this.setSecond_name(second_name);
        this.id_visit = id_visit;
    }


    public String getInn() {
        return inn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public int getId_visit() {
        return id_visit;
    }

    public void setId_visit(int id_visit) {
        this.id_visit = id_visit;
    }
}
