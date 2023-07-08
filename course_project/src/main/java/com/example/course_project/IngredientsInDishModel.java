package com.example.course_project;

import java.sql.*;

public class IngredientsInDishModel{

    /*
    private Connection connection;
    private Posts ingredients;

    public IngredientsInDishModel(Connection connection) {

    }

    public IngredientsInDishModel(Connection connection, int dishId) throws Exception {
        this.connection = connection;

        ingredients = new Posts();

        String query = "SELECT ingredients.id, ingredients.name, measurementUnits.unit, ingredientsInDish.amount\n" +
                "FROM ingredients JOIN measurementUnits ON ingredients.id_MU = measurementUnits.id JOIN ingredientsInDish ON ingredients.id = ingredientsInDish.id\n" +
                "WHERE ingredientsInDish.id_dish = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, dishId);

        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            Post post = new Post();
            post.addValue("id", resultSet.getInt("id"));
            post.addValue("name", resultSet.getString("name"));
            post.addValue("measurementUnit", resultSet.getString("unit"));
            post.addValue("amount", resultSet.getString("amount"));
            ingredients.addPost(post);
        }
        statement.close();

    }

    public Posts getIngredients() {
        return ingredients;
    }

    public void updateIngredientsInDish(Dish dish, String new_name, String amount) throws SQLException {
        int id_ing = 1;
        String[] temp1 = new_name.split(";");
        String sql = "SELECT id FROM ingredients WHERE name = '" + temp1[0]  + "'";

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery(sql);
        if (resultSet.next()) {
            id_ing = resultSet.getInt("id");
            statement.close();
        }
        else {
            statement.close();
            sql = "INSERT INTO ingredients (name) VALUES (?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, new_name);

            statement.executeUpdate();
            statement.close();

            sql = "SELECT id FROM ingredients WHERE name = '" + temp1[0]  + "'";

            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery(sql);
            resultSet.next();
            id_ing = resultSet.getInt("id");
            statement.close();
        }

        sql = "UPDATE ingredientsInDish SET id_ingredient = ?,  amount = ?";
        sql += " WHERE id = ?";

        statement = connection.prepareStatement(sql);

        statement.setInt(1, id_ing);
        statement.setFloat(2, Float.parseFloat(amount));
        statement.setInt(3, dish.getId());

        statement.executeUpdate();
        statement.close();
    }

    public void deleteIngFromDishes(Ingredient ingredient) throws SQLException {
        String sql = "DELETE FROM ingredientsInDish where id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, ingredient.getId());

        statement.executeUpdate();
        statement.close();

    }

    public void insertIngInDish(Dish dish, String name, String amount) throws SQLException {
        int id_ing = 1;
        String[] temp1 = name.split(";");
        String sql = "SELECT id FROM ingredients WHERE name = '" + temp1[0]  + "'";

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery(sql);
        if (resultSet.next()) {
            id_ing = resultSet.getInt("id");
            statement.close();
        }
        else {
            statement.close();
            sql = "INSERT INTO ingredients (name) VALUES (?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, name);

            statement.executeUpdate();
            statement.close();

            sql = "SELECT id FROM ingredients WHERE name = '" + temp1[0]  + "'";

            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery(sql);
            resultSet.next();
            id_ing = resultSet.getInt("id");
            statement.close();
        }

        sql = "INSERT INTO ingredientsInDish (id_ingredient, id_dish, amount) VALUES (?, ?, ?)";

        statement = connection.prepareStatement(sql);
        statement.setString(1, name);
        statement.setInt(2, dish.getId());
        statement.setFloat(3, Float.parseFloat(amount));

        statement.executeUpdate();
        statement.close();
    }

     */
}
