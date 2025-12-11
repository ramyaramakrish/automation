package com.npci.tests.functional;

import com.npci.tests.utils.TestConfig;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("UPI Payment Functional Tests")
public class UpiPaymentFunctionalTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = TestConfig.BASE_URL;
        RestAssured.basePath = TestConfig.BASE_PATH;
        System.out.println("\n=== Starting UPI Payment Functional Tests ===");
    }

    @Test
    @Order(1)
    @DisplayName("Should successfully process UPI payment")
    public void testSuccessfulUpiPayment() {
        given()
                .contentType("application/json")
                .body("{\n" +
                        "  \"fromUpiId\": \"user1@upi\",\n" +
                        "  \"toUpiId\": \"user2@upi\",\n" +
                        "  \"amount\": 1000,\n" +
                        "  \"remarks\": \"Functional test payment\"\n" +
                        "}")
                .when()
                .post("/upi/payment")
                .then()
                .statusCode(201)
                .body("success", equalTo(true))
                .body("message", containsString("Payment initiated successfully"))
                .body("data.status", equalTo("SUCCESS"))
                .body("data.transactionId", notNullValue())
                .body("data.amount", equalTo(1000.0f))
                .time(lessThan(2000L));

        System.out.println("✓ Test passed: Successful UPI payment");
    }

    @Test
    @Order(2)
    @DisplayName("Should validate UPI ID format")
    public void testInvalidUpiIdFormat() {
        given()
                .contentType("application/json")
                .body("{\n" +
                        "  \"fromUpiId\": \"invalid-format\",\n" +
                        "  \"toUpiId\": \"user2@upi\",\n" +
                        "  \"amount\": 1000,\n" +
                        "  \"remarks\": \"Test\"\n" +
                        "}")
                .when()
                .post("/upi/payment")
                .then()
                .statusCode(400)
                .body("success", equalTo(false));

        System.out.println("✓ Test passed: Invalid UPI format rejected");
    }

    @Test
    @Order(3)
    @DisplayName("Should reject payment with non-existent UPI ID")
    public void testNonExistentUpiId() {
        given()
                .contentType("application/json")
                .body("{\n" +
                        "  \"fromUpiId\": \"nonexistent@upi\",\n" +
                        "  \"toUpiId\": \"user2@upi\",\n" +
                        "  \"amount\": 1000,\n" +
                        "  \"remarks\": \"Test\"\n" +
                        "}")
                .when()
                .post("/upi/payment")
                .then()
                .statusCode(400)
                .body("success", equalTo(false));

        System.out.println("✓ Test passed: Non-existent UPI ID rejected");
    }

    @Test
    @Order(4)
    @DisplayName("Should handle insufficient balance")
    public void testInsufficientBalance() {
        given()
                .contentType("application/json")
                .body("{\n" +
                        "  \"fromUpiId\": \"user1@upi\",\n" +
                        "  \"toUpiId\": \"user2@upi\",\n" +
                        "  \"amount\": 999999,\n" +
                        "  \"remarks\": \"Large payment\"\n" +
                        "}")
                .when()
                .post("/upi/payment")
                .then()
                .statusCode(400)
                .body("success", equalTo(false))
                .body("message", containsString("Insufficient balance"));

        System.out.println("✓ Test passed: Insufficient balance handled");
    }

    @Test
    @Order(5)
    @DisplayName("Should validate amount constraints")
    public void testAmountValidation() {
        // Test with amount below minimum (1)
        given()
                .contentType("application/json")
                .body("{\n" +
                        "  \"fromUpiId\": \"user1@upi\",\n" +
                        "  \"toUpiId\": \"user2@upi\",\n" +
                        "  \"amount\": 0,\n" +
                        "  \"remarks\": \"Zero amount\"\n" +
                        "}")
                .when()
                .post("/upi/payment")
                .then()
                .statusCode(400);

        // Test with amount above maximum (100000)
        given()
                .contentType("application/json")
                .body("{\n" +
                        "  \"fromUpiId\": \"user1@upi\",\n" +
                        "  \"toUpiId\": \"user2@upi\",\n" +
                        "  \"amount\": 150000,\n" +
                        "  \"remarks\": \"Large amount\"\n" +
                        "}")
                .when()
                .post("/upi/payment")
                .then()
                .statusCode(400);

        System.out.println("✓ Test passed: Amount validation works");
    }

    @Test
    @Order(6)
    @DisplayName("Should extract and verify transaction details")
    public void testTransactionExtraction() {
        Response response =
                given()
                        .contentType("application/json")
                        .body("{\n" +
                                "  \"fromUpiId\": \"user3@upi\",\n" +
                                "  \"toUpiId\": \"user4@upi\",\n" +
                                "  \"amount\": 500,\n" +
                                "  \"remarks\": \"Test extraction\"\n" +
                                "}")
                        .when()
                        .post("/upi/payment")
                        .then()
                        .statusCode(201)
                        .extract()
                        .response();

        String transactionId = response.path("data.transactionId");
        System.out.println("Transaction ID: " + transactionId);

        // Verify transaction status
        given()
                .when()
                .get("/transactions/" + transactionId)
                .then()
                .statusCode(200)
                .body("data.status", equalTo("SUCCESS"))
                .body("data.amount", equalTo(500.0f));

        System.out.println("✓ Test passed: Transaction extraction and verification");
    }

    @AfterAll
    public static void tearDown() {
        System.out.println("=== UPI Payment Functional Tests Completed ===\n");
    }
}