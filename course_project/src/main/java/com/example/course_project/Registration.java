package com.example.course_project;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;


public class Registration {

    private Connection connection;


    public Registration() throws Exception {
        connection = ConnectionTry.getConnection();
    }

    public String tryReg(String user_f, String user_i, String user_o, String user_name, String user_password, String password2) throws Exception {
        String query = "SELECT * FROM users";

        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            if (resultSet.getString("user_name").equals(user_name)) {
                return "Такой пользователь уже существует";
            }
        }
        statement.close();

        if (!user_password.equals(password2)) {
            return "Пароли не совпадают";
        }

        query = "INSERT INTO users (user_name, user_f, user_i, user_o, user_password) VALUES (?, ?, ?, ?, ?)";

        statement = connection.prepareStatement(query);
        statement.setString(1, user_name);
        if (user_f.isBlank())
            statement.setString(2, "не введено");
        else
            statement.setString(2, user_f);
        if (user_f.isBlank())
            statement.setString(3, "не введено");
        else
            statement.setString(3, user_i);
        if (user_f.isBlank())
            statement.setString(4, "не введено");
        else
            statement.setString(4, user_o);
        statement.setString(5, getPassword(user_password));

        statement.executeUpdate();
        statement.close();
        return "Регистрация успешна";
    }

    private String getPassword(String password) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hashedPassword);
    }

}
