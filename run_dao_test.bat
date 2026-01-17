@echo off
echo [INFO] Compiling DAO Logic...
javac -cp "lib/*;." model/Product.java dao/ProductDAO.java main/DAOTest.java
if %errorlevel% neq 0 (
    echo [ERROR] Compilation failed.
    pause
    exit /b
)

echo [INFO] Running DAO Test...
java -cp "lib/*;." main.DAOTest
pause
