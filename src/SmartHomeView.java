import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

public class SmartHomeView extends JPanel {
    private JPanel containerPanel = new JPanel();
    private GUI_MainFrame mainFrame;

    public SmartHomeView(GUI_MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Smart Home Category"));

        containerPanel.setLayout(new GridLayout(0, 2, 10, 10));
        containerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.add(containerPanel, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(wrapperPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void displayProducts(java.util.List<Product> products) {
        containerPanel.removeAll();
        Color itemBg = new Color(230, 230, 230);

        for (Product product : products) {
            JPanel productPanel = new JPanel(new GridLayout(1, 3, 10, 10));
            productPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(210, 210, 210), 1),
                    BorderFactory.createEmptyBorder(8, 8, 8, 8)));
            productPanel.setBackground(itemBg);

            try {
                java.net.URL imgURL = getClass().getResource("/res/product_images/" + product.getId() + ".png");
                if (imgURL == null) {
                    imgURL = getClass().getResource("/res/product_images/placeholder.png");
                }
                if (imgURL != null) {
                    ImageIcon icon = new ImageIcon(imgURL);
                    Image rounded = makeRoundedImage(icon.getImage(), 100, 100, 16);
                    JLabel imgLabel = new JLabel(new ImageIcon(rounded));
                    productPanel.add(imgLabel);
                } else {
                    productPanel.add(new JLabel());
                }
            } catch (Exception e) {
                productPanel.add(new JLabel());
            }

            JPanel infoPanel = new JPanel(new GridLayout(2, 1, 5, 5));
            infoPanel.setOpaque(false);
            infoPanel.add(new JLabel(product.getName()));
            infoPanel.add(new JLabel("$" + String.format("%.2f", product.getPrice())));
            productPanel.add(infoPanel);

            JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 5, 5));
            buttonPanel.setOpaque(false);

            JButton detailBtn = new JButton("View Details");
            detailBtn.addActionListener(e -> new ProductView(product, mainFrame).show());
            buttonPanel.add(detailBtn);

            JButton addToCartBtn = new JButton("Add to Cart");
            addToCartBtn.addActionListener(e -> {
                User loggedInUser = mainFrame.getCore().getLoggedInUser();
                if (loggedInUser instanceof Admin) {
                    JOptionPane.showMessageDialog(null, "Admins cannot add products to cart. Please log in as a customer.");
                } else {
                    Cart targetCart = (loggedInUser instanceof Customer) 
                            ? ((Customer) loggedInUser).getPersonalCart() 
                            : mainFrame.getCore().getGuestCart();
                    targetCart.addItem(product, 1);
                    mainFrame.refreshCart();
                    mainFrame.updateCartButton();
                    JOptionPane.showMessageDialog(null, "Added " + product.getName() + " to cart!");
                }
            });
            buttonPanel.add(addToCartBtn);
            productPanel.add(buttonPanel);

            containerPanel.add(productPanel);
        }
        containerPanel.revalidate();
        containerPanel.repaint();
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
}