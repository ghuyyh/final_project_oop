public class Other extends Product {
    private String category;

    public Other(String id, String name, double price, int stock, String imageFileName, String category) {
        super(id, name, price, stock, imageFileName, new java.util.LinkedHashMap<>());
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
}
