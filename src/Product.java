public class Product {
    private String id;
    private String name;
    private double price;
    private int stockQuantity;
    public Product(String id, String name, double price, int stockQuantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }
    public boolean reduceStock (int amount){
        if(amount <=this.stockQuantity){
            this.stockQuantity -= amount;
            return true;
        }
        return false;
    }
    public double getPrice() {
        return this.price;
    }
    public String getDetails() {
        return "ID: " + id + ", Name: " + name + ", Price: $" + price + ", Stock: " + stockQuantity;
    }
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
    public int getStockQuantity() {
        return stockQuantity;
    }
    

}
