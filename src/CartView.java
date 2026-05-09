import javax.swing.*;
import java.awt.*;

public class CartView {
    private JPanel cartPanel = new JPanel();
    private Core core = Core.getInstance();
    private JPanel itemPanel = new JPanel();
    private JLabel totalLabel = new JLabel("Total: $0.00");
    

    public CartView() {
        getCartPanel().setLayout(new BorderLayout());
        getCartPanel().setBorder(BorderFactory.createTitledBorder(" ShoppingCart"));
        
        itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(itemPanel);
        cartPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton refreshBtn = new JButton( "Refresh Cart" );
        JButton checkoutBtn = new JButton( "Checkout" );

        refreshBtn.addActionListener(e -> refreshCart());
        checkoutBtn.addActionListener(e -> {
            User user = core.getLoggedInUser();
            if (user == null) {
                JOptionPane.showMessageDialog(cartPanel, "Please login as a Customer to checkout.");
            }   else if( user instanceof Admin ) {
                JOptionPane.showMessageDialog(cartPanel, "Admins cannot checkout. Please login as a Customer.");
            }   else if (user instanceof Customer) {
                Customer customer = (Customer) user;
                if (customer.getPersonalCart().getItems().isEmpty()) {
                    refreshCart();
                    JOptionPane.showMessageDialog(cartPanel, "Your cart is empty");
                } else {
                    customer.checkout();
                    JOptionPane.showMessageDialog(cartPanel, "Checkout successful! Thank you for your purchase.");
                    refreshCart();
                }
            }
        });
        bottomPanel.add(totalLabel, BorderLayout.WEST);
        bottomPanel.add(refreshBtn, BorderLayout.CENTER);
        bottomPanel.add(checkoutBtn, BorderLayout.EAST);
        cartPanel.add(bottomPanel, BorderLayout.SOUTH);

        refreshCart();
    }

    public void refreshCart() {
        itemPanel.removeAll();
        Cart targetCart;
        User loggedInUser = core.getLoggedInUser();

        if (loggedInUser instanceof Admin) {
            itemPanel.add(new JLabel("Admins do not use shopping cart."));
            totalLabel.setText("Total: $0.00");
        } else {
            if(loggedInUser instanceof Customer) {
                targetCart = ((Customer) loggedInUser).getPersonalCart();
            } else {
                targetCart = core.getGuestCart();
            }
            if(targetCart.getItems().isEmpty()) {
                itemPanel.add(new JLabel("Your cart is empty."));
                totalLabel.setText("Total: $0.00");
            } else {
                for (CartItem item : targetCart.getItems()) {
                    JPanel singleItemPanel = new JPanel(new BorderLayout());
                    singleItemPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
                    singleItemPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

                    String itemInfo = String.format("<html><b>%s</b><br/>Price: $%.2f<br/>Quantity: %d</html>",
                            item.getProduct().getName(), item.getProduct().getPrice(), item.getQuantity());
                    JLabel itemLabel = new JLabel(itemInfo);
                    JButton removeBtn = new JButton("Remove");
                    removeBtn.addActionListener(e -> {
                        targetCart.removeItem(item.getProduct());
                        refreshCart();
                    });
                    singleItemPanel.add(itemLabel, BorderLayout.CENTER);
                    singleItemPanel.add(removeBtn, BorderLayout.EAST);
                    itemPanel.add(singleItemPanel);
                    itemPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
                }
                totalLabel.setText( String.format("Total: $%.2f", targetCart.calculateTotal()) );
            }
        }
        itemPanel.revalidate();
        itemPanel.repaint();
    }
     
    public JPanel getCartPanel() {
        return cartPanel;
    }
}

