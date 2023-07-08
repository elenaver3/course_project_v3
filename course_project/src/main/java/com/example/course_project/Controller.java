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

public class Controller implements Initializable {

    public Button registerBtn;
    public Button tablesBtnGuest;
    public Button dishesBtnGuest;
    public Button backBtnChef;
    //Text field
    @FXML
    private TextField login;
    public TextField userLogin;
    public PasswordField userPassword;
    public TextField enterF;
    public TextField enterI;
    public TextField enterO;
    public TextField enterInn;
    public TextField enterAddress;
    public TextField enterIngName;
    public TextField enterIngMU;

    // Label
    public Label rightEnter;

    // Button
    public Button exitBtn;
    public Button usersBtn;
    public Button clientsBtn;
    public Button visitsBtn;
    public Button tablesBtn;
    public Button dishesBtn;
    public Button ingredientsBtn;
    public Button backBtn;
    public Button updateTableClients;
    public Button addData;
    public Button updateClient;
    public Button deleteClient;
    public Button updatePost;
    public Button addNewPost;
    public Button updateDish;
    public Button deleteDish;
    public Button updateTableDish;
    public Button staffBtn;
    public Button updateIngInDish;
    public Button deleteIngInDish;
    public Button updateTableIngInDish;

    // Table Column
    public TableColumn<Client, String> first_name;
    public TableColumn<Client, String> last_name;
    public TableColumn<Client, String> second_name;
    public TableColumn<Client, String> inn;
    public TableColumn<Client, String> address;
    public TableColumn<Client, String> idClient;
    public TableColumn<Dish, String> dishName;
    public TableColumn<Dish, String> idDish;
    public TableColumn<Ingredient, String> idIngredient;
    public TableColumn<Ingredient, String> ingredientName;
    public TableColumn<Ingredient, String> muName;
    public TableColumn dishAmount;

    // Table view
    public TableView<Client> clientsTable;
    public TableView<Dish> dishesTable;
    public TableView<Staff> staffTable;
    public TableView<Table> tablesTable;


    //selected
    private Client selectedClient;
    private Staff selectedStaff;
    private Dish selectedDish;
    private Table selectedTable;
    private Ingredient selectedIng;

    //other variables
    private Stage stage;
    private Scene scene;
    private Parent root;

    private boolean isConnected;
    private int role;

    //Models
    private Authorization authorization;
    private Connection connection;


    public Controller() throws Exception {
        isConnected = false;
        connection = ConnectionTry.getConnection();

    }

    @FXML
    protected void login(ActionEvent event) throws Exception {

        authorization = new Authorization(userLogin.getText(), userPassword.getText());
        role = authorization.getRole();
        User.setCurrentUserId(role);

        if (authorization.getRole() != -1) {
            isConnected = true;
            rightEnter.setText("");
            switch (role) {
                case 1:
                    User.setCurrentUserRole(1);
                    root = FXMLLoader.load(getClass().getResource("admin-view.fxml"));
                    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                    break;
                case 2:
                    User.setCurrentUserRole(2);
                    root = FXMLLoader.load(getClass().getResource("waiter-view.fxml"));
                    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                    break;
                case 3:
                    User.setCurrentUserRole(3);
                    root = FXMLLoader.load(getClass().getResource("chef-view.fxml"));
                    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                    break;
                default:
                    User.setCurrentUserRole(4);
                    root = FXMLLoader.load(getClass().getResource("guest-view.fxml"));
                    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                    break;
            }
        }
        else {
            isConnected = false;
            rightEnter.setText("Неверный логин или пароль");
        }
    }

    public void exit(ActionEvent event) throws IOException {
        isConnected = false;
        role = -1;
        User.setCurrentUserRole(4);
        User.setCurrentUserId(-1);
        root = FXMLLoader.load(getClass().getResource("first-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void openUsers(ActionEvent event) throws IOException {
        if (User.getCurrentUserRole() == 1) {
            root = FXMLLoader.load(getClass().getResource("users-view.fxml"));
        }
        else {
            root = FXMLLoader.load(getClass().getResource("change-user-view.fxml"));
        }
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void openClients(ActionEvent event) throws Exception {

        root = FXMLLoader.load(getClass().getResource("clients-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);

        stage.show();
    }

    public void openVisits(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("visits-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void openTables(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("tables-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void openDishes(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("dishes-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void openIngredients(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("ingredients-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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

    public void registrationBegin(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("registrations-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

    public void openStaff(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("staff-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void openTablesGuest(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("tables-view-guest.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void openDishesGuest(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("dishes-view-guest.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void openClientsWaiter(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("clients-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);

        stage.show();
    }

    public void openVisitsWaiter(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("visits-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void openTablesWaiter(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("tables-view-guest.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void openDishesWaiter(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("dishes-view-guest.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void openDishesChef(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("dishes-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void openIngredientsChef(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("ingredients-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}