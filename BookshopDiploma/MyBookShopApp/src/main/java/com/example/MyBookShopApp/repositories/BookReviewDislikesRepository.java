package com.example.MyBookShopApp.repositories;

import com.example.MyBookShopApp.data.book.review.BookReviewDislikesEntity;
import com.example.MyBookShopApp.data.book.review.BookReviewLikeEntity;
import com.example.MyBookShopApp.data.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookReviewDislikesRepository extends JpaRepository<BookReviewDislikesEntity,Integer> {
    BookReviewDislikesEntity findBookReviewDislikesEntityByBookReviewLikeEntityDislikesAndUserForReviewDislikes(BookReviewLikeEntity bookReviewLikeEntity, UserEntity user);
}
