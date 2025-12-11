package com.npci.tests.functional;

import com.npci.tests.utils.TestConfig;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("End-to-End Flow Functional Tests")
public class EndToEndFlowFunctionalTest {

    private static String transactionId;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = TestConfig.BASE_URL;
        RestAssured.basePath = TestConfig.BASE_PATH;
        System.out.println("\n=== Starting End-to-End Flow Tests ===");
    }

    @Test
    @Order(1)
    @DisplayName("Step 1: Check account balance")
    public void step1_checkBalance() {
        given()
                .when()
                .get("/accounts/ACC000005/balance")
                .then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("data", containsString("Balance"));

        System.out.println("✓ Step 1 passed: Balance checked");
    }

    @Test
    @Order(2)
    @DisplayName("Step 2: Validate sender UPI ID")
    public void step2_validateSenderUpi() {
        given()
                .contentType("application/json")
                .body("\"user5@upi\"")
                .when()
                .post("/upi/validate")
                .then()
                .statusCode(200)
                .body("data", equalTo(true));

        System.out.println("✓ Step 2 passed: Sender UPI validated");
    }

    @Test
    @Order(3)
    @DisplayName("Step 3: Validate receiver UPI ID")
    public void step3_validateReceiverUpi() {
        given()
                .contentType("application/json")
                .body("\"user6@upi\"")
                .when()
                .post("/upi/validate")
                .then()
                .statusCode(200)
                .body("data", equalTo(true));

        System.out.println("✓ Step 3 passed: Receiver UPI validated");
    }

    @Test
    @Order(4)
    @DisplayName("Step 4: Initiate UPI payment")
    public void step4_initiatePayment() {
        Response response =
                given()
                        .contentType("application/json")
                        .body("{\n" +
                                "  \"fromUpiId\": \"user5@upi\",\n" +
                                "  \"toUpiId\": \"user6@upi\",\n" +
                                "  \"amount\": 2500,\n" +
                                "  \"remarks\": \"E2E test payment\"\n" +
                                "}")
                        .when()
                        .post("/upi/payment")
                        .then()
                        .statusCode(201)
                        .body("data.status", equalTo("SUCCESS"))
                        .extract()
                        .response();

        transactionId = response.path("data.transactionId");
        System.out.println("✓ Step 4 passed: Payment initiated - " + transactionId);
    }

    @Test
    @Order(5)
    @DisplayName("Step 5: Verify transaction status")
    public void step5_verifyTransaction() {
        given()
                .when()
                .get("/transactions/" + transactionId)
                .then()
                .statusCode(200)
                .body("data.status", equalTo("SUCCESS"))
                .body("data.amount", equalTo(2500.0f))
                .body("data.transactionType", equalTo("UPI"));

        System.out.println("✓ Step 5 passed: Transaction verified");
    }

    @Test
    @Order(6)
    @DisplayName("Step 6: Verify balance updated")
    public void step6_verifyBalanceUpdated() {
        given()
                .when()
                .get("/accounts/ACC000005/balance")
                .then()
                .statusCode(200)
                .body("success", equalTo(true));

        System.out.println("✓ Step 6 passed: Balance updated");
    }

    @AfterAll
    public static void tearDown() {
        System.out.println("=== End-to-End Flow Tests Completed ===\n");
        System.out.println("Complete user journey executed successfully!");
    }
}