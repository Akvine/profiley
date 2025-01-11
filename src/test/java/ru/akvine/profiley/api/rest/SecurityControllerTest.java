package ru.akvine.profiley.api.rest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import ru.akvine.profiley.api.IntegrationTestsConfig;
import ru.akvine.profiley.api.RestMethods;
import ru.akvine.profiley.rest.dto.common.AuthRequest;

@DisplayName("SecurityController tests")
public class SecurityControllerTest extends IntegrationTestsConfig {

    @Test
    @DisplayName("Successful registration test")
    public void successful_registration() {
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
}
