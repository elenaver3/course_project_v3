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

public class IngredientController implements Initializable {

    //Text field
    public TextField enterIngredientName;
    public TextField enterMUnitName;
    public TextField enterAmount;

    // Button
    public Button backBtn;

    // Table Column
    public TableColumn<Ingredient, String> idIngredient;
    public TableColumn<Ingredient, String> ingredientName;
    public TableColumn<Ingredient, String> muName;

    // Table view

    public TableView<Ingredient> ingTable;
    public TableColumn<Ingredient, String> ingAmount;
    public Button addNewData;
    public Button updateIngredient;
    public Button deleteIngredient;

    //selected
    private int selectedIng;

    //other variables
    private Stage stage;
    private Scene scene;
    private Parent root;


    //Models

    private IngredientsModel ingredientsModel;
    private Connection connection;


    public IngredientController() throws Exception {
        connection = ConnectionTry.getConnection();

        ingTable = new TableView<>();
        enterIngredientName = new TextField();
        enterAmount = new TextField();
        enterMUnitName = new TextField();

        try {
            ingredientsModel = new IngredientsModel();
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
        ObservableList<Ingredient> temp = ingredientsModel.getIngredients();

        idIngredient.setCellValueFactory(new PropertyValueFactory<>("id"));
        ingredientName.setCellValueFactory(new PropertyValueFactory<>("name"));
        muName.setCellValueFactory(new PropertyValueFactory<>("measurementUnit"));
        ingAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        ingTable.setItems(temp);
    }

    private void refreshItems() {
        ObservableList<Ingredient> temp = ingredientsModel.getIngredients();
        ingTable.setItems(FXCollections.observableArrayList(temp));
    }

    public void addNewIngredientBtn(ActionEvent event) throws SQLException {
        ingredientsModel.insertIngredients(enterIngredientName.getText(), enterMUnitName.getText(), enterAmount.getText());
        refreshItems();
    }

    public void updateIngredientBtn(ActionEvent event) throws SQLException {
        selectedIng = ingTable.getSelectionModel().getSelectedItem().getId();
        ingredientsModel.updateIngredients(selectedIng, enterIngredientName.getText(), enterMUnitName.getText(), enterAmount.getText());
        refreshItems();
    }

    public void deleteIngredientBtn(ActionEvent event) throws SQLException {
        selectedIng = ingTable.getSelectionModel().getSelectedItem().getId();
        ingredientsModel.deleteIngredients(selectedIng);
        refreshItems();
    }
}
