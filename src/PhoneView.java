import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

public class PhoneView extends JPanel {
    private JPanel containerPanel = new JPanel();
    private GUI_MainFrame mainFrame;

    public PhoneView(GUI_MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Phones Category"));

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

        for (Product product : products) {
            JPanel productPanel = new JPanel(new BorderLayout(12, 10));
            productPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(210, 210, 210), 1),
                    BorderFactory.createEmptyBorder(8, 8, 8, 8)));
            productPanel.setBackground(new Color(255, 255, 255));
            productPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 130));

            JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
            leftPanel.setOpaque(false);

            try {
                java.net.URL imgURL = getClass().getResource("/res/product_images/" + product.getId() + ".png");
                if (imgURL == null) {
                    imgURL = getClass().getResource("/res/product_images/placeholder.png");
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
                specsPanel.add(new JLabel("No specs available"));
            }

            centerPanel.add(specsPanel);
            productPanel.add(centerPanel, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel(new GridBagLayout());
            buttonPanel.setOpaque(false);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new Insets(2, 2, 2, 2);

            JButton detailBtn = new JButton("View Details");
            detailBtn.addActionListener(e -> new ProductView(product, mainFrame).show());
            buttonPanel.add(detailBtn, gbc);

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
            gbc.gridy = 1;
            buttonPanel.add(addToCartBtn, gbc);
            productPanel.add(buttonPanel, BorderLayout.EAST);

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