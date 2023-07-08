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

public class VisitsDishController implements Initializable {
    public Button backBtn;

    public Button addNewData;
    public TextField enterIdVisit;
    public TableColumn<Dish, String> idVisit;
    public Button updateDish;
    public TableColumn<Dish, String> dishName;
    public TableColumn<Dish, String> dishAmount;
    public Button deleteDish;
    public TextField enterIdDish;
    public TextField enterDishAmount;
    public TableView<Dish> visitDishesTable;
    //other variables
    private Stage stage;
    private Scene scene;
    private Parent root;
    private VisitDishModel visitDishModel;
    private Connection connection;
    private int selectedVisit;

    public VisitsDishController() throws Exception {
        connection = ConnectionTry.getConnection();
        visitDishModel = new VisitDishModel();
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
        ObservableList<Dish> temp = visitDishModel.getVisitDishes();

        idVisit.setCellValueFactory(new PropertyValueFactory<>("id_visit"));
        dishName.setCellValueFactory(new PropertyValueFactory<>("name"));
        dishAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        visitDishesTable.setItems(temp);
    }


    private void refreshItems() {
        ObservableList<Dish> temp = visitDishModel.getVisitDishes();
        visitDishesTable.setItems(FXCollections.observableArrayList(temp));
    }

    public void addNewDishBtn(ActionEvent event) throws SQLException {
        visitDishModel.insertVisitDish(enterIdVisit.getText(), enterIdDish.getText(), enterDishAmount.getText());
        refreshItems();
    }

    public void updateDishBtn(ActionEvent event) throws SQLException {
        selectedVisit = visitDishesTable.getSelectionModel().getSelectedItem().getId_visit();
        visitDishModel.updateVisitDish(enterIdVisit.getText(), enterIdDish.getText(), enterDishAmount.getText());
        refreshItems();
    }

    public void deleteDishBtn(ActionEvent event) throws SQLException {
        selectedVisit = visitDishesTable.getSelectionModel().getSelectedItem().getId_visit();
        int selectedDish = visitDishesTable.getSelectionModel().getSelectedItem().getId();
        visitDishModel.deleteVisitDish(String.valueOf(selectedVisit), String.valueOf(selectedDish));
        refreshItems();
    }
}
