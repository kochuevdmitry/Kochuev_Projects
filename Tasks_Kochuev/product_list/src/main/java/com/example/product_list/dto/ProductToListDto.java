package com.example.product_list.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProductToListDto {

    @ApiModelProperty(notes = "id of list", required = true, example = "1")
    private Long listId;
    @ApiModelProperty(notes = "id of product to add to the list", required = true, example = "1")
    private Long productId;
}
