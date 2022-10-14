package com.example.MyBookShopApp.data.dto;

import com.example.MyBookShopApp.data.Book;

import java.util.ArrayList;
import java.util.List;


public class AuthorsDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private String slug;
    private String photo;
    private String description;
    private List<Book> bookList = new ArrayList<>();

    public AuthorsDto() {
    }

    public AuthorsDto(Integer id, String firstName, String lastName, String slug, String photo, String description, List<Book> bookList) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.slug = slug;
        this.photo = photo;
        this.description = description;
        this.bookList = bookList;
    }

    @Override
    public String toString() {
        return firstName + ' ' + lastName;
    }

    public String getName() {
        return lastName + " " + firstName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }
}
