package tests.demowebshop;

import API.AuthAPI;
import org.junit.jupiter.api.Test;

import static API.AuthAPI.authCookieKey;
import static io.restassured.RestAssured.given;

public class EditProfileTest extends TestBase {

    @Test
    void editUserProfileTest() {
        String valueId = "31456798",
                valueFirstName = "Lulu",
                valueLastName = "Tutu",
                valueEmail = "venera@mail.ru",
                valueCompany = "CR",
                valueCountryId = "12",
                valueStateProvinceId = "0",
                valueCity = "Moscow",
                valueAddress1 = "Lenina",
                valueAddress2 = "Mira",
                valueZipPostalCode = "562014",
                valuePhoneNumber = "583902",
                valueFaxNumber = "";

        String authCookieValue = AuthAPI.getAuthCookie(login, password);

        given()
                .contentType("application/x-www-form-urlencoded")
                .cookie(authCookieKey, authCookieValue)
                .formParam("Address.Id", valueId)
                .formParam("Address.FirstName", valueFirstName)
                .formParam("Address.LastName", valueLastName)
                .formParam("Address.Email", valueEmail)
                .formParam("Address.Company", valueCompany)
                .formParam("Address.CountryId", valueCountryId)
                .formParam("Address.StateProvinceId", valueStateProvinceId)
                .formParam("Address.City", valueCity)
                .formParam("Address.Address1", valueAddress1)
                .formParam("Address.Address2", valueAddress2)
                .formParam("Address.ZipPostalCode", valueZipPostalCode)
                .formParam("Address.PhoneNumber", valuePhoneNumber)
                .formParam("Address.FaxNumber", valueFaxNumber)
                .when()
                .post("/customer/addressedit/3121228")
                .then()
                .log().all()
                .assertThat()
                .statusCode(302);

    }
}
