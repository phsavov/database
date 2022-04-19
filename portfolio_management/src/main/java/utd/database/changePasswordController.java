package utd.database;

import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
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
    void update(ActionEvent event) throws SQLException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        User user = (User) stage.getUserData();

        String oldP = oldPass.getText();
        String newP = newPass.getText();

        if (new DatabaseController().updatePass(newP, user.getAccountID())){
            Alert made = new Alert(Alert.AlertType.CONFIRMATION);
            made.setHeaderText("Password Updated");
            made.setContentText("Your password has been updated!");
            made.showAndWait();
        } else {
            Alert made = new Alert(Alert.AlertType.ERROR);
            made.setHeaderText("Password Not Updated");
            made.setContentText("There was an issue");
            made.showAndWait();
        }

    }
}
