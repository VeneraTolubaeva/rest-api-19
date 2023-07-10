package tests.Lessons;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import models.lombok.LoginResponseLombokModel;
import models.lombok.RegisterLombokModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static models.helpers.CustomAllureListener.withCustomTemplates;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.LoginSpecs.loginRequestSpec;
import static specs.LoginSpecs.loginResponseSpec;

public class ReqresInExtendedAllureTests {

    @BeforeEach
    void setupUriAndPath() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
    }

    @Test
    void successfulLoginWithAllureTest() {
        RegisterLombokModel requestBody = new RegisterLombokModel();
        requestBody.setEmail("eve.holt@reqres.in");
        requestBody.setPassword("cityslicka");

        LoginResponseLombokModel loginResponseModel = given()
                .log().uri()
                .log().body()
                .filter(new AllureRestAssured())
                .contentType(JSON)
                .body(requestBody)
                .when()
                .post("login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(LoginResponseLombokModel.class);

        assertEquals("QpwL5tke4Pnpja7X4", loginResponseModel.getToken());
    }

    @Test
    void successfulLoginWithAllureAsConfigTest() {
        RestAssured.filters(new AllureRestAssured());
        RegisterLombokModel requestBody = new RegisterLombokModel();
        requestBody.setEmail("eve.holt@reqres.in");
        requestBody.setPassword("cityslicka");

        LoginResponseLombokModel loginResponseModel = given()
                .log().uri()
                .log().body()
                .contentType(JSON)
                .body(requestBody)
                .when()
                .post("login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(LoginResponseLombokModel.class);

        assertEquals("QpwL5tke4Pnpja7X4", loginResponseModel.getToken());
    }

    @Test
    void successfulLoginWithCustomAllureTest() {
        RegisterLombokModel requestBody = new RegisterLombokModel();
        requestBody.setEmail("eve.holt@reqres.in");
        requestBody.setPassword("cityslicka");

        LoginResponseLombokModel loginResponseModel = given()
                .log().uri()
                .log().body()
                .filter(withCustomTemplates())
                .contentType(JSON)
                .body(requestBody)
                .when()
                .post("login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(LoginResponseLombokModel.class);

        assertEquals("QpwL5tke4Pnpja7X4", loginResponseModel.getToken());
    }
    @Test
    void successfulLoginWithStepsTest() {

        RegisterLombokModel requestBody = new RegisterLombokModel();
        requestBody.setEmail("eve.holt@reqres.in");
        requestBody.setPassword("cityslicka");

        LoginResponseLombokModel loginResponseModel = step("Make request", () ->
        given()
                .log().uri()
                .log().body()
                .filter(withCustomTemplates())
                .contentType(JSON)
                .body(requestBody)
                .when()
                .post("login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(LoginResponseLombokModel.class));

        step("Check response", () ->
        assertEquals("QpwL5tke4Pnpja7X4", loginResponseModel.getToken()));
    }
    @Test
    void successfulLoginWithSpecsTest() {

        RegisterLombokModel requestBody = new RegisterLombokModel();
        requestBody.setEmail("eve.holt@reqres.in");
        requestBody.setPassword("cityslicka");

        LoginResponseLombokModel loginResponseModel = step("Make request", () ->
                given(loginRequestSpec)
//                        .spec(loginRequestSpec)
                        .body(requestBody)
                        .when()
                        .post("/login")
                        .then()
                        .spec(loginResponseSpec)
                        .extract().as(LoginResponseLombokModel.class));

        step("Check response", () ->
                assertEquals("QpwL5tke4Pnpja7X4", loginResponseModel.getToken()));
    }
}