package com.example.MyBookShopApp.service;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.book.review.BookReviewLikeEntity;
import com.example.MyBookShopApp.data.dto.BookReviewLikeEntityDto;
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

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestPropertySource("/application-test.properties")//указываем, чтобы пользоваться тестовой базой
class BookReviewLikeServiceTests {


    private final BookReviewLikeService bookReviewLikeService;
    private final UserEntityRepository userEntityRepository;
    private final BookRepository bookRepository;

    @Autowired
    public BookReviewLikeServiceTests(BookReviewLikeService bookReviewLikeService, UserEntityRepository userEntityRepository, BookRepository bookRepository) {
        this.bookReviewLikeService = bookReviewLikeService;
        this.userEntityRepository = userEntityRepository;
        this.bookRepository = bookRepository;
    }

    @MockBean
    private BookReviewLikeRepository bookReviewLikeRepositoryMock;

    @Test
    void addReviewLikeEntity() {
        UserEntity user = userEntityRepository.findUserEntityById(2);
        BookReviewLikeEntityDto addReview = new BookReviewLikeEntityDto();
        addReview.setUser(user);
        addReview.setText("test");
        addReview.setBookId(2);
        addReview.setTime(LocalDateTime.now());
        addReview.setValue((short) 5);

        Book book = bookRepository.findBookById(2);

        BookReviewLikeEntity reviewCheck = bookReviewLikeService.addReviewLikeEntity(user, addReview, book);
        assertNotNull(reviewCheck);
        Assertions.assertTrue(CoreMatchers.is(reviewCheck.getText()).matches(addReview.getText()));

        Mockito.verify(bookReviewLikeRepositoryMock, Mockito.times(1))
                .save(Mockito.any(BookReviewLikeEntity.class));
    }
}