package com.example.product_list.service;

import com.example.product_list.dto.CommonResponseDto;
import com.example.product_list.dto.ProductDto;
import com.example.product_list.entity.ProductEntity;
import com.example.product_list.exception.ProductAddException;
import com.example.product_list.mapper.ProductMapper;
import com.example.product_list.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    @Transactional
    public CommonResponseDto addProduct(ProductDto productDto) throws ProductAddException {

        if (productRepository.findByName(productDto.getName()) != null) {
            throw new ProductAddException("Ошибка добавления, такой продукт уже есть в базе", new ProductAddException());
        }
        ProductEntity productEntity = productMapper.productDtoToEntity(productDto);
        try {
            productRepository.save(productEntity);
        } catch (Exception e) {
            throw new ProductAddException("Ошибка добавления продукта: ", e);
        }
        CommonResponseDto commonResponseDto = new CommonResponseDto();
        commonResponseDto.setMessage(String.format("Product %s adedd.", productDto.getName()));
        return commonResponseDto;
    }

    @Override
    public List<ProductDto> getProduct() {
        List<ProductEntity> productEntityList = productRepository.findAll();
        return productEntityList.stream().map(productMapper::productEntityToDto).toList();
    }
}
