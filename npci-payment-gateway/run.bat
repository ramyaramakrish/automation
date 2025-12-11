@echo off
echo ===================================
echo NPCI Payment Gateway - Starting...
echo ===================================

REM Check if Java is installed
java -version >nul 2>&1
if errorlevel 1 (
    echo Error: Java is not installed
    echo Please install Java 17 or higher
    pause
    exit /b 1
)

echo Building the application...
call mvn clean package -DskipTests

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ===================================
    echo Starting NPCI Payment Gateway...
    echo ===================================
    echo.
    echo API will be available at: http://localhost:8080/api
    echo Swagger UI: http://localhost:8080/api/swagger-ui.html
    echo H2 Console: http://localhost:8080/api/h2-console
    echo.
    echo Press Ctrl+C to stop the application
    echo.
    
    REM Run the application
    java -jar target\payment-gateway-1.0.0.jar
) else (
    echo Build failed! Please check the error messages above.
    pause
    exit /b 1
)
