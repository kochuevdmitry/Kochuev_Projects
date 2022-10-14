package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.Genre;
import com.example.MyBookShopApp.data.dto.BooksPageDto;
import com.example.MyBookShopApp.data.dto.GenresDto;
import com.example.MyBookShopApp.service.BookService;
import com.example.MyBookShopApp.service.BookshpCartService;
import com.example.MyBookShopApp.service.BookstoreUserRegisterService;
import com.example.MyBookShopApp.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class GenresPageController extends CommonFooterAndHeaderController {

    private GenreService genreService;

    @Autowired
    public GenresPageController(BookstoreUserRegisterService userRegister, BookshpCartService bookshpCartService, BookService bookService, GenreService genreService) {
        super(userRegister, bookshpCartService, bookService);
        this.genreService = genreService;
    }

    @ModelAttribute("genresData")
    public List<GenresDto> genresDtoTree() {
        List<GenresDto> genresBooks = genreService.getTopGenresList();
        return genresBooks;
    }

    @ModelAttribute("genresList")
    public List<Genre> genresList() {
        List<Genre> genresList = genreService.getGenresList();
        return genresList;
    }

    @GetMapping("/genres")
    public String genresPage() {
        return "/genres/index";
    }


    @GetMapping("/genres/slug/{genreId}")
    public String gerePage(@PathVariable(value = "genreId", required = false) Integer genreId,
                           Model model) {
        if (genreId != null) {
            model.addAttribute("books", genreService.getBooksByGenreId(genreId, 0, 6));
            model.addAttribute("genre", genreService.getGenreById(genreId));
        } else {
            return "/genres/index";
        }
        return "/genres/slug";
    }

    @GetMapping("/books/genre/{genreId}")
    @ResponseBody
    public BooksPageDto getRecentBooksPage(@RequestParam("offset") Integer offset,
                                           @RequestParam("limit") Integer limit, @PathVariable(value = "genreId", required = false) Integer genreId) {
        return new BooksPageDto(genreService.getBooksByGenreId(genreId, offset, limit));
    }
}
