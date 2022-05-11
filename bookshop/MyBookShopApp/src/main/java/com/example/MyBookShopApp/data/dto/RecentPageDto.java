package com.example.MyBookShopApp.data.dto;

import java.util.List;

public class RecentPageDto {

    private Integer count;
    private List<BookDto> bookDtos;

    public RecentPageDto(List<BookDto> bookDtos) {
        this.bookDtos = bookDtos;
        this.count = bookDtos.size();
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
