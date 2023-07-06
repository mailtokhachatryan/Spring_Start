package am.myOffice.shopJDBC.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static DatabaseConnection instance;
    private Connection connection;

    private DatabaseConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/group1";
            String username = "postgres";
            String password = "postgres";
            this.connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected Successfully");
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println("Database Connection Creation Failed : " + ex.getMessage());
        }
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        } else {
            try {
                if (instance.getConnection().isClosed()) {
                    instance = new DatabaseConnection();
                }
            } catch (SQLException e) {
                System.out.println("Database Connection Creation Failed : " + e.getMessage());
            }
        }

        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}