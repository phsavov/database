package utd.database;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javafx.scene.control.Button;

public class HomePageController {

    private Scene scene;
    private Parent root;
    private Stage stage;

    @FXML
    private Button button_logout;
    @FXML
    Button changePass;
    @FXML
    Button browseButton;
    @FXML
    Button buyButton;
    @FXML
    Button sellButton;
    @FXML
    Button holdingButton;
    @FXML
    Button transactionButton;
    @FXML
    Button logOut;




    @FXML
    public void browseStockButton(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        User user = (User) stage.getUserData();

        root = FXMLLoader.load(getClass().getResource("BrowseStockPage.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setUserData(user);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void buyStockButton(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        User user = (User) stage.getUserData();

        root = FXMLLoader.load(getClass().getResource("BuyStocksPage.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setUserData(user);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void sellStocksButton(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        User user = (User) stage.getUserData();

        root = FXMLLoader.load(getClass().getResource("SellStocksPage.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setUserData(user);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void viewHoldingsButton(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        User user = (User) stage.getUserData();

        root = FXMLLoader.load(getClass().getResource("ViewHoldings.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setUserData(user);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void viewTransactionsButton(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        User user = (User) stage.getUserData();

        root = FXMLLoader.load(getClass().getResource("TransactionPage.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setUserData(user);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void logoutButton(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void changePassWord(ActionEvent event) throws IOException{
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        User user = (User) stage.getUserData();

        root = FXMLLoader.load(getClass().getResource("ChangePassword.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setUserData(user);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
