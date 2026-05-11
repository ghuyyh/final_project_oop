import java.io.*;
import java.lang.reflect.Field;
import java.util.List;

public class DataManager {
    private static final String USERS_FILE = "users.txt";
    private static final String INVENTORY_FILE = "inventory.txt";


    public static void saveAll() {
        saveUsers();
        saveInventory();
    }

    public static void saveUsers() {
        List<User> users = Core.getInstance().getUsersDatabase();
        try (PrintWriter pw = new PrintWriter(new FileWriter(USERS_FILE))) {
            for (User u : users) {
                String type = (u instanceof Admin) ? "ADMIN" : "CUSTOMER";
                String password = getPassword(u);
                pw.println(type + "," + u.getUsername() + "," + password);
            }
            System.out.println("[DataManager] Saved " + users.size() + " users to " + USERS_FILE);
        } catch (IOException e) {
            System.err.println("[DataManager] Error saving users: " + e.getMessage());
        }
    }

    private static String getPassword(User u) {
        Class<?> cls = u.getClass();
        while (cls != null) {
            try {
                Field field = cls.getDeclaredField("password");
                field.setAccessible(true);
                Object value = field.get(u);
                return value != null ? value.toString() : "";
            } catch (NoSuchFieldException e) {
                cls = cls.getSuperclass();
            } catch (IllegalAccessException e) {
                break;
            }
        }
        return "";
    }

    public static void saveInventory() {
        List<Product> inventory = Core.getInstance().getInventory();
        try (PrintWriter pw = new PrintWriter(new FileWriter(INVENTORY_FILE))) {
            for (Product p : inventory) {
                pw.println(p.getId() + "," + escape(p.getName()) + ","
                        + p.getPrice() + "," + p.getStockQuantity() + ","
                        + p.getImageFileName());
            }
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
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split(",", 3);
                if (parts.length < 3) continue;
                String type = parts[0];
                String username = parts[1];
                String password = parts[2];
                if ("ADMIN".equalsIgnoreCase(type)) {
                    db.add(new Admin(username, password));
                } else {
                    db.add(new Customer(username, password));
                }
            }
            System.out.println("[DataManager] Loaded " + db.size() + " users from " + USERS_FILE);
        } catch (IOException e) {
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
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split(",", 5);
                if (parts.length < 5) continue;
                String id = parts[0];
                String name = unescape(parts[1]);
                double price = Double.parseDouble(parts[2]);
                int stock = Integer.parseInt(parts[3]);
                String image = parts[4];
                Product p = new Product(id, name, price, stock, image);
                inv.add(p);
                hot.add(p);
            }
            System.out.println("[DataManager] Loaded " + inv.size() + " products from " + INVENTORY_FILE);
        } catch (IOException e) {
            System.err.println("[DataManager] Error loading inventory: " + e.getMessage());
        }
    }

   
    private static String escape(String s) {
        return s.replace("\\", "\\\\").replace(",", "\\,");
    }

    private static String unescape(String s) {
        return s.replace("\\,", ",").replace("\\\\", "\\");
    }
}
