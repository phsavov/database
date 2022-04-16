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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.sql.SQLException;

public class SellStocksPageController {

    private Scene scene;
    private Parent root;
    private Stage stage;

    @FXML
    private TableView<SellStockUser> table_users;

    @FXML
    private TableColumn<SellStockUser,String> col_stock;

    @FXML
    private TableColumn<SellStockUser,Double> col_priceBought;

    @FXML
    private TableColumn<SellStockUser,Double> col_sellPrice;

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
        int selectedID = table_users.getSelectionModel().getSelectedIndex();
        table_users.getItems().remove(selectedID);
    }
}
