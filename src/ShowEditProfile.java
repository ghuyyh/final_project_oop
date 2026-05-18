import javax.swing.*;
import java.awt.*;

public class ShowEditProfile extends JDialog {
    private JTextField usernameField;
    private JTextField fullNameField;
    private JTextField ageField;
    private JTextField addressField;
    private JComboBox<String> paymentComboBox; 
    private JTextField cardNumberField;        
    private JLabel cardNumberLabel;            
    private JButton saveButton;
    private JButton cancelButton;

    public ShowEditProfile(JFrame parent, Customer customer) {
        super(parent, "Edit Profile", true);
        
        setSize(400, 420);
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
        
        String[] methods = {"Cash", "Credit Card", "Debit Card"};
        paymentComboBox = new JComboBox<>(methods);
        paymentComboBox.setSelectedItem(customer.getPaymentMethod() != null ? customer.getPaymentMethod() : "Cash");
        gbc.gridx = 1;
        add(paymentComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        cardNumberLabel = new JLabel("Card Number:");
        add(cardNumberLabel, gbc);
        
        cardNumberField = new JTextField(customer.getCardNumber() != null ? customer.getCardNumber() : "", 20);
        gbc.gridx = 1;
        add(cardNumberField, gbc);

        Runnable toggleCardField = () -> {
            String selected = (String) paymentComboBox.getSelectedItem();
            boolean isCard = "Credit Card".equals(selected) || "Debit Card".equals(selected);
            
            cardNumberLabel.setVisible(isCard);
            cardNumberField.setVisible(isCard);
            
            revalidate();
            repaint();
        };
        
        paymentComboBox.addActionListener(e -> toggleCardField.run());
        toggleCardField.run(); 
        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");
        
        gbc.gridx = 0;
        gbc.gridy = 6;
        add(saveButton, gbc);
        
        gbc.gridx = 1;
        add(cancelButton, gbc);
        cancelButton.addActionListener(e -> dispose());
        saveButton.addActionListener(e -> {
            try {
                String ageText = ageField.getText().trim();
                int age = 0;
                if (!ageText.isEmpty()) {
                    age = Integer.parseInt(ageText);
                    if (age < 0) {
                        throw new NumberFormatException();
                    }
                }

                String selectedPayment = (String) paymentComboBox.getSelectedItem();
                boolean isCard = "Credit Card".equals(selectedPayment) || "Debit Card".equals(selectedPayment);
                String cardNum = cardNumberField.getText().trim();
                
                if (isCard && cardNum.isEmpty()) {
                    throw new IllegalArgumentException("Card number cannot be empty.");
                }

                customer.setUsername(usernameField.getText().trim());
                customer.setFullName(fullNameField.getText().trim());
                customer.setAge(age); 
                customer.setAddress(addressField.getText().trim());
                customer.setPaymentMethod(selectedPayment);
                
                if (isCard) {
                    customer.setCardNumber(cardNum);
                } else {
                    customer.setCardNumber(""); 
                }
                
                dispose(); 
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid positive number for Age.", "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}