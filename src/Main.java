
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Main {
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
        // JButton startBtn = new JButton("Start");
        mainScr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // mainScr.setResizable(false);
        mainScr.setPreferredSize(new Dimension((int) (0.8 * scrSize.width), (int) (0.8 * scrSize.height)));
        mainScr.setLayout(new BorderLayout());
        mainScr.add(mainPanel);
        // mainPanel.add(startBtn);
        mainScr.pack();
        mainScr.setLocationRelativeTo(null);
        mainScr.setMinimumSize(new Dimension((int) (0.8 * scrSize.width), (int) (0.8 * scrSize.height)));
        mainScr.setVisible(true);
        mainScr.setLayout(new BorderLayout());
        
        // mainScr.setExtendedState(JFrame.MAXIMIZED_BOTH);

    }
}
