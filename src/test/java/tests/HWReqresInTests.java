package tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;

public class HWReqresInTests {

    @BeforeEach
    void setupUriAndPath() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
    }


    @Test
    @DisplayName("Проверка email существующего пользователя")
    void singlelUserEmailTest() {
        given()
                .log().uri()
                .log().body()
                .when()
                .get("users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.email", is("janet.weaver@reqres.in"));
    }

    @Test
    @DisplayName("Проверка не существующего пользователя")
    void singleUserNotFoundTest() {
        get("users/23")
                .then()
                .log().status()
                .log().body()
                .statusCode(404);
    }

    @Test
    @DisplayName("Проверка списка цветовой гаммы")
    void listResourceTest() {
        get("unknown")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.id", hasItems(1, 2, 3, 4, 5, 6))
                .body("data.name", hasItems("cerulean", "fuchsia rose", "true red", "aqua sky", "tigerlily", "blue turquoise"))
                .body("data.year", hasItems(2000, 2001, 2002, 2003, 2004, 2005))
                .body("data.color", hasItems("#98B2D1", "#C74375", "#BF1932", "#7BC4C4", "#E2583E", "#53B0AE"))
                .body("data.pantone_value", hasItems("15-4020", "17-2031", "19-1664", "14-4811", "17-1456", "15-5217"));
    }

    @Test
    @DisplayName("Проверка списка DELAYED RESPONSE")
    void delayedResponseTest() {
        given()
                .log().uri()
                .log().body()
                .when()
                .get("users?delay=3")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.id", hasItems(1, 2, 3, 4, 5, 6));
    }

    @Test
    @DisplayName("Проверка удачной регистрации пользователя")
    void successfulRegisterTest() {
        String requestBody = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\" }";

        given()
                .log().uri()
                .log().body()
                .contentType(JSON)
                .body(requestBody)
                .when()
                .post("register")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("register-user-scheme.json"));
    }
}
