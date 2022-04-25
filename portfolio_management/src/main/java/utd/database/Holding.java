package utd.database;

import java.sql.Date;

public class Holding {
    
    public Stock stock;
    protected int ammount;

    private String stockID;
    private Date purchaseDate;
    private double buyPrice;
    private double currentVal;
    private String transID;
    private int numShares;
    
    public Holding() {
    }

    public int getNumShares()
    {
        return numShares;
    }

    public void setNumShares(int numShares)
    {
        this.numShares = numShares;
    }

    //Accessor Methods
    public String getTransID() {
        return transID;
    }

    public void setTransID(String transID) {
        this.transID = transID;
    }

    public double getCurrentVal() {
        return currentVal;
    }

    public void setCurrentVal(double currentVal) {
        this.currentVal = currentVal;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getStockID() {
        return stockID;
    }

    public void setStockID(String stockID) {
        this.stockID = stockID;
    }

    public Holding(Stock stock, int ammount){
        this.stock = stock;
        this.ammount = ammount;
    }
}
