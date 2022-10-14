package com.example.MyBookShopApp.data.book.review;

import com.example.MyBookShopApp.data.user.UserEntity;

import javax.persistence.*;

@Entity
@Table(name = "review_dislikes")
public class BookReviewDislikesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "review_id", referencedColumnName = "id")
    private BookReviewLikeEntity bookReviewLikeEntityDislikes;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity userForReviewDislikes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BookReviewLikeEntity getBookReviewLikeEntityDislikes() {
        return bookReviewLikeEntityDislikes;
    }

    public void setBookReviewLikeEntityDislikes(BookReviewLikeEntity bookReviewLikeEntityDislikes) {
        this.bookReviewLikeEntityDislikes = bookReviewLikeEntityDislikes;
    }

    public UserEntity getUserForReviewDislikes() {
        return userForReviewDislikes;
    }

    public void setUserForReviewDislikes(UserEntity userForReviewDislikes) {
        this.userForReviewDislikes = userForReviewDislikes;
    }
}
