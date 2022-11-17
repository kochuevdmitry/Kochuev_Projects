package com.example.product_list.controller;

import com.example.product_list.dto.CommonResponseDto;
import com.example.product_list.exception.ListAddException;
import com.example.product_list.exception.ProductAddException;
import com.example.product_list.exception.ProductToListAddException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHanflerController {

    @ExceptionHandler(ProductAddException.class)
    public ResponseEntity<CommonResponseDto> productAddException(ProductAddException e) {
        log.info("Ошибка добавления: {}", e.getLocalizedMessage());
        CommonResponseDto commonResponseDto = new CommonResponseDto();
        commonResponseDto.setError(e.getMessage());
        return new ResponseEntity<>(commonResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ListAddException.class)
    public ResponseEntity<CommonResponseDto> listAddException(ListAddException e) {
        log.info("Ошибка добавления: {}", e.getLocalizedMessage());
        CommonResponseDto commonResponseDto = new CommonResponseDto();
        commonResponseDto.setError(e.getMessage());
        return new ResponseEntity<>(commonResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductToListAddException.class)
    public ResponseEntity<CommonResponseDto> productToListAddException(ProductToListAddException e) {
        log.info("Ошибка добавления: {}", e.getLocalizedMessage());
        CommonResponseDto commonResponseDto = new CommonResponseDto();
        commonResponseDto.setError(e.getMessage());
        return new ResponseEntity<>(commonResponseDto, HttpStatus.BAD_REQUEST);
    }
}
