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
import java.util.ResourceBundle;

public class ClientController implements Initializable {
    //Text field
    public TextField enterLastName;
    public TextField enterFirstName;
    public TextField enterSecondName;
    public TextField enterPersonInn;
    public TextField enterPersonAddress;

    // Button
    public Button backBtn;
    public Button addNewData;
    public Button updateClient;
    public Button deleteClient;

    // Table Column
    public TableColumn<Client, String> first_name;
    public TableColumn<Client, String> last_name;
    public TableColumn<Client, String> second_name;
    public TableColumn<Client, String> inn;
    public TableColumn<Client, String> address;
    public TableColumn<Client, String> idClient;

    // Table view
    public TableView<Client> clientsTable;
    //selected
    private int selectedClient;

    //other variables
    private Stage stage;
    private Scene scene;
    private Parent root;

    //Models
    private ClientsModel clientsModel;
    private Connection connection;


    public ClientController() throws Exception {
        connection = ConnectionTry.getConnection();

        clientsTable = new TableView<Client>();

        enterLastName = new TextField();
        enterFirstName = new TextField();
        enterSecondName = new TextField();
        enterPersonInn = new TextField();
        enterPersonAddress = new TextField();

        try {
            clientsModel = new ClientsModel();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void backToMenu(ActionEvent event) throws Exception {
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
        ObservableList<Client> temp = clientsModel.getClients();

        idClient.setCellValueFactory(new PropertyValueFactory<>("id"));
        last_name.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        first_name.setCellValueFactory(new PropertyValueFactory<>("last_name"));
        second_name.setCellValueFactory(new PropertyValueFactory<>("second_name"));
        inn.setCellValueFactory(new PropertyValueFactory<>("inn"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));

        clientsTable.setItems(temp);

    }

    private void refreshItems() {
        ObservableList<Client> temp = clientsModel.getClients();
        clientsTable.setItems(FXCollections.observableArrayList(temp));
    }

    public void addNewClientBtn(ActionEvent event) throws SQLException {
        clientsModel.insertClient(enterLastName.getText(), enterFirstName.getText(), enterSecondName.getText(), enterPersonInn.getText(), enterPersonAddress.getText());
        refreshItems();
    }

    public void updateClientBtn(ActionEvent event) throws SQLException {
        selectedClient = clientsTable.getSelectionModel().getSelectedItem().getId();
        clientsModel.updateClients(selectedClient, enterFirstName.getText(), enterLastName.getText(), enterSecondName.getText(), enterPersonInn.getText(), enterPersonAddress.getText());
        refreshItems();
    }

    public void deleteClientBtn(ActionEvent event) throws SQLException {
        selectedClient = clientsTable.getSelectionModel().getSelectedItem().getId();
        clientsModel.deleteClient(selectedClient);
        refreshItems();
    }
}
