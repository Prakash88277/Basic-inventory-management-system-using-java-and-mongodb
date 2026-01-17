@echo off
echo [INFO] Compiling Product Logic...
javac -cp "lib/*;." model/Product.java main/ProductModelTest.java
if %errorlevel% neq 0 (
    echo [ERROR] Compilation failed.
    pause
    exit /b
)

echo [INFO] Running Product Test...
java -cp "lib/*;." main.ProductModelTest
pause
