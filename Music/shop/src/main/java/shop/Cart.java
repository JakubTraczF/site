package shop;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Cart {
    private int id;
    private int accountId;
    private List<Map.Entry<Integer, Integer>> products;

    public Cart(int id, int accountId) {
        this.id = id;
        this.accountId = accountId;
        this.products = new ArrayList<>();
    }

    public void add(int productId, int quantity) {
        products.add(Map.entry(productId, quantity));
    }

    public double price(List<Product> productList) {
        double total = 0;
        for (Map.Entry<Integer, Integer> entry : products) {
            int productId = entry.getKey();
            int quantity = entry.getValue();
            for (Product product : productList) {
                if (product.id() == productId) {
                    total += product.price() * quantity;
                }
            }
        }
        return total;
    }
}