import javax.swing.*;
import java.awt.*;
public class AdminView {
    private JPanel adminPanel = new JPanel();
   private JTextField idField = new JTextField(15);
    private JTextField nameField = new JTextField(15);
    private JTextField priceField = new JTextField(15);
    private JTextField stockField = new JTextField(15);

    public AdminView() {
        adminPanel.setLayout(new BorderLayout());
        adminPanel.setBorder(BorderFactory.createTitledBorder("Admin Panel"));
    }

    public JPanel getAdminPanel() {
        return adminPanel;
    }
}
