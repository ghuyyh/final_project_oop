import java.io.*;
import java.util.List;

public class DataManager {
    private static final String USERS_FILE = "src/database/users.dat";
    private static final String INVENTORY_FILE = "src/database/inventory.dat";


    public static void saveAll() {
        saveUsers();
        saveInventory();
    }

    public static void saveUsers() {
        List<User> users = Core.getInstance().getUsersDatabase();
        File parent = new File(USERS_FILE).getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USERS_FILE))) {
            oos.writeObject(users);
            System.out.println("[DataManager] Saved " + users.size() + " users to " + USERS_FILE);
        } catch (IOException e) {
            System.err.println("[DataManager] Error saving users: " + e.getMessage());
        }
    }


    public static void saveInventory() {
        List<Product> inventory = Core.getInstance().getInventory();
        File parent = new File(INVENTORY_FILE).getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(INVENTORY_FILE))) {
            oos.writeObject(inventory);
            System.out.println("[DataManager] Saved " + inventory.size() + " products to " + INVENTORY_FILE);
        } catch (IOException e) {
            System.err.println("[DataManager] Error saving inventory: " + e.getMessage());
        }
    }


    public static void loadAll() {
        loadUsers();
        loadInventory();
    }

    public static void loadUsers() {
        File f = new File(USERS_FILE);
        if (!f.exists()) {
            System.out.println("[DataManager] " + USERS_FILE + " not found, using defaults.");
            return;
        }
        List<User> db = Core.getInstance().getUsersDatabase();
        db.clear();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            @SuppressWarnings("unchecked")
            List<User> loadedUsers = (List<User>) ois.readObject();
            db.addAll(loadedUsers);
            System.out.println("[DataManager] Loaded " + db.size() + " users from " + USERS_FILE);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("[DataManager] Error loading users: " + e.getMessage());
        }
    }

    public static void loadInventory() {
        File f = new File(INVENTORY_FILE);
        if (!f.exists()) {
            System.out.println("[DataManager] " + INVENTORY_FILE + " not found, using defaults.");
            return;
        }
        List<Product> inv = Core.getInstance().getInventory();
        List<Product> hot = Core.getInstance().getHotProducts();
        inv.clear();
        hot.clear();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            @SuppressWarnings("unchecked")
            List<Product> loadedProducts = (List<Product>) ois.readObject();
            inv.addAll(loadedProducts);
            hot.addAll(loadedProducts);
            System.out.println("[DataManager] Loaded " + inv.size() + " products from " + INVENTORY_FILE);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("[DataManager] Error loading inventory: " + e.getMessage());
        }
    }

}
