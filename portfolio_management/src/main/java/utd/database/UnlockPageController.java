package utd.database;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;


public class UnlockPageController {


    @FXML
    PasswordField phraseField;
    @FXML
    Button unlockButton;

    public void unlockAccount(ActionEvent event) {
        String phrase;

        phrase = phraseField.getText();

        //TODO call a method that will check if the phrase is the same or not.


        if (true){
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setHeaderText("Incorrect Phrase");
            error.setContentText("You have entered the wrong mnemonic phrase\nPlease try again");
            error.showAndWait();
            phraseField.clear();
        } else {
            User user = new User();
            user.setPhrase();

            Alert newPhrase = new Alert(Alert.AlertType.CONFIRMATION);
            newPhrase.setHeaderText("Your Account Is Unlocked!");
            newPhrase.setContentText("Your account is now unlocked.\n" +
                    "Here is your new Mnemonic Phrase: "+user.getPhrase()+"\n" +
                    "Write this phrase down, This is your new phrase in case your account gets locked again.");
            newPhrase.showAndWait();

            /* 
            Parent root = FXMLLoader.load(getClass().getResource("changePassword.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setUserData(user);
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show(); 
            */
        }
    }
    
}
