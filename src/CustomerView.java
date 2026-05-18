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
    profilePanel.setLayout(new GridBagLayout());
    profilePanel.setBorder(BorderFactory.createTitledBorder("Customer Profile"));
    JPanel contentPanel = new JPanel(new GridBagLayout()) {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            try {
                ImageIcon icon = new ImageIcon("background.png"); 
                Image img = icon.getImage();
                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
            } catch (Exception e) {
            }
        }
    };

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10); 
    gbc.gridx = 0;

    Customer customer = (Customer) core.getLoggedInUser();

    if (customer == null) {
        contentPanel.add(new JLabel("No customer profile found."));
    } else {
        JLabel welcomeLbl = new JLabel("*** Welcome, " + customer.getUsername() + " ***", SwingConstants.CENTER);
        welcomeLbl.setFont(new Font("Segoe UI", Font.BOLD, 24));
        
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        contentPanel.add(welcomeLbl, gbc);

        gbc.anchor = GridBagConstraints.WEST;
    
        gbc.gridy = 1;
        contentPanel.add(new JLabel("👤 Full Name: " + (customer.getFullName() == null || customer.getFullName().trim().isEmpty() ? "(Not updated)" : customer.getFullName())), gbc);
        
        gbc.gridy = 2;
        contentPanel.add(new JLabel("🎂 Age: " + (customer.getAge() > 0 ? customer.getAge() : "(Not updated)")), gbc);
        
        gbc.gridy = 3;
        contentPanel.add(new JLabel("🏠 Address: " + (customer.getAddress() == null || customer.getAddress().trim().isEmpty() ? "(Not updated)" : customer.getAddress())), gbc);
        
        String payment = (customer.getPaymentMethod() == null || customer.getPaymentMethod().trim().isEmpty() ? "(Not updated)" : customer.getPaymentMethod());
        if (customer.getCardNumber() != null && !customer.getCardNumber().trim().isEmpty()) {
            payment += " [" + customer.getCardNumber() + "]";
        }
        gbc.gridy = 4;
        contentPanel.add(new JLabel("💳 Payment: " + payment), gbc);
        
        gbc.gridy = 5;
        contentPanel.add(new JLabel("🛒 Cart: " + customer.getPersonalCart().getItems().size() + " item(s)"), gbc);
        
        gbc.gridy = 6;
        contentPanel.add(new JLabel("📦 Orders: " + customer.getOrderHistory().size() + " order(s)"), gbc);

        JButton editProfile = new JButton(" Edit Profile");
        editProfile.setFont(new Font("Segoe UI", Font.BOLD, 13));
        editProfile.addActionListener(e -> {
            ShowEditProfile dialog = new ShowEditProfile(getMainFrame().getFrame(), customer);
            dialog.setVisible(true);
            refreshProfile();
        });
        
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.CENTER; 
        contentPanel.add(editProfile, gbc);
    }

    GridBagConstraints mainGbc = new GridBagConstraints();
    mainGbc.gridx = 0;
    mainGbc.gridy = 0;
    mainGbc.weightx = 1.0;
    mainGbc.weighty = 1.0;
    mainGbc.fill = GridBagConstraints.BOTH; 
    profilePanel.add(contentPanel, mainGbc);
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
