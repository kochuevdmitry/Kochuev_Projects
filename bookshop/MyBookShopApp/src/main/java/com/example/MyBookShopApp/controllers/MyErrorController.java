package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.service.BookService;
import com.example.MyBookShopApp.service.BookshpCartService;
import com.example.MyBookShopApp.service.BookstoreUserRegisterService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

@Controller
public class MyErrorController extends CommonFooterAndHeaderController
        implements org.springframework.boot.web.servlet.error.ErrorController {

    private final Logger logger = Logger.getLogger(MyErrorController.class.getName());

    public MyErrorController(BookstoreUserRegisterService userRegister, BookshpCartService bookshpCartService, BookService bookService) {
        super(userRegister, bookshpCartService, bookService);
    }

    @RequestMapping(value = "errors", method = RequestMethod.GET)
    public String renderErrorPage(HttpServletRequest httpRequest, Model model) {
        String errorMsg = "";
        int httpErrorCode = getErrorCode(httpRequest);

        switch (httpErrorCode) {
            case 400: {
                errorMsg = "Bad Request";
                logger.info(String.valueOf(httpErrorCode));
                model.addAttribute("errorMessage", "Что-то сломалось " + String.valueOf(httpErrorCode));
                break;
            }
            case 401: {
                errorMsg = "Unauthorized";
                logger.info("401");
                model.addAttribute("errorMessage", "Что-то сломалось " + String.valueOf(httpErrorCode));
                break;
            }
            case 404: {
                errorMsg = "Resource not found 404";
                logger.info(errorMsg);
                model.addAttribute("errorMessage", "Что-то сломалось " + String.valueOf(httpErrorCode));
                break;
            }
            case 405: {
                errorMsg = "405";
                logger.info(errorMsg);
                model.addAttribute("errorMessage", "Что-то сломалось " + String.valueOf(httpErrorCode));
                break;
            }
            case 500: {
                errorMsg = "Internal Server Error 500";
                logger.info(errorMsg);
                model.addAttribute("errorMessage", "Что-то сломалось " + String.valueOf(httpErrorCode));
                return "redirect:/signin";
            }
            case 200: {
                errorMsg = "Internal Server Error 200";
                logger.info(errorMsg);
                model.addAttribute("errorMessage", "Что-то сломалось " + String.valueOf(httpErrorCode));
                break;
            }
            default: {
                logger.info(String.valueOf(httpErrorCode));
                model.addAttribute("errorMessage", "Что-то сломалось " + String.valueOf(httpErrorCode));
                break;
            }
        }
        return "/errors/errors";
    }

    private int getErrorCode(HttpServletRequest httpRequest) {
        return (Integer) httpRequest
                .getAttribute("javax.servlet.error.status_code");
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}
