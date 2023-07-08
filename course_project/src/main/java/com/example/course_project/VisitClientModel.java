package com.example.course_project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VisitClientModel {
    private Connection connection;

    private ObservableList<Client> visits_clients;


    public VisitClientModel() throws Exception {
        this.connection = ConnectionTry.getConnection();

        visits_clients = FXCollections.observableArrayList();

        Statement statement = connection.createStatement();
        String query = "SELECT visitClients.id_visit AS visit_id, clients.id AS id, clients.last_name, clients.first_name, clients.second_name\n" +
                "FROM clients JOIN visitClients ON clients.id = visitClients.id_client;";
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            int visit_id = resultSet.getInt("visit_id");
            String last_name = resultSet.getString("last_name");
            String first_name = resultSet.getString("first_name");
            String second_name = resultSet.getString("second_name");
            Client client = new Client(id, last_name, first_name, second_name, visit_id);
            visits_clients.add(client);
        }

        statement.close();
/*
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

 */
    }

    private void updateVisitsClientsArray(int id_visit, int action) throws SQLException {
        String query = " ";
        PreparedStatement statement;
        ResultSet resultSet;
        switch (action) {
            case 1:
                query = "SELECT visitClients.id_visit AS visit_id, clients.id AS id, clients.last_name, clients.first_name, clients.second_name\n" +
                        "FROM clients JOIN visitClients ON clients.id = visitClients.id_client WHERE visitClients.id_visit = ?";
                statement = connection.prepareStatement(query);
                statement.setInt(1, id_visit);

                resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int visit_id = resultSet.getInt("visit_id");
                    String last_name = resultSet.getString("last_name");
                    String first_name = resultSet.getString("first_name");
                    String second_name = resultSet.getString("second_name");
                    Client client = new Client(id, last_name, first_name, second_name, visit_id);
                    visits_clients.add(client);
                }

                statement.close();
                break;
            case 2:
                query = "SELECT visitClients.id_visit AS visit_id, clients.id AS id, clients.last_name, clients.first_name, clients.second_name\n" +
                        "FROM clients JOIN visitClients ON clients.id = visitClients.id_client WHERE visitClients.id_visit = ?";
                statement = connection.prepareStatement(query);
                statement.setInt(1, id_visit);

                resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int visit_id = resultSet.getInt("visit_id");
                    String last_name = resultSet.getString("last_name");
                    String first_name = resultSet.getString("first_name");
                    String second_name = resultSet.getString("second_name");
                    for (int i = 0; i < visits_clients.size(); i++) {
                        if (visits_clients.get(i).getId() == id) {
                            visits_clients.set(i, new Client(id, last_name, first_name, second_name, visit_id));
                        }
                    }
                }

                statement.close();
                break;
            case 3:
                for (int i = 0; i < visits_clients.size(); i++) {
                    if (visits_clients.get(i).getId_visit() == id_visit) {
                        Client client = visits_clients.get(i);
                        visits_clients.remove(client);
                        break;
                    }
                }
                break;
        }

    }

    /*
    public Client getVisitClient(int id_visit) throws SQLException {
        Client client = new Client();
        for (Client one_visit: visits_clients) {
            if (one_visit.getId() == id_visit) {
                client = one_visit;
                break;
            }
        }
        return client;
    }

     */

    public ObservableList<Client> getVisitClients() {
        return visits_clients;
    }

    public void updateVisitClient(String visit_id, String client_id) throws SQLException {
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
        if (!client_id.isBlank()) {
            if (checkIfInt(client_id)) {
                String sql = "SELECT id FROM clients WHERE id = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, Integer.parseInt(client_id));

                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    if (id == Integer.parseInt(client_id)) {
                        isNumber2 = true;
                        break;
                    }
                }
                statement.close();
            }
        }
        if (isNumber && isNumber2) {
            String sql = "SELECT id_visit, id_client FROM visitClients";
            sql += " WHERE id_visit = ? AND id_client = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(visit_id));
            statement.setInt(2, Integer.parseInt(client_id));

            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                statement.close();

                sql = "UPDATE visitClients SET id_visit = ?, id_client = ?";
                sql += " WHERE id_visit = ?";

                statement = connection.prepareStatement(sql);

                //if (isNumber) {
                statement.setInt(1, Integer.parseInt(visit_id));
                //}
                //else {
                //     statement.setInt(1, 1);
                //}
                //if (isNumber2) {
                statement.setInt(2, Integer.parseInt(client_id));
                //}
                //else {
                //    statement.setInt(2, 1);
                //}

                statement.setInt(3, Integer.parseInt(visit_id));

                statement.executeUpdate();
                statement.close();
                this.updateVisitsClientsArray(Integer.parseInt(visit_id), 2);
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

    public void deleteVisitClient(String visit_id, String client_id) throws SQLException {
        //boolean isNumber = false;
        if (!visit_id.isBlank() && !client_id.isBlank()) {
            String sql = "DELETE FROM visitClients where id_visit = ? AND id_client = ?";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, Integer.parseInt(visit_id));
            statement.setInt(2, Integer.parseInt(client_id));

            statement.executeUpdate();
            statement.close();
            this.updateVisitsClientsArray(Integer.parseInt(visit_id), 3);
        }

    }

    public void insertVisitClient(String visit_id, String client_id) throws SQLException {
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
        if (!client_id.isBlank()) {
            if (checkIfInt(client_id)) {
                String sql = "SELECT id FROM clients WHERE id = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, Integer.parseInt(client_id));

                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    if (id == Integer.parseInt(client_id)) {
                        isNumber2 = true;
                        break;
                    }
                }
                statement.close();
            }
        }
        if (isNumber && isNumber2) {
            String sql = "SELECT id_visit, id_client FROM visitClients";
            sql += " WHERE id_visit = ? AND id_client = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(visit_id));
            statement.setInt(2, Integer.parseInt(client_id));

            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                sql = "INSERT INTO visitClients (id_visit, id_client) VALUES (?, ?)";

                statement = connection.prepareStatement(sql);

                statement.setInt(1, Integer.parseInt(visit_id));
                statement.setInt(2, Integer.parseInt(client_id));

                statement.executeUpdate();
                statement.close();
                this.updateVisitsClientsArray(Integer.parseInt(visit_id), 1);
            }
        }
    }

}
