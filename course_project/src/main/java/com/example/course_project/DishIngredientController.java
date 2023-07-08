package com.example.course_project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DishIngredientController implements Initializable {
    public Button backBtn;
    public Button addNewData;
    public Button updateDish;
    public TableView<Ingredient> dishesIngTable;
    public TableColumn<Ingredient, String>  idDish;
    public TableColumn<Ingredient, String> ingName;
    public TableColumn<Ingredient, String> ingAmount;
    public Button deleteDish;
    public TextField enterIdDish;
    public TextField enterIdIng;
    public TextField enterIngAmount;

    private Stage stage;
    private Scene scene;
    private Parent root;

    //Models
    private DishIngredientModel dishIngredientModel;
    private Connection connection;

    public DishIngredientController() throws Exception {
        this.connection = ConnectionTry.getConnection();
        dishIngredientModel = new DishIngredientModel();
    }

    public void backToMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("dishes-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Ingredient> temp = dishIngredientModel.getIngredientsInDish();

        idDish.setCellValueFactory(new PropertyValueFactory<>("id_dish"));
        ingName.setCellValueFactory(new PropertyValueFactory<>("name"));
        ingAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        dishesIngTable.setItems(temp);
    }

    private void refreshItems() {
        ObservableList<Ingredient> temp = dishIngredientModel.getIngredientsInDish();
        dishesIngTable.setItems(FXCollections.observableArrayList(temp));
    }

    public void addNewIngBtn(ActionEvent event) throws SQLException {
        dishIngredientModel.insertIngredientsInDish(enterIdDish.getText(), enterIdIng.getText(), enterIngAmount.getId());
        refreshItems();
    }

    public void updateIngBtn(ActionEvent event) throws SQLException {
        int selectedDishIng = dishesIngTable.getSelectionModel().getSelectedItem().getId_dish();
        dishIngredientModel.updateIngredientsInDish(String.valueOf(selectedDishIng), enterIdIng.getText(), enterIngAmount.getId());
        refreshItems();
    }

    public void deleteIngBtn(ActionEvent event) throws SQLException {
        int selectedDishIng = dishesIngTable.getSelectionModel().getSelectedItem().getId_dish();
        int selectedIng = dishesIngTable.getSelectionModel().getSelectedItem().getId();
        dishIngredientModel.deleteIngredientsInDish(String.valueOf(selectedDishIng), String.valueOf(selectedIng));
        refreshItems();
    }
}
