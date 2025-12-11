# NPCI Payment Gateway API

A complete Spring Boot application simulating NPCI payment services for JMeter training.

## Features

- ✅ Account Management (Get Account, Check Balance)
- ✅ UPI Payments (Validate UPI ID, Initiate Payment)
- ✅ IMPS Transfers (Immediate Payment Service)
- ✅ Transaction Status Tracking
- ✅ JWT Authentication
- ✅ H2 In-Memory Database
- ✅ Swagger/OpenAPI Documentation
- ✅ Realistic Processing Delays (100-500ms)
- ✅ 100 Pre-loaded Test Accounts
- ✅ Comprehensive Exception Handling

## Tech Stack

- **Java**: 17
- **Spring Boot**: 3.2.0
- **Database**: H2 (In-Memory)
- **Security**: Spring Security + JWT
- **API Documentation**: SpringDoc OpenAPI
- **Testing**: JUnit, REST-Assured, JMeter DSL

## Quick Start

### Prerequisites

- Java 17 or higher
- Maven 3.6+

### Run the Application

```bash
# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

The application will start on `http://localhost:8080/api`

### Access Points

- **API Base URL**: http://localhost:8080/api
- **Swagger UI**: http://localhost:8080/api/swagger-ui.html
- **H2 Console**: http://localhost:8080/api/h2-console
  - JDBC URL: `jdbc:h2:mem:npcidb`
  - Username: `npci`
  - Password: `npci123`

## API Endpoints

### Authentication

#### Login
```bash
POST /api/auth/login
Content-Type: application/json

{
  "username": "testuser",
  "password": "password"
}

Response:
{
  "success": true,
  "message": "Login successful",
  "data": {
    "token": "eyJhbGc...",
    "type": "Bearer",
    "username": "testuser"
  }
}
```

### Account Management

#### Get Account Details
```bash
GET /api/accounts/ACC000001

Response:
{
  "success": true,
  "data": {
    "accountNumber": "ACC000001",
    "accountHolderName": "Test User 1",
    "balance": 100000,
    "ifscCode": "SBIN0001234",
    "bankName": "State Bank of India",
    "accountType": "SAVINGS",
    "status": "ACTIVE"
  }
}
```

#### Get Account Balance
```bash
GET /api/accounts/ACC000001/balance

Response:
{
  "success": true,
  "message": "Balance retrieved",
  "data": "Balance: 100000"
}
```

### UPI Payments

#### Validate UPI ID
```bash
POST /api/upi/validate
Content-Type: application/json

"user1@upi"

Response:
{
  "success": true,
  "message": "UPI ID is valid",
  "data": true
}
```

#### Initiate UPI Payment
```bash
POST /api/upi/payment
Content-Type: application/json

{
  "fromUpiId": "user1@upi",
  "toUpiId": "user2@upi",
  "amount": 1000,
  "remarks": "Payment for services"
}

Response:
{
  "success": true,
  "message": "Payment initiated successfully",
  "data": {
    "transactionId": "UPI20241211123456ABC12345",
    "transactionType": "UPI",
    "amount": 1000,
    "status": "SUCCESS",
    "fromAccount": "ACC000001",
    "toAccount": "ACC000002",
    "remarks": "Payment for services"
  }
}
```

### IMPS Transfers

#### Initiate IMPS Transfer
```bash
POST /api/imps/transfer
Content-Type: application/json

{
  "fromAccount": "ACC000001",
  "toAccount": "ACC000002",
  "ifscCode": "SBIN0001234",
  "amount": 5000,
  "remarks": "IMPS transfer"
}

Response:
{
  "success": true,
  "message": "Transfer initiated successfully",
  "data": {
    "transactionId": "IMPS20241211123456DEF67890",
    "transactionType": "IMPS",
    "amount": 5000,
    "status": "SUCCESS",
    "fromAccount": "ACC000001",
    "toAccount": "ACC000002"
  }
}
```

### Transaction Status

