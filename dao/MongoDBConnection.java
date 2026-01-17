package dao;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoDBConnection {
    private static MongoClient mongoClient;
    private static MongoDatabase database;
    private static final String CONNECTION_STRING = "mongodb://localhost:27017";
    private static final String DATABASE_NAME = "inventory";

    // Private constructor to prevent instantiation
    private MongoDBConnection() {
    }

    public static MongoDatabase getDatabase() {
        if (database == null) {
            try {
                // Connect to MongoDB
                mongoClient = MongoClients.create(CONNECTION_STRING);
                database = mongoClient.getDatabase(DATABASE_NAME);
                System.out.println("✅ Successfully connected to MongoDB: " + DATABASE_NAME);
            } catch (Exception e) {
                System.err.println("❌ Failed to connect to MongoDB: " + e.getMessage());
                throw new RuntimeException("Could not connect to MongoDB", e);
            }
        }
        return database;
    }

    public static void close() {
        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null;
            database = null;
            System.out.println("Connection closed.");
        }
    }
}
