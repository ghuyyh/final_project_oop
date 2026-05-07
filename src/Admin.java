public class Admin extends User {
    public Admin(String username, String password) {
        super(username, password); 
    }
    public void updateStock(Product p, int newStock) {
        p.setStockQuantity(newStock); 
        System.out.println("Updated stock for product " + p.getId() + " to: " + newStock);
    }
    public void updatePrice(Product p, double newPrice) {
        p.setPrice(newPrice); 
        System.out.println("Updated price for product " + p.getId() + " to: $" + newPrice);
    }
    public void addNewProduct(Product p) {
        System.out.println("Admin " + getUsername() + " successfully added a new product:");
        System.out.println(p.getDetails());
    }
}