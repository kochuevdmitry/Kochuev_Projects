package com.example.MyBookShopApp.service;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.book.links.Book2UserViewHistoryEntity;
import com.example.MyBookShopApp.data.user.UserEntity;
import com.example.MyBookShopApp.repositories.Book2UserViewHistoryRepository;
import com.example.MyBookShopApp.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class Book2UserViewHistoryService {

    private BookstoreUserRegisterService userRegister;
    private Book2UserViewHistoryRepository book2UserViewHistoryRepository;

    @Autowired
    public Book2UserViewHistoryService(BookstoreUserRegisterService userRegister, Book2UserViewHistoryRepository book2UserViewHistoryRepository, BookRepository bookRepository) {
        this.userRegister = userRegister;
        this.book2UserViewHistoryRepository = book2UserViewHistoryRepository;
    }

    public boolean addBookUserViewTransaction(Book book) throws Exception {
        UserEntity user = (UserEntity) userRegister.getCurrentUser();
        Book2UserViewHistoryEntity book2UserViewHistoryEntity = new Book2UserViewHistoryEntity();
        book2UserViewHistoryEntity.setBook(book);
        book2UserViewHistoryEntity.setUserId(user.getId());
        book2UserViewHistoryEntity.setTime(LocalDateTime.now());
        book2UserViewHistoryRepository.save(book2UserViewHistoryEntity);
        return true;
    }
}
