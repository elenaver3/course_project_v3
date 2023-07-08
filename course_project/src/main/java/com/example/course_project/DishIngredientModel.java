package com.example.course_project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class DishIngredientModel {

    private ObservableList<Ingredient> ingredients;
    private Connection connection;

    public DishIngredientModel() throws Exception {
        this.connection = ConnectionTry.getConnection();

        ingredients = FXCollections.observableArrayList();

        Statement statement = connection.createStatement();
        String query = "SELECT ingredientsInDish.id_dish, ingredients.id, ingredients.name, ingredientsInDish.amount\n" +
                "FROM ingredientsInDish JOIN ingredients ON ingredientsInDish.id_ingredient = ingredients.id";
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            int id_dish = resultSet.getInt("id_dish");
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            int amount = resultSet.getInt("amount");
            Ingredient ingredient = new Ingredient(id, name, amount, id_dish);
            ingredients.add(ingredient);
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

    private void updateIngredientsInDishArray(int id_dish, int action) throws SQLException {
        String query = " ";
        PreparedStatement statement;
        ResultSet resultSet;
        switch (action) {
            case 1:
                query = "SELECT ingredientsInDish.id_dish, ingredients.id, ingredients.name, ingredientsInDish.amount\n" +
                        "FROM ingredientsInDish JOIN ingredients ON ingredientsInDish.id_ingredient = ingredients.ids WHERE id_dish = ?";
                statement = connection.prepareStatement(query);
                statement.setInt(1, id_dish);

                resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    int dish_id = resultSet.getInt("id_dish");
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    int amount = resultSet.getInt("amount");
                    Ingredient ingredient = new Ingredient(id, name, amount, dish_id);
                    ingredients.add(ingredient);
                }

                statement.close();
                break;
            case 2:
                query = "SELECT ingredientsInDish.id_dish, ingredients.id, ingredients.name, ingredientsInDish.amount\n" +
                        "FROM ingredientsInDish JOIN ingredients ON ingredientsInDish.id_ingredient = ingredients.ids WHERE id_dish = ?";
                statement = connection.prepareStatement(query);
                statement.setInt(1, id_dish);

                resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    int amount = resultSet.getInt("amount");
                    for (int i = 0; i < ingredients.size(); i++) {
                        if (ingredients.get(i).getId() == id) {
                            ingredients.set(i, new Ingredient(id, name, amount, id_dish));
                        }
                    }
                }

                statement.close();
                break;
            case 3:
                for (int i = 0; i < ingredients.size(); i++) {
                    if (ingredients.get(i).getId_dish() == id_dish) {
                        Ingredient ingredient = ingredients.get(i);
                        ingredients.remove(ingredient);
                        break;
                    }
                }
                break;
        }

    }

    public ObservableList<Ingredient> getIngredientsInDish() {
        return ingredients;
    }

    public void updateIngredientsInDish(String id_dish, String id_ingredient, String new_amount) throws SQLException {
        boolean isNumber = false;
        boolean isNumber2 = false;
        if (!id_dish.isBlank()) {
            if (checkIfInt(id_dish)) {
                String sql = "SELECT id FROM dishes WHERE id = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, Integer.parseInt(id_dish));

                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    if (id == Integer.parseInt(id_dish)) {
                        isNumber = true;
                        break;
                    }
                }
                statement.close();
            }
        }
        if (!id_ingredient.isBlank()) {
            if (checkIfInt(id_ingredient)) {
                String sql = "SELECT id FROM ingredients WHERE id = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, Integer.parseInt(id_ingredient));

                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    if (id == Integer.parseInt(id_dish)) {
                        isNumber2 = true;
                        break;
                    }
                }
                statement.close();
            }
        }
        if (isNumber && isNumber2) {
            String sql = "SELECT id_dish, id_ingredient FROM ingredientsInDish";
            sql += " WHERE id_dish = ? AND id_ingredient = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(id_dish));
            statement.setInt(2, Integer.parseInt(id_ingredient));

            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                statement.close();

                sql = "UPDATE ingredientsInDish SET id_dish = ?, id_ingredient = ?, amount = ?";
                sql += " WHERE id_dish = ?";

                statement = connection.prepareStatement(sql);

                statement.setInt(1, Integer.parseInt(id_dish));
                statement.setInt(2, Integer.parseInt(id_ingredient));
                if (!new_amount.isBlank()) {
                    if (checkIfInt(new_amount))
                        statement.setInt(3, Integer.parseInt(new_amount));
                    else
                        statement.setInt(3, 0);
                }

                statement.setInt(4, Integer.parseInt(id_dish));

                statement.executeUpdate();
                statement.close();
                this.updateIngredientsInDishArray(Integer.parseInt(id_dish), 2);
            }

        }
    }

    public void deleteIngredientsInDish(String id_dish, String id_ing) throws SQLException {
        String sql = "DELETE FROM ingredientsInDish where id_ingredient = ? AND id_dish = ?";

        if (!id_ing.isBlank() && !id_dish.isBlank()) {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, Integer.parseInt(id_ing));
            statement.setInt(2, Integer.parseInt(id_dish));

            statement.executeUpdate();
            statement.close();
            this.updateIngredientsInDishArray(Integer.parseInt(id_dish), 3);
        }

    }

    public void insertIngredientsInDish(String id_dish, String id_ing, String amount) throws SQLException {
        boolean isNumber = false;
        boolean isNumber2 = false;
        if (!id_dish.isBlank()) {
            if (checkIfInt(id_dish)) {
                String sql = "SELECT id FROM dishes WHERE id = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, Integer.parseInt(id_dish));

                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    if (id == Integer.parseInt(id_dish)) {
                        isNumber = true;
                        break;
                    }
                }
                statement.close();
            }
        }
        if (!id_ing.isBlank()) {
            if (checkIfInt(id_ing)) {
                String sql = "SELECT id FROM ingredients WHERE id = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, Integer.parseInt(id_ing));

                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    if (id == Integer.parseInt(id_dish)) {
                        isNumber2 = true;
                        break;
                    }
                }
                statement.close();
            }
        }
        if (isNumber && isNumber2) {
            String sql = "SELECT id_ingredient, id_dish FROM ingredientsInDish";
            sql += " WHERE id_ingredient = ? AND id_dish = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(id_ing));
            statement.setInt(2, Integer.parseInt(id_dish));

            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                sql = "INSERT INTO ingredientsInDish (id_dish, id_ingredient, amount) VALUES (?, ?, ?)";

                statement = connection.prepareStatement(sql);

                statement.setInt(1, Integer.parseInt(id_dish));
                statement.setInt(2, Integer.parseInt(id_ing));
                if (checkIfInt(amount) && !amount.isBlank())
                    statement.setInt(3, Integer.parseInt(amount));
                else
                    statement.setInt(3, 0);

                statement.executeUpdate();
                statement.close();
                this.updateIngredientsInDishArray(Integer.parseInt(id_dish), 1);
            }
        }
    }
}
