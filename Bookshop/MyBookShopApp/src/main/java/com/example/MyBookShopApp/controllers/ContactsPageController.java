package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.service.BookService;
import com.example.MyBookShopApp.service.BookshpCartService;
import com.example.MyBookShopApp.service.BookstoreUserRegisterService;
import com.example.MyBookShopApp.service.ContactUsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Controller
public class ContactsPageController extends CommonFooterAndHeaderController {

    private final ContactUsService contactUsService;

    @Autowired
    public ContactsPageController(BookstoreUserRegisterService userRegister, BookshpCartService bookshpCartService, BookService bookService, ContactUsService contactUsService) {
        super(userRegister, bookshpCartService, bookService);
        this.contactUsService = contactUsService;
    }

    @GetMapping("/contacts")
    public String mainPage() {
        return "/contacts";
    }

    @ModelAttribute("contactUsSuccess")
    public Boolean messageSentNotice() {
        return false;
    }

    @PostMapping("/contacts")
    public String contactUs(@RequestParam("name") @Valid @NotNull String name,
                            @RequestParam("mail") @Valid @NotNull String email,
                            @RequestParam("topic") @Valid @NotNull String topic,
                            @RequestParam("message") @Valid @NotNull String message,
                            RedirectAttributes redirectAttributes) {
        if (contactUsService.handleContactUsForm(name, email, topic, message)) {
            redirectAttributes.addFlashAttribute("contactUsSuccess", true);
        }
        return "redirect:/contacts";
    }


}
