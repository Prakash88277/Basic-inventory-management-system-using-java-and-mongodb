package model;

import org.bson.Document;

public class Product {
    private int productId;
    private String name;
    private int quantity;
    private double price;

    public Product() {
    }

    public Product(int productId, String name, int quantity, double price) {
        this.productId = productId;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters and Setters
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // Convert Java Object -> MongoDB Document
    public Document toDocument() {
        return new Document("_id", productId) // Using productId as the MongoDB _id
                .append("name", name)
                .append("quantity", quantity)
                .append("price", price);
    }

    // Convert MongoDB Document -> Java Object
    public static Product fromDocument(Document doc) {
        if (doc == null)
            return null;
        return new Product(
                doc.getInteger("_id"),
                doc.getString("name"),
                doc.getInteger("quantity"),
                doc.getDouble("price"));
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
