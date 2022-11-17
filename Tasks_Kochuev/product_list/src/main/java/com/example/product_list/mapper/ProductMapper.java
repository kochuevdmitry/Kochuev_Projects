package com.example.product_list.mapper;

import com.example.product_list.dto.ProductDto;
import com.example.product_list.entity.ProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDto productEntityToDto(ProductEntity productEntity);
    ProductEntity productDtoToEntity(ProductDto productDto);

}
