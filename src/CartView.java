import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class CartView {
    private JPanel cartPanel = new JPanel();
    private Core core = Core.getInstance();
    private JPanel itemPanel = new JPanel();
    private JLabel totalLabel = new JLabel("Total: $0.00");
    private GUI_MainFrame mainFrame;

    public CartView(GUI_MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        getCartPanel().setLayout(new BorderLayout());
        getCartPanel().setBorder(BorderFactory.createTitledBorder(" ShoppingCart"));

        itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(itemPanel);
        cartPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton refreshBtn = new JButton("Refresh Cart");
        JButton checkoutBtn = new JButton("Buy Now");

        refreshBtn.addActionListener(e -> refreshCart());

        checkoutBtn.addActionListener(e -> {
            User user = getCore().getLoggedInUser();
            if (user == null) {
                JOptionPane.showMessageDialog(cartPanel, "Please login as a Customer to buy.");
            } else if (user instanceof Admin) {
                JOptionPane.showMessageDialog(cartPanel, "Admins cannot buy. Please login as a Customer.");
            } else if (user instanceof Customer) {
                Customer customer = (Customer) user;
                if (customer.getPersonalCart().getItems().isEmpty()) {
                    refreshCart();
                    JOptionPane.showMessageDialog(cartPanel, "Your cart is empty");
                } else {
                    customer.checkout();
                    JOptionPane.showMessageDialog(cartPanel, "Checkout successful! Thank you for your purchase.");
                    refreshCart();
                    getMainFrame().updateCartButton();
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
        User loggedInUser = core.getLoggedInUser();

        if (loggedInUser instanceof Admin) {
            itemPanel.add(new JLabel("Admins do not use shopping cart."));
            totalLabel.setText("Total: $0.00");
        } else if (loggedInUser instanceof Customer) {
            Cart targetCart = ((Customer) loggedInUser).getPersonalCart();
            displayCartItems(targetCart);
        } else {
            Cart targetCart = core.getGuestCart();
            displayCartItems(targetCart);
        }
        itemPanel.revalidate();
        itemPanel.repaint();
    }

    private void displayCartItems(Cart targetCart) {
        if (targetCart.getItems().isEmpty()) {
            itemPanel.add(new JLabel("Your cart is empty."));
            totalLabel.setText("Total: $0.00");
        } else {
            for (CartItem item : targetCart.getItems()) {
                JPanel singleItemPanel = new JPanel(new BorderLayout(10, 0));
                singleItemPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                singleItemPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));
                singleItemPanel.setBackground(new Color(230, 230, 230));

                JLabel imgLabel = buildThumbnail(item.getProduct());
                singleItemPanel.add(imgLabel, BorderLayout.WEST);

                String itemInfo = String.format(
                        "<html><b>%s</b><br/>Price: $%.2f &nbsp;&nbsp; Quantity: %d</html>",
                        item.getProduct().getName(),
                        item.getProduct().getPrice(),
                        item.getQuantity());
                JLabel itemLabel = new JLabel(itemInfo);
                singleItemPanel.add(itemLabel, BorderLayout.CENTER);

                JPanel btnPanel = new JPanel(new GridLayout(2, 1, 4, 4));
                btnPanel.setOpaque(false);

                JButton detailBtn = new JButton("View Details");
                detailBtn.setFont(new Font("SansSerif", Font.PLAIN, 11));
                detailBtn.addActionListener(e ->
                        new ProductView(item.getProduct(), getMainFrame()).show());

                JButton removeBtn = new JButton("Remove");
                removeBtn.setFont(new Font("SansSerif", Font.PLAIN, 11));
                removeBtn.addActionListener(e -> {
                    targetCart.removeItem(item.getProduct());
                    refreshCart();
                    getMainFrame().updateCartButton();
                });

                btnPanel.add(detailBtn);
                btnPanel.add(removeBtn);
                singleItemPanel.add(btnPanel, BorderLayout.EAST);

                itemPanel.add(singleItemPanel);
                itemPanel.add(Box.createVerticalStrut(6));
            }
            totalLabel.setText(String.format("Total: $%.2f", targetCart.calculateTotal()));
        }
    }

    private JLabel buildThumbnail(Product product) {
        try {
            java.net.URL imgURL = getClass().getResource("/res/" + product.getImageFileName());
            if (imgURL != null) {
                ImageIcon icon = new ImageIcon(imgURL);
                Image scaled = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
                JLabel lbl = new JLabel(new ImageIcon(scaled));
                lbl.setPreferredSize(new Dimension(60, 60));
                return lbl;
            }
        } catch (Exception ignored) {}

        BufferedImage img = new BufferedImage(60, 60, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int hash = Math.abs(product.getName().hashCode());
        Color c1 = Color.getHSBColor((hash % 360) / 360f, 0.55f, 0.45f);
        Color c2 = Color.getHSBColor(((hash + 120) % 360) / 360f, 0.60f, 0.30f);
        g.setPaint(new GradientPaint(0, 0, c1, 60, 60, c2));
        g.fillRoundRect(0, 0, 60, 60, 12, 12);
        g.setColor(new Color(255, 255, 255, 200));
        g.setFont(new Font("Segoe UI", Font.BOLD, 10));
        String abbr = product.getName().length() >= 2
                ? product.getName().substring(0, 2).toUpperCase()
                : product.getName().toUpperCase();
        FontMetrics fm = g.getFontMetrics();
        g.drawString(abbr, (60 - fm.stringWidth(abbr)) / 2, 35);
        g.dispose();

        JLabel lbl = new JLabel(new ImageIcon(img));
        lbl.setPreferredSize(new Dimension(60, 60));
        return lbl;
    }

    public Core getCore() {
        return core;
    }

    public JPanel getCartPanel() {
        return cartPanel;
    }

    public GUI_MainFrame getMainFrame() {
        return mainFrame;
    }
}