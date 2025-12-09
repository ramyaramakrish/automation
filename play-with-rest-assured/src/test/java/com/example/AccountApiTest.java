package com.example;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class AccountApiTest {

    @Test
    public void testGetAccountExist() {
        // Test implementation goes here using RestAssured library
        String endpoint = "http://localhost:8080/api/v1/accounts/234235423/exists";

        given()
                .when()
                .get(endpoint)
                .then()
                .statusCode(404);

    }

}
