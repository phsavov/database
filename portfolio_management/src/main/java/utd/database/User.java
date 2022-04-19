package utd.database;

import java.util.*;

public class User {
    
    private String accountID;
    protected ArrayList<Holding> stocks = new ArrayList<>();
    private String phrase;
    public User(String accountID){
        this.accountID = accountID;
    }

    public User(){
    }

    public String getAccountID(){
        return accountID;
    }

    public void addStocks(Stock stock, int ammount){
        stocks.add(new Holding(stock, ammount));
    }

    public String generateAcountID(){
        String id = "";

        Random rand = new Random();
        id = id + Math.abs((rand.nextInt() % 10000000));

        System.out.println(id);
        if (id.length() < 10){
            return id;
        } else {
            id.substring(0, id.length() - 2);
            return id;
        }
    }

    public void setPhrase() { 
        phrase = generateMnemonicPhrase();
        //TODO set it in the database
    }

    public String getPhrase(){
        return phrase;
    }

    private String generateMnemonicPhrase(){
        String phrase = "";
        char[] alphabet = new char[26];
        int character = 65;
        for (int i = 0; i < 26; i++){
            alphabet[i] = (char) character;
            character++;
        }
        Random random = new Random();
        for (int i = 0; i < 5; i++){
            phrase = phrase + alphabet[random.nextInt(26)];
        }
        return phrase;
    }
}
