package ru.akvine.profiley.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.akvine.profiley.ProfileyApplication;
import ru.akvine.profiley.rest.dto.common.AuthRequest;
import ru.akvine.profiley.utils.Asserts;

@SpringBootTest(classes = ProfileyApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public abstract class IntegrationTestsConfig {
    @LocalServerPort
    public int serverPort;

    @Value("${spring.session.cookie-name}")
    protected String cookieSessionName;

    @PostConstruct
    public void initRestAssured() {
        RestAssured.port = serverPort;
        RestAssured.urlEncodingEnabled = false;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @ServiceConnection
    private static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15-alpine");

    static {
        postgreSQLContainer
                .withUrlParam("serverTimezone", "UTC")
                .withReuse(true)
                .start();
    }

    @PreDestroy
    public void destroy() {
        postgreSQLContainer.stop();
    }

    protected static final String USER_PASSWORD = "n*ribG@h1Pj15l>";

    protected static final String CODE_FIELD_NAME = "code";
    protected static final String MESSAGE_FIELD_NAME = "message";
    protected static final String DESCRIPTION_FIELD_NAME = "description";

    protected String doAuth(String email) {
        Asserts.isNotNull(email, "email is null");

        AuthRequest request = new AuthRequest()
                .setEmail(email)
                .setPassword(USER_PASSWORD);
        Response response = RestAssured
                .given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(RestMethods.Security.AUTHENTICATION_ENDPOINT)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract().response();
        return response.getCookie(cookieSessionName);
    }
}
