package utd.database;

public class Stock {
    
    public String ticker;
    public double open;
    public double high;
    public double close;
    public Stock(String ticker, double open, double high, double close) {
        this.ticker = ticker;
        this.open = open;
        this.high = high;
        this.close = close;
    }
    public Stock(String ticker, double close){
        this.ticker = ticker;
        this.close = close;
    }
}
