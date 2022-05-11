package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.dto.CartDto;
import com.example.MyBookShopApp.repositories.BookRepository;
import com.example.MyBookShopApp.service.BookService;
import com.example.MyBookShopApp.service.BookshpCartService;
import com.example.MyBookShopApp.service.BookstoreUserRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.logging.Logger;

@Controller
@RequestMapping("/books")
public class BookshpCartController extends CommonFooterAndHeaderController {

    private final BookRepository bookRepository;
    private final Logger logger = Logger.getLogger(BookshpCartController.class.getName());

    @Autowired
    public BookshpCartController(BookRepository bookRepository, BookstoreUserRegisterService userRegister, BookService bookService, BookshpCartService bookshpCartService) {
        super(userRegister, bookshpCartService, bookService);
        this.bookRepository = bookRepository;
    }

    @ModelAttribute(name = "bookSumm")
    public Double bookSumm() {
        return 0.0;
    }

    @ModelAttribute(name = "bookSummOld")
    public Double bookSummOld() {
        return 0.0;
    }

    @GetMapping("/cart")
    public String handleCartRequest(@CookieValue(value = "cartContents", required = false) String cartContents,
                                    Model model) throws Exception {

        if (bookService.checkAuthorization(model, userRegister)) {
            //метод загрузки записей Book2User из базы данных
            List<Book> books = bookshpCartService.loadLinkedBooksForUser(2);
            if (books == null) {
                model.addAttribute("isCartEmpty", true);
            } else {
                model.addAttribute("isCartEmpty", false);
                model.addAttribute("bookCart", books);
                model.addAttribute("bookSumm", countSumm(books));
                model.addAttribute("bookSummOld", countSummOld(books));
            }
            return "cart";
        }

        logger.info("BookshpCartController");
        if (cartContents == null || cartContents.equals("")) {
            model.addAttribute("isCartEmpty", true);
        } else {
            List<Book> booksFromCookieSlugs = getBooksFromCookieSlug(cartContents);

            model.addAttribute("isCartEmpty", false);
            model.addAttribute("bookCart", booksFromCookieSlugs);
            model.addAttribute("bookSumm", countSumm(booksFromCookieSlugs));
            model.addAttribute("bookSummOld", countSummOld(booksFromCookieSlugs));
        }

        return "cart";
    }

    private Double countSumm(List<Book> booksFromCookieSlugs){
        Double summ = 0.0;
        for (Book book : booksFromCookieSlugs) {
            summ += book.getPriceOld() * (1.0 - book.getPrice());
        }
        return summ;
    }

    private Double countSummOld(List<Book> booksFromCookieSlugs){
        Double summOld = 0.0;
        for (Book book : booksFromCookieSlugs) {
            summOld += book.getPriceOld();
        }
        return summOld;
    }

    private List<Book> getBooksFromCookieSlug(String cartContents){
        cartContents = cartContents.startsWith("/") ? cartContents.substring(1) : cartContents;
        cartContents = cartContents.endsWith("/") ? cartContents.substring(0, cartContents.length() - 1) : cartContents;
        String[] cookieSlugs = cartContents.split("/");
        return bookRepository.findBooksBySlugIn(cookieSlugs);
    }

    @PostMapping("/changeBookStatus/cart/remove/{slug}")
    public String handleRemoveBookFromCartRequest(@PathVariable("slug") String slug, @CookieValue(name =
            "cartContents", required = false) String cartContents, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) throws Exception {

        if (bookService.checkAuthorization(model, userRegister)) {
            //метод удаления записи Book2User из базы данных
            bookshpCartService.removeBookFromCart(slug);
            List<Book> books = bookshpCartService.loadLinkedBooksForUser(2);
            redirectAttributes.addFlashAttribute("bookCartSize", books.size());
            redirectAttributes.addFlashAttribute("bookCart", books);
            return "redirect:/books/cart";
        }

        if (cartContents != null && !cartContents.equals("")) {
            ArrayList<String> cookieBooks = new ArrayList<>(Arrays.asList(cartContents.split("/")));
            cookieBooks.remove(slug);

            logger.info("handleRemoveBookFromCartRequest " + cookieBooks.get(0));

            Cookie cookie = new Cookie("cartContents", String.join("/", cookieBooks));
            cookie.setPath("/");
            response.addCookie(cookie);
            model.addAttribute("isCartEmpty", false);
        } else {
            model.addAttribute("isCartEmpty", true);
        }

        return "redirect:/books/cart";
    }

    @PostMapping("/changeBookStatus/{slug}")
    public String handleChangeBookStatus(@PathVariable("slug") String slug, @RequestBody CartDto cartDto, @CookieValue(name = "cartContents",
            required = false) String cartContents, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) throws Exception {

        if (bookService.checkAuthorization(model, userRegister)) {
            //метод добавления книги в Book2User
            bookshpCartService.changeBookStatus(slug, cartDto.getStatus());
            redirectAttributes.addFlashAttribute("bookCartSize", bookshpCartService.loadLinkedBooksForUser(2).size());
            return "redirect:/books/" + slug;
        }

        //ext,ysq recjr hf,jns
        if (cartContents == null || cartContents.equals("")) {
            Cookie cookie = new Cookie("cartContents", slug);
            cookie.setPath("/");
            response.addCookie(cookie);
            model.addAttribute("isCartEmpty", false);
        } else if (!cartContents.contains(slug)) {
            StringJoiner stringJoiner = new StringJoiner("/");
            stringJoiner.add(cartContents).add(slug);
            Cookie cookie = new Cookie("cartContents", stringJoiner.toString());
            cookie.setPath("/");
            response.addCookie(cookie);
            model.addAttribute("isCartEmpty", false);
        }

        return "redirect:/books/" + slug;
    }

    //purchase all in cart
    @PostMapping("/cartBuy")
    public String handleCartPurchase(Model model, RedirectAttributes redirectAttributes) throws Exception {
        Double amount = bookshpCartService.checkCartAmount();
        if (bookService.checkAuthorization(model, userRegister)) {// && amount > 0.0) {
            //метод добавления книги в Book2User
            if (bookshpCartService.doCartPurchase(amount, 2)) {
                redirectAttributes.addFlashAttribute("bookCartSize", bookshpCartService.loadLinkedBooksForUser(2).size());
                return "redirect:/my";
            } else {
                redirectAttributes.addFlashAttribute("notEnoughMoney", true);
            }
        }
        return "redirect:/books/cart";
    }

}
