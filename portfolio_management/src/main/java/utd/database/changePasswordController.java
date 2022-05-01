package utd.database;

import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class changePasswordController {

    @FXML
    private TextField newPass;

    @FXML
    private Button updateButton;

    @FXML
    private TextField oldPass;

    @FXML
    Button cancel;

    private Scene scene;
    private Parent root;
    private Stage stage;

    @FXML
    void update(ActionEvent event) throws SQLException, IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        User user = (User) stage.getUserData();

        String oldP = oldPass.getText();
        String newP = newPass.getText();

        if (new DatabaseController().updatePass(newP, user.getAccountID())) {
            Alert made = new Alert(Alert.AlertType.CONFIRMATION);
            made.setHeaderText("Password Updated");
            made.setContentText("Your password has been updated!");
            made.showAndWait();
            root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            return;

        } else {
            Alert made = new Alert(Alert.AlertType.ERROR);
            made.setHeaderText("Password Not Updated");
            made.setContentText("There was an issue");
            made.showAndWait();
        }

    }

    @FXML
    void goBack(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        return;
    }
}
