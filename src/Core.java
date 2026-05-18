import java.util.ArrayList;
import java.util.List;

public class Core {
    private List<User> usersDatabase;
    private List<Product> inventory;
    private User loggedInUser;
    private Cart guestCart;
    private List<Product> hotProducts;

    // Singleton instance
    private static Core instance;

    private Core() {
        guestCart = new Cart();
        usersDatabase = new ArrayList<>();
        inventory = new ArrayList<>();
        loggedInUser = null;
        hotProducts = new ArrayList<>();

        usersDatabase.add(new Admin("admin", "admin123"));
        usersDatabase.add(new Customer("customer", "customer123", "Elsu", 30, "Athanor", "Visa **** 1234"));

        inventory.add(new Phone("P01", "Xiaomi 13", 1400.0, 10, "xiaomi13.png", "Xiaomi"));
        inventory.add(new Phone("P02", "iPhone 15 Pro Max", 1000.0, 5, "iphone15promax.png", "Apple"));
        inventory.add(new Phone("P03", "Samsung Galaxy S23 Ultra", 800.0, 8, "samsunggalaxys23ultra.png", "Samsung"));
        inventory.add(new Phone("P04", "OnePlus 11", 230.0, 12, "oneplus11.png", "OnePlus"));
        inventory.add(new Phone("P05", "Google Pixel 8 Pro", 888.0, 7, "googlepixel8pro.png", "Google"));

        inventory.add(new SmartHome("S01", "Google Nest Hub", 100.0, 15, "googlenesthub.png", "Google"));
        inventory.add(new SmartHome("S02", "Amazon Echo Show 10", 150.0, 10, "amazonechoshow10.png", "Amazon"));
        inventory.add(new SmartHome("S03", "Apple HomePod Mini", 99.0, 20, "applehomepodmini.png", "Apple"));
        inventory.add(new SmartHome("S04", "Samsung SmartThings Hub", 120.0, 5, "samsungsmartthingshub.png", "Samsung"));
        inventory.add(new SmartHome("S05", "Xiaomi Mi Smart Speaker", 80.0, 18, "xiaomismartspeaker.png", "Xiaomi"));

        inventory.add(new Other( "O01", "Wireless Charger", 25.0, 30, "wirelesscharger.png", "Charger"));
        inventory.add(new Other( "O02", "Phone Case", 15.0, 50, "phonecase.png", "Accessory"));
        inventory.add(new Other( "O03", "Screen Protector", 10.0, 40, "screenprotector.png", "Accessory"));
        inventory.add(new Other( "O04", "Bluetooth Earbuds", 50.0, 25, "bluetoothearbuds.png", "Audio"));
        inventory.add(new Other( "O05", "Power Bank", 40.0, 20, "powerbank.png", "Charger"));
        
        hotProducts.add(inventory.get(0));
        hotProducts.add(inventory.get(1));

    }

    public static Core getInstance() {
        if (instance == null) {
            instance = new Core();
        }
        return instance;
    }

    public User authenticate(String username, String password) {
        for (User user : usersDatabase) {
            if (user.getUsername().equals(username) && user.verifyPassword(password)) {
                loggedInUser = user;
                return user;
            }
        }
        return null; 
    }

    public boolean processCheckout() {
        return true;
    }

    public List<User> getUsersDatabase() {
        return usersDatabase;
    }

    public List<Product> getInventory() {
        return inventory;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public Cart getGuestCart() {
        return guestCart;
    }

    public void setGuestCart(Cart guestCart) {
        this.guestCart = guestCart;
    }
    
    public void toggleHotProduct(Product p) {
    if (hotProducts.contains(p)) {
        hotProducts.remove(p);
    } else {
        hotProducts.add(p);
    }
}
     

    public List<Product> getHotProducts() {
        return hotProducts;
    }

    public void setHotProducts(List<Product> hotProducts) {
        this.hotProducts = hotProducts;
    }

    public boolean registerCustomer(String username, String password) {
        for (User user : usersDatabase) {
            if (user.getUsername().equals(username)) {
                return false;
            }
        }
        usersDatabase.add(new Customer(username, password, "New Customer", 18, "Unknown", "None"));
        return true;
    }

}
