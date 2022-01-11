package ru.elizaveta199.tests.product;

import ru.elizaveta199.dto.Product;
import ru.elizaveta199.tests.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static ru.elizaveta199.Endpoints.POST_PRODUCT_ENDPOINT;
import static ru.elizaveta199.Endpoints.PRODUCT_ID_ENDPOINT;
import static ru.elizaveta199.asserts.CommonAsserts.postProductPositiveAssert;
import static io.restassured.RestAssured.given;

@Epic("Tests for product")
@Story("Post Product tests")
@Severity(SeverityLevel.CRITICAL)
public class PostProductTests extends BaseTest {
    Integer id;

    @BeforeEach
    void setUp() {

    }

    @Test
    void postProductPositiveTest() {
        id = given(postProductPositiveRequestSpec, postProductPositiveResponseSpec)
                .post(POST_PRODUCT_ENDPOINT)
                .prettyPeek()
                .jsonPath()
                .get("id");
    }

    @Test
    void postProductNegativeNotNullIdTest() {
        given(postProductNegativeNotNullIdRequestSpec, postProductNegativeNotNullIdResponseSpec)
                .post(POST_PRODUCT_ENDPOINT)
                .prettyPeek();
    }

    @Test
    void postProductNegativeNoIdTest() {
        given(postProductNegativeNoIdRequestSpec, postProductNegativeNoIdResponseSpec)
                .post(POST_PRODUCT_ENDPOINT)
                .prettyPeek();
    }

    @Test
    void postProductNegativeNoTitleTest() {
        given(postProductNegativeNoTitleRequestSpec, postProductNegativeNoTitleResponseSpec)
                .post(POST_PRODUCT_ENDPOINT)
                .prettyPeek();
    }

    @Test
    void postProductNegativeNoStringTitleTest() {
        given(postProductNegativeNoStringTitleRequestSpec, postProductNegativeNoStringTitleResponseSpec)
                .post(POST_PRODUCT_ENDPOINT)
                .prettyPeek();
    }

    @Test
    void postProductNegativeNoPriceTest() {
        given(postProductNegativeNoPriceRequestSpec, postProductNegativeNoPriceResponseSpec)
                .post(POST_PRODUCT_ENDPOINT)
                .prettyPeek();
    }

    @Test
    void postProductNegativeNoCatTitleTest() {
        given(postProductNegativeNoCatTitleRequestSpec, postProductNegativeNoCatTitleResponseSpec)
                .post(POST_PRODUCT_ENDPOINT)
                .prettyPeek();
    }

    @Test
    void postProductNegativeMinusPriceTest() {
        given(postProductNegativeMinusPriceRequestSpec, postProductNegativeMinusPriceResponseSpec)
                .post(POST_PRODUCT_ENDPOINT)
                .prettyPeek();
    }

    @Test
    void postProductNegativeFloatPriceTest() {
        given(postProductNegativeFloatPriceRequestSpec, postProductNegativeFloatPriceResponseSpec)
                .post(POST_PRODUCT_ENDPOINT)
                .prettyPeek();
    }

    @Test
    void postProductNegativeStrPriceTest() {
        given(postProductNegativeStrPriceRequestSpec, postProductNegativeStrPriceResponseSpec)
                .post(POST_PRODUCT_ENDPOINT)
                .prettyPeek();
    }

    @Test
    void postProductNegativeStrIdTest() {
        given(postProductNegativeStrIdRequestSpec, postProductNegativeStrIdResponseSpec)
                .post(POST_PRODUCT_ENDPOINT)
                .prettyPeek();
    }

    @Test
    void postProductWithDifferentAssertsPositiveTest() {
        Product response = given(postProductPositiveRequestSpec, postProductPositiveResponseSpec)
                .post(POST_PRODUCT_ENDPOINT)
                .prettyPeek()
                .body()
                .as(Product.class);
        postProductPositiveAssert(postProductPositive, response);
    }

    @AfterEach
    void tearDown() {
        if (id != null)
            given()
                    .response()
                    .spec(deleteResponseSpec)
                    .when()
                    .delete(PRODUCT_ID_ENDPOINT, id)
                    .prettyPeek()
                    .then()
                    .statusCode(200);
    }
}
