package com.example.MyBookShopApp.data.dto;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.book.review.BookReviewDislikesEntity;
import com.example.MyBookShopApp.data.book.review.BookReviewLikesEntity;
import com.example.MyBookShopApp.data.user.UserEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class BookReviewLikeEntityDto {

    private int id;

    private int reviewId;

    private UserEntity user;

    private LocalDateTime time;

    private short value;

    private String text;

    private int bookId;

    private Boolean approvedReview = false;

    private Book book;

    private List<BookReviewLikesEntity> bookReviewLikeEntities = new ArrayList<>();
    private List<BookReviewDislikesEntity> bookReviewDislikes = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public short getValue() {
        return value;
    }

    public void setValue(short value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public Boolean getApprovedReview() {
        return approvedReview;
    }

    public void setApprovedReview(Boolean approvedReview) {
        this.approvedReview = approvedReview;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public List<BookReviewLikesEntity> getBookReviewLikeEntities() {
        return bookReviewLikeEntities;
    }

    public void setBookReviewLikeEntities(List<BookReviewLikesEntity> bookReviewLikeEntities) {
        this.bookReviewLikeEntities = bookReviewLikeEntities;
    }

    public List<BookReviewDislikesEntity> getBookReviewDislikes() {
        return bookReviewDislikes;
    }

    public void setBookReviewDislikes(List<BookReviewDislikesEntity> bookReviewDislikes) {
        this.bookReviewDislikes = bookReviewDislikes;
    }
}
