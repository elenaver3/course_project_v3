package com.example.course_project;

import java.sql.*;

public class ConnectionTry {

    private static final String dbURL = "jdbc:mysql://std-mysql.ist.mospolytech.ru:3306/std_2277_restaurant";
    private static final String dbName = "std_2277_restaurant";
    private static final String dbPasswd = "restaurant2052";

    private static Connection connection = null;
    private ConnectionTry() {}
    private Connection startConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(dbURL, dbName, dbPasswd);
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return connection;
    }

    public static Connection getConnection() throws Exception {
        if (connection == null)
            connection = new ConnectionTry().startConnection();
        return connection;
    }

}
