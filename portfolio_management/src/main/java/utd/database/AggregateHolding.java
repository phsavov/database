package utd.database;

public class AggregateHolding
{
    String ticker;
    int shares;
    double sellPrice;


    public AggregateHolding(String ticker, int shares, double sellPrice) {
        this.ticker = ticker;
        this.shares = shares;
        this.sellPrice = sellPrice;
    }

    public AggregateHolding()
    {}

    public String getTicker(){
        return ticker;
    }

    public int getShares(){
        return shares;
    }

    public double getSellPrice(){
        return sellPrice;
    }
    
}