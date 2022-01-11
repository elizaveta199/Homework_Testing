package ru.elizaveta199.tests;

import com.github.javafaker.Faker;
import com.google.common.collect.ImmutableMap;
import ru.elizaveta199.dto.Product;
import ru.elizaveta199.dto.ProductAlt;
import ru.elizaveta199.dto.ProductNoId;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;

import static com.github.automatedowl.tools.AllureEnvironmentWriter.allureEnvironmentWriter;
import static ru.elizaveta199.enums.CategoryType.FURNITURE;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.HEADERS;
import static io.restassured.filter.log.LogDetail.METHOD;
import static io.restassured.filter.log.LogDetail.URI;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.hamcrest.Matchers.lessThan;

public abstract class BaseTest {

    static Properties properties = new Properties();
    static RequestSpecification logRequestSpecification;
    static ResponseSpecification responseSpecification;
    public static ResponseSpecification deleteResponseSpec;
    public static ResponseSpecification categoriesResponseSpec;
    public static ResponseSpecification productResponseSpec;
    public static ResponseSpecification productResponseNoExIdSpec;
    public static ResponseSpecification productResponseIncTypeIdSpec;

    public static Product postProductPositive;
    public static RequestSpecification postProductPositiveRequestSpec;
    public static ResponseSpecification postProductPositiveResponseSpec;

    public static Product postProductNegativeNotNullId;
    public static RequestSpecification postProductNegativeNotNullIdRequestSpec;
    public static ResponseSpecification postProductNegativeNotNullIdResponseSpec;

    public static Product postProductNegativeNoTitle;
    public static RequestSpecification postProductNegativeNoTitleRequestSpec;
    public static ResponseSpecification postProductNegativeNoTitleResponseSpec;

    public static Product postProductNegativeNoPrice;
    public static RequestSpecification postProductNegativeNoPriceRequestSpec;
    public static ResponseSpecification postProductNegativeNoPriceResponseSpec;

    public static Product postProductNegativeNoCatTitle;
    public static RequestSpecification postProductNegativeNoCatTitleRequestSpec;
    public static ResponseSpecification postProductNegativeNoCatTitleResponseSpec;

    public static Product postProductNegativeMinusPrice;
    public static RequestSpecification postProductNegativeMinusPriceRequestSpec;
    public static ResponseSpecification postProductNegativeMinusPriceResponseSpec;

    public static ProductAlt postProductNegativeStrPrice;
    public static RequestSpecification postProductNegativeStrPriceRequestSpec;
    public static ResponseSpecification postProductNegativeStrPriceResponseSpec;

    public static ProductAlt postProductNegativeStrId;
    public static RequestSpecification postProductNegativeStrIdRequestSpec;
    public static ResponseSpecification postProductNegativeStrIdResponseSpec;

    public static ProductAlt postProductNegativeFloatPrice;
    public static RequestSpecification postProductNegativeFloatPriceRequestSpec;
    public static ResponseSpecification postProductNegativeFloatPriceResponseSpec;

    public static ProductAlt postProductNegativeNoStringTitle;
    public static RequestSpecification postProductNegativeNoStringTitleRequestSpec;
    public static ResponseSpecification postProductNegativeNoStringTitleResponseSpec;

    public static ProductNoId postProductNegativeNoId;
    public static RequestSpecification postProductNegativeNoIdRequestSpec;
    public static ResponseSpecification postProductNegativeNoIdResponseSpec;

    public static Product putProductPositive;
    public static RequestSpecification putProductPositiveRequestSpec;
    public static ResponseSpecification putProductPositiveResponseSpec;

    public static Product putProductNegativeNoProduct;
    public static RequestSpecification putProductNegativeNoProductRequestSpec;
    public static ResponseSpecification putProductNegativeNoProductResponseSpec;

