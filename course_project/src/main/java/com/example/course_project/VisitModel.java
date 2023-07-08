package com.example.course_project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VisitModel {
    private Connection connection;

    private ObservableList<Visit> visits;

    private ObservableList<Client> clients;
    private ObservableList<Dish> dishes;

    public VisitModel() throws Exception {
        this.connection = ConnectionTry.getConnection();

        visits = FXCollections.observableArrayList();
        clients = FXCollections.observableArrayList();
        dishes = FXCollections.observableArrayList();

        Statement statement = connection.createStatement();
        String query = "SELECT visits.id, visits.time_start, visits.time_end, tables.table_number, CONCAT(staff.last_name, \" \", staff.first_name) AS waiter, tables.id AS table_id, staff.id AS staff_id\n" +
                "FROM visits JOIN tables ON visits.id_table = tables.id JOIN staff ON staff.id = visits.id_staff;";
        //String query = "SELECT visits.id, visits.time_start, visits.time_end, id_table, id_staff\n" +
        //                "FROM visits;";
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String time_start = resultSet.getString("time_start");
            String time_end = resultSet.getString("time_end");
            String table_number = resultSet.getString("table_number");
            String waiter = resultSet.getString("waiter");
            int staff_id = resultSet.getInt("staff_id");
            int table_id = resultSet.getInt("table_id");
            Visit visit = new Visit(id, time_start, time_end, table_number, waiter, staff_id, table_id);
            visits.add(visit);
        }
        statement.close();

        statement = connection.createStatement();
        query = "SELECT visitClients.id_visit AS visit_id, clients.id AS id, clients.last_name, clients.first_name, clients.second_name\n" +
                "FROM clients JOIN visitClients ON clients.id = visitClients.id_client;";
        resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            int visit_id = resultSet.getInt("visit_id");
            String last_name = resultSet.getString("last_name");
            String first_name = resultSet.getString("first_name");
            String second_name = resultSet.getString("second_name");
            Client client = new Client(id, last_name, first_name, second_name, visit_id);
            clients.add(client);
        }

        statement.close();

        statement = connection.createStatement();
        query = "SELECT visitDishes.id_visit AS visit_id, dishes.id AS id, dishes.name, amount\n" +
                "FROM dishes JOIN visitDishes ON dishes.id = visitDishes.id_visit;";
        resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            int visit_id = resultSet.getInt("visit_id");
            int amount = resultSet.getInt("amount");
            String name = resultSet.getString("name");
            Dish dish = new Dish(id, name, visit_id, amount);
            dishes.add(dish);
        }

        statement.close();
    }

    private int getNewVisitId() throws SQLException {
        String query = "SELECT MAX(id) AS id FROM visits";
        PreparedStatement statement = connection.prepareStatement(query);

        ResultSet resultSet = statement.executeQuery();

        resultSet.next();
        return resultSet.getInt("id");
    }

    private void updateVisitsArray(int id_visit, int action) throws SQLException {
        String query = " ";
        PreparedStatement statement;
        ResultSet resultSet;
        switch (action) {
            case 1:
                query = "SELECT visits.id, visits.time_start, visits.time_end, tables.table_number, CONCAT(staff.last_name, \" \", staff.first_name) AS waiter, tables.id AS id_table, staff.id AS id_staff\n" +
                        "FROM visits JOIN tables ON visits.id_table = tables.id JOIN staff ON staff.id = visits.id_staff WHERE visits.id = ?;";
                statement = connection.prepareStatement(query);
                statement.setInt(1, id_visit);

                resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String time_start = resultSet.getString("time_start");
                    String time_end = resultSet.getString("time_end");
                    String table_number = resultSet.getString("table_number");
                    String waiter = resultSet.getString("waiter");
                    int staff_id = resultSet.getInt("id_staff");
                    int table_id = resultSet.getInt("id_table");
                    Visit visit = new Visit(id, time_start, time_end, table_number, waiter, staff_id, table_id);
                    visits.add(visit);
                }

                statement.close();
                break;
            case 2:
                query = "SELECT visits.id, visits.time_start, visits.time_end, tables.table_number, CONCAT(staff.last_name, \" \", staff.first_name) AS waiter, tables.id AS id_table, staff.id AS id_staff\n" +
                        "FROM visits JOIN tables ON visits.id_table = tables.id JOIN staff ON staff.id = visits.id_staff WHERE visits.id = ?;";
                statement = connection.prepareStatement(query);
                statement.setInt(1, id_visit);

                resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String time_start = resultSet.getString("time_start");
                    String time_end = resultSet.getString("time_end");
                    String table_number = resultSet.getString("table_number");
                    String waiter = resultSet.getString("waiter");
                    int staff_id = resultSet.getInt("id_staff");
                    int table_id = resultSet.getInt("id_table");
                    for (int i = 0; i < visits.size(); i++) {
                        if (visits.get(i).getId() == id) {
                            visits.set(i, new Visit(id, time_start, time_end, table_number, waiter, staff_id, table_id));
                        }
                    }
                }

                statement.close();
                break;
            case 3:
                for (int i = 0; i < visits.size(); i++) {
                    if (visits.get(i).getId() == id_visit) {
                        Visit visit = visits.get(i);
                        visits.remove(visit);
                        break;
                    }
                }
                break;
        }

    }

    public Visit getVisit(int id_visit) throws SQLException {
        Visit visit = new Visit();
        for (Visit one_visit: visits) {
            if (one_visit.getId() == id_visit) {
                visit = one_visit;
                break;
            }
        }
        return visit;
    }

    public ObservableList<Visit> getVisits() {
        return visits;
    }

    public void updateVisit(int visit_id, String id_table, String id_staff) throws SQLException {
        boolean isNumber = false;
        boolean isNumber2 = false;
        if (!id_table.isBlank()) {
            if (checkIfInt(id_table)) {
                String sql = "SELECT id FROM tables WHERE id = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, Integer.parseInt(id_table));

                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    if (id == Integer.parseInt(id_table)) {
                        isNumber = true;
                        break;
                    }
                }
                statement.close();
            }
        }
        if (!id_staff.isBlank()) {
            if (checkIfInt(id_staff)) {
                String sql = "SELECT id FROM staff WHERE id = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, Integer.parseInt(id_staff));

                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    if (id == Integer.parseInt(id_staff)) {
                        isNumber2 = true;
                        break;
                    }
                }
                statement.close();
            }
        }

        String sql = "UPDATE visits SET id_table = ?, id_staff = ?";
        sql += " WHERE id = ?";

        Visit visit = getVisit(visit_id);

        PreparedStatement statement = connection.prepareStatement(sql);

        if (visit.getId() != -1) {
            if (isNumber) {
                statement.setInt(1, Integer.parseInt(id_table));
            }
            else {
                statement.setInt(1, 1);
            }
            if (isNumber2) {
                statement.setInt(2, Integer.parseInt(id_staff));
            }
            else {
                statement.setInt(2, 1);
            }

            statement.setInt(3, visit_id);

            statement.executeUpdate();
            statement.close();
            this.updateVisitsArray(visit_id, 2);
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

    public void deleteVisit(int visit_id) throws SQLException {
        String sql = "DELETE FROM visits where id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, visit_id);

        statement.executeUpdate();
        statement.close();
        this.updateVisitsArray(visit_id, 3);
    }

    public void insertVisit(String id_table, String id_staff) throws SQLException {

        java.util.Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String formattedDate = sdf.format(date);

        boolean isNumber = false;
        boolean isNumber2 = false;

        if (!id_table.isBlank()) {
            if (checkIfInt(id_table)) {
                String sql = "SELECT id FROM tables WHERE id = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, Integer.parseInt(id_table));

                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    if (id == Integer.parseInt(id_table)) {
                        isNumber = true;
                        break;
                    }
                }
                statement.close();
            }
        }
        if (!id_staff.isBlank()) {
            if (checkIfInt(id_staff)) {
                String sql = "SELECT id FROM staff WHERE id = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, Integer.parseInt(id_staff));

                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    if (id == Integer.parseInt(id_staff)) {
                        isNumber2 = true;
                        break;
                    }
                }
                statement.close();
            }
        }

        String sql = "INSERT INTO visits (time_start, id_table, id_staff) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, formattedDate);
        if (isNumber) {
            statement.setInt(2, Integer.parseInt(id_table));
        }
        else {
            statement.setInt(2, 1);
        }
        if (isNumber2) {
            statement.setInt(3, Integer.parseInt(id_staff));
        }
        else {
            statement.setInt(3, 1);
        }

        statement.executeUpdate();
        statement.close();
        this.updateVisitsArray(getNewVisitId(), 1);
    }

    public void endVisit(int id_visit) throws Exception {
        java.util.Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String formattedDate = sdf.format(date);
        String sql = "UPDATE visits SET time_end = ?";
        sql += " WHERE id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, formattedDate);
        statement.setInt(2, id_visit);

        statement.executeUpdate();
        statement.close();
        this.updateVisitsArray(id_visit, 2);
    }
}
