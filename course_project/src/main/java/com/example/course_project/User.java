package com.example.course_project;

public class User {
    private int id;
    private String user_login;
    private String user_password;
    private int user_role;
    private String user_f;
    private String user_i;
    private String user_o;

    private static int currentUserRole = 4;
    private static int currentUserId = -1;

    public User() {
        this.id = -1;
        this.user_role = -1;
        this.user_login = "";
        this.user_password = "";
        this.user_f = "";
        this.user_i = "";
        this.user_o = "";
    }

    public User(int id, int role, String login, String password, String f, String i, String o) {
        this.id = id;
        user_role = role;
        user_login = login;
        user_password = password;
        user_f = f;
        user_i = i;
        user_o = o;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUser_role(int user_role) {
        this.user_role = user_role;
    }

    public void setUser_login(String user_login) {
        this.user_login = user_login;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public void setUser_f(String user_f) {
        this.user_f = user_f;
    }

    public void setUser_i(String user_i) {
        this.user_i = user_i;
    }

    public void setUser_o(String user_o) {
        this.user_o = user_o;
    }

    public int getId() {
        return id;
    }

    public int getUser_role() {
        return user_role;
    }

    public String getUser_login() {
        return user_login;
    }

    public String getUser_password() {
        return user_password;
    }

    public String getUser_f() {
        return user_f;
    }

    public String getUser_i() {
        return user_i;
    }

    public String getUser_o() {
        return user_o;
    }

    public static int getCurrentUserRole() {
        return currentUserRole;
    }

    public static void setCurrentUserRole(int role) {
        if (role > 0 && role < 5)
            currentUserRole = role;
    }

    public static int getCurrentUserId() {
        return currentUserId;
    }

    public static void setCurrentUserId(int currentUserId) {
        User.currentUserId = currentUserId;
    }
}
