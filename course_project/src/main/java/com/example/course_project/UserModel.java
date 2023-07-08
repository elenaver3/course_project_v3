package com.example.course_project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.*;
import java.util.Base64;

public class UserModel {
    private ObservableList<User> users;
    private Connection connection;

    public UserModel() throws Exception {
        this.connection = ConnectionTry.getConnection();

        users = FXCollections.observableArrayList();

        Statement statement = connection.createStatement();
        String query = "SELECT * FROM users";
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String user_f = resultSet.getString("user_f");
            String user_i = resultSet.getString("user_i");
            String user_o = resultSet.getString("user_o");
            int user_role = resultSet.getInt("user_role");
            String user_name = resultSet.getString("user_name");
            String user_password = resultSet.getString("user_password");
            User user = new User(id, user_role, user_name, user_password, user_f, user_i, user_o);
            users.add(user);
        }
    }

    private int getNewUserId() throws SQLException {
        String query = "SELECT MAX(id) AS id FROM users";
        PreparedStatement statement = connection.prepareStatement(query);

        ResultSet resultSet = statement.executeQuery();

        resultSet.next();
        return resultSet.getInt("id");
    }

    private void updateUsersArray(int id_user, int action) throws SQLException {
        String query = " ";
        PreparedStatement statement;
        ResultSet resultSet;
        switch (action) {
            case 1:
                query = "SELECT * FROM users WHERE id = ?";
                statement = connection.prepareStatement(query);
                statement.setInt(1, id_user);

                resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String user_f = resultSet.getString("user_f");
                    String user_i = resultSet.getString("user_i");
                    String user_o = resultSet.getString("user_o");
                    int user_role = resultSet.getInt("user_role");
                    String user_name = resultSet.getString("user_name");
                    String user_password = resultSet.getString("user_password");
                    User user = new User(id, user_role, user_name, user_password, user_f, user_i, user_o);
                    users.add(user);
                }

                statement.close();
                break;
            case 2:
                query = "SELECT * FROM users WHERE id = ?";
                statement = connection.prepareStatement(query);
                statement.setInt(1, id_user);

                resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String user_f = resultSet.getString("user_f");
                    String user_i = resultSet.getString("user_i");
                    String user_o = resultSet.getString("user_o");
                    int user_role = resultSet.getInt("user_role");
                    String user_login = resultSet.getString("user_name");
                    String user_password = resultSet.getString("user_password");
                    for (int i = 0; i < users.size(); i++) {
                        if (users.get(i).getId() == id) {
                            users.set(i, new User(id, user_role, user_login, user_password, user_f, user_i, user_o));
                        }
                    }
                }

                statement.close();
                break;
            case 3:
                for (int i = 0; i < users.size(); i++) {
                    if (users.get(i).getId() == id_user) {
                        User user = users.get(i);
                        users.remove(user);
                        break;
                    }
                }
                break;
        }

    }

    public User getUser(int id_user) throws SQLException {
        User user = new User();
        for (User one_user: users) {
            if (one_user.getId() == id_user) {
                user = one_user;
                break;
            }
        }
        return user;
    }

    public ObservableList<User> getUsers() {
        return users;
    }

    public void updateUsers(int id_user, String new_f, String new_i, String new_o, String new_role, String new_login, String new_password) throws Exception {
        String sql = "SELECT * FROM users";

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery(sql);

        boolean isExists = false;
        while (resultSet.next()) {
            if (resultSet.getString("user_name").equals(new_login)) {
                isExists = true;
                break;
            }
        }
        statement.close();

        if (!isExists) {
            sql = "UPDATE users SET user_name = ?, user_password = ?, user_role = ?, user_f = ?, user_i = ?, user_o = ?";
            sql += " WHERE id = ?";

            User user = getUser(id_user);

            if (user.getId() != -1) {
                statement = connection.prepareStatement(sql);
                if (new_login.isBlank())
                    statement.setString(1, user.getUser_login());
                else
                    statement.setString(1, new_login);
                if (new_password.isBlank())
                    statement.setString(2, user.getUser_password());
                else
                    statement.setString(2, this.getPassword(new_password));
                if (new_role.isBlank() || !checkIfInt(new_role))
                    statement.setInt(3, 4);
                else {
                    if (new_role.charAt(0) > '0' && new_role.charAt(0) < '5')
                        statement.setInt(3, Integer.parseInt(new_role));
                    else {
                        statement.setInt(3, 4);
                    }
                }
                if (new_f.isBlank())
                    statement.setString(4, user.getUser_f());
                else
                    statement.setString(4, new_f);
                if (new_i.isBlank())
                    statement.setString(5, user.getUser_i());
                else
                    statement.setString(5, new_i);
                if (new_o.isBlank())
                    statement.setString(6, user.getUser_o());
                else
                    statement.setString(6, new_o);

                statement.setInt(7, id_user);

                statement.executeUpdate();
                statement.close();
                this.updateUsersArray(id_user, 2);
            }
        }
    }

    private boolean checkIfInt(String number) {
        for (int i = 0; i < number.length(); i++) {
            char c = number.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

    public void deleteUser(int id_user) throws SQLException {
        String sql = "DELETE FROM users where id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id_user);

        statement.executeUpdate();
        statement.close();
        this.updateUsersArray(id_user, 3);
    }

    public void insertUser(String new_f, String new_i, String new_o, String new_role, String new_login, String new_password) throws Exception {
        String sql = "SELECT * FROM users";

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery(sql);

        boolean isExists = false;
        while (resultSet.next()) {
            if (resultSet.getString("user_name").equals(new_login)) {
                isExists = true;
                break;
            }
        }
        statement.close();

        if (!isExists) {
            sql = "INSERT INTO users (user_name, user_password, user_role, user_f, user_i, user_o) VALUES (?, ?, ?, ?, ?, ?)";

            statement = connection.prepareStatement(sql);
            statement.setString(1, new_login);
            statement.setString(2, this.getPassword(new_password));
            if (new_role.isBlank() || !checkIfInt(new_role))
                statement.setInt(3, 4);
            else {
                if (new_role.charAt(0) > '0' && new_role.charAt(0) < '5')
                    statement.setInt(3, Integer.parseInt(new_role));
                else {
                    statement.setInt(3, 4);
                }
            }
            statement.setString(4, new_f);
            statement.setString(5, new_i);
            statement.setString(6, new_o);

            statement.executeUpdate();
            statement.close();
            this.updateUsersArray(getNewUserId(), 1);
        }
    }

    private String getPassword(String password) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hashedPassword);
    }

    public void updateUsers(int id_user, String new_f, String new_i, String new_o, String new_password, String new_password2) throws Exception {
        if (new_password.equals(new_password2)) {
            String sql = "SELECT * FROM users";

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery(sql);


            sql = "UPDATE users SET user_password = ?, user_f = ?, user_i = ?, user_o = ?";
            sql += " WHERE id = ?";

            User user = getUser(id_user);

            if (user.getId() != -1) {
                statement = connection.prepareStatement(sql);
                if (new_password.isBlank())
                    statement.setString(1, user.getUser_password());
                else
                    statement.setString(1, this.getPassword(new_password));
                if (new_f.isBlank())
                    statement.setString(2, user.getUser_f());
                else
                    statement.setString(2, new_f);
                if (new_i.isBlank())
                    statement.setString(3, user.getUser_i());
                else
                    statement.setString(3, new_i);
                if (new_o.isBlank())
                    statement.setString(4, user.getUser_o());
                else
                    statement.setString(4, new_o);

                statement.setInt(5, id_user);

                statement.executeUpdate();
                statement.close();
                this.updateUsersArray(id_user, 2);
            }
        }

    }
}
