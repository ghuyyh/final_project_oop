import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.util.Map;

public class ProductView {

    private static final Color BG = new Color(230, 230, 230);
    private static final Color SURFACE = new Color(255, 255, 255);
    private static final Color CARD = new Color(210, 210, 210);
    private static final Color ACCENT = new Color(77, 77, 77);
    private static final Color ACCENT2 = new Color(50, 50, 50);
    private static final Color TEXT_PRIMARY = new Color(50, 50, 50);
    private static final Color TEXT_SECONDARY = new Color(100, 100, 100);
    private static final Color STOCK_OK = new Color(72, 199, 142);
    private static final Color STOCK_LOW = new Color(255, 180, 60);
    private static final Color STOCK_NONE = new Color(236, 72, 72);
    private static final Color SEPARATOR = new Color(230, 230, 230);

    private static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 22);
    private static final Font FONT_PRICE = new Font("Segoe UI", Font.BOLD, 20);
    private static final Font FONT_LABEL = new Font("Segoe UI", Font.BOLD, 12);
    private static final Font FONT_VALUE = new Font("Segoe UI", Font.PLAIN, 12);
    private static final Font FONT_STOCK = new Font("Segoe UI", Font.BOLD, 13);
    private static final Font FONT_BTN = new Font("Segoe UI", Font.BOLD, 13);
    private static final Font FONT_HEADER = new Font("Segoe UI", Font.BOLD, 13);

    private final Product product;
    private final GUI_MainFrame mainFrame;
    private JDialog dialog;

    public ProductView(Product product, GUI_MainFrame mainFrame) {
        this.product = product;
        this.mainFrame = mainFrame;
    }

    public void show() {
        Frame parent = mainFrame != null
                ? (Frame) SwingUtilities.getWindowAncestor(mainFrame.getHomePanel())
                : null;

        dialog = new JDialog(parent, "Product Details", true);
        dialog.setUndecorated(false);
        dialog.setBackground(BG);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setPreferredSize(new Dimension(780, 560));

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BG);
        root.setBorder(new EmptyBorder(20, 20, 20, 20));

        root.add(buildHeader(), BorderLayout.NORTH);

        JPanel body = new JPanel(new GridBagLayout());
        body.setBackground(BG);
        body.setBorder(new EmptyBorder(16, 0, 0, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 0, 16);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.42;
        gbc.weighty = 1.0;
        body.add(buildImageCard(), gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.weightx = 0.58;
        body.add(buildInfoCard(), gbc);

        root.add(body, BorderLayout.CENTER);

        dialog.setContentPane(root);
        dialog.pack();
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
    }

    private JPanel buildHeader() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(BG);
        p.setBorder(new EmptyBorder(0, 0, 12, 0));

        JLabel breadcrumb = new JLabel(" Home  >  Product Details > " + product.getName());
        breadcrumb.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        breadcrumb.setForeground(TEXT_SECONDARY);

        JButton closeBtn = makeIconButton("✕");
        closeBtn.addActionListener(e -> dialog.dispose());

        p.add(breadcrumb, BorderLayout.WEST);
        p.add(closeBtn, BorderLayout.EAST);
        return p;
    }

    private JPanel buildImageCard() {
        JPanel card = new RoundPanel(16, CARD);
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(16, 16, 16, 16));

        BufferedImage img = generateProductImage(product);
        JLabel imgLabel = new JLabel(new ImageIcon(img), SwingConstants.CENTER);
        imgLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        imgLabel.setToolTipText("Click to view larger image");

        imgLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(ACCENT, 2, true),
                        new EmptyBorder(14, 14, 14, 14)));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                card.setBorder(new EmptyBorder(16, 16, 16, 16));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                showZoomDialog(img);
            }
        });

        JLabel hint = new JLabel("🔍 Click image to zoom in", SwingConstants.CENTER);
        hint.setFont(new Font("Segoe UI", Font.ITALIC, 10));
        hint.setForeground(TEXT_SECONDARY);
        hint.setBorder(new EmptyBorder(8, 0, 0, 0));

        card.add(imgLabel, BorderLayout.CENTER);
        card.add(hint, BorderLayout.SOUTH);
        return card;
    }

    private JPanel buildInfoCard() {
        JPanel card = new RoundPanel(16, CARD);
        card.setLayout(new BorderLayout(0, 0));
        card.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.setOpaque(false);

        JLabel nameLabel = new JLabel("<html>" + product.getName() + "</html>");
        nameLabel.setFont(FONT_TITLE);
        nameLabel.setForeground(TEXT_PRIMARY);
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        top.add(nameLabel);
        top.add(Box.createVerticalStrut(8));

        JLabel priceLabel = new JLabel(formatPrice(product.getPrice()));
        priceLabel.setFont(FONT_PRICE);
        priceLabel.setForeground(ACCENT2);
        priceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        top.add(priceLabel);
        top.add(Box.createVerticalStrut(10));

        top.add(buildStockBadge());
        top.add(Box.createVerticalStrut(16));

        JSeparator sep = new JSeparator();
        sep.setForeground(SEPARATOR);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sep.setAlignmentX(Component.LEFT_ALIGNMENT);
        top.add(sep);
        top.add(Box.createVerticalStrut(14));

        JLabel specTitle = new JLabel("\u2699  Specifications");
        specTitle.setFont(FONT_HEADER);
        specTitle.setForeground(ACCENT);
        specTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        top.add(specTitle);
        top.add(Box.createVerticalStrut(10));

        card.add(top, BorderLayout.NORTH);
        card.add(buildSpecTable(), BorderLayout.CENTER);
        card.add(buildCartButton(), BorderLayout.SOUTH);
        return card;
    }

    private JPanel buildStockBadge() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        p.setOpaque(false);
        p.setAlignmentX(Component.LEFT_ALIGNMENT);

        int stock = product.getStockQuantity();
        String text;
        Color color;
        if (stock == 0) {
            text = "Out of Stock";
            color = STOCK_NONE;
        } else if (stock <= 3) {
            text = "Low Stock  (" + stock + " left)";
            color = STOCK_LOW;
        } else {
            text = "In Stock  (" + stock + " available)";
            color = STOCK_OK;
        }

        JLabel badge = new JLabel(text);
        badge.setFont(FONT_STOCK);
        badge.setForeground(color);
        badge.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(color, 1, true),
                new EmptyBorder(4, 10, 4, 10)));
        p.add(badge);
        return p;
    }

    private JScrollPane buildSpecTable() {
        Map<String, String> specs = product.getSpecs();

        JPanel table = new JPanel(new GridBagLayout());
        table.setBackground(SURFACE);
        table.setBorder(new EmptyBorder(6, 6, 6, 6));

        GridBagConstraints lc = new GridBagConstraints();
        lc.anchor = GridBagConstraints.NORTHWEST;
        lc.fill = GridBagConstraints.HORIZONTAL;
        lc.insets = new Insets(5, 8, 5, 12);
        lc.weightx = 0.35;

        GridBagConstraints vc = new GridBagConstraints();
        vc.anchor = GridBagConstraints.NORTHWEST;
        vc.fill = GridBagConstraints.HORIZONTAL;
        vc.insets = new Insets(5, 0, 5, 8);
        vc.weightx = 0.65;
        vc.gridwidth = GridBagConstraints.REMAINDER;

        int row = 0;
        boolean even = false;
        for (Map.Entry<String, String> entry : specs.entrySet()) {
            Color rowBg = even ? new Color(230, 230, 230) : SURFACE;

            JLabel keyLbl = new JLabel(entry.getKey());
            keyLbl.setFont(FONT_LABEL);
            keyLbl.setForeground(TEXT_SECONDARY);
            keyLbl.setOpaque(true);
            keyLbl.setBackground(rowBg);

            JLabel valLbl = new JLabel("<html>" + entry.getValue() + "</html>");
            valLbl.setFont(FONT_VALUE);
            valLbl.setForeground(TEXT_PRIMARY);
            valLbl.setOpaque(true);
            valLbl.setBackground(rowBg);

            lc.gridy = row;
            vc.gridy = row;
            table.add(keyLbl, lc);
            table.add(valLbl, vc);

            row++;
            even = !even;
        }

        GridBagConstraints fc = new GridBagConstraints();
        fc.gridy = row;
        fc.weighty = 1.0;
        fc.gridwidth = GridBagConstraints.REMAINDER;
        fc.fill = GridBagConstraints.VERTICAL;
        table.add(Box.createVerticalGlue(), fc);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(new LineBorder(SEPARATOR, 1, true));
        scroll.setBackground(SURFACE);
        scroll.getViewport().setBackground(SURFACE);
        scroll.setOpaque(false);
        return scroll;
    }

    private JPanel buildCartButton() {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);
        p.setBorder(new EmptyBorder(14, 0, 0, 0));

        JButton btn = new JButton("Add to Cart");
        btn.setFont(FONT_BTN);
        btn.setForeground(Color.WHITE);
        btn.setBackground(ACCENT);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(0, 42));

        if (product.getStockQuantity() == 0) {
            btn.setEnabled(false);
            btn.setBackground(SEPARATOR);
            btn.setText("Out of Stock");
        }

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (btn.isEnabled())
                    btn.setBackground(ACCENT.brighter());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (btn.isEnabled())
                    btn.setBackground(ACCENT);
            }
        });

        btn.addActionListener(e -> {
            Core core = Core.getInstance();
            User user = core.getLoggedInUser();
            if (user instanceof Admin) {
                JOptionPane.showMessageDialog(dialog,
                        "Admins cannot add products to the cart.",
                        "Notice", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Cart cart = (user instanceof Customer)
                    ? ((Customer) user).getPersonalCart()
                    : core.getGuestCart();
            cart.addItem(product, 1);
            if (mainFrame != null)
                mainFrame.updateCartButton();
            JOptionPane.showMessageDialog(dialog,
                    "\u201c" + product.getName() + "\u201d added to cart!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        });

        p.add(btn, BorderLayout.CENTER);
        return p;
    }

    private void showZoomDialog(BufferedImage img) {
        JDialog zoom = new JDialog(dialog, product.getName(), true);
        zoom.setBackground(BG);

        int w = img.getWidth() * 2;
        int h = img.getHeight() * 2;
        Image scaled = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);

        JLabel lbl = new JLabel(new ImageIcon(scaled), SwingConstants.CENTER);
        lbl.setBorder(new EmptyBorder(16, 16, 16, 16));
        lbl.setBackground(BG);
        lbl.setOpaque(true);

        JLabel hint = new JLabel("Click anywhere to close", SwingConstants.CENTER);
        hint.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        hint.setForeground(TEXT_SECONDARY);
        hint.setBackground(BG);
        hint.setOpaque(true);
        hint.setBorder(new EmptyBorder(0, 0, 12, 0));

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG);
        panel.add(lbl, BorderLayout.CENTER);
        panel.add(hint, BorderLayout.SOUTH);

        MouseAdapter closeOnClick = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                zoom.dispose();
            }
        };
        panel.addMouseListener(closeOnClick);
        lbl.addMouseListener(closeOnClick);
        lbl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        zoom.setContentPane(panel);
        zoom.pack();
        zoom.setLocationRelativeTo(dialog);
        zoom.setVisible(true);
    }

    private BufferedImage generateProductImage(Product p) {
        int W = 260, H = 220;
        BufferedImage img = new BufferedImage(W, H, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int hash = Math.abs(p.getName().hashCode());
        Color c1 = Color.getHSBColor((hash % 360) / 360f, 0.55f, 0.35f);
        Color c2 = Color.getHSBColor(((hash + 120) % 360) / 360f, 0.60f, 0.25f);
        g.setPaint(new GradientPaint(0, 0, c1, W, H, c2));
        g.fillRoundRect(0, 0, W, H, 20, 20);

        g.setColor(new Color(255, 255, 255, 60));
        g.fillRoundRect(W / 2 - 35, H / 2 - 55, 70, 110, 12, 12);
        g.setColor(new Color(255, 255, 255, 140));
        g.setStroke(new BasicStroke(2.5f));
        g.drawRoundRect(W / 2 - 35, H / 2 - 55, 70, 110, 12, 12);
        g.setColor(new Color(0, 0, 0, 80));
        g.fillRoundRect(W / 2 - 28, H / 2 - 46, 56, 88, 6, 6);
        g.setColor(new Color(255, 255, 255, 120));
        g.fillOval(W / 2 - 6, H / 2 - 48, 12, 12);
        g.fillRoundRect(W / 2 - 8, H / 2 + 51, 16, 7, 4, 4);

        g.setColor(new Color(255, 255, 255, 220));
        g.setFont(new Font("Segoe UI", Font.BOLD, 11));
        FontMetrics fm = g.getFontMetrics();
        String label = p.getName().length() > 18 ? p.getName().substring(0, 16) + "\u2026" : p.getName();
        g.drawString(label, (W - fm.stringWidth(label)) / 2, H - 14);

        g.dispose();
        return img;
    }

    private String formatPrice(double price) {
        return String.format("$%,.2f", price);
    }

    private JButton makeIconButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setForeground(TEXT_SECONDARY);
        btn.setBackground(CARD);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(32, 28));
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setForeground(TEXT_PRIMARY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setForeground(TEXT_SECONDARY);
            }
        });
        return btn;
    }

    static class RoundPanel extends JPanel {
        private final int arc;
        private final Color bg;

        RoundPanel(int arc, Color bg) {
            this.arc = arc;
            this.bg = bg;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(bg);
            g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), arc, arc));
            g2.dispose();
            super.paintComponent(g);
        }
    }
}