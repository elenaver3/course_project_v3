package com.example.course_project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VisitDishModel {
    private Connection connection;

    private ObservableList<Dish> visits_dishes;


    public VisitDishModel() throws Exception {
        this.connection = ConnectionTry.getConnection();

        visits_dishes = FXCollections.observableArrayList();

        Statement statement = connection.createStatement();
        String query = "SELECT visitDishes.id_visit AS visit_id, dishes.id AS id, dishes.name, amount\n" +
                "FROM dishes JOIN visitDishes ON dishes.id = visitDishes.id_visit;";
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            int visit_id = resultSet.getInt("visit_id");
            int amount = resultSet.getInt("amount");
            String name = resultSet.getString("name");
            Dish dish = new Dish(id, name, visit_id, amount);
            visits_dishes.add(dish);
        }

        statement.close();

    }

    private void updateVisitsDishesArray(int id_visit, int action) throws SQLException {
        String query = " ";
        PreparedStatement statement;
        ResultSet resultSet;
        switch (action) {
            case 1:
                query = "SELECT visitDishes.id_visit AS visit_id, dishes.id AS id, dishes.name, amount\n" +
                        "FROM dishes JOIN visitDishes ON dishes.id = visitDishes.id_visit WHERE visitDishes.id_visit = ?";
                statement = connection.prepareStatement(query);
                statement.setInt(1, id_visit);

                resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int visit_id = resultSet.getInt("visit_id");
                    int amount = resultSet.getInt("amount");
                    String name = resultSet.getString("name");
                    Dish dish = new Dish(id, name, visit_id, amount);
                    visits_dishes.add(dish);
                }

                statement.close();
                break;
            case 2:
                query = "SELECT visitDishes.id_visit AS visit_id, dishes.id AS id, dishes.name, amount\n" +
                        "FROM dishes JOIN visitDishes ON dishes.id = visitDishes.id_visit WHERE visitDishes.id_visit = ?";
                statement = connection.prepareStatement(query);
                statement.setInt(1, id_visit);

                resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int visit_id = resultSet.getInt("visit_id");
                    int amount = resultSet.getInt("amount");
                    String name = resultSet.getString("name");
                    for (int i = 0; i < visits_dishes.size(); i++) {
                        if (visits_dishes.get(i).getId() == id && visits_dishes.get(i).getId_visit() == visit_id) {
                            visits_dishes.set(i, new Dish(id, name, visit_id, amount));
                        }
                    }
                }

                statement.close();
                break;
            case 3:
                for (int i = 0; i < visits_dishes.size(); i++) {
                    if (visits_dishes.get(i).getId_visit() == id_visit) {
                        Dish dish = visits_dishes.get(i);
                        visits_dishes.remove(dish);
                        break;
                    }
                }
                break;
        }

    }


    public ObservableList<Dish> getVisitDishes() {
        return visits_dishes;
    }

    public void updateVisitDish(String visit_id, String dish_id, String amount) throws SQLException {
        boolean isNumber = false;
        boolean isNumber2 = false;
        if (!visit_id.isBlank()) {
            if (checkIfInt(visit_id)) {
                String sql = "SELECT id FROM visits WHERE id = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, Integer.parseInt(visit_id));

                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    if (id == Integer.parseInt(visit_id)) {
                        isNumber = true;
                        break;
                    }
                }
                statement.close();
            }
        }
        if (!dish_id.isBlank()) {
            if (checkIfInt(dish_id)) {
                String sql = "SELECT id FROM dishes WHERE id = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, Integer.parseInt(dish_id));

                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    if (id == Integer.parseInt(dish_id)) {
                        isNumber2 = true;
                        break;
                    }
                }
                statement.close();
            }
        }
        if (isNumber && isNumber2) {
            String sql = "SELECT id_visit, id_dish FROM visitDishes";
            sql += " WHERE id_visit = ? AND id_dish = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(visit_id));
            statement.setInt(2, Integer.parseInt(dish_id));

            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                statement.close();

                sql = "UPDATE visitDishes SET id_visit = ?, id_dish = ?, amount = ?";
                sql += " WHERE id_visit = ?";

                statement = connection.prepareStatement(sql);

                statement.setInt(1, Integer.parseInt(visit_id));
                statement.setInt(2, Integer.parseInt(dish_id));
                if (checkIfInt(amount))
                    statement.setInt(3, Integer.parseInt(amount));
                else
                    statement.setInt(3, 0);

                statement.setInt(4, Integer.parseInt(visit_id));

                statement.executeUpdate();
                statement.close();
                this.updateVisitsDishesArray(Integer.parseInt(visit_id), 2);
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

    public void deleteVisitDish(String visit_id, String dish_id) throws SQLException {
        //boolean isNumber = false;
        if (!visit_id.isBlank() && !dish_id.isBlank()) {
            String sql = "DELETE FROM visitDishes where id_visit = ? AND id_dish = ?";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, Integer.parseInt(visit_id));
            statement.setInt(2, Integer.parseInt(dish_id));

            statement.executeUpdate();
            statement.close();
            this.updateVisitsDishesArray(Integer.parseInt(visit_id), 3);
        }

    }

    public void insertVisitDish(String visit_id, String dish_id, String amount) throws SQLException {
        boolean isNumber = false;
        boolean isNumber2 = false;
        if (!visit_id.isBlank()) {
            if (checkIfInt(visit_id)) {
                String sql = "SELECT id FROM visits WHERE id = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, Integer.parseInt(visit_id));

                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    if (id == Integer.parseInt(visit_id)) {
                        isNumber = true;
                        break;
                    }
                }
                statement.close();
            }
        }
        if (!dish_id.isBlank()) {
            if (checkIfInt(dish_id)) {
                String sql = "SELECT id FROM dishes WHERE id = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, Integer.parseInt(dish_id));

                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    if (id == Integer.parseInt(dish_id)) {
                        isNumber2 = true;
                        break;
                    }
                }
                statement.close();
            }
        }
        if (isNumber && isNumber2) {
            String sql = "SELECT id_visit, id_dish FROM visitDishes";
            sql += " WHERE id_visit = ? AND id_dish = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(visit_id));
            statement.setInt(2, Integer.parseInt(dish_id));

            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                sql = "INSERT INTO visitDishes (id_visit, id_dish, amount) VALUES (?, ?, ?)";

                statement = connection.prepareStatement(sql);

                statement.setInt(1, Integer.parseInt(visit_id));
                statement.setInt(2, Integer.parseInt(dish_id));
                if (checkIfInt(amount))
                    statement.setInt(3, Integer.parseInt(amount));
                else
                    statement.setInt(3, 0);

                statement.executeUpdate();
                statement.close();
                this.updateVisitsDishesArray(Integer.parseInt(visit_id), 1);
            }
        }
    }

}
