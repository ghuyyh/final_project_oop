import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class HomeView {
    private JPanel homePanel = new JPanel();
    private JPanel hotProductsPanel = new JPanel();
    private JPanel allProductPanel = new JPanel();
    private Core core = Core.getInstance();
    GUI_MainFrame mainFrame;
    final Color ITEM_BG = new Color(255, 255, 255);

    public HomeView(GUI_MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        getHomePanel().setLayout(new BorderLayout());

        JPanel contaninerPanel = new JPanel();
        contaninerPanel.setLayout(new BoxLayout(contaninerPanel, BoxLayout.Y_AXIS));

        hotProductsPanel.setLayout(new GridLayout(0, 1, 5, 5));

        hotProductsPanel.setBorder(BorderFactory.createTitledBorder("Today Hot Sales"));
        contaninerPanel.add(hotProductsPanel);

        allProductPanel.setLayout(new GridLayout(0, 2, 10, 10));
        allProductPanel.setBorder(BorderFactory.createTitledBorder("All Products"));
        contaninerPanel.add(allProductPanel);

        JScrollPane scrollPane = new JScrollPane(contaninerPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        getHomePanel().add(scrollPane, BorderLayout.CENTER);

        refreshHome();
    }

    private JPanel createProductPanel(Product product) {
        JPanel productPanel = new JPanel(new BorderLayout(12, 10));
        productPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 210, 210), 1),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)));
        productPanel.setBackground(ITEM_BG);
        productPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 130));

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        leftPanel.setOpaque(false);
        try {
            java.net.URL imgURL = getClass().getResource("/res/product_images/" + product.getId() + ".png");
            if (imgURL == null) {
                imgURL = getClass().getResource("/res/product_images/" + "placeholder.png");
            }
            if (imgURL != null) {
                ImageIcon icon = new ImageIcon(imgURL);
                Image rounded = makeRoundedImage(icon.getImage(), 100, 100, 16);
                JLabel imgLabel = new JLabel(new ImageIcon(rounded));
                leftPanel.add(imgLabel);
            } else {
                leftPanel.add(new JLabel());
            }
        } catch (Exception e) {
            System.out.println("Error loading image for product: " + product.getName());
            leftPanel.add(new JLabel());
        }
        productPanel.add(leftPanel, BorderLayout.WEST);

        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        JPanel infoPanel = new JPanel();
        infoPanel.setOpaque(false);
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(new JLabel("<html><b>Name:</b> " + product.getName() + "</html>"));

        int stock = product.getStockQuantity();
        String stockColor = stock == 0 ? "#d9534f" : (stock <= 3 ? "#d18a00" : "#0a803f");
        infoPanel.add(new JLabel("<html><b>Stock quantity:</b> <span style='color:" + stockColor + ";'>" + stock + "</span></html>"));

        infoPanel.add(new JLabel("<html><b>Price:</b> $" + String.format("%.2f", product.getPrice()) + "</html>"));
        centerPanel.add(infoPanel);

        centerPanel.add(Box.createVerticalStrut(8));

        JPanel specsPanel = new JPanel();
        specsPanel.setOpaque(false);
        specsPanel.setLayout(new BoxLayout(specsPanel, BoxLayout.Y_AXIS));
        specsPanel.add(new JLabel("<html><b>Specs</b></html>"));

        int specCount = 0;
        for (java.util.Map.Entry<String, String> entry : product.getSpecs().entrySet()) {
            if (specCount >= 3) {
                break;
            }
            specsPanel.add(new JLabel("<html><b>" + entry.getKey() + ":</b> " + entry.getValue() + "</html>"));
            specCount++;
        }

        if (specCount == 0) {
            JLabel noSpecsLabel = new JLabel("No specs available");
            noSpecsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            specsPanel.add(noSpecsLabel);
        }

        centerPanel.add(specsPanel);
        productPanel.add(centerPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(2, 2, 2, 2); // Adds a small gap between buttons

        JButton detailBtn = new JButton("View Details");
        // detailBtn.setPreferredSize(new Dimension(120, 30)); // You can now set individual sizes!
        detailBtn.addActionListener(e -> new ProductView(product, mainFrame).show());
        buttonPanel.add(detailBtn, gbc);
        
        JButton addToCartBtn = new JButton("Add to Cart");
        // addToCartBtn.setPreferredSize(new Dimension(120, 30)); // You can now set individual sizes!
        addToCartBtn.addActionListener(e -> {
            User loggedInUser = getCore().getLoggedInUser();
            if (loggedInUser instanceof Admin) {
                JOptionPane.showMessageDialog(null,
                        "Admins cannot add products to cart. Please log in as a customer.");
            } else {
                Cart targetCart;
                if (loggedInUser instanceof Customer) {
                    targetCart = ((Customer) loggedInUser).getPersonalCart();
                } else {
                    targetCart = getCore().getGuestCart();
                }
                targetCart.addItem(product, 1);
                getMainFrame().refreshCart();
                getMainFrame().updateCartButton();
                JOptionPane.showMessageDialog(null, "Added " + product.getName() + " to cart!");
            }
        });

        gbc.gridy = 1; // Move to the next row for the second button
        buttonPanel.add(addToCartBtn, gbc);
        productPanel.add(buttonPanel, BorderLayout.EAST);
        return productPanel;
    }

    public void displayProducts(java.util.List<Product> products) {
        hotProductsPanel.setVisible(false);
        allProductPanel.setLayout(new GridLayout(0, 2, 10, 10));
        allProductPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        allProductPanel.removeAll();
        for (Product product : products) {
            allProductPanel.add(createProductPanel(product));

        }
        allProductPanel.revalidate();
        allProductPanel.repaint();
    }

    public void refreshHome() {
        hotProductsPanel.setVisible(true);
        hotProductsPanel.setLayout(new GridLayout(0, 1, 5, 5));
        allProductPanel.setLayout(new GridLayout(0, 2, 10, 10));
        allProductPanel.setBorder(BorderFactory.createTitledBorder("All Products"));

        hotProductsPanel.removeAll();
        for (Product product : getCore().getInventory()) {
            if (product.getHotSale()) {
                hotProductsPanel.add(createProductPanel(product));
            }
        }

        allProductPanel.removeAll();
        for (Product product : getCore().getInventory()) {
            allProductPanel.add(createProductPanel(product));
        }

        hotProductsPanel.revalidate();
        hotProductsPanel.repaint();
        allProductPanel.revalidate();
        allProductPanel.repaint();
    }

    private BufferedImage makeRoundedImage(Image src, int w, int h, int arc) {
        BufferedImage result = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = result.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setClip(new RoundRectangle2D.Float(0, 0, w, h, arc, arc));
        g2.drawImage(src, 0, 0, w, h, null);
        g2.dispose();
        return result;
    }

    public JPanel getHomePanel() {
        return homePanel;
    }

    public GUI_MainFrame getMainFrame() {
        return mainFrame;
    }

    public Core getCore() {
        return core;
    }
}