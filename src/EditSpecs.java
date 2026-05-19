import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class EditSpecs extends JPanel {
    private final Map<String, String> specsCopy;
    private final Map<String, JTextField> fieldMap;

    public EditSpecs(Map<String, String> originalSpecs) {
        // Keep a reference to the map and track text fields
        this.specsCopy = originalSpecs;
        this.fieldMap = new LinkedHashMap<>();

        // Use GridBagLayout for a clean form look
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;
        // Generate a label and text field for each map entry
        for (Map.Entry<String, String> entry : specsCopy.entrySet()) {
            gbc.gridx = 0;
            gbc.gridy = row;
            gbc.weightx = 0.0;
            add(new JLabel(entry.getKey() + ":"), gbc);

            gbc.gridx = 1;
            gbc.weightx = 1.0;
            JTextField textField = new JTextField(entry.getValue(), 15);
            add(textField, gbc);

            // Store the field associated with the key
            fieldMap.put(entry.getKey(), textField);
            row++;
        }

        // Add a save button at the bottom
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;

        JButton saveButton = new JButton("Save Changes");
        saveButton.addActionListener(e -> saveAction());
        add(saveButton, gbc);
    }

    // Flush GUI field changes back into the original map object
    private void saveAction() {
        for (Map.Entry<String, JTextField> entry : fieldMap.entrySet()) {
            specsCopy.put(entry.getKey(), entry.getValue().getText());
        }
        JOptionPane.showMessageDialog(this, "Specifications saved successfully!");
    }

    // Simple test preview frame
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Map<String, String> sampleSpecs = new LinkedHashMap<>();
            sampleSpecs.put("Resolution", "1920x1080");
            sampleSpecs.put("Refresh Rate", "144Hz");
            sampleSpecs.put("Panel Type", "IPS");

            JFrame frame = new JFrame("Spec Editor Test");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new EditSpecs(sampleSpecs));
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
