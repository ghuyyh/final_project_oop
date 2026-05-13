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
        String orderHeader = String.format("<html><div style='background-color: #eeeeee; width: 300px; padding: 5px; margin-top: 10px;'>" + "📅 <b>Ngày mua:</b> %s <br/>" + "💰 <b>Tổng đơn:</b> $%.2f" + "</div></html>", order.getFormattedDate(), order.getTotalAmount());
        this.historyPanel.add(new JLabel(orderHeader));

    for (CartItem item : order.getItems()) { 
        String itemInfo = String.format("<html>&nbsp;&nbsp;&nbsp;&nbsp;• %s | SL: %d | Giá: $%.2f</html>", item.getProduct().getName(), item.getQuantity(), item.getProduct().getPrice());
        this.historyPanel.add(new JLabel(itemInfo));
    }
    historyPanel.add(new JLabel("<html><hr style='border: 0.5px solid #ccc;'/></html>"));
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
