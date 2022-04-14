package utd.database;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.*;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("LoginPage"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) 
    {
        //launch();
        test();    
    }

    public static void test()
    {
        try
        {
            DatabaseController db = new DatabaseController();


            /*String[] testALL = db.RawInputQuery("SELECT * FROM UserAccount;");
            for(String s : testALL) {
                System.out.println(s);
            }

            /*boolean testLogin = db.LoginUser("user", "Pass");
            system.out.println("Logged in: "testLogin);

            String[] testHoldings = db.ViewHoldings("5");
            for(String s : testHoldings)
            {
                System.out.println(s);
            }
            */
            db.CloseDBC();
        }
        catch(Exception e)
        {
            System.out.println(e.getStackTrace());
        }
    }

}