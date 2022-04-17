package utd.database;

import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;

public class createAccountController {
    
    @FXML
    TextField usernameField;
    @FXML
    PasswordField passwordField;
    @FXML
    PasswordField confirmPasswordField;
    @FXML
    TextField fname;
    @FXML
    TextField lname;
    @FXML
    Button createButton;

    private Scene scene;
    private Parent root;
    private Stage stage;

    @FXML
    public void createButtonPressed(ActionEvent event) throws SQLException, IOException {
        String uname = usernameField.getText();
        String password = passwordField.getText();
        String confirm = confirmPasswordField.getText();
        String first = fname.getText();
        String last = lname.getText();

        if (uname.length() > 30 || uname.contains("--") || uname.contains("or") || uname.contains("=")){
            usernameField.clear();
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setHeaderText("Invalid Username");
            error.setContentText("Your Username is Invalid\nPlease try again");
            error.showAndWait();
            usernameField.clear();
            return;
        }
        if (!password.equals(confirm)){
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setHeaderText("Passwords Do Not Match");
            error.setContentText("Your Passwords do not match\nPlease try again");
            error.showAndWait();
            passwordField.clear();
            confirmPasswordField.clear();
            return;
        }

        if (new DatabaseController().createUser(uname, password, first, last)){
            Alert made = new Alert(Alert.AlertType.CONFIRMATION);
            made.setHeaderText("User Created");
            made.setContentText("Your Account has been successfully Created!!!");
            made.showAndWait();

            root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            return;
        }
        
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setHeaderText("Could Not Make Account");
        error.setContentText("Sorry, We could not processes your request at this time.");
        error.showAndWait();
    }
}
