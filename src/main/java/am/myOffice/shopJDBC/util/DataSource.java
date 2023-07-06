package am.myOffice.shopJDBC.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {

    private Connection connection;

    public DataSource(String url, String username, String password, String driver) {


        try {
            Class.forName(driver);
            this.connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected Successfully");
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println("Database Connection Creation Failed : " + ex.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }
}