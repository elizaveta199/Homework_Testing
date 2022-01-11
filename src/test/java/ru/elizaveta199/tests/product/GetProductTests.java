package ru.elizaveta199.tests.product;

import ru.elizaveta199.tests.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static ru.elizaveta199.Endpoints.GET_PRODUCT_ENDPOINT;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
@Epic("Tests for product")
@Story("Get Product tests")
public class GetProductTests extends BaseTest {

    @Severity(SeverityLevel.BLOCKER)
    @Test
    void getProductsPositiveTest() {
            given()
                    .response()
                    .spec(productResponseSpec)
                    .when()
                    .get(GET_PRODUCT_ENDPOINT)
                    .prettyPeek();
    }

    @Severity(SeverityLevel.BLOCKER)
    @Test
    void getProductPositiveTest() {
                given()
                .response()
                        .spec(productResponseSpec)
                .when()
                .get(GET_PRODUCT_ENDPOINT + "18736");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Test
    void getProductNegativeNoExIdTest() {
        String nonexistentid = "99999";
        Response response =
                given()
                        .response()
                        .spec(productResponseNoExIdSpec)
                        .when()
                        .get(GET_PRODUCT_ENDPOINT + nonexistentid)
                        .prettyPeek();
        assertThat(response.body().jsonPath().get("message"), equalTo("Unable to find product with id: " + nonexistentid));
    }

    @Severity(SeverityLevel.TRIVIAL)
    @Test
    void getProductNegativeNullIdTest() {
        String nullid = null;
        Response response =
                given()
                        .response()
                        .spec(productResponseIncTypeIdSpec)
                        .when()
                        .get(GET_PRODUCT_ENDPOINT + nullid)
                        .prettyPeek();
        assertThat(response.body().jsonPath().get("error"), equalTo("Bad Request"));
    }

    @Severity(SeverityLevel.TRIVIAL)
    @Test
    void getProductNegativeStrIdTest() {
        String strid = "test";
        Response response =
                given()
                        .response()
                        .spec(productResponseIncTypeIdSpec)
                        .when()
                        .get(GET_PRODUCT_ENDPOINT + strid)
                        .prettyPeek();
        assertThat(response.body().jsonPath().get("error"), equalTo("Bad Request"));
    }

}
