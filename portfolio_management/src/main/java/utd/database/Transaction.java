package utd.database;

import java.sql.Date;
import java.util.Random;

public class Transaction {
    
    //attributes
    private String transactionID;
    private String stockID;
    private Date buyDate;
    private int quantity;

    public Transaction() 
    {
        
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Date buyDate) {
        this.buyDate = buyDate;
    }

    public String getStockID() {
        return stockID;
    }

    public void setStockID(String stockID) {
        this.stockID = stockID;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public static String GenerateTID()
    {
        String id = "";

        Random rand = new Random();
        id = id + Math.abs((rand.nextInt() % 10000000));

        
        if (id.length() < 10){
            return id;
        } else {
            id.substring(0, id.length() - 2);
            return id;
        }
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    

}
