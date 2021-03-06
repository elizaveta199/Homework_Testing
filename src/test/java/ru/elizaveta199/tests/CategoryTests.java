package ru.elizaveta199.tests;

import ru.elizaveta199.dto.Category;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static ru.elizaveta199.Endpoints.CATEGORY_ENDPOINT;
import static ru.elizaveta199.asserts.IsCategoryExists.isCategoryExists;
import static ru.elizaveta199.enums.CategoryType.FURNITURE;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@Epic("Tests for categories")
@Story("Get Category tests")
@Severity(SeverityLevel.MINOR)
public class CategoryTests extends BaseTest {

    @Severity(SeverityLevel.NORMAL)
    @Test
    @Description("Получить категорию 1")
    void getCategoryTest() {
        given()
                .response()
                .spec(categoriesResponseSpec)
                .when()
                .get(CATEGORY_ENDPOINT, 1);
    }

    @Test
    void getCategoryWithLogsTest() {
        given()
                .response()
                .spec(categoriesResponseSpec)
                        .
                when()
                .get(CATEGORY_ENDPOINT, 1)
                .prettyPeek();
    }

    @Test
    void getCategoryWithAssertsTest() {
        given()
                .response()
                .spec(categoriesResponseSpec)
                        .
                when()
                .get(CATEGORY_ENDPOINT, 1)
                .prettyPeek()
                .then()
                .body("id", equalTo(1));
    }

    @Test
    void getCategoryWithAssertsForResponseTest() {
        Response response =
                given()
                        .response()
                        .spec(categoriesResponseSpec)
                        .when()
                        .get(CATEGORY_ENDPOINT, 1);
        Category responseBody = response.body().as(Category.class);
        assertThat(response.body().jsonPath().get("products[0].categoryTitle"), equalTo("Food"));
        assertThat(responseBody.getProducts().get(0).getCategoryTitle(), equalTo("Food"));
    }

    @Test
    void getCategoryWithAssertsAfterTestForResponseTest() {
        Category response =
                given()
                        .response()
                        .spec(categoriesResponseSpec)
                        .when()
                        .get(CATEGORY_ENDPOINT, FURNITURE.getId())
                        .prettyPeek()
                        .body()
                        .as(Category.class);

        response.getProducts().forEach(
                e -> {
                    assertThat(e.getCategoryTitle(), isCategoryExists());
                    assertThat(e.getCategoryTitle(), equalTo(FURNITURE.getName()));
                }
        );
        assertThat(response.getTitle(), equalTo(FURNITURE.getName()));
        assertThat(response.getId(), equalTo(FURNITURE.getId()));
    }

    @Test
    void getCategoryWithAssertsAfterTestTest() {
        Category response =
                given()
                        .response()
                        .spec(categoriesResponseSpec)
                        .when()
                        .get(CATEGORY_ENDPOINT, 888)
                        .prettyPeek()
                        .body()
                        .as(Category.class);

        response.getProducts().forEach(
                e -> {
                    assertThat(e.getCategoryTitle(), isCategoryExists());
                    assertThat(e.getCategoryTitle(), equalTo(FURNITURE.getName()));
                }
        );
        assertThat(response.getTitle(), equalTo(FURNITURE.getName()));
        assertThat(response.getId(), equalTo(FURNITURE.getId()));
    }
}
