package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.Authors;
import com.example.MyBookShopApp.data.dto.BooksPageDto;
import com.example.MyBookShopApp.service.AuthorsService;
import com.example.MyBookShopApp.service.BookService;
import com.example.MyBookShopApp.service.BookshpCartService;
import com.example.MyBookShopApp.service.BookstoreUserRegisterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@Api(description = "authors data")
public class AuthorsPageController extends CommonFooterAndHeaderController {

    private AuthorsService authorsService;

    @Autowired
    public AuthorsPageController(BookstoreUserRegisterService userRegister, BookshpCartService bookshpCartService, BookService bookService, AuthorsService authorsService) {
        super(userRegister,
                bookshpCartService,
                bookService);
        this.authorsService = authorsService;
    }

    @ModelAttribute("authorsData")
    public Map<String, List<Authors>> authorsMap() {
        Map<String, List<Authors>> mapa = authorsService.getAuthorsMap();
        return mapa;
    }

    @GetMapping("/authors")
    public String authorsPage() {
        return "/authors/index";
    }

    @ApiOperation("method to get map of authors")//дополнительная информация в api
    @GetMapping("/api/authors")
    @ResponseBody
    public Map<String, List<Authors>> authors() {
        Map<String, List<Authors>> mapa = authorsService.getAuthorsMap();
        return mapa;
    }

    @GetMapping("/authors/slug/{authorId}")
    public String gerePage(@PathVariable(value = "authorId", required = false) Integer authorId,
                           Model model) {
        if (authorId == null) {
            return "/authors/index";
        }

        model.addAttribute("books", authorsService.getBooksByAuthorId(authorId, 0, 6));
        model.addAttribute("author", authorsService.getAuthorById(authorId));
        return "/authors/slug";
    }


    @GetMapping("/books/authorbooks/{authorId}")
    public String getAuthorBooks(@PathVariable(value = "authorId", required = false) Integer authorId, Model model) {
        model.addAttribute("booksByAuthor", authorsService.getBooksByAuthorId(authorId, 0, 6));
        model.addAttribute("author", authorsService.getAuthorById(authorId));
        return "/books/author";
    }

    @GetMapping("/books/author/{authorId}")
    @ResponseBody
    public BooksPageDto getAuthorBooksPage(@RequestParam("offset") Integer offset,
                                           @RequestParam("limit") Integer limit,
                                           @PathVariable(value = "authorId", required = false) Integer authorId) {
        return new BooksPageDto(authorsService.getBooksByAuthorId(authorId, offset, limit));
    }

}
