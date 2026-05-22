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

        products.add(buildProduct("P01", "Xiaomi 17 Ultra", 1400.0, 10, "Phone"));
        products.add(buildProduct("P02", "Xiaomi 17 Pro", 1200.0, 8, "Phone"));
        products.add(buildProduct("P03", "Leica Leitzphone powered by Xiaomi", 2200.0, 4, "Phone"));
        products.add(buildProduct("P04", "Xiaomi 15T Pro", 850.0, 12, "Phone"));
        products.add(buildProduct("P05", "Xiaomi 15", 700.0, 12, "Phone"));
        products.add(buildProduct("P07", "Xiaomi 14", 650.0, 15, "Phone"));
        products.add(buildProduct("P09", "Xiaomi 13", 550.0, 8, "Phone"));

        products.add(buildProduct("S01", "Xiaomi Robot Vacuum 5 Pro", 499.0, 10, "Smart Home"));
        products.add(buildProduct("S02", "Xiaomi Toaster", 39.0, 25, "Smart Home"));
        products.add(buildProduct("S03", "Xiaomi Smart Camera C701", 129.0, 15, "Smart Home"));
        products.add(buildProduct("S04", "Mijia Refrigerator Cross Door 510L", 899.0, 3, "Smart Home"));
        products.add(buildProduct("S05", "Xiaomi Smart Display S Mini LED 85 2026", 1499.0, 2, "Smart Home"));

        products.add(buildProduct("O01", "Xiaomi UltraThin Magnetic Power Bank 5000 15W", 29.0, 40, "Other"));
        products.add(buildProduct("O02", "Xiaomi Wireless Mouse 3", 19.0, 60, "Other"));
        products.add(buildProduct("O03", "Xiaomi Water Ionic Hair Dryer H500", 89.0, 20, "Other"));
        products.add(buildProduct("O04", "Xiaomi Smart Scale S200", 49.0, 25, "Other"));
        products.add(buildProduct("O05", "Xiaomi 120W HyperCharge Combo (Type-A)", 39.0, 30, "Other"));

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

    private static Product buildProduct(String id, String name, double price, int stock, String category) {
        return new Product(id, name, price, stock, generateDefaultSpecs(name), false, category);
    }

    private static Map<String, String> generateDefaultSpecs(String name) {
        Map<String, String> specs = new LinkedHashMap<>();
        String nameLower = name.toLowerCase();

        if (nameLower.contains("xiaomi 17 ultra")) {
            specs.put("CPU", "Snapdragon 8 Elite Gen 5");
            specs.put("RAM", "16GB");
            specs.put("Storage", "512GB UFS 4.1");
            specs.put("Display", "6.9\" AMOLED, 120Hz, 2608x1200");
            specs.put("Rear Camera", "50 MP main + 50 MP UW + 200 MP telephoto");
            specs.put("Front Camera", "50 MP");
            specs.put("Battery", "6000 mAh, 90W wired, 50W wireless");
            specs.put("OS", "Xiaomi HyperOS 3");
            specs.put("Connectivity", "5G, Wi-Fi 7, Bluetooth 6.0, NFC");
            specs.put("Dimensions", "162.9 x 77.6 x 8.29 mm - 218.4 g");
            specs.put("Water Resistance", "IP68");
        } else if (nameLower.contains("xiaomi 17 pro")) {
            specs.put("CPU", "Snapdragon 8 Elite Gen 5");
            specs.put("RAM", "12GB");
            specs.put("Storage", "256GB UFS 4.1");
            specs.put("Display", "6.3\" LTPO OLED, 120Hz, 2656x1220");
            specs.put("Rear Camera", "50 MP main + 50 MP UW + 50 MP telephoto");
            specs.put("Front Camera", "50 MP");
            specs.put("Battery", "6300 mAh, 100W wired, 50W wireless");
            specs.put("OS", "HyperOS 3 (Android 16)");
            specs.put("Connectivity", "5G, Wi-Fi 7, Bluetooth 5.4, NFC");
            specs.put("Dimensions", "151.1 x 71.8 x 8.1 mm - 191 g");
            specs.put("Water Resistance", "IP68");
        } else if (nameLower.contains("leica leitzphone")) {
            specs.put("CPU", "Snapdragon 8 Elite Gen 5");
            specs.put("RAM", "Not specified");
            specs.put("Storage", "Not specified");
            specs.put("Display", "6.9\" LTPO OLED");
            specs.put("Rear Camera", "1-inch main + 200 MP telephoto 75-100mm + UW");
            specs.put("Front Camera", "Not specified");
            specs.put("Battery", "6000 mAh, 90W wired, 50W wireless");
            specs.put("OS", "Xiaomi HyperAI (Leica UX UI)");
            specs.put("Connectivity", "5G, Wi-Fi, Bluetooth, NFC");
            specs.put("Dimensions", "Not specified");
            specs.put("Water Resistance", "Not specified");
        } else if (nameLower.contains("xiaomi 15t pro")) {
            specs.put("CPU", "MediaTek Dimensity 9400+");
            specs.put("RAM", "12GB");
            specs.put("Storage", "256GB UFS 4.1");
            specs.put("Display", "6.83\" AMOLED, 144Hz, 2772x1280");
            specs.put("Rear Camera", "50 MP main + 12 MP UW + 50 MP telephoto 5x");
            specs.put("Front Camera", "32 MP");
            specs.put("Battery", "5500 mAh, 90W wired, 50W wireless");
            specs.put("OS", "HyperOS 3 (Android 15)");
            specs.put("Connectivity", "5G, Wi-Fi 7, Bluetooth 6.0, NFC");
            specs.put("Dimensions", "162.7 x 77.9 x 8.0 mm - 210 g");
            specs.put("Water Resistance", "IP68");
        } else if (nameLower.contains("xiaomi 15") && !nameLower.contains("15t")) {
            specs.put("CPU", "Snapdragon 8 Elite");
            specs.put("RAM", "12GB");
            specs.put("Storage", "256GB UFS 4.0");
            specs.put("Display", "6.36\" LTPO OLED, 120Hz, 2670x1200");
            specs.put("Rear Camera", "50 MP main + 50 MP UW + 50 MP telephoto");
            specs.put("Front Camera", "32 MP");
            specs.put("Battery", "5240 mAh, 90W wired, 50W wireless");
            specs.put("OS", "HyperOS 2 (Android 15)");
            specs.put("Connectivity", "5G, Wi-Fi 7, Bluetooth 6.0, NFC");
            specs.put("Dimensions", "152.3 x 71.2 x 8.08 mm - 191 g");
            specs.put("Water Resistance", "IP68");
        } else if (nameLower.contains("xiaomi 14")) {
            specs.put("CPU", "Snapdragon 8 Gen 3");
            specs.put("RAM", "12GB");
            specs.put("Storage", "256GB UFS 4.0");
            specs.put("Display", "6.36\" LTPO OLED, 120Hz, 2670x1200");
            specs.put("Rear Camera", "50 MP main + 50 MP UW + 50 MP telephoto");
            specs.put("Front Camera", "32 MP");
            specs.put("Battery", "4610 mAh, 90W wired, 50W wireless");
            specs.put("OS", "Xiaomi HyperOS");
            specs.put("Connectivity", "5G, Wi-Fi 7, Bluetooth 5.4, NFC");
            specs.put("Dimensions", "152.8 x 71.5 x 8.2 mm - 193 g");
            specs.put("Water Resistance", "IP68");
        }

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
        } else if (nameLower.contains("xiaomi robot vacuum 5 pro") || nameLower.contains("robot vacuum")) {
            specs.put("Suction Power", "20000 Pa");
            specs.put("Navigation", "LiDAR and dToF Smart Retractable Radar");
            specs.put("Obstacle Avoidance", "AI Recognition with RGB camera and 3D dot projector");
            specs.put("Battery", "5200 mAh (up to 140 minutes runtime)");
            specs.put("Base Station Features", "Automatic dust emptying, 80°C hot water mop washing, hot air drying");
            specs.put("Mop System", "Rotating pads with extendable arms");
            specs.put("Dust Bag Capacity", "2.5 L (Base Station)");
            specs.put("Water Tank Capacity", "4L Clean / 4L Waste (Base Station)");
            specs.put("Smart Connectivity", "Wi-Fi, Xiaomi Home app, voice control");
            specs.put("Robot Dimensions", "350 x 350 x 88 mm");
            specs.put("Base Dimensions", "470 x 360 x 572 mm");
            specs.put("Robot Weight", "3.97 kg");
        } else if (nameLower.contains("xiaomi toaster") || nameLower.contains("toaster")) {
            specs.put("Capacity", "2 slices");
            specs.put("Rated Power", "780 - 930W");
            specs.put("Rated Voltage", "220-240V, 50-60Hz");
            specs.put("Toasting Levels", "1 to 6 settings");
            specs.put("Functions", "Toast, Reheat, Defrost, Cancel");
            specs.put("Features", "Built-in foldable toast rack, slide-out crumb tray, automatic centering");
            specs.put("Material", "ABS + PP (Food contact safe)");
            specs.put("Color", "Black");
            specs.put("Dimensions", "280 x 160 x 186 mm");
            specs.put("Weight", "1.2 kg");
        } else if (nameLower.contains("xiaomi smart camera c701") || nameLower.contains("smart camera") || nameLower.contains("c701")) {
            specs.put("Resolution", "3840 x 2160 (4K)");
            specs.put("Video Codec", "H.265");
            specs.put("Wireless Connectivity", "Wi-Fi 6 (2.4 GHz/5 GHz), Bluetooth 5.4");
            specs.put("Storage", "microSD card (8 GB to 256 GB)");
            specs.put("Power Input", "5V ⎓ 2A");
            specs.put("Compatibility", "Android 8.0 & iOS 12.0 or above");
            specs.put("Operating Temperature", "-10°C to 40°C");
            specs.put("Dimensions", "79 x 79 x 119 mm");
        } else if (nameLower.contains("mijia refrigerator cross door 510l") || nameLower.contains("refrigerator") || nameLower.contains("mijia refrigerator")) {
            specs.put("Total Capacity", "510 L");
            specs.put("Fridge Capacity", "303 L");
            specs.put("Freezer Capacity", "178 L");
            specs.put("Variable Zone Capacity", "29 L");
            specs.put("Compressor Type", "Inverter");
            specs.put("Cooling System", "Frost-free (No Frost)");
            specs.put("Noise Level", "38 dB");
            specs.put("Energy Efficiency", "3-Star Rating");
            specs.put("Smart Connectivity", "Xiaomi Home app supported");
            specs.put("Color", "Dark Gray / Slate Gray");
            specs.put("Dimensions", "852 x 600 x 1912 mm");
            specs.put("Net Weight", "85 kg");
        } else if (nameLower.contains("xiaomi smart display s mini led") || nameLower.contains("smart display s mini") || nameLower.contains("mini led 85")) {
            specs.put("Screen Size & Type", "85\" QD-Mini LED");
            specs.put("Resolution", "4K Ultra HD (3840 x 2160)");
            specs.put("Refresh Rate", "144Hz (Up to 288Hz Game Boost)");
            specs.put("Peak Brightness", "Up to 1200 nits");
            specs.put("Backlight Zones", "640 local dimming zones");
            specs.put("Color Gamut", "94% DCI-P3 (1.07 billion colors)");
            specs.put("Audio", "30W Stereo Speakers (2 x 15W), Dolby Atmos, DTS:X");
            specs.put("Processor", "Quad-core Cortex-A73 CPU, Mali-G52 MC1 GPU");
            specs.put("Memory & Storage", "3GB RAM + 32GB ROM");
            specs.put("Operating System", "Google TV");
            specs.put("Connectivity", "Wi-Fi 6, Bluetooth 5.2, 3 x HDMI 2.1 (VRR, ALLM, eARC), 2 x USB");
            specs.put("Dimensions", "1890 x 1083 x 84 mm (without base)");
            specs.put("Weight", "32.5 kg (without base)");
        } else if (nameLower.contains("ultrathin magnetic") || nameLower.contains("power bank 5000") || nameLower.contains("magnetic power bank")) {
            specs.put("Capacity", "5000mAh (Typical) / 3000mAh (Rated)");
            specs.put("Wireless Output", "15W Max (Xiaomi) / 7.5W Max (iPhone)");
            specs.put("Wired Output", "22.5W Max (USB-C)");
            specs.put("Output (USB-C + Wireless)", "7.5W + 5W Max");
            specs.put("Battery Type", "Lithium-ion (High-density silicon-carbon)");
            specs.put("Charging Mechanism", "Magnetic induction");
            specs.put("Dimensions", "98.5 x 71.5 x 6 mm");
            specs.put("Weight", "98 g");
        } else if (nameLower.contains("wireless mouse 3") || nameLower.contains("wireless mouse")) {
            specs.put("Connection Method", "2.4GHz wireless, Bluetooth");
            specs.put("Compatibility", "Windows 10, macOS 10.13 and newer");
            specs.put("Rated Input", "1.5V ⎓ 50mA");
            specs.put("Product Materials", "Plastic, metal");
            specs.put("Operating Temperature", "0°C to 40°C");
            specs.put("Dimensions", "119 x 64 x 41 mm");
            specs.put("Weight", "57 g (without battery)");
        } else if (nameLower.contains("hair dryer h500") || nameLower.contains("water ionic hair dryer") || nameLower.contains("h500")) {
            specs.put("Airflow Speed", "20m/s Ultra-high Airflow");
            specs.put("Motor Speed", "20,000 rpm High-speed Motor");
            specs.put("Temperature Control", "Smart Hot/Cold Air Alternating Mode, NTC Temperature Detection");
            specs.put("Protection", "Overheat Protection, Thermal Fuse Protection");
            specs.put("Features", "Double Water Ion Therapy, Removable Air Intake Filter");
            specs.put("Body Design", "Metal Body, Anodized aluminum alloy finish");
            specs.put("Power Cord Length", "1.7 m");
            specs.put("Dimensions", "101 mm (cylinder length without nozzle)");
            specs.put("Weight", "550 g (excluding nozzle)");
        } else if (nameLower.contains("smart scale s200") || nameLower.contains("s200") || nameLower.contains("smart scale")) {
            specs.put("Weighing Range", "0.1kg - 150kg");
            specs.put("Minimum Scale Increments", "0.05kg");
            specs.put("Metrics Provided", "Weight, BMI, Standard weight, Weight control");
            specs.put("Connectivity", "Bluetooth 5.4");
            specs.put("App Compatibility", "Xiaomi Home app (Android 8.0 / iOS 12.0 and above)");
            specs.put("User Profiles", "Up to 36 users");
            specs.put("Special Features", "Smart switching between people/objects, Balance testing");
            specs.put("Battery", "3 x AAA (Up to 180 days lifespan)");
            specs.put("Material", "Tempered glass, engineering plastic");
            specs.put("Dimensions", "280 x 280 x 24.5 mm");
            specs.put("Weight", "1.2 kg");
        } else if (nameLower.contains("120w hypercharge") || nameLower.contains("hypercharge combo") || nameLower.contains("120w hypercharge combo")) {
            specs.put("Maximum Output", "120W");
            specs.put("Input", "100-240V~, 50/60Hz, 3A");
            specs.put("Output Ports", "USB Type-A");
            specs.put("Charging Outputs", "5V⎓3A (15W), 9V⎓3A (27W), 11V⎓6A (66W Max), 20V⎓6A (120W Max)");
            specs.put("Protection", "10 layers of smart protection (over-current, over-voltage, short-circuit, etc.)");
            specs.put("Material", "Flame-retardant PC");
            specs.put("Operating Temperature", "-10°C to 40°C");
            specs.put("Dimensions", "56.1 x 77.2 x 28.8 mm (excluding prongs)");
            specs.put("Weight", "170 g");
            specs.put("Package Contents", "Charger, USB-A to USB-C cable, User manual, Warranty notice");
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