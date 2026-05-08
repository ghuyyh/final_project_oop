import java.awt.*;
import javax.swing.*;

public class GUI_MainFrame {
    private CardLayout layout = new CardLayout();
    private Core core = new Core();
    private JPanel mainPanel = new JPanel(layout);

    public GUI_MainFrame() {
        // Get the screen size
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension scrSize = toolkit.getScreenSize();

        // flat look, donot remove
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Could not set native look and feel.");
        }

        // check
        System.out.println("entry point");

        // Main screen

        JFrame mainScr = new JFrame("Store");
        ImageIcon logo = new ImageIcon(Main.class.getResource("res/logo.png"));
        mainScr.setIconImage(logo.getImage());

        mainScr.setLayout(new BorderLayout());

        // top bar
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(new Color(255, 255, 255));
        bar.setPreferredSize(new Dimension(scrSize.width, 35));

        // Left Side category and home
        JPanel category = new JPanel(new FlowLayout(FlowLayout.LEFT));
        category.setOpaque(false);
        JButton homeBtn = new JButton("Home");
        JButton categorySelect = new JButton("Categories");
        JPopupMenu categoryMenu = new JPopupMenu();
        categoryMenu.add(new JMenuItem("Phones"));
        categoryMenu.add(new JMenuItem("Smart Home"));
        categoryMenu.add(new JMenuItem("Others"));
        categorySelect.addActionListener(e -> {
            categoryMenu.show(categorySelect, 0, 35);
        });
        category.add(homeBtn);
        category.add(categorySelect);
        homeBtn.addActionListener(e -> {
            getLayout().show(getMainPanel(), "home");
        });

        // Right side: search, cart and account
        JPanel rightSide = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightSide.setOpaque(false);
        // JTextField searchField = new JTextField(10);
        JButton searchBtn = new JButton("Search");
        JButton cartBtn = new JButton("Cart (0)");
        JButton accountBtn = new JButton("Account");

        // rightSide.add(searchField);
        rightSide.add(searchBtn);
        rightSide.add(cartBtn);
        rightSide.add(accountBtn);

        bar.add(category, BorderLayout.WEST);
        bar.add(rightSide, BorderLayout.EAST);
        mainScr.add(bar, BorderLayout.NORTH);

        mainScr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainScr.setPreferredSize(new Dimension((int) (0.8 * scrSize.width), (int) (0.8 * scrSize.height)));
        mainScr.add(getMainPanel());
        mainScr.pack();
        mainScr.setLocationRelativeTo(null);
        mainScr.setMinimumSize(new Dimension((int) (0.8 * scrSize.width), (int) (0.8 * scrSize.height)));
        mainScr.setVisible(true);
        // mainScr.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
    public CardLayout getLayout() {
        return layout;
    }
    public JPanel getMainPanel() {
        return mainPanel;
    }
    public Core getCore() {
        return core;
    }

    public void showHome() {
        getLayout().show(getMainPanel(), "home");
    }

    public void showCart() {
        getLayout().show(getMainPanel(), "cart");
    }

    public void showAdmin() {
        getLayout().show(getMainPanel(), "admin");

    }
}