package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.dto.SearchWordDto;
import com.example.MyBookShopApp.data.user.UserEntity;
import com.example.MyBookShopApp.service.BookService;
import com.example.MyBookShopApp.service.BookshpCartService;
import com.example.MyBookShopApp.service.BookstoreUserRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Controller
public class CommonFooterAndHeaderController {

    public final BookstoreUserRegisterService userRegister;
    public final BookshpCartService bookshpCartService;
    public final BookService bookService;
    private final Logger logger = Logger.getLogger(CommonFooterAndHeaderController.class.getName());

    @Autowired
    public CommonFooterAndHeaderController(BookstoreUserRegisterService userRegister, BookshpCartService bookshpCartService, BookService bookService) {
        this.userRegister = userRegister;
        this.bookshpCartService = bookshpCartService;
        this.bookService = bookService;
    }

    @ModelAttribute(name = "paidBooksCount")
    public Integer paidBooksCount(@CookieValue(value = "cartContents", required = false) String cartContents, Model model) throws Exception {
        if (bookService.checkAuthorization(model, userRegister)) {
            //метод подсчета книг в корзине
            return bookshpCartService.loadLinkedBooksForUser(3).size();
        }
        return 0;
    }

    @ModelAttribute(name = "authorized")
    public Boolean authorisedUser(Model model) throws Exception {
        if (bookService.checkAuthorization(model, userRegister)) {
            //метод подсчета книг в корзине
            return true;
        }
        return false;
    }

    @ModelAttribute(name = "isAdmin")
    public Boolean isAdmin(Model model) throws Exception {
        Boolean admin = false;
        try {
            admin = bookService.checkAdmin(userRegister);
        } catch (Exception e) {
            logger.info("check admin ERROR");
        }
        if (bookService.checkAuthorization(model, userRegister) && admin) {
            return true;
        }
        return false;
    }

    @ModelAttribute(name = "bookCartSize")
    public Integer bookCartSize(@CookieValue(value = "cartContents", required = false) String cartContents, Model model) throws Exception {
        if (bookService.checkAuthorization(model, userRegister)) {
            //метод подсчета книг в корзине
            return bookshpCartService.loadLinkedBooksForUser(2).size();
        }

        if (cartContents == null || cartContents.equals("")) {
            return 0;
        } else {
            return cartContents.split("/").length;
        }
    }

    @ModelAttribute("bookKeptSize")
    public Integer bookKeptSize(@CookieValue(value = "cartContents", required = false) String cartContents, Model model) throws Exception {
        if (bookService.checkAuthorization(model, userRegister)) {
            //метод подсчета книг в корзине
            return bookshpCartService.loadLinkedBooksForUser(1).size();
        }

        if (cartContents == null || cartContents.equals("")) {
            return 0;
        } else {
            return cartContents.split("/").length;
        }
    }

    @ModelAttribute("searchResults")
    public List<Book> searchResults() {
        return new ArrayList<>();
    }

    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto() {
        return new SearchWordDto();
    }

    @ModelAttribute("searchCount")
    public Integer searchCount() {
        return 0;
    }

    @ModelAttribute("currUsr")
    public UserEntity getCurrUser() throws Exception {
        Authentication authenticationCheck = SecurityContextHolder.getContext().getAuthentication();
        if (authenticationCheck != null && !authenticationCheck.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ANONYMOUS"))) {
            logger.info("getCurrUser " + userRegister.getCurrentUser());
            return (UserEntity) userRegister.getCurrentUser();
        }
        return new UserEntity();
    }

}
