package ru.elizaveta199.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductAlt {

    @JsonProperty("id")
    private Object id;
    @JsonProperty("title")
    private Object title;
    @JsonProperty("price")
    private Object price;
    @JsonProperty("categoryTitle")
    private Object categoryTitle;

}
