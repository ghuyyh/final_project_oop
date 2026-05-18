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
        Customer customer = (Customer) core.getLoggedInUser();

        if (customer == null) {
            profilePanel.add(new JLabel("No customer profile."));
        } else {
            profilePanel.add(new JLabel("Username: " + valueOrNA(customer.getUsername())));
            profilePanel.add(new JLabel("Full Name: " + valueOrNA(customer.getFullName())));
            profilePanel.add(new JLabel("Age: " + (customer.getAge() > 0 ? customer.getAge() : "N/A")));
            profilePanel.add(new JLabel("Address: " + valueOrNA(customer.getAddress())));
            profilePanel.add(new JLabel("Payment: " + valueOrNA(customer.getPaymentMethod())));
            profilePanel.add(new JLabel("Items In Cart: " + customer.getPersonalCart().getItems().size()));
            profilePanel.add(new JLabel("Total Orders: " + customer.getOrderHistory().size()));
            profilePanel.add(Box.createVerticalStrut(15)); 
            
            JButton editProfile = new JButton("Edit Profile");
            editProfile.addActionListener(e -> {
            ShowEditProfile dialog = new ShowEditProfile(getMainFrame().getFrame(), customer);
            dialog.setVisible(true);
            refreshProfile();
        });
    }
        profilePanel.revalidate();
        profilePanel.repaint();
    }

    private String valueOrNA(String value) {
        return (value == null || value.trim().isEmpty()) ? "N/A" : value;
    }

    // private Customer getCurrentCustomer() {
    //     User currentUser = Core.getInstance().getLoggedInUser();
    //     if (currentUser instanceof Customer) {
    //         return (Customer) currentUser;
    //     }
    //     return null;
    // }

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
