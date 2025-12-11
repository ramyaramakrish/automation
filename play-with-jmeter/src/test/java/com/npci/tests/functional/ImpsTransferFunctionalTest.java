package com.npci.tests.functional;

import com.npci.tests.utils.TestConfig;
import io.restassured.RestAssured;
import org.junit.jupiter.api.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("IMPS Transfer Functional Tests")
public class ImpsTransferFunctionalTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = TestConfig.BASE_URL;
        RestAssured.basePath = TestConfig.BASE_PATH;
        System.out.println("\n=== Starting IMPS Transfer Functional Tests ===");
    }

    @Test
    @Order(1)
    @DisplayName("Should successfully process IMPS transfer")
    public void testSuccessfulImpsTransfer() {
        given()
                .contentType("application/json")
                .body("{\n" +
                        "  \"fromAccount\": \"ACC000001\",\n" +
                        "  \"toAccount\": \"ACC000002\",\n" +
                        "  \"ifscCode\": \"SBIN0001234\",\n" +
                        "  \"amount\": 5000,\n" +
                        "  \"remarks\": \"IMPS functional test\"\n" +
                        "}")
                .when()
                .post("/imps/transfer")
                .then()
                .statusCode(201)
                .body("success", equalTo(true))
                .body("data.status", equalTo("SUCCESS"))
                .body("data.transactionId", notNullValue())
                .body("data.amount", equalTo(5000.0f))
                .time(lessThan(2000L));

        System.out.println("✓ Test passed: Successful IMPS transfer");
    }

    @Test
    @Order(2)
    @DisplayName("Should validate IFSC code format")
    public void testInvalidIfscCode() {
        given()
                .contentType("application/json")
                .body("{\n" +
                        "  \"fromAccount\": \"ACC000001\",\n" +
                        "  \"toAccount\": \"ACC000002\",\n" +
                        "  \"ifscCode\": \"INVALID\",\n" +
                        "  \"amount\": 5000,\n" +
                        "  \"remarks\": \"Test\"\n" +
                        "}")
                .when()
                .post("/imps/transfer")
                .then()
                .statusCode(400)
                .body("success", equalTo(false));

        System.out.println("✓ Test passed: Invalid IFSC code rejected");
    }

    @Test
    @Order(3)
    @DisplayName("Should handle insufficient balance for IMPS")
    public void testInsufficientBalanceImps() {
        given()
                .contentType("application/json")
                .body("{\n" +
                        "  \"fromAccount\": \"ACC000001\",\n" +
                        "  \"toAccount\": \"ACC000002\",\n" +
                        "  \"ifscCode\": \"SBIN0001234\",\n" +
                        "  \"amount\": 999999,\n" +
                        "  \"remarks\": \"Large transfer\"\n" +
                        "}")
                .when()
                .post("/imps/transfer")
                .then()
                .statusCode(400)
                .body("success", equalTo(false));

        System.out.println("✓ Test passed: Insufficient balance handled");
    }

    @Test
    @Order(4)
    @DisplayName("Should validate IMPS amount limits")
    public void testImpsAmountLimits() {
        // Test below minimum
        given()
                .contentType("application/json")
                .body("{\n" +
                        "  \"fromAccount\": \"ACC000001\",\n" +
                        "  \"toAccount\": \"ACC000002\",\n" +
                        "  \"ifscCode\": \"SBIN0001234\",\n" +
                        "  \"amount\": 0,\n" +
                        "  \"remarks\": \"Zero amount\"\n" +
                        "}")
                .when()
                .post("/imps/transfer")
                .then()
                .statusCode(400);

        // Test above maximum (200000)
        given()
                .contentType("application/json")
                .body("{\n" +
                        "  \"fromAccount\": \"ACC000001\",\n" +
                        "  \"toAccount\": \"ACC000002\",\n" +
                        "  \"ifscCode\": \"SBIN0001234\",\n" +
                        "  \"amount\": 250000,\n" +
                        "  \"remarks\": \"Exceeds limit\"\n" +
                        "}")
                .when()
                .post("/imps/transfer")
                .then()
                .statusCode(400);

        System.out.println("✓ Test passed: IMPS amount limits validated");
    }

    @AfterAll
    public static void tearDown() {
        System.out.println("=== IMPS Transfer Functional Tests Completed ===\n");
    }
}