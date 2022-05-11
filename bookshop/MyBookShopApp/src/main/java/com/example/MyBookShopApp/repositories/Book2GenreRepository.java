package com.example.MyBookShopApp.repositories;

import com.example.MyBookShopApp.data.book.links.Book2GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Book2GenreRepository extends JpaRepository<Book2GenreEntity, Integer> {

    Book2GenreEntity findBook2GenreEntityByBookIdAndGenreId(int bookId, int genreId);
}
