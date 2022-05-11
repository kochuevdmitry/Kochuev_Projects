package com.example.MyBookShopApp.security;

import com.example.MyBookShopApp.data.BookstoreUserDetails;
import com.example.MyBookShopApp.data.ContactConfirmationPayload;
import com.example.MyBookShopApp.service.BookstoreUserDetailsService;
import com.example.MyBookShopApp.service.BookstoreUserRegisterService;
import com.example.MyBookShopApp.service.JWTUtilService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.Assert.assertTrue;

@SpringBootTest
@TestPropertySource("/application-test.properties")//указываем, чтобы пользоваться тестовой базой
class BookstoreUserRegisterJwtLogin {

    private final BookstoreUserRegisterService userRegister;
    private final JWTUtilService jwtUtilService;
    private final BookstoreUserDetailsService bookstoreUserDetailsService;

    @Autowired
    public BookstoreUserRegisterJwtLogin(BookstoreUserRegisterService userRegister, JWTUtilService jwtUtilService, BookstoreUserDetailsService bookstoreUserDetailsService) {
        this.userRegister = userRegister;
        this.jwtUtilService = jwtUtilService;
        this.bookstoreUserDetailsService = bookstoreUserDetailsService;
    }

    @Test
    void jwtLogin() {
        ContactConfirmationPayload payload = new ContactConfirmationPayload();
        payload.setCode("123");
        payload.setContact("tester@test.com");
        BookstoreUserDetails userDetails =
                (BookstoreUserDetails) bookstoreUserDetailsService.loadUserByUsername(payload.getContact());

        assertTrue(jwtUtilService.validateToken(userRegister.jwtLogin(payload).getResult(), userDetails));
    }
}