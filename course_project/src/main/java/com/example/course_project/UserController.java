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

public class UserController implements Initializable {
    public Button backBtn;
    public Button addNewData;
    public Button updateUser;
    public Button deleteClient;
    public TableView<User> usersTable;
    public TableColumn<User, String> idUser;
    public TableColumn<User, String> user_f;
    public TableColumn<User, String> user_i;
    public TableColumn<User, String> user_o;
    public TableColumn<User, String> user_name;
    public TableColumn<User, String> user_role;
    public TableColumn<User, String> user_password;
    public TextField enterF;
    public TextField enterI;
    public TextField enterO;
    public TextField enterLogin;
    public TextField enterRole;
    public TextField enterPassword;
    public PasswordField enterPassword1;
    public Label isSuccess;
    public Button changeUserData;

    private Connection connection;
    private UserModel userModel;

    //other variables
    private Stage stage;
    private Scene scene;
    private Parent root;

    private int selectedUser;

    public UserController() throws Exception {
        connection = ConnectionTry.getConnection();

        usersTable = new TableView<>();
        enterF = new TextField();
        enterI = new TextField();
        enterO = new TextField();
        enterRole = new TextField();
        enterLogin = new TextField();
        enterPassword = new TextField();

        try {
            userModel = new UserModel();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void backToMenu(ActionEvent event) throws IOException {
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

    public void addNewUserBtn(ActionEvent event) throws Exception {
        userModel.insertUser(enterF.getText(), enterI.getText(), enterO.getText(), enterRole.getText(), enterLogin.getText(), enterPassword.getText());
        refreshItems();
    }

    public void updateUserBtn(ActionEvent event) throws Exception {
        selectedUser = usersTable.getSelectionModel().getSelectedItem().getId();
        userModel.updateUsers(selectedUser, enterF.getText(), enterI.getText(), enterO.getText(), enterRole.getText(), enterLogin.getText(), enterPassword.getText());
        refreshItems();
    }

    public void deleteUserBtn(ActionEvent event) throws Exception {
        selectedUser = usersTable.getSelectionModel().getSelectedItem().getId();
        userModel.deleteUser(selectedUser);
        refreshItems();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (User.getCurrentUserRole() == 1) {
            ObservableList<User> temp = userModel.getUsers();

            idUser.setCellValueFactory(new PropertyValueFactory<>("id"));
            user_f.setCellValueFactory(new PropertyValueFactory<>("user_f"));
            user_i.setCellValueFactory(new PropertyValueFactory<>("user_i"));
            user_o.setCellValueFactory(new PropertyValueFactory<>("user_o"));
            user_password.setCellValueFactory(new PropertyValueFactory<>("user_password"));
            user_role.setCellValueFactory(new PropertyValueFactory<>("user_role"));
            user_name.setCellValueFactory(new PropertyValueFactory<>("user_login"));

            usersTable.setItems(temp);
        }
    }

    private void refreshItems() {
        ObservableList<User> temp = userModel.getUsers();
        usersTable.setItems(FXCollections.observableArrayList(temp));
    }

    public void userDataChanges(ActionEvent event) throws Exception {
        userModel.updateUsers(User.getCurrentUserId(), enterF.getText(), enterI.getText(), enterO.getText(), enterPassword.getText(), enterPassword1.getText());
        refreshItems();

    }
}
