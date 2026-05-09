import java.awt.*;
import javax.swing.*;

public class HomeView {
    private JPanel homePanel = new JPanel();
    private Core core = Core.getInstance();

    public HomeView() {
        //System.out.println("check: " + core.getHotProducts().size());

        homePanel.setLayout(new BorderLayout());
        homePanel.setBorder(BorderFactory.createTitledBorder("Today Hot Sales"));
        JPanel hotProductsPanel = new JPanel();
        hotProductsPanel.setLayout(new GridLayout(0,1,5,5));

        //scroll pane for hot products
        JScrollPane scrollPane = new JScrollPane(hotProductsPanel);
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
                }
            });
            productPanel.add(addToCartBtn);
            hotProductsPanel.add(productPanel);
        }
        homePanel.add(scrollPane, BorderLayout.CENTER);
    }
    public JPanel getHomePanel() {
        return homePanel;
    }
}