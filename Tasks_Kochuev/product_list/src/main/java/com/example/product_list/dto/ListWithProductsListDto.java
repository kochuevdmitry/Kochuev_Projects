package com.example.product_list.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ListWithProductsListDto {
    @JsonProperty("list_id")
    private Long id;
    @JsonProperty("list_name")
    private String name;
    @JsonProperty("total_kcal")
    private Integer totalKcal;
    @JsonProperty("products_for_list_id")
    private List<ProductDto> productsForList;

}
