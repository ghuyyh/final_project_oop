import java.util.LinkedHashMap;
import java.util.Map;

public class Product {
    private String id;
    private String name;
    private double price;
    private int stockQuantity;
    private String imageFileName;
    private Map<String, String> specs;

    public Product(String id, String name, double price, int stockQuantity, String imageFileName, Map<String, String> specs) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.imageFileName = imageFileName;
        this.specs = specs;
    }

    public Product(String id, String name, double price, int stockQuantity){
        this(id, name, price, stockQuantity, "", generateDefaultSpecs(name));
    }

    public static Map<String, String> generateDefaultSpecs(String name){
        Map<String, String> specs = new LinkedHashMap<>();
        String nameLower = name.toLowerCase();

        if (nameLower.contains("xiaomi 13")){
            specs.put("CPU", "Snapdragon 8 gen 2");
            specs.put("RAM", "12GB");
            specs.put("Storage", "256 GB UFS 3.1");
            specs.put("Display", "6.336\" AMOLED, 12Hz, 2400x1080");
            specs.put("Rear Camera", "50 MP (f/1.8) + 12 MP UW + 10 MP tele 3.2x");
            specs.put("Front Camera", "32 MP");
            specs.put("Battery","4500 mAh, 67W wired, 50W wireless");
            specs.put("OS","MIUI 14 (Android 13)");
            specs.put("Connectivity","5G, Wi-Fi 6E, Bluetooth 5.3, NFC");
            specs.put("Dimensions","152.8 x 71.5 x 8.0 mm - 189 g");
            specs.put("Water Resistance", "IP68");
        } else if (nameLower.contains("iphone 15 pro max")) {
            specs.put("CPU","Apple A17 Pro (3nm)");
            specs.put("RAM","8 GB");
            specs.put("Storage","256 GB / 512 GB / 1 TB");
            specs.put("Display","6.7\" Super Retina XDR ProMotion, 120Hz, 2796x1290");
            specs.put("Rear Camera","48 MP (f/1.78) + 12 MP UW + 12 MP tele 5x");
            specs.put("Front Camera","12 MP TrueDepth");
            specs.put("Battery","4422 mAh, MagSafe 15W, USB-C 27W");
            specs.put("OS","iOS 17");
            specs.put("Frame","Grade 5 Titanium");
            specs.put("Connectivity","5G, Wi-Fi 6E, Bluetooth 5.3, NFC, UWB");
            specs.put("Water Resistance","IP68 (6m - 30 min)");
            specs.put("Dimensions","159.9 x 76.7 x 8.25 mm - 221 g");
        } else if (nameLower.contains("samsung galaxy s23 ultra")) {
            specs.put("CPU","Snapdragon 8 Gen 2 for Galaxy");
            specs.put("RAM","12 GB");
            specs.put("Storage","256 GB / 512 GB / 1 TB");
            specs.put("Display","6.8\" Dynamic AMOLED 2X, 120Hz, 3088x1440");
            specs.put("Rear Camera","200 MP (f/1.7) + 12 MP UW + 10 MP tele 3x + 10 MP tele 10x");
            specs.put("Front Camera","12 MP");
            specs.put("S Pen","Built-in");
            specs.put("Battery","5000 mAh, 45W wired, 15W wireless");
            specs.put("OS","One UI 5.1 (Android 13)");
            specs.put("Connectivity","5G, Wi-Fi 6E, Bluetooth 5.3, NFC");
            specs.put("Water Resistance","IP68");
            specs.put("Dimensions","163.4 x 78.1 x 8.9 mm - 233 g");
        } else if (nameLower.contains("oneplus 11")) {
            specs.put("CPU","Snapdragon 8 Gen 2");
            specs.put("RAM","8 / 16 GB");
            specs.put("Storage","128 / 256 GB UFS 3.1");
            specs.put("Display","6.7\" AMOLED, 120Hz, 3216x1440");
            specs.put("Rear Camera","50 MP (f/1.8) + 48 MP UW + 32 MP tele 2x");
            specs.put("Front Camera","16 MP");
            specs.put("Battery","5000 mAh, SUPERVOOC 100W");
            specs.put("OS","OxygenOS 13 (Android 13)");
            specs.put("Connectivity","5G, Wi-Fi 6E, Bluetooth 5.3, NFC");
            specs.put("Water Resistance","IP64");
            specs.put("Dimensions","163.1 x 74.1 x 8.53 mm - 205 g");
        } else if (nameLower.contains("google pixel 8 pro")) {
            specs.put("CPU","Google Tensor G3");
            specs.put("RAM","12 GB");
            specs.put("Storage","128 / 256 / 512 GB / 1 TB");
            specs.put("Display","6.7\" LTPO OLED, 1-120Hz, 2992x1344");
            specs.put("Rear Camera","50 MP (f/1.68) + 48 MP UW + 48 MP tele 5x");
            specs.put("Front Camera","10.5 MP");
            specs.put("AI Features","Magic Eraser, Photo Unblur, Best Take");
            specs.put("Battery","5050 mAh, 30W wired, 23W wireless");
            specs.put("OS","Android 14 (7 years of updates)");
            specs.put("Connectivity","5G, Wi-Fi 7, Bluetooth 5.3, UWB");
            specs.put("Water Resistance","IP68");
            specs.put("Dimensions","162.6 x 76.5 x 8.8 mm - 213 g");
        } else {
            specs.put("Product Name",   name);
            specs.put("Product ID",     "N/A");
            specs.put("Specifications", "Please contact us for more details.");
        }
        return specs;
    }
    
    public boolean reduceStock (int amount){
        if(amount <= this.stockQuantity){
            this.stockQuantity -= amount;
            return true;
        }
        return false;
    }
    public double getPrice() {
        return this.price;
    }
    public String getDetails() {
        return "ID: " + id + ", Name: " + name + ", Price: $" + price + ", Stock: " + stockQuantity + ", Image: " + imageFileName;
    }
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getImageFileName() {
        return imageFileName;
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
    public Map<String, String> getSpecs(){
        if (specs == null) specs = new LinkedHashMap<>();
        return specs;
    }
    public void setSpecs(Map<String, String> specs){
        this.specs = specs;
    }
    public void addSpec(String key, String value){
        getSpecs().put(key, value);
    }
}
