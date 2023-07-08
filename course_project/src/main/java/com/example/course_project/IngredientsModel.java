package com.example.course_project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class IngredientsModel {
    private Connection connection;
    private ObservableList<Ingredient> ingredients;

    public IngredientsModel() throws Exception {
        this.connection = ConnectionTry.getConnection();

        ingredients = FXCollections.observableArrayList();

        Statement statement = connection.createStatement();
        String query = "SELECT ingredients.id, ingredients.name, measurementUnits.unit, ingredients.amount\n" +
                "FROM ingredients JOIN measurementUnits ON ingredients.id_MU = measurementUnits.id";
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String measurementUnit = resultSet.getString("unit");
            int amount = resultSet.getInt("amount");
            Ingredient ingredient = new Ingredient(id, name, measurementUnit, amount);
            ingredients.add(ingredient);
        }

    }

    private int getNewIngredientId() throws SQLException {
        String query = "SELECT MAX(id) AS id FROM ingredients";
        PreparedStatement statement = connection.prepareStatement(query);

        ResultSet resultSet = statement.executeQuery();

        resultSet.next();
        return resultSet.getInt("id");
    }

    private void updateIngredientsArray(int id_ing, int action) throws SQLException {
        String query = " ";
        PreparedStatement statement;
        ResultSet resultSet;
        switch (action) {
            case 1:
                query = "SELECT ingredients.id, ingredients.name, measurementUnits.unit, ingredients.amount\n" +
                        "FROM ingredients JOIN measurementUnits ON ingredients.id_MU = measurementUnits.id WHERE ingredients.id=?";
                statement = connection.prepareStatement(query);
                statement.setInt(1, id_ing);

                resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String measurementUnit = resultSet.getString("unit");
                    int amount = resultSet.getInt("amount");
                    Ingredient ingredient = new Ingredient(id, name, measurementUnit, amount);
                    ingredients.add(ingredient);
                }

                statement.close();
                break;
            case 2:
                query = "SELECT ingredients.id, ingredients.name, measurementUnits.unit, ingredients.amount\n" +
                        "FROM ingredients JOIN measurementUnits ON ingredients.id_MU = measurementUnits.id WHERE ingredients.id=?";
                statement = connection.prepareStatement(query);
                statement.setInt(1, id_ing);

                resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String measurementUnit = resultSet.getString("unit");
                    int amount = resultSet.getInt("amount");
                    for (int i = 0; i < ingredients.size(); i++) {
                        if (ingredients.get(i).getId() == id) {
                            ingredients.set(i, new Ingredient(id, name, measurementUnit, amount));
                        }
                    }
                }

                statement.close();
                break;
            case 3:
                for (int i = 0; i < ingredients.size(); i++) {
                    if (ingredients.get(i).getId() == id_ing) {
                        Ingredient ingredient = ingredients.get(i);
                        ingredients.remove(ingredient);
                        break;
                    }
                }
                break;
        }

    }

    public Ingredient getIngredient(int id_ing) throws SQLException {
        Ingredient ingredient = new Ingredient();
        for (Ingredient one_ing: ingredients) {
            if (one_ing.getId() == id_ing) {
                ingredient = one_ing;
                break;
            }
        }
        return ingredient;
    }

    public ObservableList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void updateIngredients(int ing_id, String new_name, String mUnit, String amount) throws SQLException {
        int id_unit = -1;
        String sql = "SELECT * FROM measurementUnits";

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            String unit_name = resultSet.getString("unit");
            if (unit_name.equals(mUnit)) {
                id_unit = resultSet.getInt("id");
                statement.close();
                break;
            }
        }
        if (id_unit == -1) {
            statement.close();
            sql = "INSERT INTO measurementUnits (unit) VALUES (?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, mUnit);

            statement.executeUpdate();
            statement.close();

            sql = "SELECT MAX(id) AS id FROM measurementUnits";

            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery(sql);
            resultSet.next();
            id_unit = resultSet.getInt("id");
            statement.close();
        }

        sql = "UPDATE ingredients SET name = ?, id_MU = ?";
        sql += " WHERE id = ?";

        Ingredient ingredient = getIngredient(ing_id);

        statement = connection.prepareStatement(sql);

        if (ingredient.getId() != -1) {
            if (new_name.isBlank())
                statement.setString(1, ingredient.getName());
            else
                statement.setString(1, new_name);

            statement.setInt(2, id_unit);
            statement.setInt(3, ing_id);

            statement.executeUpdate();
            statement.close();
        }
        this.updateIngredientsArray(ing_id, 2);
    }

    public void deleteIngredients(int ing_id) throws SQLException {
        String sql = "DELETE FROM ingredients where id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, ing_id);

        statement.executeUpdate();
        statement.close();
        this.updateIngredientsArray(ing_id, 3);

    }

    public void insertIngredients(String name, String mUnit, String amount) throws SQLException {
        int id_unit = 1;
        String sql = "SELECT * FROM measurementUnits";

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery(sql);
        if (resultSet.next()) {
            String unit_name = resultSet.getString("unit");
            if (unit_name == mUnit)
                id_unit = resultSet.getInt("id");
            statement.close();
        }
        else {
            statement.close();
            sql = "INSERT INTO measurementUnits (unit) VALUES (?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, mUnit);

            statement.executeUpdate();
            statement.close();

            sql = "SELECT MAX(id) FROM measurementUnits";

            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery(sql);
            resultSet.next();
            id_unit = resultSet.getInt("id");
            statement.close();
        }

        sql = "INSERT INTO ingredients (name, id_MU, amount) VALUES (?, ?, ?)";

        statement = connection.prepareStatement(sql);
        statement.setString(1, name);
        statement.setInt(2, id_unit);
        if (checkIfInt(amount))
            statement.setInt(3, Integer.parseInt(amount));
        else
            statement.setInt(3, 0);

        statement.executeUpdate();
        statement.close();
        this.updateIngredientsArray(getNewIngredientId(), 1);
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
}
