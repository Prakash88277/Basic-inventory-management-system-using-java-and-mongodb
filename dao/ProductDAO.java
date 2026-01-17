package dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import model.Product;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private final MongoCollection<Document> collection;

    public ProductDAO() {
        MongoDatabase db = MongoDBConnection.getDatabase();
        this.collection = db.getCollection("inventory");
    }

    // 1. Add Product
    public void addProduct(Product product) throws Exception {
        // Check if product already exists
        if (collection.find(Filters.eq("_id", product.getProductId())).first() != null) {
            throw new Exception("Product with ID " + product.getProductId() + " already exists.");
        }
        collection.insertOne(product.toDocument());
        System.out.println("✅ Product added: " + product.getName());
    }

    // 2. Update Product Quantity
    public void updateProductQuantity(int productId, int quantity) throws Exception {
        if (quantity < 0) {
            throw new Exception("Quantity cannot be negative.");
        }

        UpdateResult result = collection.updateOne(
                Filters.eq("_id", productId),
                Updates.set("quantity", quantity));

        if (result.getMatchedCount() == 0) {
            throw new Exception("Product with ID " + productId + " not found.");
        }
        System.out.println("✅ Updated quantity for Product ID " + productId);
    }

    // 3. Delete Product
    public void deleteProduct(int productId) throws Exception {
        DeleteResult result = collection.deleteOne(Filters.eq("_id", productId));
        if (result.getDeletedCount() == 0) {
            throw new Exception("Product with ID " + productId + " not found.");
        }
        System.out.println("✅ Product deleted: ID " + productId);
    }

    // 4. Get All Products
    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        for (Document doc : collection.find()) {
            productList.add(Product.fromDocument(doc));
        }
        return productList;
    }

    // Helper to clear collection for testing
    public void clearAll() {
        collection.deleteMany(new Document());
        System.out.println("⚠️ All products cleared from database.");
    }
}
