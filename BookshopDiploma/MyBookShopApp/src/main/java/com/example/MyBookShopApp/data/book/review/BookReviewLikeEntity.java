package com.example.MyBookShopApp.data.book.review;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.user.UserEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "book_review_like")
public class BookReviewLikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "INT NOT NULL")
    private int reviewId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @Column(columnDefinition = "TIMESTAMP NOT NULL")
    private LocalDateTime time;

    @Column(columnDefinition = "SMALLINT NOT NULL")
    private short value;

    @Column(columnDefinition = "TEXT NOT NULL")
    private String text;

    @Column(columnDefinition = "INT NOT NULL")
    private int bookId;

    private Boolean approvedReview = false;

    @ManyToOne
    @JoinColumn(name = "bookrev_id", referencedColumnName = "id")
    private Book book;

    @OneToMany (mappedBy = "bookReviewLikeEntityLikes")
    private List<BookReviewLikesEntity> bookReviewLikeEntities = new ArrayList<>();
    @OneToMany (mappedBy = "bookReviewLikeEntityDislikes")
    private List<BookReviewDislikesEntity> bookReviewDislikes = new ArrayList<>();

    public List<BookReviewLikesEntity> getBookReviewLikes() {
        return bookReviewLikeEntities;
    }

    public void setBookReviewLikes(List<BookReviewLikesEntity> bookReviewLikeEntities) {
        this.bookReviewLikeEntities = bookReviewLikeEntities;
    }

    public List<BookReviewDislikesEntity> getBookReviewDislikes() {
        return bookReviewDislikes;
    }

    public void setBookReviewDislikes(List<BookReviewDislikesEntity> bookReviewDislikes) {
        this.bookReviewDislikes = bookReviewDislikes;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Boolean getApprovedReview() {
        return approvedReview;
    }

    public void setApprovedReview(Boolean approvedReview) {
        this.approvedReview = approvedReview;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

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

    public String getFirstPartOfReviewText(){
        if (text.length() <= 100){
            return  text;
        }
        return text.substring(0,100);
    }
    public String getSecondPartOfReviewText(){
        if (text.length() <= 100){
            return  "";
        }
        return text.substring(101);
    }
}
