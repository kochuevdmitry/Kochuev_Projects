package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.example.web.dto.BookRegex;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepository implements ProjectRepository<Book>, ApplicationContextAware {

    private final Logger logger = Logger.getLogger(BookRepository.class);
    private ApplicationContext context;

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public BookRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Book> getAll() {

        List<Book> books = jdbcTemplate.query("SELECT * FROM books", (ResultSet rs, int rowNum) -> {
            Book book = new Book();
            book.setId(rs.getInt("id"));
            book.setAuthor(rs.getString("author"));
            book.setTitle(rs.getString("title"));
            book.setSize(rs.getInt("size"));
            return book;
        });
        return new ArrayList<>(books);
    }


    @Override
    public void store(Book book) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("author", book.getAuthor());
        parameterSource.addValue("title", book.getTitle());
        parameterSource.addValue("size", book.getSize());
        jdbcTemplate.update("INSERT INTO books(author,title,size) VALUES(:author,:title,:size)", parameterSource);
        logger.info("store new book: " + book);
    }

    @Override
    public boolean removeItemById(Integer bookIdToRemove) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", bookIdToRemove);
        jdbcTemplate.update("DELETE FROM books WHERE id = :id", parameterSource);
        logger.info("remove book completed");
        return true;

    }

    @Override
    public boolean removeItemByRegex(BookRegex book) {
        for(Book bookItem : getAll()){
            if(!book.getAuthor().equals("") && bookItem.getAuthor().matches(book.getAuthor())){
                MapSqlParameterSource parameterSource = new MapSqlParameterSource();
                parameterSource.addValue("author", bookItem.getAuthor());
                jdbcTemplate.update("DELETE FROM books WHERE author = :author", parameterSource);
                logger.info("remove book by author completed: " + bookItem);
                continue;
            }
            if(!book.getTitle().equals("") && bookItem.getTitle().matches(book.getTitle())){
                MapSqlParameterSource parameterSource = new MapSqlParameterSource();
                parameterSource.addValue("title", bookItem.getTitle());
                jdbcTemplate.update("DELETE FROM books WHERE title = :title", parameterSource);
                logger.info("remove book by title completed: " + bookItem);
                continue;
            }
            if(book.getSize() != null && bookItem.getSize().toString().matches(book.getSize().toString())){
                MapSqlParameterSource parameterSource = new MapSqlParameterSource();
                parameterSource.addValue("size", bookItem.getSize());
                jdbcTemplate.update("DELETE FROM books WHERE size = :size", parameterSource);
                logger.info("remove book by size completed: " + bookItem);
                continue;
            }
        }
        return true;
    }

    @Override
    public List<Book> getFilteredBooks(BookRegex book) {
        List<Book> allBooks = getAll();
        List<Book> result = new ArrayList<>();
        for (Book bookItem : allBooks){
            if(!book.getAuthor().equals("") && bookItem.getAuthor().matches(book.getAuthor())){
                logger.info("filtered book by author completed: " + book);
                result.add(bookItem);
                continue;
            }
            if(!book.getTitle().equals("") && bookItem.getTitle().matches(book.getTitle())){
                logger.info("filtered book by title completed: " + book);
                result.add(bookItem);
                continue;
            }
            if(book.getSize() != null && bookItem.getSize().toString().matches(book.getSize().toString())){
                logger.info("filtered book by size completed: " + book);
                result.add(bookItem);
                continue;
            }
        }
        return result;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    private void defaultInit(){
        logger.info("default INIT in book repo bean");
    }

    private void defaultDestroy(){
        logger.info("default DESTROY in book repo bean");
    }
}
