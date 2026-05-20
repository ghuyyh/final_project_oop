import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AdminView {
    private JPanel adminPanel = new JPanel();
    private Core core = Core.getInstance();
    private GUI_MainFrame mainFrame;

    private JTable inventoryTable;
    private DefaultTableModel tableModel;

    public AdminView(GUI_MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        adminPanel.setLayout(new BorderLayout(10, 10));
        adminPanel.setBorder(BorderFactory.createTitledBorder("Admin Panel - Add Manage Products"));

        JPanel formPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addProductButton = new JButton("Add New Product");
        addProductButton.addActionListener(e -> {
            AddProduct dialog = new AddProduct(getMainFrame(), this);
            dialog.setLocationRelativeTo(getMainFrame().getFrame());
            dialog.setVisible(true);
        });
        formPanel.add(addProductButton);

        JButton viewOrdersBtn = new JButton("View Customer Orders");
        viewOrdersBtn.addActionListener(e -> {
            AdminOrderHistoryDialog orderDialog = new AdminOrderHistoryDialog(getMainFrame().getFrame());
            orderDialog.setVisible(true);
        });
        formPanel.add(viewOrdersBtn);

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

        JPanel botTablePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton toggleHotSaleBtn = new JButton(" Enable/Disable Hot Sale");
        toggleHotSaleBtn.setBackground(Color.WHITE);
        toggleHotSaleBtn.setForeground(Color.BLACK);
        toggleHotSaleBtn.setFont(new Font("Arial", Font.BOLD, 12));
        JButton deleteProductBtn = new JButton("Delete selected product");
        deleteProductBtn.setBackground(Color.RED);
        deleteProductBtn.setForeground(Color.WHITE);
        deleteProductBtn.setFont(new Font("Arial", Font.BOLD, 12));
        JButton editProductBtn = new JButton("Edit product");

        //listener for edit product
        editProductBtn.addActionListener(e -> {
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
                    EditProduct dialog = new EditProduct(mainFrame, this, selectedProduct);
                    dialog.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(adminPanel, "Product not found.");
                }
            } else {
                JOptionPane.showMessageDialog(adminPanel, "Please select a product from the table first.");
            }
        });

        botTablePanel.add(editProductBtn);
        botTablePanel.add(deleteProductBtn);
        botTablePanel.add(toggleHotSaleBtn);

        //listener for hot sale
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
                    core.saveInventory();
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

        //listener for delete product
        deleteProductBtn.addActionListener(e -> {
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
                    core.getInventory().remove(selectedProduct);
                    core.saveInventory();
                    JOptionPane.showMessageDialog(adminPanel,
                            "Product deleted: " + selectedProduct.getName());
                } else {
                    JOptionPane.showMessageDialog(adminPanel, "Product not found.");
                }
                refreshInventoryTable();
            } else {
                JOptionPane.showMessageDialog(adminPanel, "Please select a product from the table first.");}
        });

        tablePanel.add(new JScrollPane(inventoryTable), BorderLayout.CENTER);
        tablePanel.add(botTablePanel, BorderLayout.SOUTH);

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

    public void refreshInventoryTable() {
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

    public GUI_MainFrame getMainFrame() {
        return mainFrame;
    }

    public JPanel getAdminPanel() {
        return adminPanel;
    }
}