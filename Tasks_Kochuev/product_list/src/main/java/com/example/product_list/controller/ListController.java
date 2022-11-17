package com.example.product_list.controller;

import com.example.product_list.dto.CommonResponseDto;
import com.example.product_list.dto.ListWithProductsListDto;
import com.example.product_list.dto.ProductToListDto;
import com.example.product_list.exception.ListAddException;
import com.example.product_list.exception.ProductToListAddException;
import com.example.product_list.service.ListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@Api("List controller api")
public class ListController {

    private final ListService listService;

    @PostMapping("/save/list")
    @ApiOperation("Operation to add new list")
    public ResponseEntity<CommonResponseDto> addNewList(@RequestParam String listName) throws ListAddException {
        return new ResponseEntity<>(listService.addList(listName), HttpStatus.OK);
    }

    @GetMapping("/get/list")
    @ApiOperation("Operation to get list by id with all assigned products and total kcal")
    public ResponseEntity<ListWithProductsListDto> getListById(@RequestParam Long listId) throws ProductToListAddException {
        return new ResponseEntity<>(listService.getListWithProductsById(listId), HttpStatus.OK);
    }

    @PutMapping("/assign/product_to_list")
    @ApiOperation("Operation to assign pruduct to list by id")
    public ResponseEntity<CommonResponseDto> assignProductToList(
            @RequestBody ProductToListDto productToListDto) {
        return new ResponseEntity<>(listService.assignProductToList(productToListDto), HttpStatus.OK);
    }

}
