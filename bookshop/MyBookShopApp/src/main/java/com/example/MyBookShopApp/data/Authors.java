package com.example.MyBookShopApp.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "authors")
@ApiModel(description = "api model of author entity")//дополнительное описание сущности
public class Authors {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "author id generated by db", position = 1)
//описание переменной в api, и порядок указания в документации
    private Integer id;

    @ApiModelProperty(value = "author first name", example = "Dmitry", position = 2)
//описание переменной в api, пример переменной, порядок указания переменной в документации
    private String firstName;
    @ApiModelProperty(value = "author second name", example = "Koch", position = 3)
//описание переменной в api, пример переменной, порядок указания переменной в документации
    private String lastName;

    private String slug;

    private String photo;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "author")
    @JsonIgnore//игнорим появления этих данный в API
    private List<Book> bookList = new ArrayList<>();

    public Authors(List<String> authors) {
        if (authors != null) {
            this.firstName = authors.toString();
        }
    }

    public Authors() {
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
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
}