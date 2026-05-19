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

    public HomeView(GUI_MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        getHomePanel().setLayout(new BorderLayout());

        JPanel contaninerPanel = new JPanel();
        contaninerPanel.setLayout(new BoxLayout(contaninerPanel, BoxLayout.Y_AXIS));

        hotProductsPanel.setLayout(new GridLayout(0, 1, 5, 5));

        hotProductsPanel.setBorder(BorderFactory.createTitledBorder("Today Hot Sales"));
        contaninerPanel.add(hotProductsPanel);

        allProductPanel.setLayout(new GridLayout(0, 1, 5, 5));
        allProductPanel.setBorder(BorderFactory.createTitledBorder("All Products"));
        contaninerPanel.add(allProductPanel);

        JScrollPane scrollPane = new JScrollPane(contaninerPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        getHomePanel().add(scrollPane, BorderLayout.CENTER);

        refreshHome();
    }

    private JPanel createProductPanel(Product product) {
        JPanel productPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        try {
            java.net.URL imgURL = getClass().getResource("/res/product_images/" + product.getId() + ".png");
            if (imgURL == null) {
                 imgURL = getClass().getResource("/res/product_images/" + "placeholder.png");
            }
            if (imgURL != null) {
                ImageIcon icon = new ImageIcon(imgURL);
                Image rounded = makeRoundedImage(icon.getImage(), 100, 100, 16);
                JLabel imgLabel = new JLabel(new ImageIcon(rounded));
                productPanel.add(imgLabel);
            }
        } catch (Exception e) {
            System.out.println("Error loading image for product: " + product.getName());
        }

        productPanel.add(new JLabel(product.getName() + " - $" + String.format("%.2f", product.getPrice())));

        JButton detailBtn = new JButton("View Details");
        detailBtn.addActionListener(e -> new ProductView(product, mainFrame).show());
        productPanel.add(detailBtn);

        JButton addToCartBtn = new JButton("Add to Cart");
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
        productPanel.add(addToCartBtn);
        return productPanel;
    }

    public void displayProducts(java.util.List<Product> products) {
        hotProductsPanel.setVisible(false);
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