    @SneakyThrows
    @BeforeAll
    static void beforeAll() {

        Faker faker = new Faker();
        RestAssured.filters(new AllureRestAssured());
        properties.load(new FileInputStream("src/test/resources/application.properties"));
        RestAssured.baseURI = properties.getProperty("baseURL");

        setAllureEnvironment();

        logRequestSpecification = new RequestSpecBuilder()
                .log(METHOD)
                .log(URI)
                .log(BODY)
                .log(HEADERS)
                .build();

        responseSpecification = new ResponseSpecBuilder()
                .expectResponseTime(lessThan(5000L), TimeUnit.MILLISECONDS)
                .build();

        categoriesResponseSpec = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(200)
                .expectStatusLine(containsStringIgnoringCase("HTTP/1.1 200"))
                .build();

        productResponseSpec = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(200)
                .expectStatusLine(containsStringIgnoringCase("HTTP/1.1 200"))
                .build();

        productResponseNoExIdSpec = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(404)
                .expectStatusLine(containsStringIgnoringCase("HTTP/1.1 404"))
                .build();

        productResponseIncTypeIdSpec = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(400)
                .expectStatusLine(containsStringIgnoringCase("HTTP/1.1 400"))
                .build();

        deleteResponseSpec = new ResponseSpecBuilder()
                .expectContentType("")
                .build();

        postProductPositive = Product.builder()
                .price(100)
                .title(faker.food().dish())
                .categoryTitle(FURNITURE.getName())
                .build();

        postProductPositiveRequestSpec = new RequestSpecBuilder()
                .setBody(postProductPositive)
                .setContentType(ContentType.JSON)
                .build();

        postProductPositiveResponseSpec = new ResponseSpecBuilder()
                .expectStatusCode(201)
                .expectStatusLine("HTTP/1.1 201 ")
                .expectBody("title", equalTo(postProductPositive.getTitle()))
                .expectBody("price", equalTo(postProductPositive.getPrice()))
                .expectBody("categoryTitle", equalTo(postProductPositive.getCategoryTitle()))
                .build();

        postProductNegativeNotNullId = Product.builder()
                .id(1)
                .price(100)
                .title(faker.food().dish())
                .categoryTitle(FURNITURE.getName())
                .build();

        postProductNegativeNotNullIdRequestSpec = new RequestSpecBuilder()
                .setBody(postProductNegativeNotNullId)
                .setContentType(ContentType.JSON)
                .build();

        postProductNegativeNotNullIdResponseSpec = new ResponseSpecBuilder()
                .expectStatusCode(400)
                .expectStatusLine("HTTP/1.1 400 ")
                .expectBody("message", equalTo("Id must be null for new entity"))
                .build();

        postProductNegativeNoTitle = Product.builder()
                .price(100)
                .categoryTitle(FURNITURE.getName())
                .build();

        postProductNegativeNoTitleRequestSpec = new RequestSpecBuilder()
                .setBody(postProductNegativeNoTitle)
                .setContentType(ContentType.JSON)
                .build();

        postProductNegativeNoTitleResponseSpec = new ResponseSpecBuilder()
                .expectStatusCode(400)
                .expectStatusLine("HTTP/1.1 400 ")
                .expectBody("error", equalTo("Bad Request"))
                .build();

        postProductNegativeNoPrice = Product.builder()
                .title(faker.food().dish())
                .categoryTitle(FURNITURE.getName())
                .build();

        postProductNegativeNoPriceRequestSpec = new RequestSpecBuilder()
                .setBody(postProductNegativeNoPrice)
                .setContentType(ContentType.JSON)
                .build();

        postProductNegativeNoPriceResponseSpec = new ResponseSpecBuilder()
                .expectStatusCode(400)
                .expectStatusLine("HTTP/1.1 400 ")
                .expectBody("error", equalTo("Bad Request"))
                .build();

        postProductNegativeNoCatTitle = Product.builder()
                .price(100)
                .title(faker.food().dish())
                .build();

        postProductNegativeNoCatTitleRequestSpec = new RequestSpecBuilder()
                .setBody(postProductNegativeNoCatTitle)
                .setContentType(ContentType.JSON)
                .build();

        postProductNegativeNoCatTitleResponseSpec = new ResponseSpecBuilder()
                .expectStatusCode(500)
                .expectStatusLine("HTTP/1.1 500 ")
                .expectBody("error", equalTo("Internal Server Error"))
                .build();

        postProductNegativeMinusPrice = Product.builder()
                .price(-100)
                .title(faker.food().dish())
                .categoryTitle(FURNITURE.getName())
                .build();

        postProductNegativeMinusPriceRequestSpec = new RequestSpecBuilder()
                .setBody(postProductNegativeMinusPrice)
                .setContentType(ContentType.JSON)
                .build();

        postProductNegativeMinusPriceResponseSpec = new ResponseSpecBuilder()
                .expectStatusCode(400)
                .expectStatusLine("HTTP/1.1 400 ")
                .expectBody("error", equalTo("Bad Request"))
                .build();

        postProductNegativeStrPrice = ProductAlt.builder()
                .price("test")
                .title(faker.food().dish())
                .categoryTitle(FURNITURE.getName())
                .build();

        postProductNegativeStrPriceRequestSpec = new RequestSpecBuilder()
                .setBody(postProductNegativeStrPrice)
                .setContentType(ContentType.JSON)
                .build();

        postProductNegativeStrPriceResponseSpec = new ResponseSpecBuilder()
                .expectStatusCode(400)
                .expectStatusLine("HTTP/1.1 400 ")
                .expectBody("error", equalTo("Bad Request"))
                .build();

        postProductNegativeStrId = ProductAlt.builder()
                .price("test")
                .title(faker.food().dish())
                .categoryTitle(FURNITURE.getName())
                .build();

        postProductNegativeStrIdRequestSpec = new RequestSpecBuilder()
                .setBody(postProductNegativeStrId)
                .setContentType(ContentType.JSON)
                .build();

        postProductNegativeStrIdResponseSpec = new ResponseSpecBuilder()
                .expectStatusCode(400)
                .expectStatusLine("HTTP/1.1 400 ")
                .expectBody("error", equalTo("Bad Request"))
                .build();

        postProductNegativeFloatPrice = ProductAlt.builder()
                .price(100.0)
                .title(faker.food().dish())
                .categoryTitle(FURNITURE.getName())
                .build();

        postProductNegativeFloatPriceRequestSpec = new RequestSpecBuilder()
                .setBody(postProductNegativeMinusPrice)
                .setContentType(ContentType.JSON)
                .build();

        postProductNegativeFloatPriceResponseSpec = new ResponseSpecBuilder()
                .expectStatusCode(201)
                .expectStatusLine("HTTP/1.1 201 ")
                .expectBody("title", equalTo(postProductNegativeMinusPrice.getTitle()))
                .expectBody("price", equalTo(postProductNegativeMinusPrice.getPrice()))
                .expectBody("categoryTitle", equalTo(postProductNegativeMinusPrice.getCategoryTitle()))
                .build();

        postProductNegativeNoId = ProductNoId.builder()
                .price(100)
                .title(faker.food().dish())
                .categoryTitle(FURNITURE.getName())
                .build();

        postProductNegativeNoIdRequestSpec = new RequestSpecBuilder()
                .setBody(postProductNegativeNoId)
                .setContentType(ContentType.JSON)
                .build();

        postProductNegativeNoIdResponseSpec = new ResponseSpecBuilder()
                .expectStatusCode(201)
                .expectStatusLine("HTTP/1.1 201 ")
                .expectBody("title", equalTo(postProductNegativeNoId.getTitle()))
                .expectBody("price", equalTo(postProductNegativeNoId.getPrice()))
                .expectBody("categoryTitle", equalTo(postProductNegativeNoId.getCategoryTitle()))
                .build();

        postProductNegativeNoStringTitle = ProductAlt.builder()
                .price(100)
                .title(100)
                .categoryTitle(FURNITURE.getName())
                .build();

        postProductNegativeNoStringTitleRequestSpec = new RequestSpecBuilder()
                .setBody(postProductNegativeNoStringTitle)
                .setContentType(ContentType.JSON)
                .build();

        postProductNegativeNoStringTitleResponseSpec = new ResponseSpecBuilder()
                .expectStatusCode(400)
                .expectStatusLine("HTTP/1.1 400 ")
                .expectBody("error", equalTo("Bad Request"))
                .build();

        putProductPositive = Product.builder()
                .id(18736)
                .price(99)
                .title("Bread")
                .categoryTitle("Food")
                .build();

        putProductPositiveRequestSpec = new RequestSpecBuilder()
                .setBody(putProductPositive)
                .setContentType(ContentType.JSON)
                .build();

        putProductPositiveResponseSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectStatusLine("HTTP/1.1 200 ")
                .expectBody("title", equalTo(putProductPositive.getTitle()))
                .expectBody("price", equalTo(putProductPositive.getPrice()))
                .expectBody("categoryTitle", equalTo(putProductPositive.getCategoryTitle()))
                .build();

        putProductNegativeNoProduct = Product.builder()
                .id(1)
                .price(99)
                .title("Bread")
                .categoryTitle("Food")
                .build();

        putProductNegativeNoProductRequestSpec = new RequestSpecBuilder()
                .setBody(putProductNegativeNoProduct)
                .setContentType(ContentType.JSON)
                .build();

        putProductNegativeNoProductResponseSpec = new ResponseSpecBuilder()
                .expectStatusCode(400)
                .expectStatusLine("HTTP/1.1 400 ")
                .expectBody("message", equalTo("Product with id: " + putProductNegativeNoProduct.getId() + " doesn't exist"))
                .build();

        RestAssured.requestSpecification = logRequestSpecification;
        RestAssured.responseSpecification = responseSpecification;
    }

    protected static void setAllureEnvironment() {
        allureEnvironmentWriter(
                ImmutableMap.<String, String>builder()
                        .put("URL", properties.getProperty("baseURL"))
                        .build());
    }
}
