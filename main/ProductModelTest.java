package main;

import model.Product;
import org.bson.Document;

public class ProductModelTest {
    public static void main(String[] args) {
        System.out.println("Testing Product Model...");

        // 1. Create Product
        Product p1 = new Product(101, "Laptop", 5, 45000.0);
        System.out.println("Original: " + p1);

        // 2. Convert to Document
        Document doc = p1.toDocument();
        System.out.println("To Document: " + doc.toJson());

        // 3. Convert back to Product
        Product p2 = Product.fromDocument(doc);
        System.out.println("From Document: " + p2);

        // 4. Verify equality (simple check)
        if (p1.getProductId() == p2.getProductId() && p1.getName().equals(p2.getName())) {
            System.out.println("✅ Conversion Test Passed.");
        } else {
            System.err.println("❌ Conversion Test Failed.");
        }
    }
}
