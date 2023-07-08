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

public class StaffController implements Initializable {
    //Text field
    public TextField enterLastName;
    public TextField enterFirstName;
    public TextField enterSecondName;
    public TextField enterPersonPhone;
    public TextField enterPersonAddress;

    // Table Column
    public TableColumn<Staff, String> idStaff;
    public TableColumn<Staff, String> first_name;
    public TableColumn<Staff, String> last_name;
    public TableColumn<Staff, String> second_name;
    public TableColumn<Staff, String> phone_number;
    public TableColumn<Staff, String> address;


    // Table view
    public TableView<Staff> staffTable;

    // Buttons
    public Button backBtn;
    public Button addNewData;
    public Button updateStaff;
    public Button deleteStaff;

    //selected
    private int selectedStaff;

    //other variables
    private Stage stage;
    private Scene scene;
    private Parent root;

    //Models
    private StaffModel staffModel;
    private Connection connection;


    public StaffController() throws Exception {
        connection = ConnectionTry.getConnection();

        staffTable = new TableView<>();

        enterFirstName = new TextField();
        enterLastName = new TextField();
        enterSecondName = new TextField();
        enterPersonAddress = new TextField();
        enterPersonPhone = new TextField();

        try {
            staffModel = new StaffModel();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void backToMenu(ActionEvent event) throws Exception {
        root = FXMLLoader.load(getClass().getResource("admin-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Staff> temp = staffModel.getStaff();

        idStaff.setCellValueFactory(new PropertyValueFactory<>("id"));
        last_name.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        first_name.setCellValueFactory(new PropertyValueFactory<>("last_name"));
        second_name.setCellValueFactory(new PropertyValueFactory<>("second_name"));
        phone_number.setCellValueFactory(new PropertyValueFactory<>("phone_number"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));

        staffTable.setItems(temp);
    }

    private void refreshItems() {
        ObservableList<Staff> temp = staffModel.getStaff();
        staffTable.setItems(FXCollections.observableArrayList(temp));
    }


    public void addNewStaffBtn(ActionEvent event)  throws SQLException {
        staffModel.insertStaff(enterLastName.getText(), enterFirstName.getText(), enterSecondName.getText(), enterPersonAddress.getText(), enterPersonPhone.getText());
        refreshItems();
    }

    public void updateStaffBtn(ActionEvent event) throws SQLException {
        selectedStaff = staffTable.getSelectionModel().getSelectedItem().getId();
        staffModel.updateStaff(selectedStaff, enterFirstName.getText(), enterLastName.getText(), enterSecondName.getText(), enterPersonAddress.getText(), enterPersonPhone.getText());
        refreshItems();
    }

    public void deleteStaffBtn(ActionEvent event) throws SQLException {
        selectedStaff = staffTable.getSelectionModel().getSelectedItem().getId();
        staffModel.deleteStaff(selectedStaff);
        refreshItems();
    }
}
