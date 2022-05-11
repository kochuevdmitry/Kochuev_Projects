package com.example.MyBookShopApp.security;

import com.example.MyBookShopApp.data.user.UserEntity;
import com.example.MyBookShopApp.repositories.BookstoreUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource("/application-test.properties")//указываем, чтобы пользоваться тестовой базой
class BookstoreUserRepositoryTests {

    private final BookstoreUserRepository bookstoreUserRepository;

    @Autowired
    public BookstoreUserRepositoryTests(BookstoreUserRepository bookstoreUserRepository) {
        this.bookstoreUserRepository = bookstoreUserRepository;
    }

    @Test
    public void addNewUser() {
        UserEntity user = new UserEntity();
        user.setPassword("123");
        user.setName("test");
        user.setPhone("9161111111");
        user.setEmail("test@test.com");
        user.setHash("test-hash-1234");
        user.setRegTime(LocalDateTime.now());

        assertNotNull(bookstoreUserRepository.save(user));

    }
}