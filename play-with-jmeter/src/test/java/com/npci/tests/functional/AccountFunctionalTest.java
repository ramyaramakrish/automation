package com.npci.tests.functional;

import com.npci.tests.utils.TestConfig;
import io.restassured.RestAssured;
import org.junit.jupiter.api.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Account Management Functional Tests")
public class AccountFunctionalTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = TestConfig.BASE_URL;
        RestAssured.basePath = TestConfig.BASE_PATH;
        System.out.println("\n=== Starting Account Functional Tests ===");
    }

    @Test
    @Order(1)
    @DisplayName("Should get account details successfully")
    public void testGetAccountDetails() {
        given()
                .when()
                .get("/accounts/ACC000001")
                .then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("data.accountNumber", equalTo("ACC000001"))
                .body("data.accountHolderName", notNullValue())
                .body("data.balance", notNullValue())
                .body("data.status", equalTo("ACTIVE"));

        System.out.println("✓ Test passed: Get account details");
    }

    @Test
    @Order(2)
    @DisplayName("Should get account balance")
    public void testGetAccountBalance() {
        given()
                .when()
                .get("/accounts/ACC000001/balance")
                .then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("data", containsString("Balance"))
                .time(lessThan(500L));

        System.out.println("✓ Test passed: Get account balance");
    }

    @Test
    @Order(3)
    @DisplayName("Should return 404 for non-existent account")
    public void testNonExistentAccount() {
        given()
                .when()
                .get("/accounts/INVALID999")
                .then()
                .statusCode(404)
                .body("success", equalTo(false))
                .body("message", containsString("Account not found"));

        System.out.println("✓ Test passed: Non-existent account handled");
    }

    @Test
    @Order(4)
    @DisplayName("Should get multiple account details")
    public void testMultipleAccounts() {
        for (int i = 1; i <= 5; i++) {
            String accountNumber = String.format("ACC%06d", i);

            given()
                    .when()
                    .get("/accounts/" + accountNumber)
                    .then()
                    .statusCode(200)
                    .body("data.accountNumber", equalTo(accountNumber))
                    .body("data.status", equalTo("ACTIVE"));
        }

        System.out.println("✓ Test passed: Multiple accounts retrieved");
    }

    @AfterAll
    public static void tearDown() {
        System.out.println("=== Account Functional Tests Completed ===\n");
    }
}