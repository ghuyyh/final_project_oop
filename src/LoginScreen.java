//example of a simple login screen using Java Swing
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginScreen {

    public static void main(String[] args) {
        // 1. Tell Java to use the native OS look and feel (No 3rd party libraries!)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Could not set native look and feel.");
        }

        // 2. Create the main window (JFrame)
        JFrame frame = new JFrame("E-Hospital Shop - Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 250);
        
        // Use a grid layout: 4 rows, 1 column, with some spacing
        frame.setLayout(new GridLayout(4, 1, 10, 10)); 

        // 3. Create the components
        JLabel titleLabel = new JLabel("Welcome to E-Hospital", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

        // Panel for Username
        JPanel userPanel = new JPanel();
        userPanel.add(new JLabel("Username: "));
        JTextField userField = new JTextField(15);
        userPanel.add(userField);

        // Panel for Password
        JPanel passPanel = new JPanel();
        passPanel.add(new JLabel("Password: "));
        JPasswordField passField = new JPasswordField(15);
        passPanel.add(passField);

        // Panel for Buttons
        JPanel buttonPanel = new JPanel();
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        // 4. Add a simple action to the Login button (Simulation)
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText();
                // In a real app, you would check the StoreSystem here
                JOptionPane.showMessageDialog(frame, "Attempting to log in as: " + username);
            }
        });

        // 5. Add everything to the frame
        frame.add(titleLabel);
        frame.add(userPanel);
        frame.add(passPanel);
        frame.add(buttonPanel);

        // Center the window on the screen and show it
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}