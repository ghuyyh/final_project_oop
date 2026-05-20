import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

class addall {
    public static void main(String[] args) {
        List<User> users = new ArrayList<>();
        List<Product> products = new ArrayList<>();

        Admin admin = new Admin("admin", "admin123");
        Customer customer = new Customer("customer", "customer123", "Elsu", 30, "Athanor", "Credit Card",
            "1234-5678-9012-3456");
        users.add(admin);
        users.add(customer);

        products.add(buildProduct("P01", "Xiaomi 13", 1400.0, 10));
        products.add(buildProduct("P02", "IPhone 15 Pro Max", 1000.0, 5));
        products.add(buildProduct("P03", "Samsung Galaxy S23 Ultra", 800.0, 8));
        products.add(buildProduct("P04", "OnePlus 11", 230.0, 12));
        products.add(buildProduct("P05", "Google Pixel 8 Pro", 888.0, 7));

        products.add(buildProduct("S01", "Google Nest Hub", 100.0, 15));
        products.add(buildProduct("S02", "Amazon Echo Show 10", 150.0, 10));
        products.add(buildProduct("S03", "Apple HomePod Mini", 99.0, 20));
        products.add(buildProduct("S04", "Samsung SmartThings Hub", 120.0, 5));
        products.add(buildProduct("S05", "Xiaomi Mi Smart Speaker", 80.0, 18));

        products.add(buildProduct("O01", "Wireless Charger", 25.0, 30));
        products.add(buildProduct("O02", "Phone Case", 15.0, 50));
        products.add(buildProduct("O03", "Screen Protector", 10.0, 40));
        products.add(buildProduct("O04", "Bluetooth Earbuds", 50.0, 25));
        products.add(buildProduct("O05", "Power Bank", 40.0, 20));

        File dbDir = resolveDatabaseDir();
        writeObject(new File(dbDir, "users.dat"), users);
        writeObject(new File(dbDir, "inventory.dat"), products);

        System.out.println("Saved " + users.size() + " users, "
            + products.size() + " products to " + dbDir.getPath());
    }

    private static File resolveDatabaseDir() {
        File dbDir = new File("database");
        if (dbDir.exists() && dbDir.isDirectory()) {
            return dbDir;
        }

        File srcDbDir = new File("src", "database");
        if (srcDbDir.exists() && srcDbDir.isDirectory()) {
            return srcDbDir;
        }

        if (!dbDir.mkdirs()) {
            throw new IllegalStateException("Could not create database directory: " + dbDir.getPath());
        }
        return dbDir;
    }

    private static Product buildProduct(String id, String name, double price, int stock) {
        return new Product(id, name, price, stock, generateDefaultSpecs(name), false, "");
    }

