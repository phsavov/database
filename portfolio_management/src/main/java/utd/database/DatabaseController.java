package utd.database;
import java.sql.*;

import javax.naming.spi.DirStateFactory.Result;

public class DatabaseController {
    
    private Connection connection;
    private static String BUY_STOCK_QUERY = "";
    private static String LOGIN_QUERY = "";
    private static String VIEW_HOLDINGS_QUERY = "";
    private static String SELL_STOCK_QUERY = "";
    private static String TRADE_STOCK_QUERY = "";

    public DatabaseController() throws SQLexcpetion{
        System.out.println("Hello, world");

        connection = DriverManager.getConnection("https//:127.0.0.1", "user", "password");
    }

    public void CloseDBC()
    {
        connection.close();
    }

    // takes a single string and send 
    // that unprocessed string as a query to the database
    // good for sql injection
    public ResultSet RawInputQuery(String query)
    {
        Statement s = connection.createStatement();
        return s.executeQuery(query);
    }

    public ResultSet GenericSelect(String[] colms, String from, String extras)
    {
        Statement ps = connection.createStatement();
        StringBuilder sb = new StringBuilder();

        sb.append("SELECT ");
        for (int i = 0; i < colms.length-1; i++) {
            sb.append(colms[i]);
        }
        sb.append(colms[colms.length-1]);
        sb.append("\nFROM " + from);
        if(extra != null)
        {
            sb.append("\n"+ extras +";");
        }
        else sb.append(";");
        

        return ps.executeQuery(sb.toString());
    }

    public boolean BuyStock(String accID,String stockID,int quantity)
    {
        PreparedStatement ps = connection.prepareStatement(BUY_STOCK_QUERY);
        ps.setString(1, accID);
        ps.setString(2, stockID);
        ps.setString(3, quantity);

        return ps.executeUpdate();
    }

    // hopefully safe version of user login
    public boolean LoginUser(String user, String Pass)
    {
        PreparedStatement ps = connection.prepareStatement(LOGIN_QUERY);
        ps.setString(1, user);
        ps.setString(2, Pass);

        return ps.execute();
    }

    //Returns a string array of every hold an account has
    // in the format
    // holdings[x] = "StockId,Quantity,Price,CurrentValue";
    public String[] ViewHoldings(String accId)
    {
        PreparedStatement ps = connection.prepareStatement(VIEW_HOLDINGS_QUERY);

        ps.setString(1, accId);

        ResultSet r = ps.executeQuery();
        StringBuilder sb = new StringBuilder();

        while(r.next())
        {
            sb.append(r.getString(1)+",");
            sb.append(r.getString(2)+",");
            sb.append(r.getString(3)+",");
            sb.append(r.getString(4)+",");
            sb.append(r.getString(5)+"\n");
        }

        String[] rows = sb.toString().split("\n");

        return rows;
    }

    public boolean SellStocks(String accId,String stockid)
    {
        PreparedStatement ps = connection.prepareStatement(SELL_STOCK_QUERY);

        ps.setString(1, accId);
        ps.setString(2, stockid);

        return ps.executeUpdate();
    }
}
