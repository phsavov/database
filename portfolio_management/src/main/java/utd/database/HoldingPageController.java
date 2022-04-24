package utd.database;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class HoldingPageController {

    private Scene scene;
    private Parent root;
    Stage stage;
    Node node;
    User user;

    //list that will hold the results of the query
    public ObservableList<Holding> holdingsList;

    @FXML
    private Button logOut;

    @FXML
    private Button home;

    @FXML
    private Button showHoldingsView;
    
    @FXML
    private TableView<Transaction> holdingTable;
    @FXML
    private TableColumn<Transaction, String> colStock;

    @FXML
    private TableColumn<Transaction, Date> puchaseDateCol;

    @FXML
    private TableColumn<Transaction, Double> buyPriceCol;

    @FXML
    private TableColumn<Transaction, Double> curValCol;

    @FXML
    private TableColumn<Transaction, String> transIDCol;

 
    @FXML
    void backHomeOnClick(ActionEvent event) throws IOException 
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
    void logOutOnClick(ActionEvent event) throws IOException 
    {
        
        node = (Node) event.getSource();
        stage = (Stage) node.getScene().getWindow();
        user = (User) stage.getUserData();

        root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();

        //So that user is saved regardless of button being saved
        stage.setUserData(user);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void populateHoldingsView (ActionEvent event) throws IOException, SQLException
    {
        node = (Node) event.getSource();
        stage = (Stage) node.getScene().getWindow();
        user = (User) stage.getUserData();

        holdingsList = new DatabaseController().ViewHoldings(user.getAccountID());
        colStock.setCellValueFactory(new PropertyValueFactory<>("stockID"));
        puchaseDateCol.setCellValueFactory(new PropertyValueFactory<>("purchaseDate"));
        buyPriceCol.setCellValueFactory(new PropertyValueFactory<>("buyPrice"));
        curValCol.setCellValueFactory(new PropertyValueFactory<>("currentVal"));
        transIDCol.setCellValueFactory(new PropertyValueFactory<>("transID"));
        
        
    }


}
