import javax.swing.*;
import java.awt.*;

public class ShowEditProfile extends JDialog {
    private JTextField usernameField;
    private JTextField fullNameField;
    private JTextField ageField;
    private JTextField addressField;
    private JTextField paymentField;
    private JButton saveButton;
    private JButton cancelButton;
    private Customer customer;
   
        public ShowEditProfile(JFrame parent, Customer customer) {
        super(parent, "Edit Profile", true); 
        setSize(400, 380);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Username:"), gbc);

        usernameField = new JTextField(customer.getUsername(), 20);
        gbc.gridx = 1;
        add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Full Name:"), gbc);

        fullNameField = new JTextField(customer.getFullName(), 20);
        gbc.gridx = 1;
        add(fullNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Age:"), gbc);

        ageField = new JTextField(customer.getAge() > 0 ? String.valueOf(customer.getAge()) : "", 20);
        gbc.gridx = 1;
        add(ageField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Address:"), gbc);

        addressField = new JTextField(customer.getAddress(), 20);
        gbc.gridx = 1;
        add(addressField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Payment Method:"), gbc);

        paymentField = new JTextField(customer.getPaymentMethod(), 20);
        gbc.gridx = 1;
        add(paymentField, gbc);
        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        saveButton.addActionListener(e -> {
            try {
                customer.setUsername(usernameField.getText().trim());
                customer.setFullName(fullNameField.getText().trim());
                customer.setAddress(addressField.getText().trim());
                customer.setPaymentMethod(paymentField.getText().trim());

                String ageText = ageField.getText().trim();
                if (!ageText.isEmpty()) {
                    customer.setAge(Integer.parseInt(ageText));
                } else {
                    customer.setAge(0);
                }
                javax.swing.JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                dispose(); 
            } catch (NumberFormatException ex) {
            // Simple alert if age format is invalid
            javax.swing.JOptionPane.showMessageDialog(this, "Please enter a valid number for Age.", "Input Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2; 
        add(buttonPanel, gbc);
    }
}
