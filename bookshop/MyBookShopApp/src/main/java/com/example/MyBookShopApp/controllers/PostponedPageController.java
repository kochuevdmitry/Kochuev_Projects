package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.service.BookService;
import com.example.MyBookShopApp.service.BookshpCartService;
import com.example.MyBookShopApp.service.BookstoreUserRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class PostponedPageController extends CommonFooterAndHeaderController {

    @Autowired
    public PostponedPageController(BookstoreUserRegisterService userRegister, BookshpCartService bookshpCartService, BookService bookService) {
        super(userRegister, bookshpCartService, bookService);
    }

    @ModelAttribute("postponedBooks")
    public List<Book> postponedBooks() throws Exception {
        return bookshpCartService.loadLinkedBooksForUser(1);
    }

    @GetMapping("/postponed")
    public String mainPage() {
        return "/postponed";
    }
}
