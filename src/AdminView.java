import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class AdminView {
    private JPanel adminPanel = new JPanel();
    private Core core = Core.getInstance();
    private JTextField idField = new JTextField(15);
    private JTextField nameField = new JTextField(15);
    private JTextField priceField = new JTextField(15);
    private JTextField stockField = new JTextField(15);
    private JTextField imageField = new JTextField(15);
    private GUI_MainFrame mainFrame;
    private File selectedImageFile;

    private JTable inventoryTable;
    private DefaultTableModel tableModel;

    public AdminView(GUI_MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        adminPanel.setLayout(new BorderLayout(10, 10));
        adminPanel.setBorder(BorderFactory.createTitledBorder("Admin Panel - Add Manage Products"));

        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        formPanel.add(new JLabel("Product ID:"));
        formPanel.add(idField);
        formPanel.add(new JLabel("Product Name:")); 
        formPanel.add(nameField);
        formPanel.add(new JLabel("Price:"));
        formPanel.add(priceField);
        formPanel.add(new JLabel("Stock Quantity:"));
        formPanel.add(stockField);
        formPanel.add(new JLabel("Image File Name:"));
        imageField.setEditable(false);
        JPanel imagePanel = new JPanel(new BorderLayout(5, 0));
        JButton chooseImageButton = new JButton("Choose Image");
        chooseImageButton.addActionListener(e -> chooseImage());
        imagePanel.add(imageField, BorderLayout.CENTER);
        imagePanel.add(chooseImageButton, BorderLayout.EAST);
        formPanel.add(imagePanel);

        JButton addProductButton = new JButton("Add New Product");
        addProductButton.addActionListener(e -> processAddProduct());
        formPanel.add(new JLabel(""));
        formPanel.add(addProductButton);

        JPanel tablePanel = new JPanel(new BorderLayout(5, 5));
        tablePanel.setBorder(BorderFactory.createTitledBorder("Product Inventory & Hot Sale Manager"));

        String[] columns = { "Product ID", "Product Name", "Price", "Stock", "Is Hot Sale?" };
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        inventoryTable = new JTable(tableModel);
        refreshInventoryTable();

        JButton toggleHotSaleBtn = new JButton(" Enable/Disable Hot Sale for selected product ");
        toggleHotSaleBtn.setBackground(Color.WHITE);
        toggleHotSaleBtn.setForeground(Color.BLACK);
        toggleHotSaleBtn.setFont(new Font("Arial", Font.BOLD, 12));

        toggleHotSaleBtn.addActionListener(e -> {
            int selectedRow = inventoryTable.getSelectedRow();
            if (selectedRow != -1) {
                String pId = (String) inventoryTable.getValueAt(selectedRow, 0);

                Product selectedProduct = null;
                for (Product p : core.getInventory()) {
                    if (p.getId().equals(pId)) {
                        selectedProduct = p;
                        break;
                    }
                }

                if (selectedProduct != null) {
                    selectedProduct.setHotSale(!selectedProduct.getHotSale());
                    DataManager.saveInventory();
                    JOptionPane.showMessageDialog(adminPanel,
                            "Hot sale status updated for: " + selectedProduct.getName());
                } else {
                    JOptionPane.showMessageDialog(adminPanel, "Product not found.");
                }
                refreshInventoryTable();
            } else {
                JOptionPane.showMessageDialog(adminPanel, "Please select a product from the table first.");
            }
        });

        tablePanel.add(new JScrollPane(inventoryTable), BorderLayout.CENTER);
        tablePanel.add(toggleHotSaleBtn, BorderLayout.SOUTH);

        adminPanel.add(formPanel, BorderLayout.NORTH);
        adminPanel.add(tablePanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.addActionListener(e -> {
            Core.getInstance().setLoggedInUser(null);
            JOptionPane.showMessageDialog(adminPanel, "Logged out successful.");
            getMainFrame().showHome();
        });
        bottomPanel.add(logoutBtn);
        adminPanel.add(bottomPanel, BorderLayout.SOUTH);
    }

    private void refreshInventoryTable() {
        tableModel.setRowCount(0);
        for (Product p : core.getInventory()) {
            String isHot = p.getHotSale() ? "YES" : "NO";
            tableModel.addRow(new Object[] {
                    p.getId(),
                    p.getName(),
                    "$" + String.format("%.2f", p.getPrice()),
                    p.getStockQuantity(),
                    isHot
            });
        }
    }

    private void processAddProduct() {
        try {
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            if (id.isEmpty() || name.isEmpty() || selectedImageFile == null) {
                JOptionPane.showMessageDialog(adminPanel, "All fields cannot be empty.", "Input Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            double price = Double.parseDouble(priceField.getText().trim());
            int stock = Integer.parseInt(stockField.getText().trim());
            Product newProduct = new Product(id, name, price, stock, new java.util.LinkedHashMap<>(), false, "");

            core.getInventory().add(newProduct);
            saveSelectedImage(id, selectedImageFile);
            DataManager.saveInventory();

            JOptionPane.showMessageDialog(adminPanel, "Successfully added: " + name);
            refreshInventoryTable();

            idField.setText("");
            nameField.setText("");
            priceField.setText("");
            stockField.setText("");
            imageField.setText("");
            selectedImageFile = null;
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(adminPanel, "Price and Stock must be valid numbers.", "Input Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(adminPanel, "Unable to save the selected image.", "Image Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void chooseImage() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Choose Product Image");
        chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Image files", "png", "jpg", "jpeg", "gif", "bmp"));

        int result = chooser.showOpenDialog(adminPanel);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedImageFile = chooser.getSelectedFile();
            imageField.setText(selectedImageFile.getName());
        }
    }

    private void saveSelectedImage(String productId, File imageFile) throws IOException {
        Path targetDir = Path.of("src", "res", "product_images");
        Files.createDirectories(targetDir);

        String lowerName = imageFile.getName().toLowerCase();
        String extension = ".png";
        int dotIndex = lowerName.lastIndexOf('.');
        if (dotIndex != -1) {
            extension = lowerName.substring(dotIndex);
        }

        Path targetFile = targetDir.resolve(productId + extension);
        Files.copy(imageFile.toPath(), targetFile, StandardCopyOption.REPLACE_EXISTING);
    }

    public GUI_MainFrame getMainFrame() {
        return mainFrame;
    }

    public JPanel getAdminPanel() {
        return adminPanel;
    }
}