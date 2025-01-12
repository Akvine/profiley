package ru.akvine.profiley.api.rest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import ru.akvine.profiley.RandomGenerator;
import ru.akvine.profiley.api.IntegrationTestsConfig;
import ru.akvine.profiley.api.RestMethods;
import ru.akvine.profiley.constants.ErrorCodes;
import ru.akvine.profiley.rest.dto.domain.CreateDomainRequest;
import ru.akvine.profiley.rest.dto.domain.ListDomainRequest;
import ru.akvine.profiley.rest.dto.domain.UpdateDomainRequest;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsNot.not;
import static ru.akvine.profiley.api.TestUtils.*;

@DisplayName("DomainController tests")
public class DomainControllerTest extends IntegrationTestsConfig {

    @Test
    @DisplayName("Successful create domain")
    void successful_create_domain() {
        String sessionCookie = doAuth(EXIST_USER_EMAIL_1);

        String generatedDomain = RandomGenerator.generateRandomString();
        CreateDomainRequest request = new CreateDomainRequest().setName(generatedDomain);

        RestAssured
                .given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .cookie(cookieSessionName, sessionCookie)
                .body(request)
                .when()
                .post(RestMethods.Domain.DOMAIN_ENDPOINT)
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("Failed create domain cause already exists")
    void failed_create_domain_cause_already_exists() {
        String sessionCookie = doAuth(EXIST_USER_EMAIL_1);

        CreateDomainRequest request = new CreateDomainRequest()
                .setName(DOMAIN_ALREADY_EXISTS_1);

        String expectedErrorMessage = String.format(
                "Domain with name = [%s] already exists for user with uuid = [%s]",
                DOMAIN_ALREADY_EXISTS_1,
                EXISTS_USER_UUID_1
        );

        RestAssured
                .given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .cookie(cookieSessionName, sessionCookie)
                .body(request)
                .when()
                .post(RestMethods.Domain.DOMAIN_ENDPOINT)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(CODE_FIELD_NAME, equalTo(ErrorCodes.Domain.DOMAIN_ALREADY_EXISTS_ERROR))
                .body(MESSAGE_FIELD_NAME, equalTo(expectedErrorMessage))
                .body(DESCRIPTION_FIELD_NAME, equalTo(expectedErrorMessage));
    }

    @Test
    @DisplayName("Successful list system domains")
    void successful_list_system_domains() {
        String sessionCookie = doAuth(EXIST_USER_EMAIL_1);

        ListDomainRequest request = new ListDomainRequest();

        RestAssured
                .given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .cookie(cookieSessionName, sessionCookie)
                .body(request)
                .when()
                .get(RestMethods.Domain.DOMAIN_ENDPOINT)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("domains", notNullValue())
                .body("domains", not(empty()))
                .body("domains.size()", greaterThan(2));
    }

    @Test
    @DisplayName("Successful list empty without system domains")
    void successful_list_empty_without_system_domains() {
        String sessionCookie = doAuth(EXIST_USER_EMAIL_1);

        ListDomainRequest request = new ListDomainRequest()
                .setIncludeSystems(false);

        RestAssured
                .given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .cookie(cookieSessionName, sessionCookie)
                .body(request)
                .when()
                .get(RestMethods.Domain.DOMAIN_ENDPOINT)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("domains", notNullValue());
    }

    @Test
    @DisplayName("Successful update domain")
    void successful_update_domain() {
        String sessionCookie = doAuth(EXIST_USER_EMAIL_2);

        String newDomainName = "newDomainName";
        UpdateDomainRequest request = new UpdateDomainRequest()
                .setDomainName(DOMAIN_ALREADY_EXISTS_2)
                .setNewDomainName(newDomainName);

        RestAssured
                .given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .cookie(cookieSessionName, sessionCookie)
                .body(request)
                .when()
                .patch(RestMethods.Domain.DOMAIN_ENDPOINT)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("domains", notNullValue())
                .body("domains", not(empty()))
                .body("domains[0].name", equalTo(newDomainName))
                .body("domains[0].system", equalTo(false));

    }

}
