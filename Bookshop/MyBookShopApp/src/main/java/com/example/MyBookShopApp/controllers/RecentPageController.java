package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.dto.BookDto;
import com.example.MyBookShopApp.data.dto.BooksPageDto;
import com.example.MyBookShopApp.data.dto.RecentDatesDto;
import com.example.MyBookShopApp.service.BookService;
import com.example.MyBookShopApp.service.BookshpCartService;
import com.example.MyBookShopApp.service.BookstoreUserRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class RecentPageController extends CommonFooterAndHeaderController {

    @Autowired
    public RecentPageController(BookstoreUserRegisterService userRegister, BookshpCartService bookshpCartService, BookService bookService) {
        super(userRegister, bookshpCartService, bookService);
    }

    @ModelAttribute("recentBooks")
    public List<BookDto> recentBooks() throws ParseException {
        return bookService.getPageOfRecentBooks(recentDatesDto().getRecentFrom()
                , recentDatesDto().getRecentTo(), 0, 6);
    }

    @GetMapping("/books/new")
    public String mainPage() {
        return "/books/recent";
    }

    @ModelAttribute("recentDatesDto")
    public RecentDatesDto recentDatesDto() {
        Date from = java.sql.Date.valueOf(LocalDate.now().minusMonths(1));
        Date to = java.sql.Date.valueOf(LocalDate.now());
        return new RecentDatesDto(from, to);
    }

    @ModelAttribute("recentBooksList")
    public List<BookDto> recentBooksList() {
        return new ArrayList<>();
    }

    @GetMapping("/books/recents")
    @ResponseBody
    public BooksPageDto getNextRecentPage(@RequestParam("from") String from,
                                          @RequestParam("to") String to,
                                          @RequestParam("offset") Integer offset,
                                          @RequestParam("limit") Integer limit
    ) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Date recentFrom;
        Date recentTo;
        if (from.equals("")) {
            recentFrom = formatter.parse("00.00.0000");
        } else {
            recentFrom = formatter.parse(from);
        }
        if (to.equals("")) {
            recentTo = formatter.parse("00.00.0000");
        } else {
            recentTo = formatter.parse(to);
        }

        return new BooksPageDto(bookService.getPageOfRecentBooks(recentFrom, recentTo, offset, limit));
    }

}
