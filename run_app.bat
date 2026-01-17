@echo off
echo [INFO] Compiling Application...
javac -cp "lib/*;." model/Product.java dao/ProductDAO.java service/InventoryService.java ui/MainFrame.java main/MainApp.java
if %errorlevel% neq 0 (
    echo [ERROR] Compilation failed.
    pause
    exit /b
)

echo [INFO] Running Inventory System...
java -cp "lib/*;." main.MainApp
