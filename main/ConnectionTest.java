package main;

import dao.MongoDBConnection;
import com.mongodb.client.MongoDatabase;

public class ConnectionTest {
    public static void main(String[] args) {
        System.out.println("Testing MongoDB Connection...");
        try {
            MongoDatabase db = MongoDBConnection.getDatabase();
            System.out.println("Database Name: " + db.getName());

            // Just to verify interaction, list collections (might be empty initially)
            System.out.println("Collections:");
            for (String name : db.listCollectionNames()) {
                System.out.println(" - " + name);
            }

            // Clean up
            MongoDBConnection.close();
            System.out.println("Test Passed.");
        } catch (Exception e) {
            System.err.println("Test Failed!");
            e.printStackTrace();
        }
    }
}
