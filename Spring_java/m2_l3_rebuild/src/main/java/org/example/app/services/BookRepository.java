package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Repository
public class BookRepository implements ProjectRepository<Book> {

    private final Logger logger = Logger.getLogger(BookRepository.class);
    private final List<Book> repo = new ArrayList<>();
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public BookRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Book> retreiveAll() {
        List<Book> books = jdbcTemplate.query("SELECT * FROM books",
                (ResultSet rs, int rowline) -> {
                    Book book = new Book();
                    book.setId(rs.getInt("id"));
                    book.setAuthor(rs.getString("author"));
                    book.setTitle(rs.getString("title"));
                    book.setSize(rs.getInt("size"));
                    return book;
                });
        return books;
    }

    public List<Book> retreiveByAuthor(String author) {
        logger.info("filter by author regex bookRepo");
        List<Book> books = retreiveAll();
        List<Book> booksByAuthor =  new ArrayList<>();

        for (Book book: books){
            if (book.getAuthor().toLowerCase(Locale.ROOT).contains(author.toLowerCase(Locale.ROOT))){
                booksByAuthor.add(book);
            }
        }
        logger.info("filter by author regex bookRepo " + books.size());
        return booksByAuthor;
    }


    @Override
    public void store(Book book) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("author", book.getAuthor());
        parameterSource.addValue("title", book.getTitle());
        parameterSource.addValue("size", book.getSize());
        jdbcTemplate.update(
                "INSERT INTO  books(author,title, size) VALUES (:author, :title, :size)"
                , parameterSource);
        logger.info("store new book: " + book);

    }

    @Override
    public boolean removeItemById(Integer bookIdToRemove) {
        logger.info("remove by id started");
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", bookIdToRemove);
        List<Book> books = jdbcTemplate.query("SELECT * FROM books WHERE id=:id", parameterSource,
                (ResultSet rs, int rowline) -> {
                    Book book = new Book();
                    book.setId(rs.getInt("id"));
                    book.setAuthor(rs.getString("author"));
                    book.setTitle(rs.getString("title"));
                    book.setSize(rs.getInt("size"));
                    return book;
                });
        if (!books.isEmpty()) {
            jdbcTemplate.update("DELETE FROM books WHERE id = :id", parameterSource);
            logger.info("remove by id completed");
            return true;
        }
        logger.info("wrong id");
        return false;
    }

    @Override
    public boolean removeItemByAuthor(String bookAuthorToRemove) {
        logger.info("remove by author started");
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("author", bookAuthorToRemove);
        List<Book> books = jdbcTemplate.query("SELECT * FROM books WHERE author=:author", parameterSource,
                (ResultSet rs, int rowline) -> {
                    Book book = new Book();
                    book.setId(rs.getInt("id"));
                    book.setAuthor(rs.getString("author"));
                    book.setTitle(rs.getString("title"));
                    book.setSize(rs.getInt("size"));
                    return book;
                });
        if (!books.isEmpty()) {
            jdbcTemplate.update("DELETE FROM books WHERE author=:author", parameterSource);
            logger.info("remove by author completed");
            return true;
        }
        logger.info("wrong author");
        return false;
    }

    @Override
    public boolean removeItemByTitle(String bookTitleToRemove) {
        for (Book book : retreiveAll()) {
            if (book.getTitle().equals(bookTitleToRemove)) {
                logger.info("remove book completed: " + book);
                return repo.remove(book);
            }
        }
        return false;
    }

    @Override
    public boolean removeItemBySize(Integer bookSizeToRemove) {
        for (Book book : retreiveAll()) {
            if (book.getSize().equals(bookSizeToRemove)) {
                logger.info("remove book completed: " + book);
                return repo.remove(book);
            }
        }
        return false;
    }
}
