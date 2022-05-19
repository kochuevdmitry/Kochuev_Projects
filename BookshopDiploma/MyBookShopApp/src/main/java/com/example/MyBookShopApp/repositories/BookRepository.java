package com.example.MyBookShopApp.repositories;

import com.example.MyBookShopApp.data.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findBooksByAuthor_FirstName(String name);

    @Query("from Book")
    List<Book> customFindAllBooks();//тестируем кастомный метод выборки всех книг, который в принципе уже есть в сервисе

    List<Book> findBooksByAuthorFirstNameContaining(String authorFirstName);

    List<Book> findBooksByTitleContaining(String bookTitle);

    List<Book> findBooksByPriceOldBetween(Integer min, Integer max);

    List<Book> findBooksByPriceOldIs(Integer price);

    @Query("from Book where isBestseller=1")
    List<Book> getBestsellers();

    @Query(value = "SELECT * FROM books WHERE discount = (SELECT MAX(discount) FROM books)", nativeQuery = true)
    List<Book> getBooksWithMaxDiscount();

    Page<Book> findBookByTitleContaining(String bookTitle, Pageable nextPage);

    Integer countBooksByTitleContaining(String bookTitle);

    @Query("from Book ORDER BY pubDate")
    List<Book> findAllRecentBooks(Pageable nextPage);

    @Query("from Book ORDER BY popularity DESC")
    List<Book> findAllOrderByPopularity();

    Page<Book> findBooksByPubDateBetweenOrderByPubDate(Date recentFrom, Date recentTo, Pageable nextPage);

    List<Book> findBooksByGenreId(Integer genreId, Pageable nextPage);

    List<Book> findBooksByAuthorId(Integer authorId, Pageable nextPage);

    List<Book> findBooksByAuthorId(Integer authorId);

    Book findBookBySlug(String slug);

    List<Book> findBooksBySlugIn(String[] slugs);

    Book findBookById(Integer id);

    @Query("from Book ORDER BY rating DESC")
    List<Book> findAllOrderByRating(Pageable nextPage);
}
