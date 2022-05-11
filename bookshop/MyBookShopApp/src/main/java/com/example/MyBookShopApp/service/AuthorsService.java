package com.example.MyBookShopApp.service;

import com.example.MyBookShopApp.data.Authors;
import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.dto.BookDto;
import com.example.MyBookShopApp.helpers.Helpers;
import com.example.MyBookShopApp.repositories.AuthorsRepository;
import com.example.MyBookShopApp.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthorsService {

    private AuthorsRepository authorsRepository;
    private BookRepository bookRepository;

    @Autowired
    public AuthorsService(AuthorsRepository authorsRepository, BookRepository bookRepository) {
        this.authorsRepository = authorsRepository;
        this.bookRepository = bookRepository;
    }

    public Map<String, List<Authors>> getAuthorsMap() {
        return authorsRepository.findAll().stream().collect(Collectors.groupingBy((Authors a) -> {
            return a.getLastName().substring(0, 1);
        }));
    }

    public Map<String, Integer> getMapOfTags() {
        return authorsRepository.findAuthorsAndCount();
    }

    public Authors getAuthorById(Integer authorId) {
        return authorsRepository.findAuthorsById(authorId);
    }

    public List<BookDto> getBooksByAuthorId(Integer authorId, Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        List<Book> bookList = bookRepository.findBooksByAuthorId(authorId, nextPage);
        List<BookDto> bookDtos = new ArrayList<>();
        if (bookList != null) {
            for (Book book : bookList) {
                bookDtos.add(Helpers.BookHelper.bookToBookDto(book));
            }
        }
        return bookDtos;
    }
}

