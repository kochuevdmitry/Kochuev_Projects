package com.example.MyBookShopApp.service;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.book.review.BookReviewLikeEntity;
import com.example.MyBookShopApp.data.user.UserEntity;
import com.example.MyBookShopApp.repositories.BookRepository;
import com.example.MyBookShopApp.repositories.BookReviewLikeRepository;
import com.example.MyBookShopApp.repositories.UserEntityRepository;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.TreeMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestPropertySource("/application-test.properties")//указываем, чтобы пользоваться тестовой базой
class BookReviewLikeServiceRatingsTest {


    private final BookReviewLikeService bookReviewLikeService;
    private final BookRepository bookRepository;

    @Autowired
    public BookReviewLikeServiceRatingsTest(BookReviewLikeService bookReviewLikeService, BookRepository bookRepository) {
        this.bookReviewLikeService = bookReviewLikeService;
        this.bookRepository = bookRepository;
    }

    @Test
    void getBookRatingsByBookId() {
        TreeMap ratings = new TreeMap();
        ratings.put("stars1",1);
        ratings.put("stars2",0);
        ratings.put("stars3",1);
        ratings.put("stars4",0);
        ratings.put("stars5",1);
        ratings.put("average",3);

        TreeMap checkMap = bookReviewLikeService.getBookRatingsByBookId(2);

        assertEquals(checkMap, ratings);
    }

}