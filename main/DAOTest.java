package main;

import dao.ProductDAO;
import model.Product;
import java.util.List;

public class DAOTest {
    public static void main(String[] args) {
        System.out.println("Testing ProductDAO...");
        ProductDAO dao = new ProductDAO();

        try {
            // Cleanup before test
            dao.clearAll();

            // 1. Test Add
            System.out.println("\n--- Testing Add ---");
            Product p1 = new Product(101, "Mouse", 10, 500.0);
            Product p2 = new Product(102, "Keyboard", 5, 1200.0);
            dao.addProduct(p1);
            dao.addProduct(p2);

            // 2. Test Read (Get All)
            System.out.println("\n--- Testing Read ---");
            List<Product> products = dao.getAllProducts();
            System.out.println("Total Products: " + products.size());
            for (Product p : products) {
                System.out.println(p);
            }

            // 3. Test Update
            System.out.println("\n--- Testing Update ---");
            dao.updateProductQuantity(101, 20); // Update Mouse to 20

            // Verify update
            List<Product> updatedList = dao.getAllProducts();
            for (Product p : updatedList) {
                if (p.getProductId() == 101) {
                    System.out.println("Updated Mouse Quantity: " + p.getQuantity());
                }
            }

            // 4. Test Error Handling (Duplicate)
            System.out.println("\n--- Testing Duplicate Error ---");
            try {
                dao.addProduct(new Product(101, "Mouse Duplicate", 1, 100));
            } catch (Exception e) {
                System.out.println("Caught expected error: " + e.getMessage());
            }

            // 5. Test Delete
            System.out.println("\n--- Testing Delete ---");
            dao.deleteProduct(102); // Delete Keyboard
            System.out.println("Remaining Products: " + dao.getAllProducts().size());

            System.out.println("\n✅ DAO Test Completed Successfully.");

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("❌ DAO Test Failed.");
        }
    }
}
