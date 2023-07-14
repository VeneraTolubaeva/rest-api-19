package tests.demowebshop;

import API.AuthAPI;
import models.lombok.AddToCartResp;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import static API.AuthAPI.authCookieKey;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static models.helpers.CustomAllureListener.withCustomTemplates;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CartTests extends TestBase {
    String  authCookieValue = AuthAPI.getAuthCookie(login,password) ;
    int countOfItems;
    int quantity = 2;
    String data = "product_attribute_72_5_18=52" +
            "&product_attribute_72_6_19=54" +
            "&product_attribute_72_3_20=58" +
            "&addtocart_72.EnteredQuantity=" + quantity;
    @Test
    void addToCartAsAuthorizedTest() {

       step("Check number of items in cart", () -> {
            String browserPage = given()
                    .cookie(authCookieKey, authCookieValue)
                    .when()
                    .get("/cart")
                    .then()
                    .statusCode(200)
                    .extract()
                    .asString();

            Document doc = Jsoup.parse(browserPage);
            String count = doc.select(".cart-qty").text();
            countOfItems = Integer.parseInt(count.substring(1, count.length()-1));
        });

        AddToCartResp response = step("Add items to cart", () -> given()
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .cookie(AuthAPI.authCookieKey, authCookieValue)
                .filter(withCustomTemplates())
                .body(data)
                .when()
                .post("/addproducttocart/details/72/1")
                .then()
                .statusCode(200)
                .extract().as(AddToCartResp.class));

        step("Check adding items to cart", () -> {
            assertEquals("true", response.getSuccess());
            assertEquals("The product has been added to your <a href=\"/cart\">shopping cart</a>", response.getMessage());
            assertEquals("(" + (countOfItems + quantity) + ")", response.getUpdatetopcartsectionhtm());
        });
    }

//    @Test
//    void addToCartAsAnonymTest() {
//
//        given()
//                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
//                .body(data)
//                .when()
//                .post("/addproducttocart/details/72/1")
//                .then()
//                .log().all()
//                .statusCode(200)
//                .body("success", is(true))
//                .body("message", is("The product has been added to your <a href=\"/cart\">shopping cart</a>"))
//                .body("updatetopcartsectionhtml", is("(2)"));
//    }
}
