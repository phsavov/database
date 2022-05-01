package utd.database;
import java.sql.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DatabaseController {
    
    private Connection connection;
    private static String INSERT_TRANSACTION ="INSERT INTO \"Transactions\"(\"TransactionID\",\"StockID\",\"Quantity\",\"Date\")\n" +
            "VALUES(?,?,?,?);\n";
    private static String INSERT_HOLDING ="INSERT INTO \"Holdings\"(\"AccountID\",\"TransactionID\",\"StockID\",\"BuyPrice\",\"CurrentValue\",\"PurchaseDate\")\n" +
            "VALUES(?,?,?,?,?,?);\n";
    private static String UPDATE_BALANCE ="UPDATE \"Portfolio\"\n" +
            "SET \"Balance\" = ?\n" +
            "WHERE \"AccountID\"= ?;";

    private static String ADD_FUNDS = "UPDATE \"Portfolio\" SET \"Balance\" = Balance + ? WHERE \"AccountID\"= ?;";

    private static String AGGREGATE_HOLDINGS= "select \"Holdings\".\"StockID\", sum(\"Quantity\") as \"shares\", \"Holdings\".\"CurrentValue\" as \"Sell Price\"\n"+
                                               "from (\"Holdings\" join \"Transactions\" on \"Holdings\".\"TransactionID\" = \"Transactions\".\"TransactionID\")\n"+
                                               "where \"AccountID\" = ?\n"+
                                               "group by \"Holdings\".\"StockID\", \"Holdings\".\"CurrentValue\";";
    
    private static String INSERT_STOCK = "INSERT INTO \"Stocks\"(\"StockID\", \"StockValue\", \"Open\", \"Close\", \"High\", \"Change\") VALUES (?,?,?,?,?,?);";
    private static String CREATE_USER = "INSERT INTO \"UserAccount\"(\"AccountID\",\"Uname\",\"userPassword\",\"FirstName\",\"LastName\") VALUES (?,?,?,?,?);";
    private static String UNAME_QUERY = "Select \"AccountID\" from \"UserAccount\" where \"Uname\" = ?";
    private static String LOGIN_QUERY = "Select \"AccountID\" from \"UserAccount\" where \"Uname\" = ? and \"userPassword\" = ?;";
    private static String PHRASE_QUERY = "Select \"MnemonicPhrase\" from \"SecurityInfo\" where \"AccountID\" = ?";
    private static String GET_STOCKS = "Select \"StockID\", \"Close\" from \"Stocks\";";
    private static String VIEW_HOLDINGS_QUERY =  "Select * from \"Holdings\" where \"AccountID\" = ?";
    private static String SELL_STOCK_QUERY = "";
    private static String TRADE_STOCK_QUERY = "";
    private static String VIEW_TRANSACTION_QUERY = "Select * from \"Transactions\", \"UserAccount\" where \"AccountID\" = ?";
    private static String GET_BALANCE = "select \"Balance\"\n from \"Portfolio\"\n where \"Portfolio\".\"AccountID\" = ?;";
    private static String GET_ONE_STOCK = "Select \"StockID\" from \"Stocks\" WHERE \"StockID\" = ?;";

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

    public double getBalance(String accID) throws SQLException
    {
        PreparedStatement ps = connection.prepareStatement(GET_BALANCE);
        ps.setString(1, accID);
        ResultSet result = ps.executeQuery();
        result.next();
        return result.getDouble(1);
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

    public int[] BuyStock(String accID, String stockID, int quantity, double balance, double price) throws SQLException
    {
        PreparedStatement p1s = connection.prepareStatement(INSERT_TRANSACTION);
        PreparedStatement p2s = connection.prepareStatement(INSERT_HOLDING);
        PreparedStatement p3s = connection.prepareStatement(UPDATE_BALANCE);
        Date currentDate = new Date(System.currentTimeMillis());
        String transID =  Transaction.GenerateTID();

        p1s.setString(1, transID);
        p1s.setString(2, stockID);
        p1s.setInt(3, quantity);
        p1s.setDate(4,currentDate);

        p2s.setString(1, accID);
        p2s.setString(2, transID);
        p2s.setString(3, stockID);
        p2s.setDouble(4, price);
        p2s.setDouble(5, price);
        p2s.setDate(6, currentDate);

        p3s.setDouble(1, balance);
        p3s.setString(2, accID);

        Statement batch = connection.createStatement();
        
        batch.addBatch(String.valueOf(p1s));
        batch.addBatch(String.valueOf(p2s));
        batch.addBatch(String.valueOf(p3s));
        return batch.executeBatch();
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
     public ObservableList<Holding> ViewHoldings(String accId) throws SQLException
    {
        ObservableList<Holding> holdList = FXCollections.observableArrayList();
        PreparedStatement ps = connection.prepareStatement(VIEW_HOLDINGS_QUERY);
        ps.setString(1, accId);
        ResultSet rSet = ps.executeQuery();
        while(rSet.next())
        {
            //creating a Holdings object
            Holding holdLst = new Holding();

            //setting the attributes of that object with the values from the query with the column names used
            holdLst.setStockID(rSet.getString("StockID"));
            holdLst.setBuyPrice(rSet.getDouble("BuyPrice"));
            holdLst.setCurrentVal(rSet.getDouble("currentValue") );

            //adding the object to the holdings list
            holdList.add(holdLst);  
        }
        //returning the holding list
        return holdList;
    }
/*
    public ObservableList<Stock> sellList(String userID) throws SQLException{
        ObservableList<Stock> list = FXCollections.observableArrayList();
        PreparedStatement prep = connection.prepareStatement(VIEW_HOLDINGS_QUERY);
        prep.setString(1, userID);

        ResultSet result = prep.executeQuery();

        while (result.next()){
            list.add(new Stock(result.getString("StockID"), result.getDouble("BuyPrice"), result.getDouble("CurrentValue")));
        }

        return list;
    }
    */
    
    public ObservableList<Transaction> ViewTransaction(String userID) throws SQLException
    {
        ObservableList<Transaction> transactionList = FXCollections.observableArrayList();

        PreparedStatement prepStat = connection.prepareStatement(VIEW_TRANSACTION_QUERY);
        prepStat.setString(1, userID);
        ResultSet resultSet = prepStat.executeQuery();
        while(resultSet.next())
        {
            Transaction tsn = new Transaction();
            tsn.setTransactionID(resultSet.getString("TransactionID"));
            tsn.setStockID(resultSet.getString("StockID"));
            tsn.setQuantity(resultSet.getInt("Quantity"));
            tsn.setBuyDate(resultSet.getDate("Date")); 
            transactionList.add(tsn);  
        }
        return transactionList;
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

    public boolean updateBal(String accId,double StockValue, int stockNum) throws SQLException
    {
        PreparedStatement p = connection.prepareStatement(ADD_FUNDS);

        p.setDouble(1, StockValue * stockNum);
        p.setString(2, accId);

        return p.execute();
    }

    public boolean addFunds(String accId,double amt) throws SQLException
    {
        PreparedStatement p = connection.prepareStatement(ADD_FUNDS);
        
        p.setDouble(1, amt);
        p.setString(2, accId);

        return p.execute();
    }

    public double getSellVal(String stockID) throws SQLException {
        Statement query = connection.createStatement();
        ResultSet result = query.executeQuery("Select \"CurrentValue\" from \"Holdings\" WHERE \"StockID\" = '"+stockID+"';");
        result.next();
        return result.getDouble(1);
    }

    public boolean getStockID(String ticker) throws SQLException {
        Statement query = connection.createStatement();
        ResultSet result = query.executeQuery(GET_ONE_STOCK);

        if(result.absolute(1)) {
            return false;
        }
        else {
            return true;
        }
    }

    public ObservableList<AggregateHolding> aggregateHoldings (String accId) throws SQLException
    {
        PreparedStatement p = connection.prepareStatement(AGGREGATE_HOLDINGS);

        ObservableList<AggregateHolding> hlist = FXCollections.observableArrayList();

        p.setString(1, accId);
        ResultSet resultSet = p.executeQuery();
        while(resultSet.next())
        {
            AggregateHolding h = new AggregateHolding();
            h.ticker = resultSet.getString("StockID");
            h.shares = resultSet.getInt("shares");
            h.sellPrice = resultSet.getDouble("Sell Price");
            hlist.add(h);  
        }

        return hlist;
    }

    public boolean SellStock(String accid, String stockid, int quantity) throws SQLException
    {
        PreparedStatement p1 = connection.prepareStatement(INSERT_TRANSACTION);
        PreparedStatement p3 = connection.prepareStatement(INSERT_HOLDING);
        PreparedStatement p2 = connection.prepareStatement(UPDATE_BALANCE);

        double sellPrice = getSellVal(stockid);
        Date today = new Date(System.currentTimeMillis());
        String tid = Transaction.GenerateTID();

        p1.setString(1, tid);
        p1.setString(2, stockid);
        p1.setInt(3,-quantity);
        p1.setDate(4, today);

        p2.setDouble(1, quantity * sellPrice);
        p2.setString(2, accid);

        p3.setString(1, accid);
        p3.setString(2, tid);
        p3.setString(3, stockid);
        p3.setDouble(4, 0);
        p3.setDouble(5, sellPrice);
        p3.setDate(6, today);
        
        Statement s = connection.createStatement();

        s.addBatch(String.valueOf(p1));
        s.addBatch(String.valueOf(p2));
        s.addBatch(String.valueOf(p3));

        int[] state = s.executeBatch();

        for (int x : state) 
        {
            if (x < 0)
                return false;
        }
        return true;
    }

    public boolean deleteSell(String ticker) throws SQLException {
        Statement query = connection.createStatement();
        return query.executeUpdate("DELETE FROM \"Holdings\" WHERE \"StockID\" = '"+ticker+"';") > 0;
    }
}
