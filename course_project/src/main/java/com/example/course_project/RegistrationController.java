package com.example.course_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

public class RegistrationController {
    public Button backBtn;
    public Button submitReg;
    public PasswordField enterPassword;
    public TextField enterLogin;
    public Label isSuccess;
    public PasswordField enterPassword1;
    public TextField enterF;
    public TextField enterI;
    public TextField enterO;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private Connection connection;

    public RegistrationController() throws Exception {
        connection = ConnectionTry.getConnection();
    }

    public void registration(ActionEvent event) throws Exception {
        Registration registration = new Registration();
        isSuccess.setText(registration.tryReg(enterF.getText(), enterI.getText(), enterO.getText(),
                enterLogin.getText(), enterPassword.getText(), enterPassword1.getText()));
    }

    public void backToMenu(ActionEvent event) throws IOException, SQLException {
        root = FXMLLoader.load(getClass().getResource("first-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
