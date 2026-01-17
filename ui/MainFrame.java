package ui;

import service.InventoryService;
import model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MainFrame extends JFrame {
    // Components
    private JTextField txtId, txtName, txtQuantity, txtPrice;
    private JButton btnAdd, btnUpdate, btnDelete, btnView;
    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel lblStatus;

    private InventoryService service;

    public MainFrame() {
        this.service = new InventoryService();

        setTitle("Inventory Management System");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center on screen
        setLayout(new BorderLayout());

        // --- 1. Header Panel (NORTH) ---
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(45, 52, 54)); // Dark Grey
        JLabel lblTitle = new JLabel("INVENTORY MANAGEMENT SYSTEM");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(Color.WHITE);
        headerPanel.add(lblTitle);
        add(headerPanel, BorderLayout.NORTH);

        // --- 2. Form Panel (WEST) with GridBagLayout ---
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Product Details"));
        formPanel.setPreferredSize(new Dimension(300, 0));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Product ID
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Product ID:"), gbc);
        gbc.gridx = 1;
        txtId = new JTextField(15);
        formPanel.add(txtId, gbc);

        // Name
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        txtName = new JTextField(15);
        formPanel.add(txtName, gbc);

        // Quantity
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Quantity:"), gbc);
        gbc.gridx = 1;
        txtQuantity = new JTextField(15);
        formPanel.add(txtQuantity, gbc);

        // Price
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Price:"), gbc);
        gbc.gridx = 1;
        txtPrice = new JTextField(15);
        formPanel.add(txtPrice, gbc);

        // Buttons Panel (Inside Form)
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        btnAdd = new JButton("Add Product");
        btnUpdate = new JButton("Update Product");
        btnDelete = new JButton("Delete Product");
        btnView = new JButton("Refresh Table");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnView);

        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 5, 5, 5);
        formPanel.add(buttonPanel, gbc);

        styleButton(btnAdd);
        styleButton(btnUpdate);
        styleButton(btnDelete);
        styleButton(btnView);

        add(formPanel, BorderLayout.WEST);

        // --- 3. Table Panel (CENTER) ---
        String[] columns = { "ID", "Name", "Quantity", "Price" };
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };
        table = new JTable(tableModel);
        table.setRowHeight(25);
        table.setAutoCreateRowSorter(true); // Enable Sorting
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Inventory List"));
        add(scrollPane, BorderLayout.CENTER);

        // --- 4. Status Panel (SOUTH) ---
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusPanel.setBorder(BorderFactory.createEtchedBorder());
        lblStatus = new JLabel("Ready");
        statusPanel.add(lblStatus);
        add(statusPanel, BorderLayout.SOUTH);

        // --- Event Listeners ---
        initEvents();
        initValidation(); // New validation logic

        // Load initial data
        loadProducts();
    }

    private void initValidation() {
        // Add listeners to text fields for real-time validation
        javax.swing.event.DocumentListener validationListener = new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                validateInputs();
            }

            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                validateInputs();
            }

            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                validateInputs();
            }
        };

        txtId.getDocument().addDocumentListener(validationListener);
        txtQuantity.getDocument().addDocumentListener(validationListener);
        txtPrice.getDocument().addDocumentListener(validationListener);
    }

    private void validateInputs() {
        boolean isValid = true;

        // Validate ID (Must be positive integer)
        if (!isNumeric(txtId.getText())) {
            txtId.setBorder(BorderFactory.createLineBorder(Color.RED));
            isValid = false;
        } else {
            txtId.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
        }

        // Validate Quantity (Must be non-negative integer)
        if (!isNumeric(txtQuantity.getText())) {
            txtQuantity.setBorder(BorderFactory.createLineBorder(Color.RED));
            isValid = false;
        } else {
            txtQuantity.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
        }

        // Validate Price (Must be non-negative double)
        if (!isDouble(txtPrice.getText())) {
            txtPrice.setBorder(BorderFactory.createLineBorder(Color.RED));
            isValid = false;
        } else {
            txtPrice.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
        }

        // Enable/Disable Add Button
        btnAdd.setEnabled(isValid);
    }

    private void styleButton(JButton btn) {
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setBackground(new Color(230, 230, 230));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (btn.isEnabled())
                    btn.setBackground(new Color(200, 200, 200));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (btn.isEnabled())
                    btn.setBackground(new Color(230, 230, 230));
            }
        });
    }

    private boolean isNumeric(String str) {
        if (str == null || str.trim().isEmpty())
            return true; // Allow empty for typing
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isDouble(String str) {
        if (str == null || str.trim().isEmpty())
            return true; // Allow empty
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void setStatus(String message, boolean isError) {
        lblStatus.setText(message);
        lblStatus.setForeground(isError ? Color.RED : new Color(0, 100, 0));
        // Auto-clear status after 3 seconds
        new Timer(3000, e -> lblStatus.setText("Ready")).start();
    }

    private void initEvents() {
        // Keyboard Shortcuts
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getRootPane().getActionMap();

        inputMap.put(KeyStroke.getKeyStroke("ENTER"), "enter");
        actionMap.put("enter", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                btnAdd.doClick();
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("DELETE"), "delete");
        actionMap.put("delete", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                btnDelete.doClick();
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("control R"), "refresh");
        actionMap.put("refresh", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                btnView.doClick();
            }
        });

        // 1. Add Product
        btnAdd.addActionListener(e -> {
            String result = service.addProduct(
                    txtId.getText(),
                    txtName.getText(),
                    txtQuantity.getText(),
                    txtPrice.getText());
            setStatus(result, result.startsWith("Error"));
            if (result.startsWith("Success")) {
                JOptionPane.showMessageDialog(this, result);
                clearFields();
                loadProducts();
            }
        });

        // 2. Update Product
        btnUpdate.addActionListener(e -> {
            String result = service.updateProduct(
                    txtId.getText(),
                    txtName.getText(),
                    txtQuantity.getText(),
                    txtPrice.getText());
            setStatus(result, result.startsWith("Error"));
            if (result.startsWith("Success")) {
                JOptionPane.showMessageDialog(this, result);
                clearFields();
                loadProducts();
            }
        });

        // 3. Delete Product
        btnDelete.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete Product ID: " + txtId.getText() + "?",
                    "Confirm Delete", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                String result = service.deleteProduct(txtId.getText());
                setStatus(result, result.startsWith("Error"));
                if (result.startsWith("Success")) {
                    JOptionPane.showMessageDialog(this, result);
                    clearFields();
                    loadProducts();
                }
            }
        });

        // 4. View Products (Reload)
        btnView.addActionListener(e -> {
            loadProducts();
            setStatus("Table Refreshed", false);
        });

        // Table Selection
        table.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                // Convert view index to model index (handling sorting)
                int modelRow = table.convertRowIndexToModel(selectedRow);
                txtId.setText(tableModel.getValueAt(modelRow, 0).toString());
                txtName.setText(tableModel.getValueAt(modelRow, 1).toString());
                txtQuantity.setText(tableModel.getValueAt(modelRow, 2).toString());
                txtPrice.setText(tableModel.getValueAt(modelRow, 3).toString());
            }
        });
    }

    private void loadProducts() {
        tableModel.setRowCount(0); // Clear table
        List<Product> products = service.getAllProducts();
        for (Product p : products) {
            tableModel.addRow(new Object[] {
                    p.getProductId(),
                    p.getName(),
                    p.getQuantity(),
                    p.getPrice()
            });
        }
    }

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtQuantity.setText("");
        txtPrice.setText("");
        table.clearSelection();
    }
}
