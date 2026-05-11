import java.awt.*;
import javax.swing.*;

public class GUI_MainFrame {
    private CardLayout layout = new CardLayout();
    private Core core = Core.getInstance();
    private JPanel mainPanel = new JPanel(layout);
    private HomeView homePanel = new HomeView(this);
    private CartView cartPanel = new CartView(this);
    private AdminView adminPanel = new AdminView(this);
    private LoginView loginPanel = new LoginView(this);
    private CustomerView customerPanel = new CustomerView(this);
    private SearchView searchPanel = new SearchView(this);
    private JButton cartBtn;

    public GUI_MainFrame() {
        // Get the screen size
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension scrSize = toolkit.getScreenSize();

        // flat look, donot remove
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("fail set flat look");
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
            showHome();
        });

        // Right side: search, cart and account
        JPanel rightSide = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightSide.setOpaque(false);
        // JTextField searchField = new JTextField(10);
        JButton searchBtn = new JButton("Search");
        JButton accountBtn = new JButton("Account");
        cartBtn = new JButton("Cart (0)");
        cartBtn.addActionListener(e -> {
            showCart();
        });
        searchBtn.addActionListener(e -> {
            showSearch();
        });

        accountBtn.addActionListener(e -> {
            if (Core.getInstance().getLoggedInUser() == null) {
                showLogin();
            } else if (Core.getInstance().getLoggedInUser() instanceof Admin) {
                showAdmin();
            } else {
                showCustomer();
            }
        });
        // rightSide.add(searchField);
        rightSide.add(searchBtn);
        rightSide.add(cartBtn);
        rightSide.add(accountBtn);

        bar.add(category, BorderLayout.WEST);
        bar.add(rightSide, BorderLayout.EAST);
        mainScr.add(bar, BorderLayout.NORTH);

        // main panel
        mainPanel.add("home", getHomePanel());
        mainPanel.add("cart", getCartPanel());
        mainPanel.add("admin", getAdminPanel());
        mainPanel.add("customer", getCustomerPanel());
        mainPanel.add("search", getSearchPanel());
        mainPanel.setBackground(new Color(238, 238, 238));

        // Wrap loginPanel so it respects preferred size
        JPanel loginWrapper = new JPanel();
        loginWrapper.setLayout(new FlowLayout(FlowLayout.CENTER));
        loginWrapper.add(getLoginPanel());
        mainPanel.add("login", loginWrapper);

        // main screen setting
        mainScr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainScr.setPreferredSize(new Dimension((int) (0.8 * scrSize.width), (int) (0.8 * scrSize.height)));
        mainScr.add(mainPanel);
        mainScr.pack();
        mainScr.setLocationRelativeTo(null);
        mainScr.setMinimumSize(new Dimension((int) (0.8 * scrSize.width), (int) (0.8 * scrSize.height)));
        mainScr.setVisible(true);
        showHome();

    }

    // geter
    public CardLayout getLayout() {
        return layout;
    }

    public JPanel getHomePanel() {
        return homePanel.getHomePanel();
    }

    public Core getCore() {
        return core;
    }

    public JPanel getCartPanel() {
        return cartPanel.getCartPanel();
    }

    public JPanel getSearchPanel() {
        return searchPanel.getSearchPanel();
    }

    public void refreshCart() {
        cartPanel.refreshCart();
    }

    public JPanel getLoginPanel() {
        return loginPanel.getLoginPanel();
    }

    public JPanel getAdminPanel() {
        return adminPanel.getAdminPanel();
    }

    public JPanel getCustomerPanel() {
        return customerPanel.getCustomerPanel();
    }

    public void showHome() {
        homePanel.refreshHome();
        getLayout().show(mainPanel, "home");

    }

    public void showCart() {
        getLayout().show(mainPanel, "cart");
    }

    public void showAdmin() {
        getLayout().show(mainPanel, "admin");
    }

    public void showLogin() {
        getLayout().show(mainPanel, "login");
    }

    public void showCustomer() {
        customerPanel.refreshHistory();
        getLayout().show(mainPanel, "customer");
    }
    public void showSearch(){
        getLayout().show(mainPanel, "search");
    }

    public JButton getCartBtn() {
        return cartBtn;
    }

    public void updateCartButton(JButton button) {
        if (getCartBtn() != null) {
            cartBtn.setText("Cart (" + (Core.getInstance().getLoggedInUser() instanceof Customer
                    ? ((Customer) getCore().getLoggedInUser()).getPersonalCart().getItems().size()
                    : Core.getInstance().getGuestCart().getItems().size()) + ")");
        }
    }
}
