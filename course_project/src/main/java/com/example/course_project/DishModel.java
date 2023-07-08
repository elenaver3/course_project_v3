package com.example.course_project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class DishModel {

    private ObservableList<Dish> dishes;
    private Connection connection;

    public DishModel() throws Exception {
        this.connection = ConnectionTry.getConnection();

        dishes = FXCollections.observableArrayList();

        Statement statement = connection.createStatement();
        String query = "SELECT * FROM dishes";
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            Dish dish = new Dish(id, name);
            dishes.add(dish);
        }
        /*
        statement = connection.createStatement();
        query = "SELECT dishes.name AS dish_name, ingredients.name AS inredients_name, amount\n" +
                "FROM dishes JOIN ingredientsInDish ON dishes.id = ingredientsInDish.id_dish JOIN ingredients ON ingredients.id = ingredientsInDish.id_ingredient";
        resultSet = statement.executeQuery(query);

         */

    }

    private int getNewDishId() throws SQLException {
        String query = "SELECT MAX(id) AS id FROM dishes";
        PreparedStatement statement = connection.prepareStatement(query);

        ResultSet resultSet = statement.executeQuery();

        resultSet.next();
        return resultSet.getInt("id");
    }

    private void updateDishesArray(int id_dish, int action) throws SQLException {
        String query = " ";
        PreparedStatement statement;
        ResultSet resultSet;
        switch (action) {
            case 1:
                query = "SELECT * FROM dishes WHERE id = ?";
                statement = connection.prepareStatement(query);
                statement.setInt(1, id_dish);

                resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    Dish dish = new Dish(id, name);
                    dishes.add(dish);
                }

                statement.close();
                break;
            case 2:
                query = "SELECT * FROM dishes WHERE id = ?";
                statement = connection.prepareStatement(query);
                statement.setInt(1, id_dish);

                resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    for (int i = 0; i < dishes.size(); i++) {
                        if (dishes.get(i).getId() == id) {
                            dishes.set(i, new Dish(id, name));
                        }
                    }
                }

                statement.close();
                break;
            case 3:
                for (int i = 0; i < dishes.size(); i++) {
                    if (dishes.get(i).getId() == id_dish) {
                        Dish dish = dishes.get(i);
                        dishes.remove(dish);
                        break;
                    }
                }
                break;
        }

    }

    public Dish getDish(int id_dish) throws SQLException {
        Dish dish = new Dish();
        for (Dish one_dish: dishes) {
            if (one_dish.getId() == id_dish) {
                dish = one_dish;
                break;
            }
        }
        return dish;
    }

    public ObservableList<Dish> getDishes() {
        return dishes;
    }

    public void updateDishes(int dish_id, String new_name) throws SQLException {
        String sql = "UPDATE dishes SET name = ?";
        sql += " WHERE id = ?";

        Dish dish = getDish(dish_id);

        PreparedStatement statement = connection.prepareStatement(sql);

        if (dish.getId() != -1) {
            if (new_name.isBlank())
                statement.setString(1, dish.getName());
            else
                statement.setString(1, new_name);

            statement.setInt(2, dish_id);

            statement.executeUpdate();
            statement.close();
        }
        this.updateDishesArray(dish_id, 2);
    }

    public void deleteDish(int dish_id) throws SQLException {
        String sql = "DELETE FROM dishes where id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, dish_id);

        statement.executeUpdate();
        statement.close();
        this.updateDishesArray(dish_id, 3);
    }

    public void insertDish(String name) throws SQLException {

        String sql = "INSERT INTO dishes (name) VALUES (?)";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, name);

        statement.executeUpdate();
        statement.close();
        this.updateDishesArray(getNewDishId(), 1);
    }
}
