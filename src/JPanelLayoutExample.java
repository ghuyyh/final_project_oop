import javax.swing.*;
import java.awt.*;

public class JPanelLayoutExample {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Could not set native look and feel.");
        }

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("JPanel Layout Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 300);

            // Panel with FlowLayout (default)
            JPanel flowPanel = new JPanel();
            flowPanel.setBorder(BorderFactory.createTitledBorder("FlowLayout"));
            flowPanel.add(new JButton("Button 1"));
            flowPanel.add(new JButton("Button 2"));
            flowPanel.add(new JButton("Button 3"));

            // Panel with BorderLayout
            JPanel borderPanel = new JPanel(new BorderLayout());
            borderPanel.setBorder(BorderFactory.createTitledBorder("BorderLayout"));
            borderPanel.add(new JButton("North"), BorderLayout.NORTH);
            borderPanel.add(new JButton("South"), BorderLayout.SOUTH);
            borderPanel.add(new JButton("East"), BorderLayout.EAST);
            borderPanel.add(new JButton("West"), BorderLayout.WEST);
            borderPanel.add(new JButton("Center"), BorderLayout.CENTER);

            JPanel gridPanel = new JPanel(new GridLayout(2, 2, 5, 5));
            gridPanel.setBorder(BorderFactory.createTitledBorder("GridLayout"));
            for (int i = 1; i <= 4; i++) {
                gridPanel.add(new JButton("B" + i));
            }

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(flowPanel);
            mainPanel.add(borderPanel);
            mainPanel.add(gridPanel);

            frame.add(new JScrollPane(mainPanel));
            frame.setVisible(true);
        });
    }
}
