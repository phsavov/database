package utd.database;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.Label;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginPageController {

    @FXML
    Button cancelButton;
    @FXML
    Label loginMessageLabel;
    @FXML
    Button createAccount;
    @FXML
    TextField usernameTextField;
    @FXML
    PasswordField passwordTextField;

    private Scene scene;
    private Parent root;
    private Stage stage;

    @FXML
    public void loginButtonOnAction(ActionEvent e) throws SQLException, IOException {
        if (usernameTextField.getText().isBlank() == false && passwordTextField.getText().isBlank() == false) {
            String user = usernameTextField.getText();
            String pass = passwordTextField.getText();
    
            if (user.contains("--") || user.contains("#") || user.contains(";") || user.contains("`")){
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setHeaderText("Incorrect Credentials");
                error.setContentText("You have entered the wrong Credentials\nPlease try again");
                error.showAndWait();
                usernameTextField.clear();
                passwordTextField.clear();
                return;
            }


            if (validateLogin(user, pass)){
                Alert loggedIn = new Alert(Alert.AlertType.CONFIRMATION);
                loggedIn.setHeaderText("CONFIRMED");
                loggedIn.setContentText("You have logged into your Account!!!");
                loggedIn.showAndWait();
                root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
                stage = (Stage)((Node)e.getSource()).getScene().getWindow();
                stage.setUserData(new User(new DatabaseController().getAccountID(user, pass)));
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } 
        } else {
            loginMessageLabel.setText("Please enter username and password.");
        }
    }

    @FXML
    public void cancelButtonOnAction(ActionEvent e) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void create(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("createAccountPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public boolean validateLogin(String user, String pass) throws SQLException {
        DatabaseController connectNow = new DatabaseController();
        try {
            return connectNow.LoginUser(user, pass);
        } catch (SQLException e) {
            e.printStackTrace();
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setHeaderText("Incorrect Credentials");
            error.setContentText("You have entered the wrong Credentials\nPlease try again");
            error.showAndWait();
            usernameTextField.clear();
            passwordTextField.clear();
            return false;
        }
    }
}
