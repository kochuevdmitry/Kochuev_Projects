package com.example.MyBookShopApp.data.user;

import com.example.MyBookShopApp.data.book.review.BookReviewDislikesEntity;
import com.example.MyBookShopApp.data.book.review.BookReviewLikeEntity;
import com.example.MyBookShopApp.data.book.review.BookReviewLikesEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    //@SequenceGenerator( name = "jpaSequence", sequenceName = "JPA_SEQUENCE", allocationSize = 1, initialValue = 101 )
    //@GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "jpaSequence")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    private String hash;

    @Column(columnDefinition = "TIMESTAMP NOT NULL")
    private LocalDateTime regTime;

    @Column
    private Double balance;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    private String name;

    @OneToMany (mappedBy = "user")
    private List<BookReviewLikeEntity> bookReviewLikeEntities = new ArrayList<>();

    private String email;
    private String phone;
    private String password;
    private Boolean verified;
    private Boolean banned;

    @OneToMany (mappedBy = "userForRole", fetch = FetchType.EAGER)
    private List<UserRoleEntity> userRoleEntities = new ArrayList<>();

    @OneToMany (mappedBy = "userForReviewLikes")
    private List<BookReviewLikesEntity> bookReviewLikesEntities = new ArrayList<>();

    @OneToMany (mappedBy = "userForReviewDislikes")
    private List<BookReviewDislikesEntity> bookReviewDislikes = new ArrayList<>();

    public List<BookReviewDislikesEntity> getBookReviewDislikes() {
        return bookReviewDislikes;
    }

    public void setBookReviewDislikes(List<BookReviewDislikesEntity> bookReviewDislikes) {
        this.bookReviewDislikes = bookReviewDislikes;
    }

    public List<BookReviewLikesEntity> getBookReviewLikesEntities() {
        return bookReviewLikesEntities;
    }

    public void setBookReviewLikesEntities(List<BookReviewLikesEntity> bookReviewLikesEntities) {
        this.bookReviewLikesEntities = bookReviewLikesEntities;
    }

    public List<UserRoleEntity> getUserRoleEntities() {
        return userRoleEntities;
    }

    public void setUserRoleEntities(List<UserRoleEntity> userRoleEntities) {
        this.userRoleEntities = userRoleEntities;
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

    public List<BookReviewLikeEntity> getBookReviewLikeEntities() {
        return bookReviewLikeEntities;
    }

    public void setBookReviewLikeEntities(List<BookReviewLikeEntity> bookReviewLikeEntities) {
        this.bookReviewLikeEntities = bookReviewLikeEntities;
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
}
