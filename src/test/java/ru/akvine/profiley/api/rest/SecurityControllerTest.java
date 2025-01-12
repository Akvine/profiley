package ru.akvine.profiley.api.rest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import ru.akvine.profiley.RandomGenerator;
import ru.akvine.profiley.api.IntegrationTestsConfig;
import ru.akvine.profiley.api.RestMethods;
import ru.akvine.profiley.api.TestUtils;
import ru.akvine.profiley.constants.ErrorCodes;
import ru.akvine.profiley.rest.dto.common.AuthRequest;
import ru.akvine.profiley.rest.dto.common.RegistrationRequest;

import static org.hamcrest.Matchers.equalTo;

@DisplayName("SecurityController tests")
public class SecurityControllerTest extends IntegrationTestsConfig {

    @Test
    @DisplayName("Successful registration test")
    void successful_registration() {
        RegistrationRequest request = new RegistrationRequest();
        request.setEmail("testemail@gmail.com");
        request.setPassword("jkdsjm!394L");

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
    @DisplayName("Failed registration cause password doesn't match requirements")
    void failed_registration_password_doesnt_match_requirements() {
        RegistrationRequest request = new RegistrationRequest();
        request.setEmail("testemail@gmail.com");
        request.setPassword("1234567788");

        RestAssured
                .given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(RestMethods.Security.REGISTRATION_ENDPOINT)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(CODE_FIELD_NAME, equalTo(ErrorCodes.Validation.PASSWORD_INVALID_ERROR))
                .body(MESSAGE_FIELD_NAME, equalTo("Password is invalid!"))
                .body(DESCRIPTION_FIELD_NAME, equalTo("Password is invalid!"));
    }

    @Test
    @DisplayName("Successful auth test")
    void successful_auth_test() {
        AuthRequest request = new AuthRequest()
                .setEmail("user1@example.com")
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
                .setEmail(TestUtils.EXIST_USER_EMAIL_1)
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

    @Test
    @DisplayName("Failed auth cause user not found")
    void failed_auth_cause_user_not_found() {
        String email = "testemail@gmail.com";
        String expectedErrorMessage = "User not found by email = [" + email + "]";
        AuthRequest request = new AuthRequest()
                .setEmail(email)
                .setPassword(USER_PASSWORD);
        RestAssured
                .given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(RestMethods.Security.AUTHENTICATION_ENDPOINT)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(CODE_FIELD_NAME, equalTo(ErrorCodes.User.USER_NOT_FOUND_ERROR))
                .body(MESSAGE_FIELD_NAME, equalTo(expectedErrorMessage))
                .body(DESCRIPTION_FIELD_NAME, equalTo(expectedErrorMessage));
    }

    @Test
    @DisplayName("Failed auth cause bad credentials")
    void failed_auth_cause_password_invalid() {
        String expectedErrorMessage = "Bad credentials!";

        AuthRequest request = new AuthRequest()
                .setEmail(TestUtils.EXIST_USER_EMAIL_1)
                .setPassword("some another password");

        RestAssured
                .given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(RestMethods.Security.AUTHENTICATION_ENDPOINT)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(CODE_FIELD_NAME, equalTo(ErrorCodes.User.BAD_CREDENTIALS_ERROR))
                .body(MESSAGE_FIELD_NAME, equalTo(expectedErrorMessage))
                .body(DESCRIPTION_FIELD_NAME, equalTo(expectedErrorMessage));
    }

    @Test
    @DisplayName("Successful logout")
    void successful_logout() {
        RestAssured
                .given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .when()
                .post(RestMethods.Security.LOGOUT_ENDPOINT)
                .then()
                .statusCode(HttpStatus.OK.value());
    }
}
