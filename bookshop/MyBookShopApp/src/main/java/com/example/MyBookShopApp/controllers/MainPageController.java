package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.Tags;
import com.example.MyBookShopApp.data.dto.BookDto;
import com.example.MyBookShopApp.data.dto.BooksPageDto;
import com.example.MyBookShopApp.data.dto.SearchWordDto;
import com.example.MyBookShopApp.errs.EmptySearchException;
import com.example.MyBookShopApp.service.BookService;
import com.example.MyBookShopApp.service.BookshpCartService;
import com.example.MyBookShopApp.service.BookstoreUserRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Controller
public class MainPageController extends CommonFooterAndHeaderController {

    private final Logger logger = Logger.getLogger(MainPageController.class.getName());

    @Autowired
    public MainPageController(BookstoreUserRegisterService userRegister, BookshpCartService bookshpCartService, BookService bookService) {
        super(userRegister, bookshpCartService, bookService);
    }

    @GetMapping("/")
    public String mainPage() {
        return "index";
    }

    @ModelAttribute("recommendedBooks")
    public List<BookDto> recommendedBooks(Model model) throws Exception {
        if (bookService.checkAuthorization(model, userRegister)) {
            if (bookService.getPageOfRecommendedBooksForUser(0, 6).size() == 0) {
                return bookService.getPageOfRecommendedBooks(0, 6);
            }
            return bookService.getPageOfRecommendedBooksForUser(0, 6);
        }
        return bookService.getPageOfRecommendedBooks(0, 6);
    }

    @GetMapping("/books/recommended")
    @ResponseBody
    public BooksPageDto getRecommendedBooksPage(@RequestParam("offset") Integer offset,
                                                @RequestParam("limit") Integer limit,
                                                Model model) throws Exception {
        if (bookService.checkAuthorization(model, userRegister)) {
            if (bookService.getPageOfRecommendedBooksForUser(offset, limit).size() == 0) {
                return new BooksPageDto(bookService.getPageOfRecommendedBooks(offset, limit));
            }
            return new BooksPageDto(bookService.getPageOfRecommendedBooksForUser(offset, limit));
        }

        List<BookDto> bookList = bookService.getPageOfRecommendedBooks(offset, limit);
        return new BooksPageDto(bookList);//.getContent());
    }

    @GetMapping(value = {"/search", "/search/{searchWord}"})
    public String getSearchResults(@PathVariable(value = "searchWord", required = false) SearchWordDto searchWordDto,
                                   Model model) throws EmptySearchException {
        if (searchWordDto != null) {
            logger.info("getSearchResults " + searchWordDto.getExample());
            model.addAttribute("searchWordDto", searchWordDto);
            model.addAttribute("searchResults", bookService.getPageOfSearchResultBooks(searchWordDto.getExample(), 0, 5).getContent());
            //можно переключить поиск на поиск кинг гугл - строчка ниже расскоментить, а верх закоментить
            //model.addAttribute("searchResults", bookService.getPageOfGoogleBooksApiSearchResult(searchWordDto.getExample(), 0, 5));
            model.addAttribute("searchCount",
                    bookService.getCountOfSearchResultBooks(searchWordDto.getExample()));
            return "/search/index";
        } else {
            throw new EmptySearchException("Поиск по null невозможен");
        }

    }

    @GetMapping("/search/page/{searchWord}")
    @ResponseBody
    public BooksPageDto getNextSearchPage(@RequestParam("offset") Integer offset,
                                          @RequestParam("limit") Integer limit,
                                          @PathVariable(value = "searchWord", required = false) SearchWordDto searchWordDto) {
        //logger.info("getNextSearchPage " + offset + limit + searchWordDto.getExample());
        //можно переключить с поиска по базе книг на поиск по гуглу - это поиск не в ехедера, а поиск на самой поисковой страничке
        //return new BooksPageDto(bookService.getPageOfSearchResultBooks(searchWordDto.getExample(), offset, limit).getContent());
        return new BooksPageDto(bookService.getPageOfGoogleBooksApiSearchResult(searchWordDto.getExample(), offset, limit));
    }

    @ModelAttribute("recentBooks")
    public List<BookDto> recentBooks() {
        return bookService.getListOfRecentBooks(0, 6);
    }

    @GetMapping("/books/recent")
    @ResponseBody
    public BooksPageDto getRecentBooksPage(@RequestParam("offset") Integer offset,
                                           @RequestParam("limit") Integer limit) {
        return new BooksPageDto(bookService.getListOfRecentBooks(offset, limit));
    }

    @ModelAttribute("popularBooks")
    public List<BookDto> popularBooks() {
        return bookService.getListOfPopularBooks(0, 6);
    }

    @GetMapping("/books/popular")
    @ResponseBody
    public BooksPageDto getPopularBooksPage(@RequestParam("offset") Integer offset,
                                            @RequestParam("limit") Integer limit) {
        return new BooksPageDto(bookService.getListOfPopularBooks(offset, limit));
    }

    @ModelAttribute("tags")
    public Map<Tags, Integer> tags() {
        return bookService.getMapOfTags();
    }

}
