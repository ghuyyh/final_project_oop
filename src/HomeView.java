import java.awt.*;
import javax.swing.*;

public class HomeView {
    private JPanel homePanel = new JPanel();
    private JPanel hotProductsPanel = new JPanel();
    private Core core = Core.getInstance();
    GUI_MainFrame mainFrame;
    

    public HomeView(GUI_MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        getHomePanel().setLayout(new BorderLayout());
        getHomePanel().setBorder(BorderFactory.createTitledBorder("Today Hot Sales"));
        hotProductsPanel.setLayout(new GridLayout(0,1,5,5));

        //scroll pane for hot products
        JScrollPane scrollPane = new JScrollPane(hotProductsPanel);
        getHomePanel().add(scrollPane, BorderLayout.CENTER);
        refreshHome();  
    }
    public void displayProducts(java.util.List<Product> products) {
        hotProductsPanel.removeAll();
        for (Product product : products) {
            JPanel productPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            try{
                java.net.URL imgURL = getClass().getResource("/res/product_images/" + product.getImageFileName());
                if(imgURL != null){
                    ImageIcon icon = new ImageIcon(imgURL);
                    Image img = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                    JLabel imgLabel = new JLabel(new ImageIcon(img));
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
                if(loggedInUser instanceof Admin){
                    JOptionPane.showMessageDialog(null, "Admins cannot add products to cart. Please log in as a customer.");
                }
                else {
                    Cart targetCart;
                    if (loggedInUser instanceof Customer) {
                        targetCart = ((Customer) loggedInUser).getPersonalCart();
                    } else {
                        targetCart = getCore().getGuestCart();
                    }
                    targetCart.addItem(product, 1);
                    getMainFrame().refreshCart();
                    getMainFrame().updateCartButton();
                    JOptionPane.showMessageDialog(null,"Added " + product.getName() + " to cart!");
                    
                }
            });
            productPanel.add(addToCartBtn);
            hotProductsPanel.add(productPanel);
        }
        hotProductsPanel.revalidate();
        hotProductsPanel.repaint();
    }
    public void refreshHome() {
        displayProducts(getCore().getInventory());
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