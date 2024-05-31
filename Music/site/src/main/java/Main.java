import database.DatabaseConnection;
import auth.AccountManager;
import auth.Account;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        DatabaseConnection dbConnection = new DatabaseConnection();
        try {
            dbConnection.connect("test.db");
            Connection conn = dbConnection.getConnection();
            Statement stmt = conn.createStatement();
            String createTableSQL = "CREATE TABLE IF NOT EXISTS accounts (id INTEGER PRIMARY KEY, username TEXT, password TEXT)";
            stmt.execute(createTableSQL);

            AccountManager accountManager = new AccountManager(dbConnection);
            accountManager.register("john_doe", "password123");
            boolean isAuthenticated = accountManager.authenticate("john_doe", "password123");
            System.out.println("Authentication successful: " + isAuthenticated);

            Account account = accountManager.getAccount("john_doe");
            System.out.println("Account retrieved: ID=" + account.id() + ", Username=" + account.username());

            dbConnection.disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}