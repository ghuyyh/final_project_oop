import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PurchaseOrder {
    private List<CartItem> items; 
    private LocalDateTime orderTime;
    private double totalAmount;

    public PurchaseOrder(List<CartItem> items, LocalDateTime orderTime) {
        this.items = items;
        this.orderTime = orderTime;
        this.totalAmount = 0;
        for (CartItem item : items) {
            this.totalAmount += item.getTotalPrice();
        }
    }
    public List<CartItem> getItems() {
        return items;
} 
    public String getFormattedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm - dd/MM/yyyy");
        return orderTime.format(formatter);
    }

    public double getTotalAmount() { return totalAmount; }
}