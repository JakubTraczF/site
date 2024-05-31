package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void connect(String dbPath) throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
        System.out.println("Connected to the database.");
    }

    public void disconnect() throws SQLException {
        if (connection != null) {
            connection.close();
            System.out.println("Disconnected from the database.");
        }
    }
}