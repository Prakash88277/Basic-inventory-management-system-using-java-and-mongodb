@echo off
echo [INFO] Compiling Service Logic...
javac -cp "lib/*;." model/Product.java dao/ProductDAO.java service/InventoryService.java main/ServiceTest.java
if %errorlevel% neq 0 (
    echo [ERROR] Compilation failed.
    pause
    exit /b
)

echo [INFO] Running Service Test...
java -cp "lib/*;." main.ServiceTest
pause
