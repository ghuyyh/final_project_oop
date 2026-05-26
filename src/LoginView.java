import javax.swing.*;
import java.awt.*;


public class LoginView {
    private JPanel loginPanel = new JPanel();
    private JTextField usernameField = new JTextField(25);
    private JPasswordField passwordField = new JPasswordField(25);
    private JButton loginBtn = new JButton("Login");
    private JButton regBtn = new JButton("Register");
    private JCheckBox showPasswordBox = new JCheckBox("Show");
    private GUI_MainFrame mainFrame;

    public LoginView(GUI_MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        if (Core.getInstance().getLoggedInUser() == null) {

            getLoginPanel().setBorder(BorderFactory.createTitledBorder("Account"));
            getLoginPanel().setPreferredSize(new Dimension(550,150));
            getLoginPanel().setLayout(new GridLayout(3, 1, 10, 10));

            JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
            JLabel userLabel = new JLabel("Username: ");
            userPanel.add(userLabel);
            userPanel.add(getUsernameField());
            getLoginPanel().add(userPanel);

            JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
            JLabel passwordLabel = new JLabel("Password:  ");
            passwordPanel.add(passwordLabel);
            passwordPanel.add(getPasswordField());
            char showPasswordChar = getPasswordField().getEchoChar();
            showPasswordBox.addItemListener(e -> {
        if (showPasswordBox.isSelected()) {
        getPasswordField().setEchoChar((char) 0); 
        } else {
        getPasswordField().setEchoChar(showPasswordChar); 
    }
});
passwordPanel.add(showPasswordBox); 
getLoginPanel().add(passwordPanel);

loginPanel.add(passwordPanel);
            JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
            btnPanel.add(getLoginBtn());
            btnPanel.add(getRegBtn());
            getLoginPanel().add(btnPanel);

            loginBtn.addActionListener(e -> {
                if (Core.getInstance().getLoggedInUser() != null) {
                    Core.getInstance().setLoggedInUser(null);
                    loginBtn.setText("Login");
                    usernameField.setText("");
                    passwordField.setText("");
                    JOptionPane.showMessageDialog(null, "Logged out successful.");

                    this.mainFrame.showHome();
                } else {
                    String username = getUsernameField().getText();
                    String password = new String(getPasswordField().getPassword());
                    User user = Core.getInstance().authenticate(username, password);
                    if (user != null) {
                        JOptionPane.showMessageDialog(null, "Login successful! Welcome, " + user.getUsername() + "!");
                        Core.getInstance().setLoggedInUser(user);

                        if (user instanceof Admin) {
                            Core.getInstance().setGuestCart(new Cart());
                            this.mainFrame.showAdmin();
                        } else {
                            Customer customer = (Customer) user;
                            Cart guestCart = Core.getInstance().getGuestCart();
                            for (CartItem item : guestCart.getItems()) {
                                customer.getPersonalCart().addItem(item.getProduct(), item.getQuantity());
                            }
                            guestCart.clearCart();
                            this.mainFrame.updateCartButton();
                            this.mainFrame.showHome();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid username or password.");
                    }
                }
            });
            regBtn.addActionListener(e -> {
                JTextField regUsernameField = new JTextField(20);
                JPasswordField regPasswordField = new JPasswordField(20);
                JPasswordField regConfirmPasswordField = new JPasswordField(20);

                JPanel regPanel = new JPanel(new GridLayout(3, 2, 10, 10));
                regPanel.add(new JLabel("Username:"));
                regPanel.add(regUsernameField);
                regPanel.add(new JLabel("Password:"));
                regPanel.add(regPasswordField);
                regPanel.add(new JLabel("Confirm Password:"));
                regPanel.add(regConfirmPasswordField);

                int result = JOptionPane.showConfirmDialog(null, regPanel, "Register New Account",
                        JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    String u = regUsernameField.getText().trim();
                    String p = new String(regPasswordField.getPassword());
                    String cp = new String(regConfirmPasswordField.getPassword());
                    if (u.isEmpty() || p.isEmpty() || cp.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "All fields are required.");
                    } else if (!p.equals(cp)) {
                        JOptionPane.showMessageDialog(null, "Passwords do not match.");
                    } else {
                        boolean success = Core.getInstance().registerCustomer(u, p);
                        if (success) {
                            JOptionPane.showMessageDialog(null, "Registration successful! You can now log in.");
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "Username already exists. Please choose a different one.");
                        }
                    }
                }
            });
        } else {
            if (Core.getInstance().getLoggedInUser() instanceof Admin) {
                getMainFrame().showAdmin();
            } else {
                getMainFrame().showCustomer();
            }
        }
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

    public GUI_MainFrame getMainFrame() {
        return mainFrame;
    }
}
