package com.example.MyBookShopApp.data.dto;

public class CartDto {

    String booksIds;
    String status;

    public CartDto() {
    }

    public String getBooksIds() {
        return booksIds;
    }

    public void setBooksIds(String booksIds) {
        this.booksIds = booksIds;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
