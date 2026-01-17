package service;

import dao.ProductDAO;
import model.Product;
import java.util.List;

public class InventoryService {
    private ProductDAO productDAO;

    public InventoryService() {
        this.productDAO = new ProductDAO();
    }

    // 1. Add Product with Validation
    public String addProduct(String idStr, String name, String quantityStr, String priceStr) {
        try {
            // Basic Validation
            if (name == null || name.trim().isEmpty()) {
                return "Error: Product Name cannot be empty.";
            }
            if (idStr == null || idStr.trim().isEmpty())
                return "Error: Product ID is required.";
            if (quantityStr == null || quantityStr.trim().isEmpty())
                return "Error: Quantity is required.";
            if (priceStr == null || priceStr.trim().isEmpty())
                return "Error: Price is required.";

            // Parsing
            int id = Integer.parseInt(idStr);
            int quantity = Integer.parseInt(quantityStr);
            double price = Double.parseDouble(priceStr);

            // Logic Validation
            if (id <= 0)
                return "Error: Product ID must be positive.";
            if (quantity < 0)
                return "Error: Quantity cannot be negative.";
            if (price < 0)
                return "Error: Price cannot be negative.";

            Product product = new Product(id, name, quantity, price);
            productDAO.addProduct(product);

            return "Success: Product added successfully!";
        } catch (NumberFormatException e) {
            return "Error: ID, Quantity, and Price must be valid numbers.";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    // 2. Update Product (Dynamic Update)
    public String updateProduct(String idStr, String name, String quantityStr, String priceStr) {
        try {
            if (idStr == null || idStr.trim().isEmpty())
                return "Error: Product ID is required.";
            int id = Integer.parseInt(idStr);

            String nameToUpdate = (name != null && !name.trim().isEmpty()) ? name : null;
            Integer quantityToUpdate = null;
            Double priceToUpdate = null;

            if (quantityStr != null && !quantityStr.trim().isEmpty()) {
                quantityToUpdate = Integer.parseInt(quantityStr);
                if (quantityToUpdate < 0)
                    return "Error: Quantity cannot be negative.";
            }

            if (priceStr != null && !priceStr.trim().isEmpty()) {
                priceToUpdate = Double.parseDouble(priceStr);
                if (priceToUpdate < 0)
                    return "Error: Price cannot be negative.";
            }

            if (nameToUpdate == null && quantityToUpdate == null && priceToUpdate == null) {
                return "Error: At least one field (Name, Quantity, Price) must be provided.";
            }

            productDAO.updateProductDynamic(id, nameToUpdate, quantityToUpdate, priceToUpdate);
            return "Success: Product updated!";
        } catch (NumberFormatException e) {
            return "Error: ID, Quantity, and Price must be valid numbers.";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    // 3. Delete Product
    public String deleteProduct(String idStr) {
        try {
            if (idStr == null || idStr.trim().isEmpty())
                return "Error: Product ID is required.";

            int id = Integer.parseInt(idStr);
            productDAO.deleteProduct(id);
            return "Success: Product deleted!";
        } catch (NumberFormatException e) {
            return "Error: Product ID must be a number.";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    // 4. Get All Products
    public List<Product> getAllProducts() {
        return productDAO.getAllProducts();
    }
}