    private static Map<String, String> generateDefaultSpecs(String name) {
        Map<String, String> specs = new LinkedHashMap<>();
        String nameLower = name.toLowerCase();

        if (nameLower.contains("xiaomi 13")) {
            specs.put("CPU", "Snapdragon 8 gen 2");
            specs.put("RAM", "12GB");
            specs.put("Storage", "256 GB UFS 3.1");
            specs.put("Display", "6.336\" AMOLED, 12Hz, 2400x1080");
            specs.put("Rear Camera", "50 MP (f/1.8) + 12 MP UW + 10 MP tele 3.2x");
            specs.put("Front Camera", "32 MP");
            specs.put("Battery", "4500 mAh, 67W wired, 50W wireless");
            specs.put("OS", "MIUI 14 (Android 13)");
            specs.put("Connectivity", "5G, Wi-Fi 6E, Bluetooth 5.3, NFC");
            specs.put("Dimensions", "152.8 x 71.5 x 8.0 mm - 189 g");
            specs.put("Water Resistance", "IP68");
        } else if (nameLower.contains("iphone 15 pro max")) {
            specs.put("CPU", "Apple A17 Pro (3nm)");
            specs.put("RAM", "8 GB");
            specs.put("Storage", "256 GB / 512 GB / 1 TB");
            specs.put("Display", "6.7\" Super Retina XDR ProMotion, 120Hz, 2796x1290");
            specs.put("Rear Camera", "48 MP (f/1.78) + 12 MP UW + 12 MP tele 5x");
            specs.put("Front Camera", "12 MP TrueDepth");
            specs.put("Battery", "4422 mAh, MagSafe 15W, USB-C 27W");
            specs.put("OS", "iOS 17");
            specs.put("Frame", "Grade 5 Titanium");
            specs.put("Connectivity", "5G, Wi-Fi 6E, Bluetooth 5.3, NFC, UWB");
            specs.put("Water Resistance", "IP68 (6m - 30 min)");
            specs.put("Dimensions", "159.9 x 76.7 x 8.25 mm - 221 g");
        } else if (nameLower.contains("samsung galaxy s23 ultra")) {
            specs.put("CPU", "Snapdragon 8 Gen 2 for Galaxy");
            specs.put("RAM", "12 GB");
            specs.put("Storage", "256 GB / 512 GB / 1 TB");
            specs.put("Display", "6.8\" Dynamic AMOLED 2X, 120Hz, 3088x1440");
            specs.put("Rear Camera", "200 MP (f/1.7) + 12 MP UW + 10 MP tele 3x + 10 MP tele 10x");
            specs.put("Front Camera", "12 MP");
            specs.put("S Pen", "Built-in");
            specs.put("Battery", "5000 mAh, 45W wired, 15W wireless");
            specs.put("OS", "One UI 5.1 (Android 13)");
            specs.put("Connectivity", "5G, Wi-Fi 6E, Bluetooth 5.3, NFC");
            specs.put("Water Resistance", "IP68");
            specs.put("Dimensions", "163.4 x 78.1 x 8.9 mm - 233 g");
        } else if (nameLower.contains("oneplus 11")) {
            specs.put("CPU", "Snapdragon 8 Gen 2");
            specs.put("RAM", "8 / 16 GB");
            specs.put("Storage", "128 / 256 GB UFS 3.1");
            specs.put("Display", "6.7\" AMOLED, 120Hz, 3216x1440");
            specs.put("Rear Camera", "50 MP (f/1.8) + 48 MP UW + 32 MP tele 2x");
            specs.put("Front Camera", "16 MP");
            specs.put("Battery", "5000 mAh, SUPERVOOC 100W");
            specs.put("OS", "OxygenOS 13 (Android 13)");
            specs.put("Connectivity", "5G, Wi-Fi 6E, Bluetooth 5.3, NFC");
            specs.put("Water Resistance", "IP64");
            specs.put("Dimensions", "163.1 x 74.1 x 8.53 mm - 205 g");
        } else if (nameLower.contains("google pixel 8 pro")) {
            specs.put("CPU", "Google Tensor G3");
            specs.put("RAM", "12 GB");
            specs.put("Storage", "128 / 256 / 512 GB / 1 TB");
            specs.put("Display", "6.7\" LTPO OLED, 1-120Hz, 2992x1344");
            specs.put("Rear Camera", "50 MP (f/1.68) + 48 MP UW + 48 MP tele 5x");
            specs.put("Front Camera", "10.5 MP");
            specs.put("AI Features", "Magic Eraser, Photo Unblur, Best Take");
            specs.put("Battery", "5050 mAh, 30W wired, 23W wireless");
            specs.put("OS", "Android 14 (7 years of updates)");
            specs.put("Connectivity", "5G, Wi-Fi 7, Bluetooth 5.3, UWB");
            specs.put("Water Resistance", "IP68");
            specs.put("Dimensions", "162.6 x 76.5 x 8.8 mm - 213 g");
        } else if (nameLower.contains("google nest hub")) {
            specs.put("Display", "7\" LCD touchscreen, 1024x600");
            specs.put("Process", "Amlogic S905D, Quad-core ARM Cortex-A55");
            specs.put("RAM", "2 GB DDR3");
            specs.put("Speaker", "1.7\" full-range driver");
            specs.put("Microphone", "3 far-field mics");
            specs.put("Connectivity", "Wi-Fi 802.11a/b/g/n/ac, Bluetooth 5.0, Zigbee, Thread");
            specs.put("Camera", "None");
            specs.put("Assistant", "Google Assistant");
            specs.put("Dimension", "177 x 118 x 68 mm - 570 g");
        } else if (nameLower.contains("amazon echo show 10")) {
            specs.put("Display", "10.1\" HD touchscreen, 1280x800");
            specs.put("Motor", "Brushless, rotates +/-175 degrees");
            specs.put("Speaker", "3\" woofer + 2x1\" tweeters");
            specs.put("Camera", "13 MP, auto-framing");
            specs.put("Microphone", "Far-field array");
            specs.put("Connectivity", "Wi-Fi 802.11a/b/g/n/ac, Bluetooth, Zigbee, Matter");
            specs.put("Assistant", "Alexa");
            specs.put("Dimensions", "251 x 229 x 170 mm");
        } else if (nameLower.contains("apple homepod mini")) {
            specs.put("Chip", "Apple S5");
            specs.put("Speaker", "360-degree audio");
            specs.put("Microphone", "4 far-field mics");
            specs.put("Audio Output", "12W (360-degree sound via acoustic waveguide)");
            specs.put("Connectivity", "Wi-Fi 802.11n, Bluetooth 5.0, Ultra Wideband, Thread");
            specs.put("Assistant", "Siri (up to 6 voice profiles)");
            specs.put("Power", "USB-C, 20W adapter");
            specs.put("Dimensions", "84 mm tall, 98 mm diameter - 345 g");
        } else if (nameLower.contains("samsung smartthings hub")) {
            specs.put("Processor", "ARM Cortex-A9, 1GHz");
            specs.put("Protocols", "Zigbee, Z-Wave, Bluetooth 4.1");
            specs.put("Connectivity", "Wi-Fi 802.11 b/g/n, Ethernet");
            specs.put("USB", "2x USB-A (expansion)");
            specs.put("Power", "AC adapter");
            specs.put("App", "SmartThings (Android / iOS)");
            specs.put("Dimensions", "127 x 127 x 31 mm");
        } else if (nameLower.contains("xiaomi mi smart speaker")) {
            specs.put("Model", "L09G");
            specs.put("Speaker", "12W, 63.5mm full-range driver");
            specs.put("Processor", "Texas Instruments TAS5805M");
            specs.put("Microphone", "2 far-field mics");
            specs.put("Light", "RGB ring (16 million colors)");
            specs.put("Connectivity", "Wi-Fi 2.4GHz/5GHz, Bluetooth 4.2 (A2DP)");
            specs.put("Assistant", "Google Assistant, Chromecast built-in");
            specs.put("Power", "DC 12V/1.5A");
            specs.put("Dimensions", "131 x 104 x 151 mm - 853 g");
        } else if (nameLower.contains("wireless charger")) {
            specs.put("Type", "Qi Wireless Charging Pad");
            specs.put("Output Power", "15W max (iPhone 12W, Android 10W)");
            specs.put("Input", "USB-C, 5V/2A");
            specs.put("Compatibility", "Qi-certified devices, iPhone 8 and later, AirPods");
            specs.put("Charging Time", "~2 hours (0-100%)");
            specs.put("Safety", "Over-current, over-voltage, temperature protection");
            specs.put("Cable Length", "1 meter USB-C cable included");
            specs.put("Dimensions", "100 x 100 x 7 mm - 80 g");
        } else if (nameLower.contains("phone case")) {
            specs.put("Material", "TPU + Polycarbonate");
            specs.put("Protection", "MIL-STD-810G drop tested up to 2m");
            specs.put("Compatibility", "Multiple models available");
            specs.put("Features", "Raised edges for screen & camera protection");
            specs.put("Thickness", "1.5 mm");
            specs.put("Weight", "30 g");
            specs.put("Colors", "Clear, Black, Blue, Red");
        } else if (nameLower.contains("screen protector")) {
            specs.put("Material", "Tempered Glass, 9H hardness");
            specs.put("Thickness", "0.33 mm");
            specs.put("Transparency", "99.9% HD clarity");
            specs.put("Oleophobic", "Yes, fingerprint-resistant coating");
            specs.put("Installation", "Bubble-free adhesive");
            specs.put("Compatibility", "Multiple models available");
            specs.put("Package", "2 pieces + installation kit");
        } else if (nameLower.contains("bluetooth earbuds")) {
            specs.put("Driver", "10 mm dynamic driver");
            specs.put("Frequency", "20 Hz - 20 kHz");
            specs.put("Bluetooth", "5.3, range up to 10 m");
            specs.put("Battery", "Earbuds 6h, case 24h total");
            specs.put("Charging", "USB-C, fast charge 15 min = 2h playback");
            specs.put("Microphone", "2 mics per earbud, ENC noise cancellation");
            specs.put("Water Resistance", "IPX5");
            specs.put("Codec", "SBC, AAC");
            specs.put("Weight", "5 g per earbud, 45 g case");
        } else if (nameLower.contains("power bank")) {
            specs.put("Capacity", "20000 mAh / 74 Wh");
            specs.put("Input", "USB-C 18W, Micro-USB 10W");
            specs.put("Output", "USB-C 20W PD, USB-A 18W QC 3.0");
            specs.put("Simultaneous", "2 devices at once");
            specs.put("Charging Time", "~5 hours (full charge)");
            specs.put("Safety", "Over-charge, over-discharge, short circuit protection");
            specs.put("Dimensions", "158 x 74 x 20 mm - 340 g");
            specs.put("Certifications", "CE, FCC, RoHS");
        } else {
            specs.put("Product Name", name);
            specs.put("Product ID", "N/A");
            specs.put("Specifications", "Please contact us for more details.");
        }
        return specs;
    }

    private static void writeObject(File file, Object data) {
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(data);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write: " + file.getPath(), e);
        }
    }
}
