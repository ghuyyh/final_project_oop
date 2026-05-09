import javax.swing.*;
import java.awt.*;
public class AdminView {
    private JPanel adminPanel = new JPanel();
   private JTextField idField = new JTextField(15);
    private JTextField nameField = new JTextField(15);
    private JTextField priceField = new JTextField(15);
    private JTextField stockField = new JTextField(15);

    public AdminView() {
        adminPanel.setLayout(new BorderLayout());
        adminPanel.setBorder(BorderFactory.createTitledBorder("Admin Panel - Add Manage Products"));
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.add(new JLabel("Product ID:"));
        formPanel.add(idField);
        formPanel.add(new JLabel("Product Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Price:"));
        formPanel.add(priceField);
        formPanel.add(new JLabel("Stock Quantity:"));
        formPanel.add(stockField);
        JButton addProductButton = new JButton("Add New Product");
        formPanel.add(new JLabel(""));
        formPanel.add(addProductButton);
        adminPanel.add(formPanel, BorderLayout.CENTER);
    }
    private void processAddProduct() {
        try{
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            if (id.isEmpty() || name.isEmpty()) {
                JOptionPane.showMessageDialog(adminPanel, "Product ID and Name cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            double price = Double.parseDouble(priceField.getText().trim());
            int stock = Integer.parseInt(stockField.getText().trim());
            Product newProduct = new Product(id, name, price, stock);
            core.getInventory().addProduct(newProduct);
            core.getHotProducts().addProduct(newProduct);
            JOptionPane.showMessageDialog(adminPanel, "Successfully added:" + name);
            idField.setText("");
            nameField.setText("");
            priceField.setText("");
            stockField.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(adminPanel, "Price and Stock must be valid numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public JPanel getAdminPanel() {
        return adminPanel;
    }
}
