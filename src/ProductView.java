// import javax.naming.NameParser;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.Map;

public class ProductView{
    private Product product;
    private GUI_MainFrame mainFrame;

    public ProductView(Product product, GUI_MainFrame mainFrame){
        this.product = product;
        this.mainFrame = mainFrame;
    }

    public void show(){
        JDialog dialog = new JDialog();
        dialog.setTitle("Product Details - Click the photo to zoom");
        dialog.setModal(true);
        dialog.setLayout(new BorderLayout(10,10));
        dialog.setMinimumSize(new Dimension(850, 500)); 

        ImageIcon icon = loadProductImageIcon();

        Image smallImg = icon.getImage().getScaledInstance(
                360,
                360,
                Image.SCALE_SMOOTH
        );

        JLabel imageLabel = new JLabel(new ImageIcon(smallImg));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        imageLabel.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {

            JDialog zoomDialog = new JDialog(dialog);
            zoomDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            zoomDialog.setSize(800, 800);
            Image bigImg = icon.getImage().getScaledInstance(750, 750,Image.SCALE_SMOOTH);
            JLabel bigLabel = new JLabel(new ImageIcon(bigImg));
            bigLabel.setHorizontalAlignment((SwingConstants.CENTER));
            zoomDialog.add(bigLabel);
            zoomDialog.setLocationRelativeTo(null);
            zoomDialog.setVisible(true);
        }
    });

        JPanel infoPanel = new JPanel(new GridLayout(0, 1, 5,5));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));

        JLabel nameKey = new JLabel("Name: ");
        JLabel nameVal = new JLabel(product.getName());
        nameVal.setFont(new Font("Segoe UI", Font.BOLD, 12));
        nameKey.setFont(new Font("Segor UI", Font.PLAIN, 12));
        namePanel.add(nameKey);
        namePanel.add(nameVal);
        infoPanel.add(namePanel);

        infoPanel.add(new JLabel("Price: $" + String.format("%,.2f", product.getPrice())));
        int stock = product.getStockQuantity();
        JLabel stockLabel = new JLabel("Stock: " + stock);
        stockLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        if (stock == 0){
            stockLabel.setForeground(new Color(220, 50, 50));
        } else if (stock <= 3){
            stockLabel.setForeground(new Color(220, 140, 0));
        } else {
            stockLabel.setForeground(new Color(0, 130, 60));
        }
        infoPanel.add(stockLabel);
        infoPanel.add(new JLabel("─────────────────────────────"));
        // infoPanel.add(new JLabel("Specification: "));

        JLabel nameSpecs = new JLabel("Specification: ");
        infoPanel.add(nameSpecs);
        nameSpecs.setFont(new Font("Segoe UI", Font.BOLD, 12));

        for (Map.Entry<String, String> entry : product.getSpecs().entrySet()) {
            JPanel specRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
            JLabel keyLabel = new JLabel("  " + entry.getKey() + ": ");
            JLabel valLabel = new JLabel(entry.getValue());
            keyLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
            valLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            specRow.add(keyLabel);
            specRow.add(valLabel);
            infoPanel.add(specRow);
        }
            
        JScrollPane scrollPane = new JScrollPane(infoPanel);
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        centerPanel.add(imageLabel);
        centerPanel.add(scrollPane);
        dialog.add(centerPanel, BorderLayout.CENTER);
        JButton addToCartBtn = new JButton("Add to Cart");

        addToCartBtn.addActionListener(e -> {
            User user = Core.getInstance().getLoggedInUser();
            if (user instanceof Admin) {
                JOptionPane.showMessageDialog(dialog, "Admins cannot add products to cart.");
                return;
            }
            if (product.getStockQuantity() <= 0) {
                    JOptionPane.showMessageDialog(null, "Sorry, this product is out of stock!");
                    return;
                }
            Cart cart = (user instanceof Customer)
                ? ((Customer) user).getPersonalCart()
                : Core.getInstance().getGuestCart();
            cart.addItem(product, 1);
            mainFrame.updateCartButton();
            mainFrame.refreshCart();
            JOptionPane.showMessageDialog(dialog, "Added \"" + product.getName() + "\" to cart!");
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(addToCartBtn);
        dialog.add(bottomPanel, BorderLayout.SOUTH);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }   

    private ImageIcon loadProductImageIcon() {
        java.net.URL imgURL = getClass().getResource("/res/product_images/" + product.getId() + ".png");
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        }

        java.net.URL defaultURL = getClass().getResource("/res/product_images/placeholder.png");
        if (defaultURL != null) {
            return new ImageIcon(defaultURL);
        }

        return new ImageIcon();
    }
}