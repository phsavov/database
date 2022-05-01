package utd.database;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.*;
import java.sql.Date;
import java.sql.SQLException;

public class buyStockController {

    @FXML
    Button returnHome;

    @FXML
    Button buyButton;

    @FXML
    Button add;

    @FXML
    TextField funds;

    @FXML
    TextField stockTextField;

    @FXML
    TextField priceTextField;

    @FXML
    TextField balanceTextField;

    @FXML
    TextField buyTextField;

    @FXML
    TableView<Stock> stocksTable;

    @FXML
    TableColumn<Stock, String> stock;

    @FXML
    TableColumn<Stock, Double> price;

    @FXML
    Button load;

    private Scene scene;
    private Parent root;
    private Stage stage;

    private String accID;
    private String transactionID;
    private Double balance;
    private Double totalPrice;
    private Date currentDate;

    @FXML
    public void goBack(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void buyStock(ActionEvent event) throws IOException, SQLException {
        
        balance = Double.valueOf(new DatabaseController().getBalance(accID));
        balanceTextField.setText(String.valueOf(balance));

        totalPrice = Double.valueOf(priceTextField.getText()) * Double.valueOf(buyTextField.getText());
        if(totalPrice > balance) {
            Alert insufficientFunds = new Alert(Alert.AlertType.ERROR);
            insufficientFunds.setHeaderText("Insufficient Funds");
            insufficientFunds.setContentText("The transaction was unable to complete. \nPlease add more funds to your account.");
            insufficientFunds.showAndWait();
        } else {
            DatabaseController connectNow = new DatabaseController();
            Transaction newTransaction = new Transaction();
            try {
                balance -= totalPrice;
                connectNow.BuyStock(accID, stockTextField.getText(),
                        Integer.valueOf(buyTextField.getText()), balance, Double.valueOf(priceTextField.getText()));
                Alert purchaseConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
                purchaseConfirmation.setHeaderText("Purchase Confirmation");
                purchaseConfirmation.setContentText("Purchase completed.");
                purchaseConfirmation.showAndWait();
                stage.setUserData(new Transaction());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void rowClicked(MouseEvent event) throws SQLException {
        Stock clickedStock = stocksTable.getSelectionModel().getSelectedItem();
        stockTextField.setText(String.valueOf(clickedStock.getTicker()));
        priceTextField.setText(String.valueOf(clickedStock.getClose()));

        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        User user = (User) stage.getUserData();
        accID = String.valueOf(user.getAccountID());

        buyTextField.setEditable(true);
        buyTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if(!t1.matches("\\d*")) {
                    buyTextField.setText(t1.replaceAll("[^\\d]", ""));
                }
            }
        });

        BooleanBinding booleanBind = buyTextField.textProperty().isEmpty();
        buyButton.disableProperty().bind(booleanBind);
    }

    @FXML
    public void loadBalance(ActionEvent event) throws SQLException{
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        User user = (User) stage.getUserData();
        accID = String.valueOf(user.getAccountID());

        balance = Double.valueOf(new DatabaseController().getBalance(accID));
        balanceTextField.setText(String.valueOf(balance));

    }

    @FXML
    public void AddFunds(ActionEvent event)
    {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        User user = (User) stage.getUserData();
        accID = String.valueOf(user.getAccountID());


        String adding = funds.getText();

        
        try
        {
            double add = Double.parseDouble(adding);

            DatabaseController db = new DatabaseController();

            System.out.println(accID);

            db.addFunds(accID, add);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            Alert made = new Alert(Alert.AlertType.ERROR);
            made.setHeaderText("Invalid Input");
            made.setContentText("There was an issue with the users input");
            made.showAndWait();
            funds.clear();
            
        }
    }

    public void initialize() throws SQLException {
        ObservableList<Stock> listOfStocks = new DatabaseController().getStocks();
        stock.setCellValueFactory(new PropertyValueFactory<>("ticker"));
        price.setCellValueFactory(new PropertyValueFactory<>("close"));
        stocksTable.setItems(listOfStocks);
    }

    public void updateBalance() throws SQLException
    {
        balance = Double.valueOf(new DatabaseController().getBalance(accID));
        balanceTextField.setText(String.valueOf(balance));
    }
}

