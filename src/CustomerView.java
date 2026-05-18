import javax.swing.*;
import java.awt.*;

public class CustomerView {
    private JPanel customerPanel = new JPanel();
    private Core core = Core.getInstance();

    private GUI_MainFrame mainFrame;
    private JPanel profilePanel = new JPanel();
    private JPanel historyPanel = new JPanel();

    public CustomerView(GUI_MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        getCustomerPanel().setLayout(new BorderLayout());
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 0));

        profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));
        profilePanel.setBorder(BorderFactory.createTitledBorder("Customer Profile"));

        historyPanel.setLayout(new BoxLayout(historyPanel, BoxLayout.Y_AXIS));
        JPanel historyContainer = new JPanel(new BorderLayout());
        historyContainer.setBorder(BorderFactory.createTitledBorder("Order History"));
        historyContainer.add(new JScrollPane(historyPanel), BorderLayout.CENTER);

        centerPanel.add(profilePanel);
        centerPanel.add(historyContainer);
        customerPanel.add(centerPanel, BorderLayout.CENTER);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.addActionListener(e -> {
            Core.getInstance().setLoggedInUser(null);
            JOptionPane.showMessageDialog(customerPanel, "Logged out successful.");
            getMainFrame().showHome();

        });
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(logoutBtn);
        customerPanel.add(bottomPanel, BorderLayout.SOUTH);

        refreshProfile();
        refreshHistory();
    }

    public void refreshProfile() {
    profilePanel.removeAll();
    profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));
    profilePanel.setBorder(BorderFactory.createTitledBorder("Customer Profile"));
    Customer customer = (Customer) core.getLoggedInUser();

    if (customer == null) {
        profilePanel.add(new JLabel("No customer profile found."));
    } else {
        JLabel welcomeLbl = new JLabel("👋 Welcome, " + customer.getUsername() + "!");
        welcomeLbl.setFont(new Font("Segoe UI", Font.BOLD, 16)); 
        profilePanel.add(welcomeLbl);
        profilePanel.add(Box.createVerticalStrut(15)); // Cách ra một đoạn
        profilePanel.add(new JLabel("👤 Full Name: " + (customer.getFullName() == null || customer.getFullName().trim().isEmpty() ? "(Not updated)" : customer.getFullName())));
        profilePanel.add(Box.createVerticalStrut(5));
        profilePanel.add(new JLabel("🎂 Age: " + (customer.getAge() > 0 ? customer.getAge() : "(Not updated)")));
        profilePanel.add(Box.createVerticalStrut(5));
        profilePanel.add(new JLabel("🏠 Address: " + (customer.getAddress() == null || customer.getAddress().trim().isEmpty() ? "(Not updated)" : customer.getAddress())));
        profilePanel.add(Box.createVerticalStrut(5));

        String payment = (customer.getPaymentMethod() == null || customer.getPaymentMethod().trim().isEmpty() ? "(Not updated)" : customer.getPaymentMethod());
        if (customer.getCardNumber() != null && !customer.getCardNumber().trim().isEmpty()) {
            payment += " [" + customer.getCardNumber() + "]";
        }
        profilePanel.add(new JLabel("💳 Payment: " + payment));
        profilePanel.add(Box.createVerticalStrut(5));
        
        profilePanel.add(new JLabel("🛒 Cart: " + customer.getPersonalCart().getItems().size() + " item(s)"));
        profilePanel.add(Box.createVerticalStrut(5));
        
        profilePanel.add(new JLabel("📦 Orders: " + customer.getOrderHistory().size() + " order(s)"));
        profilePanel.add(Box.createVerticalStrut(15)); 

        JButton editProfile = new JButton("✏️ Edit Profile");
        editProfile.addActionListener(e -> {
        ShowEditProfile dialog = new ShowEditProfile(getMainFrame().getFrame(), customer);
        dialog.setVisible(true);
        refreshProfile(); 
        profilePanel.add(editProfile);
    });
}
    profilePanel.revalidate();
    profilePanel.repaint();
}
    public void refreshHistory() {
        historyPanel.removeAll();
        Customer customer = (Customer) core.getLoggedInUser();
        if (customer != null) {
            if (customer.getOrderHistory().isEmpty()) {
                historyPanel.add(new JLabel("No order history yet."));
            } else {
    for (PurchaseOrder order : customer.getOrderHistory()) {
            String headerText = "--- ORDER DATE: " + order.getFormattedDate() + " | TOTAL MONEY: $" + order.getTotalAmount() + " ---";
            JLabel lblHeader = new JLabel(headerText);
            historyPanel.add(lblHeader);

    for (CartItem item : order.getItems()) {
            String productName = item.getProduct().getName();
            int qty = item.getQuantity();
            double price = item.getProduct().getPrice();   
            JLabel itemLabel = new JLabel("Product: " + productName + " | Quantity: " + qty + " | Price: $" + price);
            historyPanel.add(itemLabel);
           } 
            historyPanel.add(new JLabel());
        }
    }
} else {
            historyPanel.add(new JLabel("No order history yet."));
}
        historyPanel.revalidate();
        historyPanel.repaint();
    }
    public JPanel getCustomerPanel() {
        return customerPanel;
    }

    public Core getCore() {
        return core;
    }

    public JPanel getHistoryPanel() {
        return historyPanel;
    }

    public GUI_MainFrame getMainFrame() {
        return mainFrame;
    }
}
