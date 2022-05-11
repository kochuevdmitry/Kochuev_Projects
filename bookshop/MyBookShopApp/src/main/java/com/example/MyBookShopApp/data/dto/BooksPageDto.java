package com.example.MyBookShopApp.data.dto;

import java.util.List;

public class BooksPageDto {

    private Integer count;
    private List<BookDto> bookDtos;

    public BooksPageDto(List<BookDto> bookDtos) {
        this.count = bookDtos.size();
        this.bookDtos = bookDtos;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<BookDto> getBookDtos() {
        return bookDtos;
    }

    public void setBookDtos(List<BookDto> bookDtos) {
        this.bookDtos = bookDtos;
    }
}
