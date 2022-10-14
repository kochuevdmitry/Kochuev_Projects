package com.example.MyBookShopApp.service;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.book.links.Book2UserEntity;
import com.example.MyBookShopApp.data.book.links.Book2UserTypeEntity;
import com.example.MyBookShopApp.data.user.UserEntity;
import com.example.MyBookShopApp.repositories.Book2UserRepository;
import com.example.MyBookShopApp.repositories.Book2UserTypeRepository;
import com.example.MyBookShopApp.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class BookshpCartService {

    private BookRepository bookRepository;
    private Book2UserRepository book2UserRepository;
    private BookstoreUserRegisterService userRegister;
    private Book2UserTypeRepository book2UserTypeRepository;
    private BookService bookService;
    private ProfileService profileService;

    @Autowired
    public BookshpCartService(BookRepository bookRepository, Book2UserRepository book2UserRepository, BookstoreUserRegisterService userRegister, Book2UserTypeRepository book2UserTypeRepository, BookService bookService, ProfileService profileService) {
        this.bookRepository = bookRepository;
        this.book2UserRepository = book2UserRepository;
        this.userRegister = userRegister;
        this.book2UserTypeRepository = book2UserTypeRepository;
        this.bookService = bookService;
        this.profileService = profileService;
    }

    //move to KEPT or CART
    public void changeBookStatus(String slug, String status) throws Exception {
        UserEntity user = (UserEntity) userRegister.getCurrentUser();
        Book book = bookRepository.findBookBySlug(slug);
        Book2UserEntity book2User = book2UserRepository.findBook2UserEntityByBookIdAndUserId(book.getId(), user.getId());
        Book2UserTypeEntity book2UserTypeEntity = book2UserTypeRepository.findBook2UserTypeEntitiesByName(status);

        if (book2User == null) {
            book2User = new Book2UserEntity();
            book2User.setBookId(book.getId());
            book2User.setUserId(user.getId());
            book2User.setTypeId(book2UserTypeEntity.getId());
            book2User.setTime(LocalDateTime.now());
            book2UserRepository.save(book2User);
         } else {
            if (book2User.getTypeId() == 3 && book2UserTypeEntity.getId() == 4) {
                book2User.setTypeId(book2UserTypeEntity.getId());
                book2User.setTime(LocalDateTime.now());
                book2UserRepository.save(book2User);
             } else {
                if (book2User.getTypeId() == 4 && book2UserTypeEntity.getId() == 3) {
                    book2User.setTypeId(book2UserTypeEntity.getId());
                    book2User.setTime(LocalDateTime.now());
                    book2UserRepository.save(book2User);
                }
            }

            if (book2User.getTypeId() == 1 && book2User.getTypeId() == 2) {
                book2User.setTypeId(book2UserTypeEntity.getId());
                book2User.setTime(LocalDateTime.now());
                book2UserRepository.save(book2User);
            }
        }
        bookService.updatePopularity(book.getId());
    }

    public void removeBookFromCart(String slug) throws Exception {
        UserEntity user = (UserEntity) userRegister.getCurrentUser();
        Book book = bookRepository.findBookBySlug(slug);
        Book2UserEntity book2User = book2UserRepository.findBook2UserEntityByBookIdAndUserId(book.getId(), user.getId());
        book2UserRepository.delete(book2User);
        bookService.updatePopularity(book.getId());
    }

    public List<Book> loadLinkedBooksForUser(Integer typeId) throws Exception {
        UserEntity user = (UserEntity) userRegister.getCurrentUser();
        if (user != null) {
            List<Book2UserEntity> book2UserEntities = book2UserRepository.findAllByUserIdAndTypeId(user.getId(), typeId);
            List<Book> books = new ArrayList<>();

            if (book2UserEntities != null) {
                for (Book2UserEntity book2User : book2UserEntities) {
                    books.add(bookRepository.findBookById(book2User.getBookId()));
                }
                return books;
            } else {
                return null;
            }
        }
        return null;
    }

    //all CART books purchase
    public boolean doCartPurchase(Double amount, Integer typeId) throws Exception {
        UserEntity user = (UserEntity) userRegister.getCurrentUser();
        if (user.getBalance() >= amount) {
            List<Book2UserEntity> book2UserEntities = book2UserRepository.findAllByUserIdAndTypeId(user.getId(), typeId);

            if (book2UserEntities != null) {
                profileService.payment(user, -amount, "Books purchase");

                for (Book2UserEntity book2User : book2UserEntities) {
                    if (book2User.getTypeId() == 2) {
                        book2User.setTypeId(3);
                        book2User.setTime(LocalDateTime.now());
                        book2UserRepository.save(book2User);
                        bookService.updatePopularity(book2User.getBookId());
                    }
                }
            }
            return true;
        }
        return false;
    }

    public Double checkCartAmount() throws Exception {
        List<Book> books = loadLinkedBooksForUser(2);
        Double amount = 0.0;
        if (books != null) {
            for (Book book : books) {
                amount += book.getPriceOld() * (1.0 - book.getPrice());
            }
        }
        return amount;
    }

}
