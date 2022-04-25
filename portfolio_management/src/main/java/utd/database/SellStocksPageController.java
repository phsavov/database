package utd.database;

import javafx.application.Application;
import javafx.collections.ObservableList;
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

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;

public class SellStocksPageController {

    private Scene scene;
    private Parent root;
    private Stage stage;

    @FXML
    TableView<AggregateHolding> sellStocks;

    @FXML
    private TableColumn<AggregateHolding, String> col_stock;

    @FXML
    private TableColumn<AggregateHolding, Double> col_sellPrice;

    @FXML
    private TableColumn<AggregateHolding, Integer> numShares;

    @FXML
    private Button updateButton;

    @FXML
    Button sellButton;

    @FXML
    Button homePage;

    @FXML
    private TextField ticker;

    @FXML
    private TextField numStocks;

    User user;

    @FXML
    public void homeButton(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void sellStock(ActionEvent event) throws SQLException {
        DatabaseController database = new DatabaseController();
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        User user = (User) stage.getUserData();

        String tickerString = ticker.getText();
        String numofShares = numStocks.getText();

        for (int i = 0; i < numofShares.length(); i++) {
            if (numofShares.charAt(i) > 57) {
                Alert badInput = new Alert(Alert.AlertType.ERROR);
                badInput.setHeaderText("Incorrect Input");
                badInput.setHeaderText("The number of shares was invalid.\n Please enter a valid value.");
                badInput.showAndWait();
                ticker.clear();
                return;
            }
        }

        if (tickerString.length() > 5) {
            Alert badInput = new Alert(Alert.AlertType.ERROR);
            badInput.setHeaderText("Incorrect Input");
            badInput.setHeaderText("The Stock ticker entered was too long.\n Please enter a correct ticker.");
            badInput.showAndWait();
            ticker.clear();
            return;
        }

        if (database.SellStock(user.getAccountID(), tickerString, Integer.valueOf(numofShares))) {
            Alert sold = new Alert(Alert.AlertType.CONFIRMATION);
            sold.setHeaderText("CONFIRMED");
            sold.setContentText("You have sold the stock!");
            sold.showAndWait();
            ticker.clear();
            numStocks.clear();
        } else {
            Alert sold = new Alert(Alert.AlertType.ERROR);
            sold.setHeaderText("ERROR");
            sold.setContentText("There was an error with selling the stock\ntransaction not completed");
            sold.showAndWait();
            ticker.clear();
            numStocks.clear();
        }
    }

    @FXML
    public void loadButton(ActionEvent event) throws SQLException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        user = (User) stage.getUserData();

        ObservableList<AggregateHolding> listOfStocks = new DatabaseController().aggregateHoldings(user.getAccountID());

        col_stock.setCellValueFactory(new PropertyValueFactory<>("ticker"));
        numShares.setCellValueFactory(new PropertyValueFactory<>("shares"));
        col_sellPrice.setCellValueFactory(new PropertyValueFactory<>("sellPrice"));

        try {
            sellStocks.setItems(listOfStocks);
            System.out.print("here");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
