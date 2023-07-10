package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;
import static io.restassured.http.ContentType.JSON;
import static models.helpers.CustomAllureListener.withCustomTemplates;
import static org.hamcrest.Matchers.notNullValue;

public class LoginSpecs {
    public static RequestSpecification loginRequestSpec = with()
            .log().uri()
            .log().body()
            .filter(withCustomTemplates())
            .contentType(JSON)
            .baseUri("https://reqres.in")
            .basePath("/api");
    public static RequestSpecification logRequestSpec = with()
            .log().uri()
            .log().body()
            .filter(withCustomTemplates())
            .baseUri("https://reqres.in")
            .basePath("/api");

    public static ResponseSpecification loginResponseSpec = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(200)
            .expectBody("token", notNullValue())
            .build();
    public static ResponseSpecification loginResponse200OKSpec = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(200)
            .build();
    public static ResponseSpecification loginResponseListSpec = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(200)
//            .expectBody("id", notNullValue())
//            .expectBody("name", notNullValue())
//            .expectBody("year", notNullValue())
//            .expectBody("color", notNullValue())
//            .expectBody("pantone_value", notNullValue())
            .build();
    public static ResponseSpecification loginResponse404Spec = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(404)
            .build();
}
