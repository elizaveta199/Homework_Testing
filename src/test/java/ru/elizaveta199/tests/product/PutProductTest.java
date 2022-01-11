package ru.elizaveta199.tests.product;

import ru.elizaveta199.tests.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Test;

import static ru.elizaveta199.Endpoints.GET_PRODUCT_ENDPOINT;
import static io.restassured.RestAssured.given;

@Epic("Tests for product")
@Story("Put Product tests")
public class PutProductTest extends BaseTest {

    @Test
    void putProductPositiveTest() {
        given(putProductPositiveRequestSpec, putProductPositiveResponseSpec)
                .put(GET_PRODUCT_ENDPOINT)
                .prettyPeek();
    }

    @Test
    void putProductNegativeNoProductTest() {
        given(putProductNegativeNoProductRequestSpec, putProductNegativeNoProductResponseSpec)
                .put(GET_PRODUCT_ENDPOINT)
                .prettyPeek();
    }

}
