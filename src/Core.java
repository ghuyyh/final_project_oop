import java.util.ArrayList;
import java.util.List;

public class Core {
    private List<User> usersDatabase;
    private List<Product> inventory;
    private User loggedInUser;
    private Cart guestCart;

    // Singleton instance
    private static Core instance;

    private Core() {
        guestCart = new Cart();
        usersDatabase = new ArrayList<>();
        inventory = new ArrayList<>();
        loggedInUser = null;
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

    public String routeUserView() {
        if (loggedInUser instanceof Admin) {
            return "admin";
        } else if (loggedInUser instanceof Customer) {
            return "customer";
        } else {
            return "guest";
        }
    }
}
