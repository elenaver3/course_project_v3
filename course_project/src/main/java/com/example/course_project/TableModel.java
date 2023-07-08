package com.example.course_project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class TableModel {
    private ObservableList<Table> tables;
    private Connection connection;

    public TableModel() throws Exception {
        this.connection = ConnectionTry.getConnection();

        tables = FXCollections.observableArrayList();

        Statement statement = connection.createStatement();
        String query = "SELECT * FROM tables";
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String table_number = resultSet.getString("table_number");
            int max_people = resultSet.getInt("max_people");
            Table table = new Table(id, table_number, max_people);
            tables.add(table);
        }

    }

    private int getNewTableId() throws SQLException {
        String query = "SELECT MAX(id) AS id FROM tables";
        PreparedStatement statement = connection.prepareStatement(query);

        ResultSet resultSet = statement.executeQuery();

        resultSet.next();
        return resultSet.getInt("id");
    }

    private void updateTablesArray(int id_table, int action) throws SQLException {
        String query = " ";
        PreparedStatement statement;
        ResultSet resultSet;
        switch (action) {
            case 1:
                query = "SELECT * FROM tables WHERE id = ?";
                statement = connection.prepareStatement(query);
                statement.setInt(1, id_table);

                resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String table_number = resultSet.getString("table_number");
                    int max_people = resultSet.getInt("max_people");
                    Table table = new Table(id, table_number, max_people);
                    tables.add(table);
                }

                statement.close();
                break;
            case 2:
                query = "SELECT * FROM tables WHERE id = ?";
                statement = connection.prepareStatement(query);
                statement.setInt(1, id_table);

                resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String table_number = resultSet.getString("table_number");
                    int max_people = resultSet.getInt("max_people");
                    for (int i = 0; i < tables.size(); i++) {
                        if (tables.get(i).getId() == id) {
                            tables.set(i, new Table(id, table_number, max_people));
                        }
                    }
                }

                statement.close();
                break;
            case 3:
                for (int i = 0; i < tables.size(); i++) {
                    if (tables.get(i).getId() == id_table) {
                        Table table = tables.get(i);
                        tables.remove(table);
                        break;
                    }
                }
                break;
        }

    }

    public Table getTable(int id_table) throws SQLException {
        Table table = new Table();
        for (Table one_table: tables) {
            if (one_table.getId() == id_table) {
                table = one_table;
                break;
            }
        }
        return table;
    }

    public ObservableList<Table> getTables() {
        return tables;
    }

    public void updateTables(int id_table, String new_table_number, String new_max_people) throws SQLException {
        String sql = "UPDATE tables SET table_number = ?, max_people = ?";
        sql += " WHERE id = ?";

        Table table = getTable(id_table);

        PreparedStatement statement = connection.prepareStatement(sql);

        if (table.getId() != -1) {
            if (new_table_number.isBlank())
                statement.setString(1, table.getTable_number());
            else
                statement.setString(1, new_table_number);
            if (new_max_people.isBlank() || !checkIfInt(new_max_people))
                statement.setInt(2, table.getMax_people());
            else
                statement.setInt(2, Integer.parseInt(new_max_people));

            statement.setInt(3, id_table);

            statement.executeUpdate();
            statement.close();
        }
        this.updateTablesArray(id_table, 2);
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

    public void deleteTables(int id_table) throws SQLException {
        String sql = "DELETE FROM tables where id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id_table);

        statement.executeUpdate();
        statement.close();
        this.updateTablesArray(id_table, 3);
    }

    public void insertTable(String table_number, String max_people) throws SQLException {

        String sql = "INSERT INTO tables (table_number, max_people) VALUES (?, ?)";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, table_number);
        if (checkIfInt(max_people))
            statement.setInt(2, Integer.parseInt(max_people));
        else
            statement.setInt(2, 0);

        statement.executeUpdate() ;
        statement.close();
        this.updateTablesArray(getNewTableId(), 1);
    }
}
