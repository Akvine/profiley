package ru.akvine.profiley.api.rest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import ru.akvine.profiley.RandomGenerator;
import ru.akvine.profiley.api.IntegrationTestsConfig;
import ru.akvine.profiley.api.RestMethods;
import ru.akvine.profiley.rest.dto.common.AuthRequest;

@DisplayName("SecurityController tests")
public class SecurityControllerTest extends IntegrationTestsConfig {

    @Test
    @DisplayName("Successful registration test")
    void successful_registration() {
        AuthRequest request = new AuthRequest()
                .setUsername("testemail@gmail.com")
                .setPassword("some password");
        RestAssured
                .given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(RestMethods.Security.REGISTRATION_ENDPOINT)
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("Successful auth test")
    void successful_auth_test() {
        AuthRequest request = new AuthRequest()
                .setUsername("user1@example.com")
                .setPassword(USER_PASSWORD);
        RestAssured
                .given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(RestMethods.Security.AUTHENTICATION_ENDPOINT)
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("Failed auth cause invalid password")
    void failed_auth_cause_invalid_password() {
        AuthRequest request = new AuthRequest()
                .setUsername("user1@example.com")
                .setPassword(RandomGenerator.generateRandomString());
        RestAssured
                .given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(RestMethods.Security.AUTHENTICATION_ENDPOINT)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }
}
