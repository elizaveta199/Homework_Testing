package ru.elizaveta199.asserts;

import ru.elizaveta199.dto.Product;
import io.qameta.allure.Step;
import lombok.experimental.UtilityClass;
import org.hamcrest.CoreMatchers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

@UtilityClass
public class CommonAsserts {

    @Step
    public Integer postProductPositiveAssert(Product product, Product response) {
        Integer id = response.getId();
        assertThat(id, CoreMatchers.is(not(nullValue())));
        assertThat(response.getCategoryTitle(), IsCategoryExists.isCategoryExists());
        assertThat(response.getCategoryTitle(), equalTo(product.getCategoryTitle()));
        assertThat(response.getTitle(), equalTo(product.getTitle()));
        assertThat(response.getPrice(), equalTo(product.getPrice()));
        return id;
    }
}
