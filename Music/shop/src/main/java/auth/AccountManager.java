package auth;

import at.favre.lib.crypto.bcrypt.BCrypt;
import database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountManager {
    private final DatabaseConnection dbConnection;

    public AccountManager(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public void register(String username, String password) throws SQLException {
        Connection conn = dbConnection.getConnection();
        String hashedPassword = BCrypt.withDefaults().hashToString(12, password.toCharArray());
        String insertSQL = "INSERT INTO accounts (username, password) VALUES (?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(insertSQL);
        pstmt.setString(1, username);
        pstmt.setString(2, hashedPassword);
        pstmt.executeUpdate();
    }

    public boolean authenticate(String username, String password) throws SQLException {
        Connection conn = dbConnection.getConnection();
        String querySQL = "SELECT password FROM accounts WHERE username = ?";
        PreparedStatement pstmt = conn.prepareStatement(querySQL);
        pstmt.setString(1, username);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            String hashedPassword = rs.getString("password");
            BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), hashedPassword);
            return result.verified;
        }
        return false;
    }

    public Account getAccount(String username) throws SQLException {
        Connection conn = dbConnection.getConnection();
        String querySQL = "SELECT id, username FROM accounts WHERE username = ?";
        PreparedStatement pstmt = conn.prepareStatement(querySQL);
        pstmt.setString(1, username);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return new Account(rs.getInt("id"), rs.getString("username"));
        }
        return null;
    }

    public Account getAccount(int id) throws SQLException {
        Connection conn = dbConnection.getConnection();
        String querySQL = "SELECT id, username FROM accounts WHERE id = ?";
        PreparedStatement pstmt = conn.prepareStatement(querySQL);
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return new Account(rs.getInt("id"), rs.getString("username"));
        }
        return null;
    }
}