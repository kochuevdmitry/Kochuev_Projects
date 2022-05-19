package com.example.MyBookShopApp.repositories;

import com.example.MyBookShopApp.data.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchRepository extends JpaRepository<Book, Integer> {
    Page<Book> findBookByTitleContaining(String bookTitle, Pageable nextPage);
}
