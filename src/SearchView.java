import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class SearchView {
    private JPanel searchPanel = new JPanel();
    private JPanel resultsPanel = new JPanel();
    private JTextField searchField = new JTextField(20);
    private JLabel statusLabel = new JLabel("Enter a keyword to search...");
    private Core core = Core.getInstance();
    private GUI_MainFrame mainFrame;

    public SearchView(GUI_MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        searchPanel.setLayout(new BorderLayout(10, 10));
        searchPanel.setBorder(BorderFactory.createTitledBorder("Search Products"));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JButton searchBtn = new JButton("Search");
        JButton clearBtn = new JButton("Clear");
       

        topPanel.add(new JLabel("Keyword:"));
        topPanel.add(searchField);
        topPanel.add(searchBtn);
        topPanel.add(clearBtn);
        topPanel.add(statusLabel);

        searchPanel.add(topPanel, BorderLayout.NORTH);

        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(resultsPanel);
        searchPanel.add(scrollPane, BorderLayout.CENTER);

        searchBtn.addActionListener(e -> performSearch());
        searchField.addActionListener(e -> performSearch()); // Enter key

        clearBtn.addActionListener(e -> {
            searchField.setText("");
            resultsPanel.removeAll();
            statusLabel.setText("Enter a keyword to search...");
            resultsPanel.revalidate();
            resultsPanel.repaint();
        });

        // backBtn.addActionListener(e -> mainFrame.showHome());
    }

    public void performSearch() {
        String keyword = searchField.getText().trim().toLowerCase();
        resultsPanel.removeAll();

        if (keyword.isEmpty()) {
            statusLabel.setText("Please enter a keyword.");
            resultsPanel.revalidate();
            resultsPanel.repaint();
            return;
        }

        List<Product> results = new ArrayList<>();
        for (Product p : core.getInventory()) {
            if (p.getName().toLowerCase().contains(keyword)
                    || p.getId().toLowerCase().contains(keyword)) {
                results.add(p);
            }
        }

        if (results.isEmpty()) {
            statusLabel.setText("No results for \"" + keyword + "\".");
        } else {
            statusLabel.setText(results.size() + " result(s) for \"" + keyword + "\".");
            for (Product product : results) {
                resultsPanel.add(buildProductRow(product));
                resultsPanel.add(Box.createVerticalStrut(8));
            }
        }

        resultsPanel.revalidate();
        resultsPanel.repaint();
    }

    private JPanel buildProductRow(Product product) {
        JPanel row = new JPanel(new BorderLayout(10, 5));
        row.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));
        row.setBackground(Color.WHITE);

        try {
            java.net.URL imgURL = getClass().getResource("/res/" + product.getId() + ".png");
            if (imgURL != null) {
                ImageIcon icon = new ImageIcon(imgURL);
                Image img = icon.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
                row.add(new JLabel(new ImageIcon(img)), BorderLayout.WEST);
            }
        } catch (Exception ignored) {}

        int stock = product.getStockQuantity();
        String stockColor = stock > 0 ? "green" : "red";
        String stockText = stock > 0 ? "In Stock: " + stock : "Out of Stock";
        String info = String.format(
                "<html><b>%s</b> &nbsp; <font color='gray'>[%s]</font><br/>Price: <b>$%.2f</b>" +
                " &nbsp; <font color='%s'>%s</font></html>",
                product.getName(), product.getId(), product.getPrice(), stockColor, stockText);
        row.add(new JLabel(info), BorderLayout.CENTER);

        JButton addBtn = new JButton(stock > 0 ? "Add to Cart" : "Out of Stock");
        addBtn.setEnabled(stock > 0);
        addBtn.addActionListener(e -> {
            User user = core.getLoggedInUser();
            if (user instanceof Admin) {
                JOptionPane.showMessageDialog(searchPanel,
                        "Admins cannot add to cart.");
                return;
            }
            Cart cart = (user instanceof Customer)
                    ? ((Customer) user).getPersonalCart()
                    : core.getGuestCart();
            cart.addItem(product, 1);
            mainFrame.refreshCart();
            mainFrame.updateCartButton();
            JOptionPane.showMessageDialog(searchPanel, "Added \"" + product.getName() + "\" to cart!");
        });
        row.add(addBtn, BorderLayout.EAST);

        return row;
    }

    public void focusSearchField() {
        searchField.requestFocusInWindow();
    }

    public JPanel getSearchPanel() {
        return searchPanel;
    }
}
