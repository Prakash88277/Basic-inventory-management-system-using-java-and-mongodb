package dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import model.Product;
import org.bson.Document;

import com.mongodb.client.model.Sorts;
import org.bson.conversions.Bson;
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

    // 2. Update Product (Name, Quantity, Price)
    public void updateProduct(Product product) throws Exception {
        UpdateResult result = collection.updateOne(
                Filters.eq("_id", product.getProductId()),
                Updates.combine(
                        Updates.set("name", product.getName()),
                        Updates.set("quantity", product.getQuantity()),
                        Updates.set("price", product.getPrice())));

        if (result.getMatchedCount() == 0) {
            throw new Exception("Product with ID " + product.getProductId() + " not found.");
        }
        System.out.println("✅ Updated Product ID " + product.getProductId());
    }

    // 2b. Update Product Quantity (Legacy support)
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

    // 2. Update Product (Dynamic/Partial Update)
    public void updateProductDynamic(int id, String name, Integer quantity, Double price) throws Exception {
        List<Bson> updates = new ArrayList<>();
        if (name != null)
            updates.add(Updates.set("name", name));
        if (quantity != null)
            updates.add(Updates.set("quantity", quantity));
        if (price != null)
            updates.add(Updates.set("price", price));

        if (updates.isEmpty())
            return; // Nothing to update

        UpdateResult result = collection.updateOne(
                Filters.eq("_id", id),
                Updates.combine(updates));

        if (result.getMatchedCount() == 0) {
            throw new Exception("Product with ID " + id + " not found.");
        }
        System.out.println("✅ Updated Product ID " + id);
    }

    // 4. Get All Products (Sorted by ID)
    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        for (Document doc : collection.find().sort(Sorts.ascending("_id"))) {
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
