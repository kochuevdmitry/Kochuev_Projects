package com.example.product_list.controller;

import com.example.product_list.dto.ProductDto;
import com.example.product_list.dto.CommonResponseDto;
import com.example.product_list.exception.ProductAddException;
import com.example.product_list.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Api("Product controller api")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/save/product")
    @ApiOperation("Operation to add new product")
    public ResponseEntity<CommonResponseDto> saveNewProduct(@RequestBody ProductDto productDto) throws ProductAddException {
        return new ResponseEntity<>(productService.addProduct(productDto), HttpStatus.OK);
    }

    @GetMapping("/get/product")
    @ApiOperation("Operation to add get all products")
    public ResponseEntity<List<ProductDto>> getProducts() {
        return new ResponseEntity<>(productService.getProduct(), HttpStatus.OK);
    }
}
