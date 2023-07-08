package com.example.course_project;

public class Visit {
    private int id;
    private String time_start;
    private String time_end;
    private String table;
    private String waiter;
    private int staff_id;
    private int table_id;

    public Visit() {
        this.id = -1;
        this.time_start = " ";
        this.time_end = " ";
        this.table = " ";
        this.waiter = " ";
        this.staff_id = -1;
        this.table_id = -1;
    }

    public Visit(int id, String dateTimeStart, String dateTimeEnd, String table, String waiter, int id_staff, int id_table) {
        this.id = id;
        this.time_start = dateTimeStart;
        this.time_end = dateTimeEnd;
        this.table = table;
        this.waiter = waiter;
        this.staff_id = id_staff;
        this.table_id = id_table;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime_end() {
        return time_end;
    }

    public String getTime_start() {
        return time_start;
    }

    public void setTime_end(String time_end) {
        this.time_end = time_end;
    }

    public void setTime_start(String time_start) {
        this.time_start = time_start;
    }

    public int getStaff_id() {
        return staff_id;
    }


    public int getTable_id() {
        return table_id;
    }

    public void setStaff_id(int staff_id) {
        this.staff_id = staff_id;
    }

    public void setTable_id(int table_id) {
        this.table_id = table_id;
    }


    public void setTable(String table) {
        this.table = table;
    }
    public void setWaiter(String waiter) {
        this.waiter = waiter;
    }


    public String getTable() {
        return table;
    }

    public String getWaiter() {
        return waiter;
    }

}
