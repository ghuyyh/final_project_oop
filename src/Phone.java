public class Phone  extends Product {
    private String brand;

    public Phone(String id, String name, double price, int stock, String imageFileName, String brand) {
        super(id, name, price, stock, imageFileName);
        this.brand = brand;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    
    
}
