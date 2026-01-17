package main;

import service.InventoryService;
import model.Product;
import java.util.List;

public class ServiceTest {
    public static void main(String[] args) {
        System.out.println("Testing InventoryService...");
        InventoryService service = new InventoryService();

        // 1. Test Validation (Empty Name)
        System.out.println("\n--- Test Invalid Input ---");
        String res1 = service.addProduct("200", "", "10", "100");
        System.out.println("Result (Empty Name): " + res1);

        // 2. Test Validation (Invalid Number)
        String res2 = service.addProduct("abc", "Good Product", "10", "100");
        System.out.println("Result (Invalid ID): " + res2);

        // 3. Test Valid Add
        System.out.println("\n--- Test Valid Add ---");
        String res3 = service.addProduct("201", "Service Item", "50", "99.99");
        System.out.println("Add Result: " + res3);

        // 4. Test View
        System.out.println("\n--- Test View ---");
        List<Product> products = service.getAllProducts();
        for (Product p : products) {
            System.out.println(p);
        }

        // 5. Test Update
        System.out.println("\n--- Test Update ---");
        String res4 = service.updateProductQuantity("201", "100");
        System.out.println("Update Result: " + res4);

        // 6. Test Delete
        System.out.println("\n--- Test Delete ---");
        String res5 = service.deleteProduct("201");
        System.out.println("Delete Result: " + res5);

        System.out.println("\n✅ Service Test Completed.");
    }
}
