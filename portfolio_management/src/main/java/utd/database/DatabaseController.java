package utd.database;
import java.sql.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DatabaseController {
    
    private Connection connection;
    private static String BUY_STOCK_QUERY = "";
    private static String INSERT_STOCK = "INSERT INTO \"Stocks\"(\"StockID\", \"StockValue\", \"Open\", \"Close\", \"High\", \"Change\") VALUES (?,?,?,?,?,?);";
    private static String CREATE_USER = "INSERT INTO \"UserAccount\"(\"AccountID\",\"Uname\",\"userPassword\",\"FirstName\",\"LastName\") VALUES (?,?,?,?,?);";
    private static String UNAME_QUERY = "Select \"AccountID\" from \"UserAccount\" where \"Uname\" = ?";
    private static String LOGIN_QUERY = "Select \"AccountID\" from \"UserAccount\" where \"Uname\" = ? and \"userPassword\" = ?;";
    private static String PHRASE_QUERY = "Select \"MnemonicPhrase\" from \"SecurityInfo\" where \"AccountID\" = ?";
    private static String GET_STOCKS = "Select \"StockID\", \"Close\" from \"Stocks\";";
    private static String VIEW_HOLDINGS_QUERY = "";
    private static String SELL_STOCK_QUERY = "";
    private static String TRADE_STOCK_QUERY = "";

    public DatabaseController() throws SQLException
    {
        connection = DriverManager.getConnection("jdbc:postgresql://ads-postgres.cm0vx7gau7uz.us-east-1.rds.amazonaws.com:5432/database_project", "phsavov", "PhiLeTo2001BL");
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

    public ObservableList<Stock> getStocks() throws SQLException{
        ObservableList<Stock> stocks = FXCollections.observableArrayList();
        Statement query = connection.createStatement();
        ResultSet result = query.executeQuery(GET_STOCKS);

        while (result.next()){
            stocks.add(new Stock(result.getString(1), result.getString(2)));
        }

        return stocks;
        
    }

    public boolean addStock(String ticker, double open, double high, double close) throws SQLException{
        PreparedStatement prep = connection.prepareStatement(INSERT_STOCK);
        prep.setString(1, ticker);
        prep.setDouble(2, close);
        prep.setDouble(3, open);
        prep.setDouble(4, close);
        prep.setDouble(5, high);
        prep.setDouble(6, (close - open));
        try{
            prep.executeUpdate();
        } catch (Exception e){
            return false;
        }
        return true;
    }

    public String getAccountID(String user, String pass) throws SQLException{
        Statement query = connection.createStatement();
        ResultSet result = query.executeQuery("Select \"AccountID\" from \"UserAccount\" WHERE \"Uname\" = '"+user+"' and \"userPassword\" = '"+pass+"';");
        result.next();
        return result.getString(1);
    }

    public boolean updatePass(String newPass, String accountID) throws SQLException{

        Statement query = connection.createStatement();
        return query.executeUpdate("UPDATE \"UserAccount\" SET \"userPassword\" = '"+newPass+"' WHERE \"AccountID\" = '"+accountID+"';") > 0;
    }

    public String[] GenericSelect(String[] colms, String from, String extras) throws SQLException
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
        

        return ResultsettoStringArray(ps.executeQuery(sb.toString()));
    }

    public int BuyStock(String accID,String stockID,int quantity) throws SQLException
    {
        PreparedStatement ps = connection.prepareStatement(BUY_STOCK_QUERY);
        ps.setString(1, accID);
        ps.setString(2, stockID);
        ps.setInt(3, quantity);

        return ps.executeUpdate();
    }

    public boolean createUser(String username, String password, String fname, String lname) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(CREATE_USER);
        ps.setString(1, (new User().generateAcountID()));
        ps.setString(2, username);
        ps.setString(3, password);
        ps.setString(4, fname);
        ps.setString(5, lname);
        ps.executeUpdate();
        return true;
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
