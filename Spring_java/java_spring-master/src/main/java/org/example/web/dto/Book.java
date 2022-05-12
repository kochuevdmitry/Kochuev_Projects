package org.example.web.dto;

import javax.validation.constraints.*;

public class Book {

    private Integer id;

    @Pattern(regexp="\\D+", message = "Only not digits")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    private String author;

    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    private String title;

    @NotNull(message = "Size should be 10 - 999")
    @Digits(integer = 4, fraction = 0)
    private Integer size;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", size=" + size +
                '}';
    }
}
