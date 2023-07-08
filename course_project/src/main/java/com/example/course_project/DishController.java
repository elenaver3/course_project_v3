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

public class DishController implements Initializable {

    // Table Column
    public TableColumn<Dish, String> dishName;
    public TableColumn<Dish, String> idDish;

    public TableView<Dish> dishesTable;

    public Button backBtn;
    public Button addNewData;
    public Button updateDish;
    public Button deleteDish;
    public TextField enterDishName;
    public Button addIngInDIsh;

    //selected
    private int selectedDish;

    //other variables
    private Stage stage;
    private Scene scene;
    private Parent root;

    //Models
    private DishModel dishModel;
    private Connection connection;


    public DishController() throws Exception {
        connection = ConnectionTry.getConnection();

        dishesTable = new TableView<>();

        enterDishName = new TextField();

        addNewData = new Button();
        updateDish = new Button();
        deleteDish = new Button();

        /*
        if (User.getCurrentUserRole() != 4) {
            enterDishName.setDisable(false);
            addNewData.setDisable(false);
            updateDish.setDisable(false);
            deleteDish.setDisable(false);
        }
        else {
            enterDishName.setDisable(true);
            addNewData.setDisable(true);
            updateDish.setDisable(true);
            deleteDish.setDisable(true);
        }

         */

        try {
            dishModel = new DishModel();
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
        ObservableList<Dish> temp = dishModel.getDishes();

        idDish.setCellValueFactory(new PropertyValueFactory<>("id"));
        dishName.setCellValueFactory(new PropertyValueFactory<>("name"));

        dishesTable.setItems(temp);
    }

    private void refreshItems() {
        ObservableList<Dish> temp = dishModel.getDishes();
        dishesTable.setItems(FXCollections.observableArrayList(temp));
    }

    public void addNewDishBtn(ActionEvent event) throws SQLException {
        dishModel.insertDish(enterDishName.getText());
        refreshItems();
    }

    public void updateDishBtn(ActionEvent event) throws SQLException {
        selectedDish = dishesTable.getSelectionModel().getSelectedItem().getId();
        dishModel.updateDishes(selectedDish, enterDishName.getText());
        refreshItems();
    }

    public void deleteDishBtn(ActionEvent event) throws SQLException {
        selectedDish = dishesTable.getSelectionModel().getSelectedItem().getId();
        dishModel.deleteDish(selectedDish);
        refreshItems();
    }


    public void addIngInDishView(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("ingredients-in-dish-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
