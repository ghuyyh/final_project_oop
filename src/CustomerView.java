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
        historyPanel.setBorder(BorderFactory.createEmptyBorder());
        JScrollPane historyScrollPane = new JScrollPane(historyPanel);
        historyScrollPane.setBorder(BorderFactory.createEmptyBorder());
        historyContainer.add(historyScrollPane, BorderLayout.CENTER);

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
                    if (img != null) {
                        g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
                    }
                } catch (Exception e) {
                }
            }
        };
        contentPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;

        Customer customer = (Customer) core.getLoggedInUser();

        if (customer == null) {
            contentPanel.add(new JLabel("No customer profile found."));
        } else {
            Font labelFont = new Font("Segoe UI Emoji", Font.BOLD, 14);
            Color textColor = Color.BLACK;

            JLabel welcomeLbl = new JLabel("*** Welcome, " + customer.getUsername() + " ***", SwingConstants.CENTER);
            welcomeLbl.setFont(new Font("Segoe UI", Font.BOLD, 24));
            welcomeLbl.setForeground(textColor);
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.CENTER;
            contentPanel.add(welcomeLbl, gbc);

            gbc.anchor = GridBagConstraints.WEST;

            JLabel nameLbl = new JLabel("👤 Full Name: "
                    + (customer.getFullName() == null || customer.getFullName().trim().isEmpty() ? "(Not updated)"
                            : customer.getFullName()));
            nameLbl.setFont(labelFont);
            nameLbl.setForeground(textColor);
            gbc.gridy = 1;
            contentPanel.add(nameLbl, gbc);

            JLabel ageLbl = new JLabel("🎂 Age: " + (customer.getAge() > 0 ? customer.getAge() : "(Not updated)"));
            ageLbl.setFont(labelFont);
            ageLbl.setForeground(textColor);
            gbc.gridy = 2;
            contentPanel.add(ageLbl, gbc);

            JLabel addrLbl = new JLabel("🏠 Address: "
                    + (customer.getAddress() == null || customer.getAddress().trim().isEmpty() ? "(Not updated)"
                            : customer.getAddress()));
            addrLbl.setFont(labelFont);
            addrLbl.setForeground(textColor);
            gbc.gridy = 3;
            contentPanel.add(addrLbl, gbc);

            String payment = (customer.getPaymentMethod() == null || customer.getPaymentMethod().trim().isEmpty()
                    ? "(Not updated)"
                    : customer.getPaymentMethod());
            if (customer.getCardNumber() != null && !customer.getCardNumber().trim().isEmpty()) {
                payment += " [" + customer.getCardNumber() + "]";
            }
            JLabel payLbl = new JLabel("💳 Payment: " + payment);
            payLbl.setFont(labelFont);
            payLbl.setForeground(textColor);
            gbc.gridy = 4;
            contentPanel.add(payLbl, gbc);

            JLabel cartLbl = new JLabel("🛒 Cart: " + customer.getPersonalCart().getItems().size() + " item(s)");
            cartLbl.setFont(labelFont);
            cartLbl.setForeground(textColor);
            gbc.gridy = 5;
            contentPanel.add(cartLbl, gbc);

            JLabel orderLbl = new JLabel("📦 Orders: " + customer.getOrderHistory().size() + " order(s)");
            orderLbl.setFont(labelFont);
            orderLbl.setForeground(textColor);
            gbc.gridy = 6;
            contentPanel.add(orderLbl, gbc);

            JButton editProfile = new JButton("Edit Profile");
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
        historyPanel.setLayout(new BoxLayout(historyPanel, BoxLayout.Y_AXIS));
        historyPanel.setBackground( new Color(240, 242, 245));

        Customer customer = (Customer) core.getLoggedInUser();

        if (customer != null && !customer.getOrderHistory().isEmpty()) {
            for (PurchaseOrder order : customer.getOrderHistory()) {
                JPanel oderPanel = new JPanel(new BorderLayout(15, 10));
                oderPanel.setBackground(Color.WHITE);
                oderPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                        BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                        oderPanel.setMaximumSize(new Dimension(Short.MAX_VALUE, 150));
                        JPanel infoPanel = new JPanel();
                infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
                infoPanel.setBackground(Color.WHITE);
                JLabel dateLabel = new JLabel("Order Date: " + order.getFormattedDate());
                dateLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
                dateLabel.setForeground(new Color(50, 50, 50));
                infoPanel.add(dateLabel);
                infoPanel.add(Box.createVerticalStrut(8));
            JPanel itemListPanel = new JPanel();
                itemListPanel.setLayout(new BoxLayout(itemListPanel, BoxLayout.Y_AXIS));
                itemListPanel.setBackground(Color.WHITE);

            for (CartItem item : order.getItems()) {
                String productName = item.getProduct().getName();
                int qty = item.getQuantity();
                double price = item.getProduct().getPrice();

            JLabel itemLabel = new JLabel("Product: " + productName + " | Quantity: " + qty + " | Price: $" + price);
                itemLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                itemLabel.setForeground(new Color(80, 80, 80));
                itemListPanel.add(itemLabel);
                itemListPanel.add(Box.createVerticalStrut(4));    
            }
                infoPanel.add(itemListPanel);
                oderPanel.add(infoPanel, BorderLayout.CENTER);

                JPanel rightPanel = new JPanel(new GridBagLayout());
                rightPanel.setBackground(Color.WHITE);
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.anchor = GridBagConstraints.EAST;
                gbc.insets = new Insets(0, 0, 5, 0);

                JLabel totalLabel = new JLabel(String.format("Total: $%.2f", order.getTotalAmount()));
                totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
                totalLabel.setForeground(new Color(50, 50, 50));
                rightPanel.add(totalLabel, gbc);

                gbc.gridy = 1;
                gbc.insets = new Insets(5, 0, 0, 0);
                JButton reButton = new JButton("RE-ORDER");
                reButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
                reButton.setBackground(new Color(70, 130, 180));
                reButton.setForeground(Color.WHITE);
                reButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                rightPanel.add(reButton, gbc);
                oderPanel.add(rightPanel, BorderLayout.EAST);
                historyPanel.add(oderPanel);
                historyPanel.add(Box.createVerticalStrut(10));
        }
    } 
        else {
            JPanel emptyPanel = new JPanel(new GridBagLayout());
            emptyPanel.setBackground(historyPanel.getBackground());
            JLabel emptyLabel = new JLabel("No orders found.");
            emptyLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
            emptyLabel.setForeground(new Color(100, 100, 100));

            emptyPanel.add(emptyLabel);
            historyPanel.add(emptyPanel);
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
