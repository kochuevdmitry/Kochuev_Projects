package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.BookService;
import org.example.web.dto.Book;
import org.example.web.dto.BookAuthorToRemove;
import org.example.web.dto.BookIdToRemove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

@Controller
@RequestMapping(value = "/books")
public class BookShelfController {

    private Logger logger = Logger.getLogger(BookShelfController.class);
    private BookService bookService;

    @Autowired
    public BookShelfController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/shelf")
    public String books(Model model) {
        refreshBooksShelf(new Book(), bookService.getAllBooks(), new BookIdToRemove(), new BookAuthorToRemove(),  model, logger);
        logger.info("book shelf loaded");
        return "book_shelf";
    }

    @GetMapping("/shelf/filter_by_author")
    public String booksByAuthor(@RequestParam(value = "authorRegex") String authorRegex, Model model) {
        logger.info("lets filter by author");
        if (authorRegex.equals("")){
            logger.info("empty field");
            refreshBooksShelf(new Book(), bookService.getAllBooks(), new BookIdToRemove(), new BookAuthorToRemove(),  model, logger);
            return "book_shelf";
        }
        bookService.refreshBooksShelfByAuthor(model, authorRegex, logger);
        return "book_shelf";
    }

    @PostMapping("/save")
    public String saveBook(@Valid Book book, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            logger.info("Mistake adding book " + bindingResult.toString());
            refreshBooksShelf(book, bookService.getAllBooks(), new BookIdToRemove(), new BookAuthorToRemove(),  model, logger);
            return "book_shelf";
        } else {
            bookService.saveBook(book);
            logger.info("current repository size: " + bookService.getAllBooks().size());
            return "redirect:/books/shelf";
        }

    }

    //если убрать в BookService, то не работают  <td th:if="${#fields.hasErrors('author')}">
    public void refreshBooksShelf(Book book, List<Book> books, BookIdToRemove bookIdToRemove, BookAuthorToRemove bookAuthorToRemove, Model model, Logger logger) {
        logger.info("got book shelf");
        model.addAttribute("book", book);
        model.addAttribute("bookList", books);
        model.addAttribute("bookIdToRemove", bookIdToRemove);
        model.addAttribute("bookAuthorToRemove", bookAuthorToRemove);
    }

    @PostMapping("/remove")
    public String removeBookById(@Valid BookIdToRemove bookIdToRemove, BindingResult bindingResult, Model model) {
        logger.info("lets remove by id");
        if (bindingResult.hasErrors()) {
            logger.info("incorrect id");
            refreshBooksShelf(new Book(), bookService.getAllBooks(), new BookIdToRemove(), new BookAuthorToRemove(),  model, logger);
            model.addAttribute("errorIdRemove", "please input 4 digits ID");
            return "book_shelf";
        }

        if (bookService.removeBookById(bookIdToRemove.getId())) {
            return "redirect:/books/shelf";
        } else {
            refreshBooksShelf(new Book(), bookService.getAllBooks(), bookIdToRemove, new BookAuthorToRemove(),  model, logger);
            model.addAttribute("errorIdRemove", "no such id");
            return "book_shelf";
        }

    }

    @PostMapping("/remove_by_author")
    public String removeBookByAuthor(@Valid BookAuthorToRemove bookAuthorToRemove, BindingResult bindingResult, Model model) {
        logger.info("lets remove by author");
        if (bindingResult.hasErrors()) {
            logger.info("incorrect author");
            refreshBooksShelf(new Book(), bookService.getAllBooks(), new BookIdToRemove(), new BookAuthorToRemove(),  model, logger);
            model.addAttribute("errorAuthorRemove", "please input correct author");
            return "book_shelf";
        }

        if (bookService.removeBookByAuthor(bookAuthorToRemove.getAuthor())) {
            return "redirect:/books/shelf";
        } else {
            refreshBooksShelf(new Book(), bookService.getAllBooks(), new BookIdToRemove(), bookAuthorToRemove,  model, logger);
            model.addAttribute("errorIdRemove", "no such author");
            return "book_shelf";
        }

    }

    /*@PostMapping("/remove_by_author")
    public String removeBookByAuthor(@RequestParam(value = "bookAuthorToRemove") String bookAuthorToRemove) {
        if (bookService.removeBookByAuthor(bookAuthorToRemove)) {
            return "redirect:/books/shelf";
        } else {
            return "redirect:/books/shelf";
        }
    }*/

    @PostMapping("/remove_by_title")
    public String removeBookByTitle(@RequestParam(value = "bookTitleToRemove") String bookTitleToRemove) {
        if (bookService.removeBookByTitle(bookTitleToRemove)) {
            return "redirect:/books/shelf";
        } else {
            return "redirect:/books/shelf";
        }
    }

    @PostMapping("/remove_by_size")
    public String removeBookBySize(@RequestParam(value = "bookSizeToRemove") Integer bookSizeToRemove) {
        if (bookService.removeBookBySize(bookSizeToRemove)) {
            return "redirect:/books/shelf";
        } else {
            return "redirect:/books/shelf";
        }
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("file") MultipartFile file, Model model) throws Exception {

        logger.info("file upload start");
        if (file.isEmpty()) {
            logger.info("file upload fail, empty");
            refreshBooksShelf(new Book(), bookService.getAllBooks(), new BookIdToRemove(), new BookAuthorToRemove(),  model, logger);
            model.addAttribute("errorUpload", "empty file, please choose correct file");
            return "book_shelf";
        }
        logger.info("file is ok");

        String name = file.getOriginalFilename();
        byte[] bytes = file.getBytes();

        //create dir
        String rootPath = System.getProperty("catalina.home");
        File dir = new File(rootPath + File.separator + "external_uploads");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        //create file
        File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
        stream.write(bytes);
        stream.close();

        logger.info("Path - " + rootPath);
        logger.info("new file saves at: " + serverFile.getAbsolutePath());

        return "redirect:/books/shelf";
    }

}
