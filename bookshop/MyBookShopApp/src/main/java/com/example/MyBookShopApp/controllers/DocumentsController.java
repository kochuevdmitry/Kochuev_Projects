package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.service.BookService;
import com.example.MyBookShopApp.service.BookshpCartService;
import com.example.MyBookShopApp.service.BookstoreUserRegisterService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DocumentsController extends CommonFooterAndHeaderController {

    public DocumentsController(BookstoreUserRegisterService userRegister, BookshpCartService bookshpCartService, BookService bookService) {
        super(userRegister, bookshpCartService, bookService);
    }

    @GetMapping("/documents")
    public String mainPage() {
        return "/documents/index";
    }
}
