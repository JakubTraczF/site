import database.DatabaseConnection;
import auth.AccountManager;
import auth.Account;
import shop.Product;
import shop.Cart;
import shop.ShopClient;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        DatabaseConnection dbConnection = new DatabaseConnection();
        try {
            dbConnection.connect("test.db");
            Connection conn = dbConnection.getConnection();
            Statement stmt = conn.createStatement();
            String createTableSQL = "CREATE TABLE IF NOT EXISTS accounts (id INTEGER PRIMARY KEY, username TEXT, password TEXT)";
            stmt.execute(createTableSQL);
            String createProductTableSQL = "CREATE TABLE IF NOT EXISTS products (id INTEGER PRIMARY KEY, name TEXT, price REAL)";
            stmt.execute(createProductTableSQL);
            String createCartTableSQL = "CREATE TABLE IF NOT EXISTS carts (id INTEGER PRIMARY KEY, account_id INTEGER)";
            stmt.execute(createCartTableSQL);
            String createCartProductsTableSQL = "CREATE TABLE IF NOT EXISTS cart_products (cart_id INTEGER, product_id INTEGER, quantity INTEGER)";
            stmt.execute(createCartProductsTableSQL);

            AccountManager accountManager = new AccountManager(dbConnection);
            accountManager.register("john_doe", "password123");
            boolean isAuthenticated = accountManager.authenticate("john_doe", "password123");
            System.out.println("Authentication successful: " + isAuthenticated);

            Account account = accountManager.getAccount("john_doe");
            System.out.println("Account retrieved: ID=" + account.id() + ", Username=" + account.username());

            ShopClient shopClient = new ShopClient(account.id(), account.username());
            Cart cart = new Cart(1, account.id());
            cart.add(1, 2); // Example product with ID 1 and quantity 2
            shopClient.addCart(cart);

            List<Product> productList = new ArrayList<>();
            productList.add(new Product(1, "Guitar", 499.99));
            productList.add(new Product(2, "Piano", 999.99));

            double totalPrice = cart.price(productList);
            System.out.println("Total cart price: " + totalPrice);

            dbConnection.disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}