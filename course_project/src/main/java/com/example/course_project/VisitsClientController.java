package com.example.course_project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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

public class VisitsClientController implements Initializable {
    public Button backBtn;

    public Button addNewData;
    public Button updateVisitClient;
    public TableView<Client> visitsClientsTable;
    public TableColumn<Client, String> first_name;
    public TableColumn<Client, String> last_name;
    public TableColumn<Client, String> second_name;
    public Button deleteVisitClient;
    public TextField enterIdVisit;
    public TextField enterIdClient;
    public TableColumn idVisit;
    //other variables
    private Stage stage;
    private Scene scene;
    private Parent root;
    private VisitClientModel visitClientModel;
    private Connection connection;
    private int selectedVisit;

    public VisitsClientController() throws Exception {
        connection = ConnectionTry.getConnection();
        visitClientModel = new VisitClientModel();
    }

    public void backToMenu(ActionEvent event) throws IOException, SQLException {
        root = FXMLLoader.load(getClass().getResource("visits-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Client> temp = visitClientModel.getVisitClients();

        idVisit.setCellValueFactory(new PropertyValueFactory<>("id_visit"));
        last_name.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        first_name.setCellValueFactory(new PropertyValueFactory<>("last_name"));
        second_name.setCellValueFactory(new PropertyValueFactory<>("second_name"));

        visitsClientsTable.setItems(temp);
    }


    private void refreshItems() {
        ObservableList<Client> temp = visitClientModel.getVisitClients();
        visitsClientsTable.setItems(FXCollections.observableArrayList(temp));
    }

    public void addNewVisitClientBtn(ActionEvent event) throws Exception {
        visitClientModel.insertVisitClient(enterIdVisit.getText(), enterIdClient.getText());
        refreshItems();
    }

    public void updateVisitClientBtn(ActionEvent event) throws Exception {
        selectedVisit = visitsClientsTable.getSelectionModel().getSelectedItem().getId_visit();
        visitClientModel.updateVisitClient(enterIdVisit.getText(), enterIdClient.getText());
        refreshItems();
    }

    public void deleteVisitClientBtn(ActionEvent event) throws Exception {
        selectedVisit = visitsClientsTable.getSelectionModel().getSelectedItem().getId_visit();
        int selectedClient = visitsClientsTable.getSelectionModel().getSelectedItem().getId();
        visitClientModel.deleteVisitClient(String.valueOf(selectedVisit), String.valueOf(selectedClient));
        refreshItems();
    }
}
