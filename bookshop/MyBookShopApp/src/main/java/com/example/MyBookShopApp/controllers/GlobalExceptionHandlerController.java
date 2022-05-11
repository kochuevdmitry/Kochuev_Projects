package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.errs.EmptyPaymentException;
import com.example.MyBookShopApp.errs.EmptySearchException;
import com.example.MyBookShopApp.errs.PasswordChangeNotMatchException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import java.util.logging.Logger;

@ControllerAdvice
public class GlobalExceptionHandlerController {

    @ExceptionHandler(UsernameNotFoundException.class)
    public String handleUsernameNotFoundException(UsernameNotFoundException e) {
        Logger.getLogger(this.getClass().getSimpleName()).warning(e.getLocalizedMessage());
        return "forward:/errors/errors";
    }

    @ExceptionHandler(ServletException.class)
    public String handleServletExceptions(ServletException e) {
        Logger.getLogger(this.getClass().getSimpleName()).warning(e.getLocalizedMessage());
        return "forward:/signin";
    }

    @ExceptionHandler(EmptySearchException.class)
    public String handleEmptySearchException(EmptySearchException e, RedirectAttributes redirectAttributes) {
        Logger.getLogger(this.getClass().getSimpleName()).warning(e.getLocalizedMessage());
        redirectAttributes.addFlashAttribute("searchError", e);
        return "redirect:/";
    }

    @ExceptionHandler(EmptyPaymentException.class)
    public String handleEmptyPaymentException(EmptyPaymentException e, RedirectAttributes redirectAttributes) {
        Logger.getLogger(this.getClass().getSimpleName()).warning(e.getLocalizedMessage());
        redirectAttributes.addFlashAttribute("emptyPayment", e);
        redirectAttributes.addFlashAttribute("failedPayment", true);
        return "redirect:/profile";
    }

    @ExceptionHandler(PasswordChangeNotMatchException.class)
    public String handlePasswordChangeNotMatchException(PasswordChangeNotMatchException e, RedirectAttributes redirectAttributes) {
        Logger.getLogger(this.getClass().getSimpleName()).warning(e.getLocalizedMessage());
        redirectAttributes.addFlashAttribute("passwordChangeMatchText", e.getLocalizedMessage());
        redirectAttributes.addFlashAttribute("passwordChangeMatch", true);
        return "redirect:/profile";
    }

}
