package com.example.course_project;

import java.sql.*;

public class ConnectionTry {

    private static String dbURL = "jdbc:mysql://std-mysql.ist.mospolytech.ru:3306/std_2277_restaurant";
    private static String dbName = "std_2277_restaurant";
    private static String dbPasswd = "restaurant2052";

    private static Connection connection = null;
    private ConnectionTry() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection(dbURL, dbName, dbPasswd);

        }
        catch (Exception e) {
            System.out.println(e);
        }
    }


    private Connection startConnection() throws Exception {

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


    protected void disconnect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
