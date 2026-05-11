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
        usersDatabase.add(new Customer("customer", "customer123"));

        inventory.add(new Product("P01", "Xiaomi 13", 1400.0, 10, "xiaomi13.png"));
        inventory.add(new Product("P02", "iPhone 15 Pro Max", 1000.0, 5, "iphone15promax.png"));
        inventory.add(new Product("P03", "Samsung Galaxy S23 Ultra", 800.0, 8, "samsunggalaxys23ultra.png"));
        inventory.add(new Product("P04", "OnePlus 11", 230.0, 12, "oneplus11.png"));
        inventory.add(new Product("P05", "Google Pixel 8 Pro", 888.0, 7, "googlepixel8pro.png"));
        hotProducts.addAll(inventory);

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
        return null; // Authentication failed
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
    
    // wth is this :))) có cái kia ở bên main frame rồi
    // public String routeUserView() {
    // if (loggedInUser instanceof Admin) {
    // return "admin";
    // } else if (loggedInUser instanceof Customer) {
    // return "customer";
    // } else {
    // return "guest";
    // }
    // }

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
        usersDatabase.add(new Customer(username, password));
        return true;
    }

}
