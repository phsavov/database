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

public class SellStockUser {

    String stock;
    double priceBought, sellPrice;

    public void setStock(String stock) {
        this.stock = stock;
    }

    public void setPriceBought(double priceBought) {
        this.priceBought = priceBought;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getStock() {
        return stock;
    }

    public double getPriceBought() {
        return priceBought;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public SellStockUser(String stock, double priceBought, double sellPrice) {
        this.stock = stock;
        this.priceBought = priceBought;
        this.sellPrice = sellPrice;
    }
}
