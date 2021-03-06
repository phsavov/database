package utd.database;

public class Stock {

    public String ticker;
    public String open;
    public String high;
    public String close;
    public double bought;
    public double sold;


    public Stock(String ticker, String open, String high, String close) {
        this.ticker = ticker;
        this.open = open;
        this.high = high;
        this.close = close;
    }

    

    public Stock(String ticker, String close) {
        this.ticker = ticker;
        this.close = close;
    }

    public Stock(String ticker, double bought, double sold) {
        this.ticker = ticker;
        this.bought = bought;
        this.sold = sold;
    }
    
    public String getTicker() {
        return ticker;
    }

    public String getOpen() {
        return open;
    }

    public String getHigh() {
        return high;
    }

    public final String getClose() {
        return close;
    }
    public Double getBought(){
        return bought;
    }
    public Double getSold(){
        return sold;
    }
    
}
