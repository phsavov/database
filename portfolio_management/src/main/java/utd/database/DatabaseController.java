package utd.database;
import java.sql.*;

public class DatabaseController {
    
    private Connection connection;
    private static String BUY_STOCK_QUERY = "";
    private static String LOGIN_QUERY = "";
    private static String VIEW_HOLDINGS_QUERY = "";
    private static String SELL_STOCK_QUERY = "";
    private static String TRADE_STOCK_QUERY = "";

    public DatabaseController() throws SQLException{
        System.out.println("Hello, world");

        connection = DriverManager.getConnection("ads-postgres.cm0vx7gau7uz.us-east-1.rds.amazonaws.com", " phsavov", "PhiLeTo2001BL$");
    }

    public void CloseDBC() throws SQLException
    {
        connection.close();
    }

    // takes a single string and send 
    // that unprocessed string as a query to the database
    // good for sql injection
    public String[] RawInputQuery(String query) throws SQLException
    {
        Statement s = connection.createStatement();
        return ResultsettoStringArray(s.executeQuery(query));
    }

    public ResultSet GenericSelect(String[] colms, String from, String extras) throws SQLException
    {
        Statement ps = connection.createStatement();
        StringBuilder sb = new StringBuilder();

        sb.append("SELECT ");
        for (int i = 0; i < colms.length-1; i++) {
            sb.append(colms[i]);
        }
        sb.append(colms[colms.length-1]);
        sb.append("\nFROM " + from);
        if(extras != null)
        {
            sb.append("\n"+ extras +";");
        }
        else sb.append(";");
        

        return ps.executeQuery(sb.toString());
    }

    public int BuyStock(String accID,String stockID,int quantity) throws SQLException
    {
        PreparedStatement ps = connection.prepareStatement(BUY_STOCK_QUERY);
        ps.setString(1, accID);
        ps.setString(2, stockID);
        ps.setInt(3, quantity);

        return ps.executeUpdate();
    }

    // hopefully safe version of user login
    public boolean LoginUser(String user, String Pass) throws SQLException
    {
        PreparedStatement ps = connection.prepareStatement(LOGIN_QUERY);
        ps.setString(1, user);
        ps.setString(2, Pass);

        return ps.execute();
    }

    //Returns a string array of every hold an account has
    // in the format
    // holdings[x] = "StockId,Quantity,Price,CurrentValue";
    public String[] ViewHoldings(String accId) throws SQLException
    {
        PreparedStatement ps = connection.prepareStatement(VIEW_HOLDINGS_QUERY);

        ps.setString(1, accId);

        return ResultsettoStringArray(ps.executeQuery());
    }

    public int SellStocks(String accId,String stockid) throws SQLException
    {
        PreparedStatement ps = connection.prepareStatement(SELL_STOCK_QUERY);

        ps.setString(1, accId);
        ps.setString(2, stockid);

        return ps.executeUpdate();
    }

    public int TradeStocks(String toAccID, String fromAccID, String stockID, int count) throws SQLException
    {
        PreparedStatement ps = connection.prepareStatement(TRADE_STOCK_QUERY);

        ps.setString(1, toAccID);
        ps.setString(2, fromAccID);
        ps.setString(3, stockID);
        ps.setInt(4, count);

        return ps.executeUpdate();
    }

    private String[] ResultsettoStringArray(ResultSet r) throws SQLException
    {
        StringBuilder sb = new StringBuilder();

        while(r.next())
        {
            for (int i = 1; i < r.getMetaData().getColumnCount(); i++) 
            {
                sb.append(r.getString(i)+ ",");
            }
            sb.append(r.getString(r.getMetaData().getColumnCount())+"\n");
        }

        return sb.toString().split("\n");
    }
}
