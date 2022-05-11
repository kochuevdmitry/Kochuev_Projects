package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.Tags;
import com.example.MyBookShopApp.data.dto.BooksPageDto;
import com.example.MyBookShopApp.service.BookService;
import com.example.MyBookShopApp.service.BookshpCartService;
import com.example.MyBookShopApp.service.BookstoreUserRegisterService;
import com.example.MyBookShopApp.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class TagsPageController extends CommonFooterAndHeaderController {
    private TagsService tagsService;

    @Autowired
    public TagsPageController(BookstoreUserRegisterService userRegister, BookshpCartService bookshpCartService, BookService bookService, TagsService tagsService) {
        super(userRegister, bookshpCartService, bookService);
        this.tagsService = tagsService;
    }

    @ModelAttribute("tags")
    public Map<Tags, Integer> tags() {
        return bookService.getMapOfTags();
    }

    @GetMapping(value = {"/tags", "/tags/{tagId}"})
    public String tagsPage(@PathVariable(value = "tagId", required = false) Integer tagId,
                           Model model) {
        model.addAttribute("taggedBooks", bookService.getListOfTaggedBooks(0, 20, tagId));
        model.addAttribute("tagName", tagsService.getTag(tagId));
        return "/tags/index";
    }

    @GetMapping("/tags/page/{tagId}")
    @ResponseBody
    public BooksPageDto getPopularBooksPage(@PathVariable(value = "tagId", required = false) Integer tagId,
                                            @RequestParam("offset") Integer offset,
                                            @RequestParam("limit") Integer limit) {
        return new BooksPageDto(bookService.getListOfTaggedBooks(offset, limit, tagId));
    }

}
