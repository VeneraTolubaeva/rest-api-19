package API;

import static io.restassured.RestAssured.given;
import static models.helpers.CustomAllureListener.withCustomTemplates;

public class AuthAPI {
    public static String authCookieKey = "NOPCOMMERCE.AUTH";
    public static String getAuthCookie(String login, String password) {

        return given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("Email", login)
                .formParam("Password", password)
                .when()
                .post("/login")
                .then()
                .log().all()
                .statusCode(302)
                .extract()
                .cookie(authCookieKey);
    }
}
