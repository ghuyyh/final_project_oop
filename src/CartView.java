import javax.swing.*;
import java.awt.*;

public class CartView {
    private JPanel cartPanel = new JPanel();
    private Core core = Core.getInstance();

    public CartView() {
        getCartPanel().setLayout(new BorderLayout());
        getCartPanel().setBorder(BorderFactory.createTitledBorder("Cart"));
        refreshCart();
    }

    public void refreshCart() {
    }

    public JPanel getCartPanel() {
        return cartPanel;
    }

}
