package com.example.product_list.service;

import com.example.product_list.dto.CommonResponseDto;
import com.example.product_list.dto.ListWithProductsListDto;
import com.example.product_list.dto.ProductToListDto;
import com.example.product_list.exception.ListAddException;
import com.example.product_list.exception.ProductToListAddException;

public interface ListService {

    CommonResponseDto addList(String listName) throws ListAddException;

    ListWithProductsListDto getListWithProductsById(Long listId) throws ProductToListAddException;

    CommonResponseDto assignProductToList(ProductToListDto productToListDto);
}
