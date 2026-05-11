public class SmartHome extends Product {
    private String type;

    public SmartHome(String id, String name, double price, int stock, String imageFileName, String type) {
        super(id, name, price, stock, imageFileName);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
}
