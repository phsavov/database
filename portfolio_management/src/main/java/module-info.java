module utd.database {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens utd.database to javafx.fxml;
    exports utd.database;
}
