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

    private InventoryService service;

    public MainFrame() {
        this.service = new InventoryService();

        setTitle("Inventory Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center on screen
        setLayout(new BorderLayout());

        // --- Top Panel: Inputs ---
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputPanel.add(new JLabel("Product ID:"));
        txtId = new JTextField();
        inputPanel.add(txtId);

        inputPanel.add(new JLabel("Name:"));
        txtName = new JTextField();
        inputPanel.add(txtName);

        inputPanel.add(new JLabel("Quantity:"));
        txtQuantity = new JTextField();
        inputPanel.add(txtQuantity);

        inputPanel.add(new JLabel("Price:"));
        txtPrice = new JTextField();
        inputPanel.add(txtPrice);

        add(inputPanel, BorderLayout.NORTH);

        // --- Middle Panel: Buttons ---
        JPanel buttonPanel = new JPanel(new FlowLayout());
        btnAdd = new JButton("Add Product");
        btnUpdate = new JButton("Update Product");
        btnDelete = new JButton("Delete Product");
        btnView = new JButton("View All Products");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnView);

        // Wrapper for buttons to go SOUTH of Inputs
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(buttonPanel, BorderLayout.NORTH);

        // --- Bottom Panel: Table ---
        String[] columns = { "ID", "Name", "Quantity", "Price" };
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        centerPanel.add(scrollPane, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        // --- Event Listeners ---
        initEvents();

        // Load initial data
        loadProducts();
    }

    private void initEvents() {
        // 1. Add Product
        btnAdd.addActionListener(e -> {
            String result = service.addProduct(
                    txtId.getText(),
                    txtName.getText(),
                    txtQuantity.getText(),
                    txtPrice.getText());
            JOptionPane.showMessageDialog(this, result);
            if (result.startsWith("Success")) {
                clearFields();
                loadProducts();
            }
        });

        // 2. Update Quantity
        // 2. Update Product
        btnUpdate.addActionListener(e -> {
            String result = service.updateProduct(
                    txtId.getText(),
                    txtName.getText(),
                    txtQuantity.getText(),
                    txtPrice.getText());
            JOptionPane.showMessageDialog(this, result);
            if (result.startsWith("Success")) {
                clearFields();
                loadProducts();
            }
        });

        // 3. Delete Product
        btnDelete.addActionListener(e -> {
            String result = service.deleteProduct(txtId.getText());
            JOptionPane.showMessageDialog(this, result);
            if (result.startsWith("Success")) {
                clearFields();
                loadProducts();
            }
        });

        // 4. View Products (Reload)
        btnView.addActionListener(e -> {
            loadProducts();
            JOptionPane.showMessageDialog(this, "Inventory Refreshed!");
        });

        // Optional: Click on table row to fill fields
        table.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                txtId.setText(tableModel.getValueAt(selectedRow, 0).toString());
                txtName.setText(tableModel.getValueAt(selectedRow, 1).toString());
                txtQuantity.setText(tableModel.getValueAt(selectedRow, 2).toString());
                txtPrice.setText(tableModel.getValueAt(selectedRow, 3).toString());
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
    }
}