#### Get Transaction Status
```bash
GET /api/transactions/UPI20241211123456ABC12345

Response:
{
  "success": true,
  "message": "Transaction retrieved successfully",
  "data": {
    "transactionId": "UPI20241211123456ABC12345",
    "transactionType": "UPI",
    "amount": 1000,
    "status": "SUCCESS",
    "fromAccount": "ACC000001",
    "toAccount": "ACC000002"
  }
}
```

## Pre-loaded Test Data

The application loads 100 test accounts on startup:

- **Account Numbers**: ACC000001 to ACC000100
- **UPI IDs**: user1@upi to user100@upi
- **Initial Balance**: ₹100,000 each
- **Bank**: State Bank of India
- **IFSC Code**: SBIN0001234
- **Status**: ACTIVE

## Testing

### Run Functional Tests
```bash
mvn test -Dtest=*FunctionalTest
```

### Run Performance Tests
```bash
mvn test -Dtest=*LoadTest
```

### Run Specific Test
```bash
mvn test -Dtest=UPIPaymentLoadTest
```

## Configuration

Edit `src/main/resources/application.yml`:

```yaml
app:
  simulation:
    delay:
      min: 100  # Minimum processing delay (ms)
      max: 500  # Maximum processing delay (ms)
```

## Error Handling

The API returns appropriate HTTP status codes:

- `200 OK`: Successful request
- `201 Created`: Resource created (transactions)
- `400 Bad Request`: Validation errors, insufficient balance
- `401 Unauthorized`: Invalid credentials
- `404 Not Found`: Account/UPI ID/Transaction not found
- `500 Internal Server Error`: Server errors

## Sample Use Cases for JMeter Testing

### 1. Load Testing UPI Payments
- Simulate 100 concurrent users
- Each user makes a UPI payment
- Measure response time and throughput

### 2. Stress Testing IMPS
- Gradually increase load from 0 to 500 users
- Identify breaking point
- Monitor error rates

### 3. Endurance Testing
- Run 50 concurrent users for 1 hour
- Check for memory leaks
- Verify system stability

### 4. Spike Testing
- Sudden spike from 10 to 1000 users
- Test auto-scaling capabilities
- Measure recovery time

## Project Structure

```
src/
├── main/
│   ├── java/com/npci/gateway/
│   │   ├── NpciPaymentGatewayApplication.java
│   │   ├── config/
│   │   │   ├── SecurityConfig.java
│   │   │   └── DataLoader.java
│   │   ├── controller/
│   │   │   ├── AuthController.java
│   │   │   ├── AccountController.java
│   │   │   ├── UpiController.java
│   │   │   ├── ImpsController.java
│   │   │   └── TransactionController.java
│   │   ├── model/
│   │   │   ├── Account.java
│   │   │   ├── UpiId.java
│   │   │   └── Transaction.java
│   │   ├── dto/
│   │   ├── repository/
│   │   ├── service/
│   │   ├── exception/
│   │   └── util/
│   └── resources/
│       └── application.yml
└── test/
    └── java/com/npci/gateway/
        ├── functional/    # REST-Assured tests
        └── performance/   # JMeter tests
```

## Tips for JMeter Training

1. **Start Simple**: Begin with single endpoint testing
2. **Add Complexity**: Chain multiple requests (login → payment)
3. **Parameterize**: Use different accounts for each virtual user
4. **Assertions**: Validate response codes and content
5. **Realistic Scenarios**: Add think times between requests
6. **Monitor**: Watch response times and error rates

## Troubleshooting

### Application won't start
- Check Java version: `java -version` (should be 17+)
- Ensure port 8080 is available

### H2 Console not accessible
- Check `application.yml` for H2 configuration
- Verify URL: http://localhost:8080/api/h2-console

### Tests failing
- Ensure application is running
- Check test data is loaded
- Verify account numbers and UPI IDs match test data

## License

This is a training project for NPCI employees.

## Support

For issues or questions, contact the training team.
