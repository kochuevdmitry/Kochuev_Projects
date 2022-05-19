package com.example.MyBookShopApp.data.user;

import com.example.MyBookShopApp.data.book.review.BookReviewLikeEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users_change_check")
public class UserEntityChangeVerification {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "TIMESTAMP NOT NULL")
    private LocalDateTime tokenTime;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    private String token;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    private String name;

    private String email;
    private String phone;
    private String password;
    private int userId;

    private LocalDateTime expireTime;

    public UserEntityChangeVerification(Integer expireTime) {
        this.expireTime = LocalDateTime.now().plusSeconds(expireTime);
    }

    public UserEntityChangeVerification() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }

    public Boolean isExpired(){
        return LocalDateTime.now().isAfter(expireTime);
    }

    public LocalDateTime getTokenTime() {
        return tokenTime;
    }

    public void setTokenTime(LocalDateTime tokenTime) {
        this.tokenTime = tokenTime;
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


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
