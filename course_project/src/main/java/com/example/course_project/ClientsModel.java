package com.example.course_project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;


public class ClientsModel {
    private ObservableList<Client> clients;
    private Connection connection;

    public ClientsModel() throws Exception {
        this.connection = ConnectionTry.getConnection();
        //clients = new ArrayList<>();
        clients = FXCollections.observableArrayList();

        Statement statement = connection.createStatement();
        String query = "SELECT * FROM clients";
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String last_name = resultSet.getString("last_name");
            String first_name = resultSet.getString("first_name");
            String second_name = resultSet.getString("second_name");
            String address = resultSet.getString("address");
            String inn = resultSet.getString("inn");
            Client client = new Client(id, last_name, first_name, second_name, address, inn);
            clients.add(client);
        }

    }

    private int getNewClientId() throws SQLException {
        String query = "SELECT MAX(id) AS id FROM clients";
        PreparedStatement statement = connection.prepareStatement(query);

        ResultSet resultSet = statement.executeQuery();

        resultSet.next();
        return resultSet.getInt("id");
    }

    private void updateClientsArray(int id_client, int action) throws SQLException {
        String query = " ";
        PreparedStatement statement;
        ResultSet resultSet;
        switch (action) {
            case 1:
                query = "SELECT * FROM clients WHERE id = ?";
                statement = connection.prepareStatement(query);
                statement.setInt(1, id_client);

                resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String last_name = resultSet.getString("last_name");
                    String first_name = resultSet.getString("first_name");
                    String second_name = resultSet.getString("second_name");
                    String address = resultSet.getString("address");
                    String inn = resultSet.getString("inn");
                    Client client = new Client(id, last_name, first_name, second_name, address, inn);
                    clients.add(client);
                }

                statement.close();
                break;
            case 2:
                query = "SELECT * FROM clients WHERE id = ?";
                statement = connection.prepareStatement(query);
                statement.setInt(1, id_client);

                resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String last_name = resultSet.getString("last_name");
                    String first_name = resultSet.getString("first_name");
                    String second_name = resultSet.getString("second_name");
                    String address = resultSet.getString("address");
                    String inn = resultSet.getString("inn");
                    for (int i = 0; i < clients.size(); i++) {
                        if (clients.get(i).getId() == id) {
                            clients.set(i, new Client(id, last_name, first_name, second_name, address, inn));
                        }
                    }
                }

                statement.close();
                break;
            case 3:
                for (int i = 0; i < clients.size(); i++) {
                    if (clients.get(i).getId() == id_client) {
                        Client client = clients.get(i);
                        clients.remove(client);
                        break;
                    }
                }
                break;
        }

    }

    public Client getClient(int id_client) throws SQLException {
        Client client = new Client();
        for (Client one_client: clients) {
            if (one_client.getId() == id_client) {
                client = one_client;
                break;
            }
        }
        return client;
    }

    public ObservableList<Client> getClients() {
        return clients;
    }

    public void updateClients(int id_client, String new_last, String new_first, String new_second, String new_inn, String new_address) throws SQLException {
        String sql = "UPDATE clients SET last_name = ?, first_name = ?, second_name = ?, inn = ?, address = ?";
        sql += " WHERE id = ?";

        Client client = getClient(id_client);


        if (client.getId() != -1) {
            PreparedStatement statement = connection.prepareStatement(sql);
            if (new_last.isBlank())
                statement.setString(1, client.getLast_name());
            else
                statement.setString(1, new_last);
            if (new_first.isBlank())
                statement.setString(2, client.getFirst_name());
            else
                statement.setString(2, new_first);
            if (new_second.isBlank())
                statement.setString(3, client.getSecond_name());
            else
                statement.setString(3, new_second);
            if (new_inn.isBlank())
                statement.setString(4, client.getInn());
            else
                statement.setString(4, new_inn);
            if (new_address.isBlank())
                statement.setString(5, client.getAddress());
            else
                statement.setString(5, new_address);

            statement.setInt(6, id_client);

            statement.executeUpdate();
            statement.close();
            this.updateClientsArray(id_client, 2);
        }
    }

    public void deleteClient(int id_client) throws SQLException {
        String sql = "DELETE FROM clients where id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id_client);

        statement.executeUpdate();
        statement.close();
        this.updateClientsArray(id_client, 3);
    }

    public void insertClient(String first_name, String last_name, String second_name, String inn, String address) throws SQLException {

        String sql = "INSERT INTO clients (last_name, first_name, second_name, inn, address) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, last_name);
        statement.setString(2, first_name);
        statement.setString(3, second_name);
        statement.setString(4, inn);
        statement.setString(5, address);

        statement.executeUpdate();
        statement.close();
        this.updateClientsArray(getNewClientId(), 1);
    }
}


