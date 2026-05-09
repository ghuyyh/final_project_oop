import javax.swing.*;
import java.awt.*;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;

public class LoginView {
    private JPanel loginPanel = new JPanel();
    private JTextField usernameField = new JTextField(25);
    private JPasswordField passwordField = new JPasswordField(25);
    private JButton loginBtn = new JButton("Login");
    private JButton regBtn = new JButton("Register");

    public LoginView() {
        if (Core.getInstance().getLoggedInUser() != null) {
            loginBtn.setText("Logout");
        }
        getLoginPanel().setBorder(BorderFactory.createTitledBorder("Account"));
        getLoginPanel().setPreferredSize(new Dimension(400, 150));
        getLoginPanel().setLayout(new GridLayout(3, 1, 10, 10));
        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        userPanel.add(new JLabel("Username:"));
        userPanel.add(getUsernameField());
        getLoginPanel().add(userPanel);
        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        passwordPanel.add(new JLabel("Password:"));
        passwordPanel.add(getPasswordField());
        getLoginPanel().add(passwordPanel);
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        btnPanel.add(getLoginBtn());
        btnPanel.add(getRegBtn());
        getLoginPanel().add(btnPanel);

        loginBtn.addActionListener(e -> {
            String username = getUsernameField().getText();
            String password = new String(getPasswordField().getPassword());
            User user = Core.getInstance().authenticate(username, password);
            if (user != null) {
                JOptionPane.showMessageDialog(null, "Login successful! Welcome, " + user.getUsername() + "!");
                getLoginBtn().setText("Logout");
                if (user instanceof Admin) {
                    Core.getInstance().setLoggedInUser(user);
                    Core.getInstance().getGuestCart().clearCart();
                    Core.getInstance().setGuestCart(new Cart());
                    new GUI_MainFrame();
                } else {
                    Core.getInstance().setLoggedInUser(user);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Invalid username or password.");
            }
        });
    }

    public JPanel getLoginPanel() {
        return loginPanel;
    }

    public JTextField getUsernameField() {
        return usernameField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JButton getLoginBtn() {
        return loginBtn;
    }

    public JButton getRegBtn() {
        return regBtn;
    }

}
