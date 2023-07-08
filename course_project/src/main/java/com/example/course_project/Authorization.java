package com.example.course_project;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.*;
import java.util.Arrays;
import java.util.Base64;


public class Authorization {

    private Connection connection;

    private User user;

    public Authorization(String userLogin, String userPassword) throws Exception {

        this.connection = ConnectionTry.getConnection();
        user = new User();

        try {

            //String query = "SELECT * FROM users WHERE user_name='" + userLogin + "' AND user_password='" + this.getPassword(userPassword) + "'";
            String query = "SELECT * FROM users";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                if (resultSet.getString("user_password").equals(this.getPassword(userPassword)) &&
                        resultSet.getString("user_name").equals(userLogin)) {
                    user.setId(resultSet.getInt("id"));
                    user.setUser_role(resultSet.getInt("user_role"));
                    user.setUser_login(resultSet.getString("user_name"));
                    user.setUser_password(resultSet.getString("user_password"));
                    user.setUser_f(resultSet.getString("user_f"));
                    user.setUser_i(resultSet.getString("user_i"));
                    user.setUser_o(resultSet.getString("user_o"));
                    break;
                }
            }
            statement.close();
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    public int getRole() {
        return user.getUser_role();
    }

    private String getPassword(String password) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hashedPassword);
    }
}
