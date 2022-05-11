package com.example.MyBookShopApp.service;

import com.example.MyBookShopApp.controllers.GlobalExceptionHandlerController;
import com.example.MyBookShopApp.data.BookstoreUserDetails;
import com.example.MyBookShopApp.data.PhoneNumberUserDetails;
import com.example.MyBookShopApp.data.user.UserEntity;
import com.example.MyBookShopApp.repositories.BookstoreUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class BookstoreUserDetailsService implements UserDetailsService {

    private final BookstoreUserRepository bookstoreUserRepository;
    @Autowired
    private GlobalExceptionHandlerController handlerController;

    @Autowired
    public BookstoreUserDetailsService(BookstoreUserRepository bookstoreUserRepository) {
        this.bookstoreUserRepository = bookstoreUserRepository;
    }

    public void save(UserEntity user) {
        bookstoreUserRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserEntity bookstoreUser = bookstoreUserRepository.findUserEntityByEmail(s);
        if (bookstoreUser != null) {
            return new BookstoreUserDetails(bookstoreUser);
        }

        bookstoreUser = bookstoreUserRepository.findUserEntityByPhone(s);
        if (bookstoreUser != null) {
            return new PhoneNumberUserDetails(bookstoreUser);
        } else {
            handlerController.handleUsernameNotFoundException(new UsernameNotFoundException("user not found doh!"));
            throw new UsernameNotFoundException("user not found doh!");
        }
    }

}
