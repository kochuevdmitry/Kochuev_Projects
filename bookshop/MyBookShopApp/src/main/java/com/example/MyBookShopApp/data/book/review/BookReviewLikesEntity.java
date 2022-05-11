package com.example.MyBookShopApp.data.book.review;

import com.example.MyBookShopApp.data.user.UserEntity;

import javax.persistence.*;

@Entity
@Table(name = "review_likes")
public class BookReviewLikesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "review_id", referencedColumnName = "id")
    private BookReviewLikeEntity bookReviewLikeEntityLikes;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity userForReviewLikes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BookReviewLikeEntity getBookReviewLikeEntityLikes() {
        return bookReviewLikeEntityLikes;
    }

    public void setBookReviewLikeEntityLikes(BookReviewLikeEntity bookReviewLikeEntityLikes) {
        this.bookReviewLikeEntityLikes = bookReviewLikeEntityLikes;
    }

    public UserEntity getUserForReviewLikes() {
        return userForReviewLikes;
    }

    public void setUserForReviewLikes(UserEntity userForReviewLikes) {
        this.userForReviewLikes = userForReviewLikes;
    }
}
