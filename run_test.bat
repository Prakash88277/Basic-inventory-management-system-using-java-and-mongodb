@echo off
if not exist lib\mongodb-driver-sync-4.11.1.jar (
    echo [ERROR] JARs not found in lib/ folder. Please run the download command or download them manually.
    pause
    exit /b
)

echo [INFO] Compiling...
javac -cp "lib/*;." dao/MongoDBConnection.java main/ConnectionTest.java
if %errorlevel% neq 0 (
    echo [ERROR] Compilation failed.
    pause
    exit /b
)

echo [INFO] Running...
java -cp "lib/*;." main.ConnectionTest
pause
