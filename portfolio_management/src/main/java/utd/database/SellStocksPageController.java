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
    private TableView<Stock> table_users;

    @FXML
    private TableColumn<Stock,String> col_stock;

    @FXML
    private TableColumn<Stock,Double> col_priceBought;

    @FXML
    private TableColumn<Stock,Double> col_sellPrice;

    @FXML
    private Button updateButton;

    @FXML
    Button homePage;

    @FXML
    TextField Ticker;

    @FXML
    TextField NumStock;

    @FXML
    public void homeButton(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void sellButton(ActionEvent event) throws SQLException {
        DatabaseController database = new DatabaseController();
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        User user = (User) stage.getUserData();
        
        String ticker = Ticker.getText();
        double stockNum = Double.parseDouble(NumStock.getText());

        if (ticker.length() > 5){
            Alert badInput = new Alert(Alert.AlertType.ERROR);
            badInput.setHeaderText("Incorrect Input");
            badInput.setHeaderText("The Stock ticker entered was too long.\n Please enter a correct ticker.");
            badInput.showAndWait();
            Ticker.clear();
            return;
        }

        boolean stockTicker = new DatabaseController().getStockID(ticker);
        double sellPrice = new DatabaseController().getSellVal(ticker);

        if (stockTicker && new DatabaseController().updateBal(sellPrice, stockNum)) {
            Alert made = new Alert(Alert.AlertType.CONFIRMATION);
            made.setHeaderText("Stock sold");
            made.showAndWait();
            new DatabaseController().deleteSell(ticker);
        }
        else {
            Alert made = new Alert(Alert.AlertType.ERROR);
            made.setHeaderText("Stock Not Sold");
            made.setContentText("There was an issue");
            made.showAndWait();
        }
        //int selectedID = table_users.getSelectionModel().getSelectedIndex();
        //table_users.getItems().remove(selectedID);
    }

    @FXML
    void loadButton(ActionEvent event) throws SQLException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        User user = (User) stage.getUserData();

        ObservableList<Stock> listOfStocks = new DatabaseController().getStocks();
        
        col_stock.setCellValueFactory(new PropertyValueFactory<>("ticker"));
        col_priceBought.setCellValueFactory(new PropertyValueFactory<>("bought"));
        col_sellPrice.setCellValueFactory(new PropertyValueFactory<>("sold"));

        table_users.setItems(listOfStocks);
    }
}
