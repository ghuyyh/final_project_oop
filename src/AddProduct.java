import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class AddProduct extends JDialog {
	private Core core = Core.getInstance();
	private AdminView adminView;

	private JTextField idField = new JTextField(15);
	private JTextField nameField = new JTextField(15);
	private JTextField priceField = new JTextField(15);
	private JTextField stockField = new JTextField(15);
	private JTextField imageField = new JTextField(15);
	private File selectedImageFile;

	// specs storage and UI
	private java.util.Map<String, String> specsMap = new java.util.LinkedHashMap<>();
	private JPanel specsRowsPanel = new JPanel();
	private java.util.List<JTextField[]> specRows = new java.util.ArrayList<>();

	public AddProduct(GUI_MainFrame parent, AdminView adminView) {
		super(parent.getFrame(), "Add New Product", true);
		this.adminView = adminView;

		JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

		// Product fields panel
		JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
		formPanel.add(new JLabel("Product ID:"));
		formPanel.add(idField);
		formPanel.add(new JLabel("Product Name:"));
		formPanel.add(nameField);
		formPanel.add(new JLabel("Price:"));
		formPanel.add(priceField);
		formPanel.add(new JLabel("Stock Quantity:"));
		formPanel.add(stockField);
		formPanel.add(new JLabel("Image File Name:"));

		imageField.setEditable(false);
		JPanel imagePanel = new JPanel(new BorderLayout(5,0));
		JButton chooseImageButton = new JButton("Choose Image");
		chooseImageButton.addActionListener(e -> chooseImage());
		imagePanel.add(imageField, BorderLayout.CENTER);
		imagePanel.add(chooseImageButton, BorderLayout.EAST);
		formPanel.add(imagePanel);

		mainPanel.add(formPanel, BorderLayout.NORTH);

		// Specs section
		JPanel specsPanel = new JPanel(new BorderLayout(5, 5));
		specsPanel.setBorder(BorderFactory.createTitledBorder("Product Specifications"));

		// Scrollable rows container
		specsRowsPanel.setLayout(new BoxLayout(specsRowsPanel, BoxLayout.Y_AXIS));
		JScrollPane specsScrollPane = new JScrollPane(specsRowsPanel);
		specsScrollPane.setPreferredSize(new java.awt.Dimension(400, 120));

		// Button to add row
		JButton addRowBtn = new JButton("Add Spec Row");
		addRowBtn.addActionListener(e -> addSpecRow());

		specsPanel.add(specsScrollPane, BorderLayout.CENTER);
		specsPanel.add(addRowBtn, BorderLayout.SOUTH);

		mainPanel.add(specsPanel, BorderLayout.CENTER);

		// Button panel
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		JButton addBtn = new JButton("Add");
		addBtn.addActionListener(e -> processAddProduct());
		JButton cancelBtn = new JButton("Cancel");
		cancelBtn.addActionListener(e -> dispose());
		buttonPanel.add(addBtn);
		buttonPanel.add(cancelBtn);

		mainPanel.add(buttonPanel, BorderLayout.SOUTH);

		setContentPane(mainPanel);
		setSize(500, 450);
		setLocationRelativeTo(parent.getFrame());
	}

	private void addSpecRow() {
		JPanel rowPanel = new JPanel(new BorderLayout(5, 5));
		JTextField keyField = new JTextField(10);
		JTextField valueField = new JTextField(15);

		JPanel inputPanel = new JPanel(new BorderLayout(5, 0));
		inputPanel.add(new JLabel("Key:"), BorderLayout.WEST);
		inputPanel.add(keyField, BorderLayout.CENTER);

		JPanel valuePanel = new JPanel(new BorderLayout(5, 0));
		valuePanel.add(new JLabel("Value:"), BorderLayout.WEST);
		valuePanel.add(valueField, BorderLayout.CENTER);

		JButton removeBtn = new JButton("Remove");
		removeBtn.addActionListener(e -> {
			specsRowsPanel.remove(rowPanel);
			specRows.remove(new JTextField[]{keyField, valueField});
			specsRowsPanel.revalidate();
			specsRowsPanel.repaint();
		});

		rowPanel.add(inputPanel, BorderLayout.WEST);
		rowPanel.add(valuePanel, BorderLayout.CENTER);
		rowPanel.add(removeBtn, BorderLayout.EAST);

		specRows.add(new JTextField[]{keyField, valueField});
		specsRowsPanel.add(rowPanel);
		specsRowsPanel.revalidate();
		specsRowsPanel.repaint();
	}

	private void processAddProduct() {
		try {
			String id = idField.getText().trim();
			String name = nameField.getText().trim();
			if (id.isEmpty() || name.isEmpty()) {
				JOptionPane.showMessageDialog(this, "All fields cannot be empty.", "Input Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			double price = Double.parseDouble(priceField.getText().trim());
			int stock = Integer.parseInt(stockField.getText().trim());

			// Collect all specs from rows
			specsMap.clear();
			for (JTextField[] row : specRows) {
				String k = row[0].getText().trim();
				String v = row[1].getText().trim();
				if (!k.isEmpty() && !v.isEmpty()) {
					specsMap.put(k, v);
				}
			}

			Product newProduct = new Product(id, name, price, stock, specsMap, false, "");

			core.getInventory().add(newProduct);
			if (selectedImageFile != null) {
				saveImage(id, selectedImageFile);
			}
			core.saveInventory();

			JOptionPane.showMessageDialog(this, "Successfully added: " + name);

			if (adminView != null) {
				adminView.refreshInventoryTable();
			}

			dispose();
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Price and Stock must be valid numbers.", "Input Error",
					JOptionPane.ERROR_MESSAGE);
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(this, "Unable to save the selected image.", "Image Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void chooseImage() {
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("Choose Product Image");
		chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
				"Image files", "png", "jpg", "jpeg", "gif", "bmp"));

		int result = chooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			selectedImageFile = chooser.getSelectedFile();
			imageField.setText(selectedImageFile.getName());
		}
	}

	private void saveImage(String productId, File imageFile) throws IOException {
		if (imageFile == null) {
			return;
		}

		Path targetDir = Path.of("src", "res", "product_images");
		Files.createDirectories(targetDir);

		String lowerName = imageFile.getName().toLowerCase();
		String extension = ".png";
		int dotIndex = lowerName.lastIndexOf('.');
		if (dotIndex != -1) {
			extension = lowerName.substring(dotIndex);
		}

		Path targetFile = targetDir.resolve(productId + extension);
		Files.copy(imageFile.toPath(), targetFile, StandardCopyOption.REPLACE_EXISTING);
	}
}