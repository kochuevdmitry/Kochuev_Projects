package com.example.product_list.service;

import com.example.product_list.dto.CommonResponseDto;
import com.example.product_list.dto.ProductDto;
import com.example.product_list.exception.ProductAddException;

import java.util.List;

public interface ProductService {

    CommonResponseDto addProduct(ProductDto productDto) throws ProductAddException;

    List<ProductDto> getProduct();
}
