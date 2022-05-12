package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.exceptions.BookShelfLoginException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@ControllerAdvice
public class ErrorControllerAdvice {

    private final Logger logger = Logger.getLogger(ErrorController.class);

    @ExceptionHandler(BookShelfLoginException.class)
    public String handleError(Model model, BookShelfLoginException exception){
        logger.info("error advice:" + exception.getMessage());
        model.addAttribute("errorMessage", exception.getMessage());
        return "errors/errors";
    }


}

