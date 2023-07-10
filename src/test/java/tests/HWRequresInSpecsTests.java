package tests;

import models.lombok.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.*;

import static org.assertj.core.api.Assertions.assertThat;
import static specs.LoginSpecs.*;

@Tag("spec")
public class HWRequresInSpecsTests {

        @Test
        @DisplayName("Check single user email")
        void singlelUserEmailTest() {
            String expectedUserEmail = "janet.weaver@reqres.in";
            SingleUserResponseLombokModel response = step("Make request", () ->
            given(logRequestSpec)
                    .when()
                    .get("/users/2")
                    .then()
                    .spec(loginResponse200OKSpec)
                    .extract().as(SingleUserResponseLombokModel.class));
            step("Check response", () ->
                    assertThat(response.getData().getEmail().equals(expectedUserEmail)));
        }

        @Test
        @DisplayName("Check single user not found")
        void singleUserNotFoundTest() {
            NotFoundUserLombokModel response = step("Make request", () ->
                    given(logRequestSpec)
                            .when()
                            .get("/users/23")
                            .then()
                            .spec(loginResponse404Spec)
                            .extract().as(NotFoundUserLombokModel.class));
            step("Check response", () ->
                    assertThat(response.getError()).isNull());
        }

        @Test
        @DisplayName("Check list users")
        void listUserTest() {
        Integer expectedIdFirstUser = 7;
        Integer expectedIdLastUser = 12;

        ListUsersLombokModel response = step("Make a request", () -> given(logRequestSpec)
                .when()
                .get("/users?page=2")
                .then()
                .spec(loginResponse200OKSpec)
                .extract().as(ListUsersLombokModel.class));

        step("Check first user id", () ->
                assertThat(response.getData().getFirst().getId()).isEqualTo(expectedIdFirstUser));

        step("Check last user id", () ->
                assertThat(response.getData().getLast().getId()).isEqualTo(expectedIdLastUser));
    }


        @Test
        @DisplayName("Check list DELAYED RESPONSE")
        void delayedResponseTest() {
            Integer expectedIdFirstUser = 1;
            Integer expectedIdLastUser = 6;

            DelayedResponseLombokModel response = step("Make request", () ->
                    given(logRequestSpec)
                            .when()
                            .get("users?delay=3")
                            .then()
                            .spec(loginResponse200OKSpec)
                            .extract().as(DelayedResponseLombokModel.class));

            step("Check first user id", () ->
                    assertThat(response.getData().getFirst().getId()).isEqualTo(expectedIdFirstUser));

            step("Check last user id", () ->
                    assertThat(response.getData().getLast().getId()).isEqualTo(expectedIdLastUser));
        }

        @Test
        @DisplayName("Check successful registration")
        void successfulRegisterTest() {
            RegisterLombokModel registerLombokModel = new RegisterLombokModel();
            registerLombokModel.setEmail("eve.holt@reqres.in");
            registerLombokModel.setPassword("pistol");


            LoginResponseLombokModel response = step("Make request", () ->
                    given(loginRequestSpec)
                            .body(registerLombokModel)
                            .when()
                            .post("register")
                            .then()
                            .spec(loginResponse200OKSpec)
                            .extract().as(LoginResponseLombokModel.class));

            step("Check user ID", () ->
                    assertThat(response.getId()).isNotNull());

            step("Check token", () ->
                    assertThat(response.getToken()).isNotNull());
        }
}
