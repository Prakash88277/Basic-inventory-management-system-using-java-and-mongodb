# Java Inventory Management System

## Project Setup

### 1. Java Version
Ensure you have **Java 8** or higher installed.
`java -version`

### 2. MongoDB Setup
Ensure **MongoDB Community Server** is installed and running locally.
Connection String: `mongodb://localhost:27017`

### 3. Dependencies

#### Option A: Maven (Recommended)
Add the following to your `pom.xml`:
```xml
<dependencies>
    <dependency>
        <groupId>org.mongodb</groupId>
        <artifactId>mongodb-driver-sync</artifactId>
        <version>4.10.0</version> <!-- Or latest stable version -->
    </dependency>
</dependencies>
```

#### Option B: Manual JAR (For College/Offline Setup)
1. Download the following JARs from [Maven Central](https://mvnrepository.com/artifact/org.mongodb/mongodb-driver-sync):
   - `mongodb-driver-sync-4.x.x.jar`
   - `bson-4.x.x.jar`
   - `mongodb-driver-core-4.x.x.jar`
2. Add them to your project's classpath / build path.

## specific Project Structure
- `model/`: Entity classes (POJOs)
- `dao/`: Database Access Objects
- `service/`: Business Logic
- `ui/`: Swing GUI classes
- `main/`: Application entry point
