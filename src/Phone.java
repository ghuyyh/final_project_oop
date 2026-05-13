public class Phone  extends Product {
    private String brand;

    public Phone(String id, String name, double price, int stock, String imageFileName, String brand) {
        super(id, name, price, stock, imageFileName, new java.util.LinkedHashMap<>());
        this.brand = brand;
        addSpec("Brand", brand);
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    
    
}
