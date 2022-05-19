package com.example.MyBookShopApp.repositories;

import com.example.MyBookShopApp.data.book.links.Book2TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Book2TagRepository extends JpaRepository<Book2TagEntity, Integer> {
    Book2TagEntity findBook2TagByBookIdAndTagId(int bookId, int tagId);
}
