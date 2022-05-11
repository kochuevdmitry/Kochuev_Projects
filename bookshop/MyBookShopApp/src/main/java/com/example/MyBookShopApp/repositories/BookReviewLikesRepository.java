package com.example.MyBookShopApp.repositories;

import com.example.MyBookShopApp.data.book.review.BookReviewLikeEntity;
import com.example.MyBookShopApp.data.book.review.BookReviewLikesEntity;
import com.example.MyBookShopApp.data.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookReviewLikesRepository extends JpaRepository<BookReviewLikesEntity, Integer> {

    BookReviewLikesEntity findBookReviewLikesEntityByBookReviewLikeEntityLikesAndUserForReviewLikes(BookReviewLikeEntity bookReviewLikeEntity, UserEntity user);
}
