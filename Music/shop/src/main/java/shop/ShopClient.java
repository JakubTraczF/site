package shop;

import auth.Account;
import java.util.ArrayList;
import java.util.List;

public class ShopClient extends Account {
    private List<Cart> carts;

    public ShopClient(int id, String username) {
        super(id, username);
        this.carts = new ArrayList<>();
    }

    public List<Cart> getCarts() {
        return carts;
    }

    public void addCart(Cart cart) {
        carts.add(cart);
    }
}