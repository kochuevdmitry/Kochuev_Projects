package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.dto.BookDto;
import com.example.MyBookShopApp.data.dto.RecentPageDto;
import com.example.MyBookShopApp.service.BookService;
import com.example.MyBookShopApp.service.BookshpCartService;
import com.example.MyBookShopApp.service.BookstoreUserRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class LastViewedPageController extends CommonFooterAndHeaderController {

    @Autowired
    public LastViewedPageController(BookstoreUserRegisterService userRegister, BookshpCartService bookshpCartService, BookService bookService) {
        super(userRegister, bookshpCartService, bookService);
    }

    @GetMapping("/lastview")
    public String getLastView() {

        return "/books/lastviewed";
    }

    @ModelAttribute("lastViewedBooks")
    public List<BookDto> lastViewedBooks() throws Exception {
        return bookService.getLastViewedBooksPage(0, 3);
    }

    @GetMapping(value = "/books/lastviews", produces = "application/json")
    @ResponseBody
    public RecentPageDto getLastViewed(@RequestParam(required = false) Integer offset,
                                       @RequestParam(required = false) Integer limit,
                                       Model model) throws Exception {
        List<BookDto> list2 = bookService.getLastViewedBooksPage(offset, limit);
        return new RecentPageDto(list2);
    }
}
