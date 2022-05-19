package com.example.MyBookShopApp.repositories;

import com.example.MyBookShopApp.data.book.review.BookReviewLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookReviewLikeRepository extends JpaRepository<BookReviewLikeEntity, Integer> {

    List<BookReviewLikeEntity> findBookReviewLikeEntitiesByBookId(int bookId);

    BookReviewLikeEntity findBookReviewLikeEntitiesById(int reviewId);

    List<BookReviewLikeEntity> findBookReviewLikeEntitiesByBookIdAndApprovedReview(int bookId, Boolean approvedReview);

    List<BookReviewLikeEntity> findBookReviewLikeEntitiesByApprovedReview(boolean b);
}
