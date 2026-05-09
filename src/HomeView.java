import java.awt.*;
import javax.swing.*;

public class HomeView {
    private JPanel homePanel = new JPanel();
    private JPanel hotProductsPanel = new JPanel();
    private Core core = Core.getInstance();
    private GUI_MainFrame mainFrame;

    public HomeView(GUI_MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        homePanel.setLayout(new BorderLayout());
        homePanel.setBorder(BorderFactory.createTitledBorder("Today Hot Sales"));
        hotProductsPanel.setLayout(new GridLayout(0,1,5,5));

        //scroll pane for hot products
        JScrollPane scrollPane = new JScrollPane(hotProductsPanel);
        homePanel.add(scrollPane, BorderLayout.CENTER);
        refreshHome();
    }
    public void refreshHome() {
        hotProductsPanel.removeAll();
        for (Product product : core.getHotProducts()) {
            JPanel productPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            productPanel.add(new JLabel(product.getName() + " - $" + String.format("%.2f", product.getPrice())));
            JButton addToCartBtn = new JButton("Add to Cart");

            addToCartBtn.addActionListener(e -> { 
                User loggedInUser = core.getLoggedInUser();
                if(loggedInUser instanceof Admin){
                    JOptionPane.showMessageDialog(null, "Admins cannot add products to cart. Please log in as a customer.");
                }
                else {
                    Cart targetCart;
                    if (loggedInUser instanceof Customer) {
                        targetCart = ((Customer) loggedInUser).getPersonalCart();
                    } else {
                        targetCart = core.getGuestCart();
                    }
                    targetCart.addItem(product, 1);
                    JOptionPane.showMessageDialog(null," Added" + product.getName() + " to cart!");
                    this.mainFrame.updateAccountButton();
                    this.mainFrame.updateCartButton();
                }
            });
            productPanel.add(addToCartBtn);
            hotProductsPanel.add(productPanel);
        }
        hotProductsPanel.revalidate();
        hotProductsPanel.repaint();
    }
    public JPanel getHomePanel() {
        return homePanel;
    }
}