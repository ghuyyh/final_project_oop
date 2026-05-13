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
        dialog.setTitle("Product Details");
        dialog.setModal(true);
        dialog.setLayout(new BorderLayout(10,10));
        dialog.setMinimumSize(new Dimension(850, 500)); 

        ImageIcon icon = new ImageIcon(getClass().getResource("/res/product_images/" + product.getImageFileName()));
        System.out.println(icon.getIconWidth());

        Image smallImg = icon.getImage().getScaledInstance(
                200,
                200,
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

        infoPanel.add(new JLabel("Name: " + product.getName()));
        infoPanel.add(new JLabel("Price: $" + String.format("%,.2f", product.getPrice())));
        infoPanel.add(new JLabel("Stock: " + product.getStockQuantity()));
        infoPanel.add(new JLabel("─────────────────────────────"));
        infoPanel.add(new JLabel("Specification: "));

        for(Map.Entry<String, String> entry : product.getSpecs().entrySet()){
           infoPanel.add(new JLabel("  " + entry.getKey() + ": " + entry.getValue()));
        }
 
        JScrollPane scrollPane = new JScrollPane(infoPanel);
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        centerPanel.add(imageLabel);
        centerPanel.add(scrollPane);
        dialog.add(centerPanel, BorderLayout.CENTER);
        JButton addToCartBtn = new JButton("Add to Cart");

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(addToCartBtn);
        dialog.add(bottomPanel, BorderLayout.SOUTH);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);

        addToCartBtn.addActionListener(e -> {
            User user = Core.getInstance().getLoggedInUser();
            if (user instanceof Admin) {
                JOptionPane.showMessageDialog(dialog, "Admins cannot add products to cart.");
                return;
            }
            Cart cart = (user instanceof Customer)
                ? ((Customer) user).getPersonalCart()
                : Core.getInstance().getGuestCart();
            cart.addItem(product, 1);
            mainFrame.updateCartButton();
            JOptionPane.showMessageDialog(dialog, "Added \"" + product.getName() + "\" to cart!");
        });

    }   
}