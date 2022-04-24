package utd.database;


import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class TransactionPageController {
    
    private Scene scene;
    private Parent root;
    public Stage stage;
    public ObservableList<Transaction> transList;

    @FXML
    private Button Home;

    @FXML
    private Button logOut;

    @FXML
    private Button showTransactionView;

    @FXML
    private TableView<Transaction> transactionTable;
    @FXML
    private TableColumn<Transaction, String> stockCol;

    @FXML
    private TableColumn<Transaction, Date> dateCol;

    @FXML
    private TableColumn<Transaction, String> transactionCol;

    @FXML
    private TableColumn<Transaction, String> qtyCol;

    Node node;
    User user;

    @FXML
    void backToHome(ActionEvent event) throws IOException 
    {
        node = (Node) event.getSource();
        stage = (Stage) node.getScene().getWindow();
        user = (User) stage.getUserData();
        root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setUserData(user);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void logOutButton(ActionEvent event) throws IOException 
    {
        node = (Node) event.getSource();
        stage = (Stage) node.getScene().getWindow();
        user = (User) stage.getUserData();
        root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setUserData(user);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML 
    void populateTransactionView(ActionEvent event) throws IOException, SQLException
    {
        node = (Node) event.getSource();
        stage = (Stage) node.getScene().getWindow();
        user = (User) stage.getUserData();

        transList = new DatabaseController().ViewTransaction(user.getAccountID());
        transactionCol.setCellValueFactory(new PropertyValueFactory<>("TransactionID"));
        stockCol.setCellValueFactory(new PropertyValueFactory<>("stockID"));
        qtyCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("buyDate"));
    }

  

}
