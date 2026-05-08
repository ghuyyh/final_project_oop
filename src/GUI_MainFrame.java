import java.awt.*;
import javax.swing.*;

public class GUI_MainFrame {
    public static void main(String[] args) {
        // Get the screen size
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension scrSize = toolkit.getScreenSize();

        // flat look, donot remove
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Could not set native look and feel.");
        }

        System.out.println("entry point");

        JFrame mainScr = new JFrame("Ex Store");
        ImageIcon icon = new ImageIcon(Main.class.getResource("res/icon.png"));
        mainScr.setIconImage(icon.getImage());
        JPanel mainPanel = new JPanel();
        mainScr.setLayout(new BorderLayout());

        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(Color.DARK_GRAY);
        bar.setPreferredSize(new Dimension(scrSize.width, 35));

        // Left Side
        JPanel category = new JPanel(new FlowLayout(FlowLayout.LEFT));
        category.setOpaque(false);
        JButton homeBtn = new JButton("Home");

        category.add(homeBtn);

        // Right side: cart and account
        JPanel rightSide = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightSide.setOpaque(false);
        JButton cartBtn = new JButton("Cart (0)");
        JButton accountBtn = new JButton("Account");

        rightSide.add(cartBtn);
        rightSide.add(accountBtn);

        bar.add(category, BorderLayout.WEST);
        bar.add(rightSide, BorderLayout.EAST);
        mainScr.add(bar, BorderLayout.NORTH);

        // JButton startBtn = new JButton("Start");
        mainScr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // mainScr.setResizable(false);
        mainScr.setPreferredSize(new Dimension((int) (0.8 * scrSize.width), (int) (0.85 * scrSize.height)));
        mainScr.add(mainPanel);
        // mainPanel.add(startBtn);
        mainScr.pack();
        mainScr.setLocationRelativeTo(null);
        mainScr.setMinimumSize(new Dimension((int) (0.8 * scrSize.width), (int) (0.85 * scrSize.height)));
        mainScr.setVisible(true);

        // mainScr.setExtendedState(JFrame.MAXIMIZED_BOTH);

    }
}