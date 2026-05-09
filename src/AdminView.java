import javax.swing.*;
import java.awt.*;
public class AdminView {
    private JPanel adminPanel = new JPanel();
   

    public AdminView() {
        adminPanel.setLayout(new BorderLayout());
        adminPanel.setBorder(BorderFactory.createTitledBorder("Admin Panel"));
    }

    public JPanel getAdminPanel() {
        return adminPanel;
    }
}
