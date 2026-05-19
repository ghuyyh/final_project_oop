import java.util.ArrayList;
import java.util.List;
// import java.io.*;

public class Core {
    private List<User> usersDatabase;
    private List<Product> inventory;
    private User loggedInUser;
    private Cart guestCart;
    private List<Product> hotProducts;

    // Singleton instance
    private static Core instance;

    private Core() {
        instance = this;
        guestCart = new Cart();
        usersDatabase = new ArrayList<>();
        inventory = new ArrayList<>();
        loggedInUser = null;
        hotProducts = new ArrayList<>();
        loadInventory();
        loadUsers();

        // usersDatabase.add(new Admin("admin", "admin123"));
        // usersDatabase.add(new Customer("customer", "customer123", "Elsu", 30,
        // "Athanor", "Credit Card", "1234-5678-9012-3456"));

        // inventory.add(new Product("P01", "Xiaomi 13", 1400.0, 10));
        // inventory.add(new Product("P02", "IPhone 15 Pro Max", 1000.0, 5));
        // inventory.add(new Product("P03", "Samsung Galaxy S23 Ultra", 800.0, 8));
        // inventory.add(new Product("P04", "OnePlus 11", 230.0, 12));
        // inventory.add(new Product("P05", "Google Pixel 8 Pro", 888.0, 7));

        // inventory.add(new Product("S01", "Google Nest Hub", 100.0, 15));
        // inventory.add(new Product("S02", "Amazon Echo Show 10", 150.0, 10));
        // inventory.add(new Product("S03", "Apple HomePod Mini", 99.0, 20));
        // inventory.add(new Product("S04", "Samsung SmartThings Hub", 120.0, 5));
        // inventory.add(new Product("S05", "Xiaomi Mi Smart Speaker", 80.0, 18));

        // inventory.add(new Product( "O01", "Wireless Charger", 25.0, 30));
        // inventory.add(new Product( "O02", "Phone Case", 15.0, 50));
        // inventory.add(new Product( "O03", "Screen Protector", 10.0, 40));
        // inventory.add(new Product( "O04", "Bluetooth Earbuds", 50.0, 25));
        // inventory.add(new Product( "O05", "Power Bank", 40.0, 20));

        // hotProducts.add(inventory.get(0));
        // hotProducts.add(inventory.get(1));

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
        // usersDatabase.add(new Customer(username, password, "", -1, "", "", ""));
        return true;
    }

    public void loadUsers() {
        DataManager.loadUsers();
    }
    public void loadInventory(){
        DataManager.loadInventory();
    }

}
