import javax.swing.*;
import java.awt.*;

public class AdminView {
    private JPanel adminPanel = new JPanel();
    private Core core = Core.getInstance();
    private JTextField idField = new JTextField(15);
    private JTextField nameField = new JTextField(15);
    private JTextField priceField = new JTextField(15);
    private JTextField stockField = new JTextField(15);
    private JTextField imageField = new JTextField(15);
    private GUI_MainFrame mainFrame;

    public AdminView(GUI_MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        adminPanel.setLayout(new BorderLayout());
        adminPanel.setBorder(BorderFactory.createTitledBorder("Admin Panel - Add Manage Products"));

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.add(new JLabel("Product ID:"));
        formPanel.add(idField);
        formPanel.add(new JLabel("Product Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Price:"));
        formPanel.add(priceField);
        formPanel.add(new JLabel("Stock Quantity:"));
        formPanel.add(stockField);

        formPanel.add(new JLabel("Image File Name:"));
        formPanel.add(imageField);


        JButton addProductButton = new JButton("Add New Product");
        addProductButton.addActionListener(e -> processAddProduct());
        formPanel.add(new JLabel(""));
        formPanel.add(addProductButton);

        adminPanel.add(formPanel, BorderLayout.CENTER);

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

    private void processAddProduct() {
        try {
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            String imageFileName = imageField.getText().trim();
            if (id.isEmpty() || name.isEmpty() || imageFileName.isEmpty()) {
                JOptionPane.showMessageDialog(adminPanel, "All fields cannot be empty.", "Input Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            double price = Double.parseDouble(priceField.getText().trim());
            int stock = Integer.parseInt(stockField.getText().trim());
            Product newProduct = new Product(id, name, price, stock, imageFileName, new java.util.LinkedHashMap<>());
            core.getInventory().add(newProduct);
            core.getHotProducts().add(newProduct);
            JOptionPane.showMessageDialog(adminPanel, "Successfully added:" + name);
            idField.setText("");
            nameField.setText("");
            priceField.setText("");
            stockField.setText("");
            imageField.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(adminPanel, "Price and Stock must be valid numbers.", "Input Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public GUI_MainFrame getMainFrame() {
        return mainFrame;
    }

    public JPanel getAdminPanel() {
        return adminPanel;
    }
}
