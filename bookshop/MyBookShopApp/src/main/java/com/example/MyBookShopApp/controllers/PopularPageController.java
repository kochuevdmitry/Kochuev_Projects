package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.dto.BookDto;
import com.example.MyBookShopApp.data.dto.PopularPageDto;
import com.example.MyBookShopApp.service.BookService;
import com.example.MyBookShopApp.service.BookshpCartService;
import com.example.MyBookShopApp.service.BookstoreUserRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class PopularPageController extends CommonFooterAndHeaderController {

    @Autowired
    public PopularPageController(BookstoreUserRegisterService userRegister, BookshpCartService bookshpCartService, BookService bookService) {
        super(userRegister, bookshpCartService, bookService);
    }

    @GetMapping("/books/popul")
    public String mainPage() {
        return "/books/popular";
    }

    @ModelAttribute("popularBooks")
    public List<BookDto> popularBooks() {
        return bookService.getListOfPopularBooks(0, 20);
    }

    @GetMapping("/books/populs")
    @ResponseBody
    public PopularPageDto getPopularBooksPage(@RequestParam(required = false) Integer offset,
                                              @RequestParam(required = false) Integer limit) {
        int offsett = 0;
        int limitt = 20;
        if (offset != null) {
            offsett = offset;
        }
        if (limit != null) {
            limitt = limit;
        }
        return new PopularPageDto(bookService.getListOfPopularBooks(offsett, limitt));
    }
}
