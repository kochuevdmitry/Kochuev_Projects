package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController {

    private final Logger logger = Logger.getLogger(ErrorController.class);

    @RequestMapping(value = "errors", method = RequestMethod.GET)
    public String renderErrorPage(HttpServletRequest httpRequest, Model model) {
        logger.info("Vot ono");
        String errorMsg = "";
        int httpErrorCode = getErrorCode(httpRequest);

        switch (httpErrorCode) {
            case 400: {
                errorMsg = "Bad Request";
                logger.info(httpErrorCode);
                break;
            }
            case 401: {
                errorMsg = "Unauthorized";
                logger.info("401");
                break;
            }
            case 404: {
                errorMsg = "Resource not found";
                logger.info("404 error yes");
                break;
            }
            case 500: {
                errorMsg = "Internal Server Error";
                logger.info("500");
                break;
            }
        }
        logger.info(httpErrorCode);
        model.addAttribute("errorMessage", "Http Error Code: " + httpErrorCode + " " + errorMsg);
        return "/errors/errors";
    }

    private int getErrorCode(HttpServletRequest httpRequest) {
        return (Integer) httpRequest
                .getAttribute("javax.servlet.error.status_code");
    }
}

