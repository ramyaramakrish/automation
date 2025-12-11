# NPCI Payment Gateway - Quick Start Guide

## 📦 What You Have

A complete, production-ready Spring Boot application with:
- ✅ 100 pre-loaded test accounts (ACC000001 to ACC000100)
- ✅ 100 UPI IDs (user1@upi to user100@upi)
- ✅ Complete UPI Payment API
- ✅ Complete IMPS Transfer API
- ✅ JWT Authentication
- ✅ Swagger Documentation
- ✅ H2 Database Console
- ✅ Realistic delays (100-500ms)

## 🚀 Get Started in 3 Steps

### Step 1: Extract the ZIP
```bash
unzip npci-payment-gateway.zip
cd npci-payment-gateway
```

### Step 2: Run the Application

**Linux/Mac:**
```bash
chmod +x run.sh
./run.sh
```

**Windows:**
```cmd
run.bat
```

**Or manually:**
```bash
mvn clean install
mvn spring-boot:run
```

### Step 3: Test the API

**Open Swagger UI:**
```
http://localhost:8080/api/swagger-ui.html
```

**Try a UPI Payment:**
```bash
curl -X POST http://localhost:8080/api/upi/payment \
  -H "Content-Type: application/json" \
  -d '{
    "fromUpiId": "user1@upi",
    "toUpiId": "user2@upi",
    "amount": 1000,
    "remarks": "Test payment"
  }'
```

## 📋 Available Endpoints

### Authentication
- `POST /api/auth/login` - Get JWT token
  - Username: `testuser`
  - Password: `password`

### Accounts
- `GET /api/accounts/{accountNumber}` - Get account details
- `GET /api/accounts/{accountNumber}/balance` - Check balance

### UPI
- `POST /api/upi/validate` - Validate UPI ID
- `POST /api/upi/payment` - Initiate UPI payment

### IMPS
- `POST /api/imps/transfer` - Initiate IMPS transfer

### Transactions
- `GET /api/transactions/{transactionId}` - Get transaction status

## 🧪 Test Data

**Accounts:** ACC000001 to ACC000100  
**UPI IDs:** user1@upi to user100@upi  
**Initial Balance:** ₹100,000 each  
**IFSC Code:** SBIN0001234  

## 🎯 Next Steps

1. **Explore Swagger UI** - Test all endpoints interactively
2. **Check H2 Console** - View database records
   - URL: http://localhost:8080/api/h2-console
   - JDBC URL: `jdbc:h2:mem:npcidb`
   - Username: `npci`
   - Password: `npci123`
3. **Start JMeter Training** - Use this API for performance testing

## 🔥 Sample JMeter Test Scenarios

### Scenario 1: Simple Load Test
- 50 users
- Each makes 1 UPI payment
- Expected: All succeed in < 1 second

### Scenario 2: Concurrent Payments
- 100 users
- Simultaneous UPI payments
- Check for race conditions

### Scenario 3: Full Transaction Flow
1. Login → Get token
2. Validate UPI ID
3. Check balance
4. Initiate payment
5. Check transaction status

## 📞 Support

For issues:
1. Check if Java 17+ is installed: `java -version`
2. Ensure port 8080 is free
3. Review application logs
4. Check README.md for detailed documentation

## 🎓 Perfect for Learning

This application is designed for:
- JMeter training
- REST-Assured testing
- Spring Boot learning
- Banking domain understanding
- Performance testing practice

Happy Testing! 🚀
