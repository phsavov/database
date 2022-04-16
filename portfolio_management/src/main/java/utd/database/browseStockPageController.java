package utd.database;

import java.io.*;
import java.net.*;

import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class browseStockPageController {
    
    @FXML
    Button returnHome;

    @FXML
    TableView<Stock> stocksTable;

    @FXML
    TextField stockField;

    @FXML
    Button addButton;

    @FXML
    TableColumn<Stock, String> stock;

    @FXML
    TableColumn<Stock, String> price;

    private Scene scene;
    private Parent root;
    private Stage stage;
    
    @FXML
    public void goBack(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void addButtonPushed(ActionEvent event) throws IOException {
        String ticker = stockField.getText();

        if (ticker.length() > 5){
            Alert badInput = new Alert(Alert.AlertType.ERROR);
            badInput.setHeaderText("Incorrect Input");
            badInput.setHeaderText("The Stock ticker entered was too long.\n Please enter a correct ticker.");
            badInput.showAndWait();
            stockField.clear();
            return;
        }
        String api = "http://127.0.0.1:5000/stock/"+ticker;
        InputStreamReader in = null;
        try {
            URL url = new URL(api);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            in = new InputStreamReader(conn.getInputStream());
        } catch (Exception e) {
            Alert noConnection = new Alert(Alert.AlertType.ERROR);
            noConnection.setHeaderText("No Connection to API");
            noConnection.setHeaderText("Java was not able to connect to the API.");
            noConnection.showAndWait();
            stockField.clear();
            return;
        }
        
        
        BufferedReader br = new BufferedReader(in);
        String response = br.readLine();


        if (response.equals("Invalid Ticker")) {
            Alert badTicker = new Alert(Alert.AlertType.ERROR);
            badTicker.setHeaderText("Ticker does not exist.");
            badTicker.setHeaderText("The Ticker."+ticker+"does not exist.\nPlease try again.");
            badTicker.showAndWait();
            stockField.clear();
            return;
        } else {
            //TODO Make the two items into observable list
            System.out.println(response);
        }
    }


}
