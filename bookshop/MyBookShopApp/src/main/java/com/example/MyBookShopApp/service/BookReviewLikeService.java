package com.example.MyBookShopApp.service;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.book.review.BookReviewDislikesEntity;
import com.example.MyBookShopApp.data.book.review.BookReviewLikeEntity;
import com.example.MyBookShopApp.data.book.review.BookReviewLikesEntity;
import com.example.MyBookShopApp.data.dto.BookReviewLikeEntityDto;
import com.example.MyBookShopApp.data.user.UserEntity;
import com.example.MyBookShopApp.repositories.BookReviewDislikesRepository;
import com.example.MyBookShopApp.repositories.BookReviewLikeRepository;
import com.example.MyBookShopApp.repositories.BookReviewLikesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.TreeMap;

@Service
public class BookReviewLikeService {

    private BookReviewLikeRepository bookReviewLikeRepository;
    private BookReviewLikesRepository bookReviewLikesRepository;
    private BookReviewDislikesRepository bookReviewDislikesRepository;

    @Autowired
    public BookReviewLikeService(BookReviewLikeRepository bookReviewLikeRepository, BookReviewLikesRepository bookReviewLikesRepository, BookReviewDislikesRepository bookReviewDislikesRepository) {
        this.bookReviewLikeRepository = bookReviewLikeRepository;
        this.bookReviewLikesRepository = bookReviewLikesRepository;
        this.bookReviewDislikesRepository = bookReviewDislikesRepository;
    }

    public TreeMap<String, Integer> getBookRatingsByBookId(int bookId) {
        TreeMap<String, Integer> ratings = new TreeMap<>();
        List<BookReviewLikeEntity> bookReviewLikeEntities = bookReviewLikeRepository.findBookReviewLikeEntitiesByBookId(bookId);
        int rating = 0;
        int[] rate = new int[5];
        if (bookReviewLikeEntities != null && bookReviewLikeEntities.size() != 0) {
            for (BookReviewLikeEntity like : bookReviewLikeEntities) {
                rating += like.getValue();
                rate[like.getValue() - 1]++;
            }
            rating = rating / bookReviewLikeEntities.size();
        }
        ratings.put("stars1", rate[0]);
        ratings.put("stars2", rate[1]);
        ratings.put("stars3", rate[2]);
        ratings.put("stars4", rate[3]);
        ratings.put("stars5", rate[4]);
        ratings.put("average", rating);

        return ratings;
    }

    public List<BookReviewLikeEntity> getBookLikesByBookId(int bookId) {
        return bookReviewLikeRepository.findBookReviewLikeEntitiesByBookIdAndApprovedReview(bookId, true);
    }

    public BookReviewLikeEntity addReviewLikeEntity(UserEntity user, BookReviewLikeEntityDto addReview, Book book) {
        BookReviewLikeEntity reviewLikeEntity = new BookReviewLikeEntity();
        reviewLikeEntity.setValue(addReview.getValue());
        reviewLikeEntity.setBookId(book.getId());
        reviewLikeEntity.setText(addReview.getText());
        reviewLikeEntity.setUser(user);
        reviewLikeEntity.setTime(LocalDateTime.now());
        reviewLikeEntity.setBook(book);
        bookReviewLikeRepository.save(reviewLikeEntity);
        return reviewLikeEntity;
    }

    public String getBookSlugByReviewId(Integer reviewId) {
        return bookReviewLikeRepository.findBookReviewLikeEntitiesById(reviewId).getBook().getSlug();
    }


    public String setLikeDislikeForReview(Integer value, Integer reviewId, UserEntity user) {
        if (value == 1){
            BookReviewLikesEntity bookReviewLikesEntity = bookReviewLikesRepository
                    .findBookReviewLikesEntityByBookReviewLikeEntityLikesAndUserForReviewLikes(bookReviewLikeRepository.findBookReviewLikeEntitiesById(reviewId), user);
            if (bookReviewLikesEntity == null){
                bookReviewLikesEntity = new BookReviewLikesEntity();
                bookReviewLikesEntity.setUserForReviewLikes(user);
                bookReviewLikesEntity.setBookReviewLikeEntityLikes(bookReviewLikeRepository.findBookReviewLikeEntitiesById(reviewId));
                bookReviewLikesRepository.save(bookReviewLikesEntity);
            } else {
                bookReviewLikesRepository.delete(bookReviewLikesEntity);
            }
        }
        if (value == -1){
            BookReviewDislikesEntity bookReviewDislikesEntity = bookReviewDislikesRepository.findBookReviewDislikesEntityByBookReviewLikeEntityDislikesAndUserForReviewDislikes(bookReviewLikeRepository.findBookReviewLikeEntitiesById(reviewId), user);
            if (bookReviewDislikesEntity == null){
                bookReviewDislikesEntity = new BookReviewDislikesEntity();
                bookReviewDislikesEntity.setUserForReviewDislikes(user);
                bookReviewDislikesEntity.setBookReviewLikeEntityDislikes(bookReviewLikeRepository.findBookReviewLikeEntitiesById(reviewId));
                bookReviewDislikesRepository.save(bookReviewDislikesEntity);
            } else {
                bookReviewDislikesRepository.delete(bookReviewDislikesEntity);
            }
        }
        return bookReviewLikeRepository.findBookReviewLikeEntitiesById(reviewId).getBook().getSlug();
    }
}
