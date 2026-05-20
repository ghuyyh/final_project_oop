import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AdminOrderHistoryDialog extends JDialog {

    public AdminOrderHistoryDialog(JFrame parent) {
        super(parent, "All Customers - Order History", true);
        setSize(700, 550);
        setLocationRelativeTo(parent);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel historyPanel = new JPanel();
        historyPanel.setLayout(new BoxLayout(historyPanel, BoxLayout.Y_AXIS));
        historyPanel.setBackground(new Color(240, 242, 245));
        historyPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        Core core = Core.getInstance();
        boolean hasOrders = false;

        for (User user : core.getUsersDatabase()) {
            if (user instanceof Customer) {
                Customer customer = (Customer) user;
                List<PurchaseOrder> orders = customer.getOrderHistory();
                
                if (orders != null && !orders.isEmpty()) {
                    for (PurchaseOrder order : orders) {
                        hasOrders = true;
                        
                        JPanel orderPanel = new JPanel(new BorderLayout(15, 10));
                        orderPanel.setBackground(Color.WHITE);
                        orderPanel.setBorder(BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                        orderPanel.setMaximumSize(new Dimension(Short.MAX_VALUE, 160));

                        JPanel infoPanel = new JPanel();
                        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
                        infoPanel.setBackground(Color.WHITE);

                        String displayName = (customer.getFullName() == null || customer.getFullName().trim().isEmpty()) 
                                              ? customer.getUsername() : customer.getFullName();
                        JLabel customerLabel = new JLabel(" Customer Name: " + displayName);
                        customerLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
                        customerLabel.setForeground(new Color(25, 118, 210));
                        
                        JLabel dateLabel = new JLabel(" Order Date: " + order.getFormattedDate());
                        dateLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
                        dateLabel.setForeground(new Color(50, 50, 50));

                        infoPanel.add(customerLabel);
                        infoPanel.add(Box.createVerticalStrut(5));
                        infoPanel.add(dateLabel);
                        infoPanel.add(Box.createVerticalStrut(8));

                        JPanel itemListPanel = new JPanel();
                        itemListPanel.setLayout(new BoxLayout(itemListPanel, BoxLayout.Y_AXIS));
                        itemListPanel.setBackground(Color.WHITE);

                        for (CartItem item : order.getItems()) {
                            String productName = item.getProduct().getName();
                            int qty = item.getQuantity();
                            double price = item.getProduct().getPrice();

                            JLabel itemLabel = new JLabel("  - " + productName + " | Qty: " + qty + " | Price: $" + price);
                            itemLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                            itemLabel.setForeground(new Color(80, 80, 80));
                            itemListPanel.add(itemLabel);
                            itemListPanel.add(Box.createVerticalStrut(2));
                        }
                        
                        infoPanel.add(itemListPanel);
                        orderPanel.add(infoPanel, BorderLayout.CENTER);

                        JPanel rightPanel = new JPanel(new GridBagLayout());
                        rightPanel.setBackground(Color.WHITE);
                        
                        JLabel totalLabel = new JLabel(String.format("Total: $%.2f", order.getTotalAmount()));
                        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
                        totalLabel.setForeground(new Color(220, 50, 50));
                        rightPanel.add(totalLabel);

                        orderPanel.add(rightPanel, BorderLayout.EAST);
                        historyPanel.add(orderPanel);
                        historyPanel.add(Box.createVerticalStrut(15));
                    }
                }
            }
        }

        if (!hasOrders) {
            JPanel emptyPanel = new JPanel(new GridBagLayout());
            emptyPanel.setBackground(historyPanel.getBackground());
            JLabel emptyLabel = new JLabel("No customer orders found in the database.");
            emptyLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
            emptyLabel.setForeground(new Color(100, 100, 100));
            emptyPanel.add(emptyLabel);
            historyPanel.add(emptyPanel);
        }

        JScrollPane scrollPane = new JScrollPane(historyPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton closeBtn = new JButton("Close");
        closeBtn.addActionListener(e -> dispose());
        bottomPanel.add(closeBtn);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }
}