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

public class VisitsController implements Initializable {
    public Button backBtn;
    public TableColumn<Visit, String> idVisit;
    public TableColumn<Visit, String> visitTimeStart;
    public TableColumn<Visit, String> visitTimeEnd;
    public TableColumn<Visit, String> visitTableTable;
    public TableColumn<Visit, String> visitWaiter;
    public Button addVisitDishes;
    public Button addVisitClients;
    public Button updateVisit;
    public Button deleteVisit;
    public TableView<Visit> visitsTable;
    public Button addNewData;
    public TextField enterWaiter;
    public TextField enterTableNumber;
    public Button endVisit;
    public ChoiceBox enterTableNumber1;
    public ChoiceBox enterWaiter1;
    public Button updateVisitClient;
    public TableView visitsClientsTable;
    public TableColumn first_name;
    public TableColumn last_name;
    public TableColumn second_name;
    public Button deleteVisitClient;
    public TextField enterIdVisit;
    public TextField enterIdClient;
    //other variables
    private Stage stage;
    private Scene scene;
    private Parent root;
    private VisitModel visitModel;
    private Connection connection;
    private int selectedVisit;

    public VisitsController() throws Exception {
        connection = ConnectionTry.getConnection();
        visitModel = new VisitModel();
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
        ObservableList<Visit> temp = visitModel.getVisits();

        idVisit.setCellValueFactory(new PropertyValueFactory<>("id"));
        visitTimeStart.setCellValueFactory(new PropertyValueFactory<>("time_start"));
        visitTimeEnd.setCellValueFactory(new PropertyValueFactory<>("time_end"));
        visitTableTable.setCellValueFactory(new PropertyValueFactory<>("table"));
        visitWaiter.setCellValueFactory(new PropertyValueFactory<>("waiter"));

        visitsTable.setItems(temp);
    }
    
    public void addDishesInVisitView(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("dishes-in-visit-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void addClientsInVisitView(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("clients-in-visit-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void refreshItems() {
        ObservableList<Visit> temp = visitModel.getVisits();
        visitsTable.setItems(FXCollections.observableArrayList(temp));
    }

    public void addNewVisitBtn(ActionEvent event) throws SQLException {
        visitModel.insertVisit(enterTableNumber.getText(), enterWaiter.getText());
        refreshItems();
    }

    public void updateVisitBtn(ActionEvent event)  throws Exception  {
        selectedVisit = visitsTable.getSelectionModel().getSelectedItem().getId();
        visitModel.updateVisit(selectedVisit, enterTableNumber.getText(), enterWaiter.getText());
        refreshItems();
    }

    public void deleteVisitBtn(ActionEvent event) throws Exception {
        selectedVisit = visitsTable.getSelectionModel().getSelectedItem().getId();
        visitModel.deleteVisit(selectedVisit);
        refreshItems();
    }

    public void endVisitAction(ActionEvent event) throws Exception {
        selectedVisit = visitsTable.getSelectionModel().getSelectedItem().getId();
        visitModel.endVisit(selectedVisit);
        refreshItems();
    }

    /*
    public void addNewVisitClientBtn(ActionEvent event) throws Exception {
        visitModel.insertClientVisit(enterIdVisit.getText(), enterIdClient.getText());
        refreshItems();
    }

    public void updateVisitClientBtn(ActionEvent event) throws Exception {
        selectedVisit = visitsClientsTable.getSelectionModel().getSelectedItem().getId_visit();
        visitModel.updateVisit(selectedVisit, enterTableNumber.getText(), enterWaiter.getText());
        refreshItems();
    }

    public void deleteVisitClientBtn(ActionEvent event) throws Exception {
        selectedVisit = visitsClientsTable.getSelectionModel().getSelectedItem();
        visitModel.deleteVisit(selectedVisit);
        refreshItems();
    }

     */
}
