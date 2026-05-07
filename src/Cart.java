import java.util.ArrayList;
import java.util.List;
public class Cart {
    private List<CartItem> items;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public void addItem(Product product, int quantity) {
        for(CartItem item : items) {
            if(item.getProduct().getId().equals(product.getId())) {
                item.increaseQuantity(quantity);
                return;
            }
        }
        items.add(new CartItem(product, quantity));
    }
    public void removeItem(Product product) {
        items.removeIf(item -> item.getProduct().getId().equals(product.getId()));
    }
    public double calculateTotal() {
        double total = 0.0;
        for(CartItem item : items) {
            total += item.getTotalPrice();
        }
        return total;
    }
    public List<CartItem> getItems() {
        return items;
    }
    public void clearCart() {
        items.clear();
    }
    
}
