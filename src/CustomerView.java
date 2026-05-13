import javax.swing.*;
import java.awt.*;

public class CustomerView {
    private JPanel customerPanel = new JPanel();
    private Core core = Core.getInstance();

    private GUI_MainFrame mainFrame;
    private JPanel historyPanel = new JPanel();

    public CustomerView(GUI_MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        getCustomerPanel().setLayout(new BorderLayout());
        getCustomerPanel().setBorder(BorderFactory.createTitledBorder("Customer Profile"));
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.addActionListener(e -> {
            Core.getInstance().setLoggedInUser(null);
            JOptionPane.showMessageDialog(customerPanel, "Logged out successful.");
            getMainFrame().showHome();

        });
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(logoutBtn);
        customerPanel.add(bottomPanel, BorderLayout.SOUTH);
        historyPanel.setLayout(new BoxLayout(historyPanel, BoxLayout.Y_AXIS));

        refreshHistory();
        JScrollPane scrollPane = new JScrollPane(historyPanel);
        customerPanel.add(scrollPane, BorderLayout.CENTER);
    }

    public void refreshHistory() {
        historyPanel.removeAll();
        User user = getCore().getLoggedInUser();
        if (user != null) {
            Customer customer = (Customer) user;
            if (customer.getOrderHistory().isEmpty()) {
                historyPanel.add(new JLabel("No order history yet."));
            } else {
    for (PurchaseOrder order : customer.getOrderHistory()) {
            String headerText = "--- ORDER DATE: " + order.getFormattedDate() + " | TOTAL MONEY: $" + order.getTotalAmount() + " ---";
            JLabel lblHeader = new JLabel(headerText);
            lblHeader.setFont(new Font("Arial", Font.BOLD, 12));
            historyPanel.add(lblHeader);

    for (CartItem item : order.getItems()) {
            String productName = item.getProduct().getName();
            int qty = item.getQuantity();
            double price = item.getProduct().getPrice();   
            String itemText = "    • " + productName + " (Qty: " + qty + ") - Price: $" + price;
            historyPanel.add(new JLabel(itemText));
    }
            historyPanel.add(new JLabel(" "));
            } 
        } 
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
