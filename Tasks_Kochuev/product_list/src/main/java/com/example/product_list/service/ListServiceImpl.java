package com.example.product_list.service;

import com.example.product_list.dto.CommonResponseDto;
import com.example.product_list.dto.ListWithProductsListDto;
import com.example.product_list.dto.ProductToListDto;
import com.example.product_list.entity.ListEntity;
import com.example.product_list.entity.ProductEntity;
import com.example.product_list.exception.ListAddException;
import com.example.product_list.exception.ProductToListAddException;
import com.example.product_list.mapper.ProductMapper;
import com.example.product_list.repository.ListRepository;
import com.example.product_list.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ListServiceImpl implements ListService{

    private  final ListRepository listRepository;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    @Transactional
    public CommonResponseDto addList(String listName) throws ListAddException {
        if (listRepository.findByName(listName) != null){
            throw new ListAddException("Ошибка добавления, такой список уже есть в базе", new ListAddException());
        }
        ListEntity listEntity = new ListEntity();
        listEntity.setName(listName);
        try {
            listRepository.save(listEntity);
        } catch (Exception e) {
            throw new ListAddException("Ошибка добавления нового списка: ", e);
        }
        CommonResponseDto commonResponseDto = new CommonResponseDto();
        commonResponseDto.setMessage(String.format("List %s added", listName));
        return commonResponseDto;
    }

    @Override
    public ListWithProductsListDto getListWithProductsById(Long listId) throws ProductToListAddException {
        if (listRepository.findById(listId).isEmpty()){
            throw new ProductToListAddException("Нет такого списка.", new ProductToListAddException());
        }
        ListEntity entity = listRepository.findById(listId).get();
        ListWithProductsListDto result = new ListWithProductsListDto();
        result.setId(listId);
        result.setName(listRepository.findById(listId).get().getName());
        if (entity.getProductEntities() != null){
            result.setProductsForList(entity.getProductEntities()
                    .stream().map(productMapper::productEntityToDto).toList());
            result.setTotalKcal(entity.getProductEntities().stream().mapToInt(e -> e.getKcal()).sum());
        }

        return result;
    }

    @Override
    @Transactional
    public CommonResponseDto assignProductToList(ProductToListDto productToListDto) {
        ListEntity listEntity = listRepository.findById(productToListDto.getListId()).orElseThrow();
        ProductEntity productEntity = productRepository.findById(productToListDto.getProductId()).orElseThrow();
        List<ProductEntity> productEntityList = listEntity.getProductEntities();
        if (productEntityList == null){
            productEntityList = new ArrayList<>();
        }
        productEntityList.add(productEntity);
        listEntity.setProductEntities(productEntityList);
        listRepository.save(listEntity);
        CommonResponseDto commonResponseDto = new CommonResponseDto();
        commonResponseDto.setMessage(String.format("Product %s added to list %s", productEntity.getName(), listEntity.getName()));
        return commonResponseDto;
    }
}
