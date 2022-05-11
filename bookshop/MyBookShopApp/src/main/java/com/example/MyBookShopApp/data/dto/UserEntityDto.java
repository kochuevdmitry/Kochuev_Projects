package com.example.MyBookShopApp.data.dto;

import com.example.MyBookShopApp.data.book.review.BookReviewDislikesEntity;
import com.example.MyBookShopApp.data.book.review.BookReviewLikeEntity;
import com.example.MyBookShopApp.data.book.review.BookReviewLikesEntity;
import com.example.MyBookShopApp.data.user.UserRoleEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class UserEntityDto {
    private int id;
    private String hash;
    private LocalDateTime regTime;
    private Double balance;
    private String name;
    private List<BookReviewLikeEntity> bookReviewLikeEntities = new ArrayList<>();
    private String email;
    private String phone;
    private String password;
    private Boolean verified;
    private Boolean banned;
    private List<UserRoleEntity> userRoleEntities = new ArrayList<>();
    private List<BookReviewLikesEntity> bookReviewLikesEntities = new ArrayList<>();
    private List<BookReviewDislikesEntity> bookReviewDislikes = new ArrayList<>();

    public UserEntityDto() {
    }

    public UserEntityDto(int id, String hash, LocalDateTime regTime, Double balance, String name, List<BookReviewLikeEntity> bookReviewLikeEntities, String email, String phone, String password, Boolean verified, Boolean banned, List<UserRoleEntity> userRoleEntities, List<BookReviewLikesEntity> bookReviewLikesEntities, List<BookReviewDislikesEntity> bookReviewDislikes) {
        this.id = id;
        this.hash = hash;
        this.regTime = regTime;
        this.balance = balance;
        this.name = name;
        this.bookReviewLikeEntities = bookReviewLikeEntities;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.verified = verified;
        this.banned = banned;
        this.userRoleEntities = userRoleEntities;
        this.bookReviewLikesEntities = bookReviewLikesEntities;
        this.bookReviewDislikes = bookReviewDislikes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public LocalDateTime getRegTime() {
        return regTime;
    }

    public void setRegTime(LocalDateTime regTime) {
        this.regTime = regTime;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BookReviewLikeEntity> getBookReviewLikeEntities() {
        return bookReviewLikeEntities;
    }

    public void setBookReviewLikeEntities(List<BookReviewLikeEntity> bookReviewLikeEntities) {
        this.bookReviewLikeEntities = bookReviewLikeEntities;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public Boolean getBanned() {
        return banned;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }

    public List<UserRoleEntity> getUserRoleEntities() {
        return userRoleEntities;
    }

    public void setUserRoleEntities(List<UserRoleEntity> userRoleEntities) {
        this.userRoleEntities = userRoleEntities;
    }

    public List<BookReviewLikesEntity> getBookReviewLikesEntities() {
        return bookReviewLikesEntities;
    }

    public void setBookReviewLikesEntities(List<BookReviewLikesEntity> bookReviewLikesEntities) {
        this.bookReviewLikesEntities = bookReviewLikesEntities;
    }

    public List<BookReviewDislikesEntity> getBookReviewDislikes() {
        return bookReviewDislikes;
    }

    public void setBookReviewDislikes(List<BookReviewDislikesEntity> bookReviewDislikes) {
        this.bookReviewDislikes = bookReviewDislikes;
    }
}
