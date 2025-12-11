#!/bin/bash

echo "==================================="
echo "NPCI Payment Gateway - Starting..."
echo "==================================="

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "Error: Java is not installed"
    echo "Please install Java 17 or higher"
    exit 1
fi

# Check Java version
JAVA_VERSION=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}' | cut -d'.' -f1)
if [ "$JAVA_VERSION" -lt 17 ]; then
    echo "Error: Java 17 or higher is required"
    echo "Current version: $JAVA_VERSION"
    exit 1
fi

# Build the project
echo "Building the application..."
mvn clean package -DskipTests

if [ $? -eq 0 ]; then
    echo ""
    echo "==================================="
    echo "Starting NPCI Payment Gateway..."
    echo "==================================="
    echo ""
    echo "API will be available at: http://localhost:8080/api"
    echo "Swagger UI: http://localhost:8080/api/swagger-ui.html"
    echo "H2 Console: http://localhost:8080/api/h2-console"
    echo ""
    echo "Press Ctrl+C to stop the application"
    echo ""
    
    # Run the application
    java -jar target/payment-gateway-1.0.0.jar
else
    echo "Build failed! Please check the error messages above."
    exit 1
fi
