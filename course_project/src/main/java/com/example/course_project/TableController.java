package com.example.course_project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class TableController implements Initializable {

    //Text field

    // Button
    public Button backBtn;

    // Table Column
    public TableColumn<Table, String> idTable;
    public TableColumn<Table, String> tableNumber;
    public TableColumn<Table, String> tableMaxPeople;

    // Table view
    public TableView<Table> tablesTableView;
    public Button addNewData;
    public Button updateTable;
    public Button deleteTable;
    public TextField enterTableName;
    public TextField enterTableMaxPeople;


    //selected
    private int selectedTable;

    //other variables
    private Stage stage;
    private Scene scene;
    private Parent root;
    //Model
    private TableModel tableModel;
    private Connection connection;


    public TableController() throws Exception {
        connection = ConnectionTry.getConnection();
        tablesTableView = new TableView<Table>();

        enterTableName = new TextField();
        enterTableMaxPeople = new TextField();

        addNewData = new Button();
        updateTable = new Button();
        deleteTable = new Button();

        if (User.getCurrentUserRole() != 4) {
            enterTableName.setDisable(false);
            enterTableMaxPeople.setDisable(false);
            addNewData.setDisable(false);
            updateTable.setDisable(false);
            deleteTable.setDisable(false);
        }
        else {
            enterTableName.setDisable(true);
            enterTableMaxPeople.setDisable(true);
            addNewData.setDisable(true);
            updateTable.setDisable(true);
            deleteTable.setDisable(true);
        }

        try {
            tableModel = new TableModel();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void backToMenu(ActionEvent event) throws IOException, SQLException {
        switch (User.getCurrentUserRole()) {
            case 1:
                root = FXMLLoader.load(getClass().getResource("admin-view.fxml"));
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                break;

            case 2:
                root = FXMLLoader.load(getClass().getResource("waiter-view.fxml"));
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                break;

            case 3:
                root = FXMLLoader.load(getClass().getResource("chef-view.fxml"));
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                break;

            default:
                root = FXMLLoader.load(getClass().getResource("guest-view.fxml"));
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                break;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Table> temp = tableModel.getTables();

        idTable.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableNumber.setCellValueFactory(new PropertyValueFactory<>("table_number"));
        tableMaxPeople.setCellValueFactory(new PropertyValueFactory<>("max_people"));

        tablesTableView.setItems(temp);
    }

    private void refreshItems() {
        ObservableList<Table> temp = tableModel.getTables();
        tablesTableView.setItems(FXCollections.observableArrayList(temp));
    }

    public void addNewTableBtn(ActionEvent event) throws SQLException {
        tableModel.insertTable(enterTableName.getText(), enterTableMaxPeople.getText());
        refreshItems();
    }

    public void updateTableBtn(ActionEvent event) throws SQLException {
        selectedTable = tablesTableView.getSelectionModel().getSelectedItem().getId();
        tableModel.updateTables(selectedTable, enterTableName.getText(), enterTableMaxPeople.getText());
        refreshItems();
    }

    public void deleteTableBtn(ActionEvent event) throws SQLException {
        selectedTable = tablesTableView.getSelectionModel().getSelectedItem().getId();
        tableModel.deleteTables(selectedTable);
        refreshItems();
    }
}
