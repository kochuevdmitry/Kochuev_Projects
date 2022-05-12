package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.exceptions.BookShelfLoginException;
import org.example.app.services.LoginService;
import org.example.web.dto.Login;
import org.example.web.dto.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/login")
public class LoginController {

    private final Logger logger = Logger.getLogger(LoginController.class);
    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping
    public String login(Model model) {
        logger.info("GET /login returns login_page.html and list all available logins for testing purposes");
        model.addAttribute("loginForm", new LoginForm());
        model.addAttribute("loginSaveForm", new Login());
        model.addAttribute("logins", loginService.getAllLogins());
        return "login_page";
    }

    @PostMapping("/auth")
    public String authenticate(LoginForm loginFrom) throws BookShelfLoginException {
        if (loginService.authenticate(loginFrom)) {
            logger.info("login OK redirect to book shelf");
            return "redirect:/books/shelf";
        } else {
            logger.info("login FAIL redirect back to login");
            throw new BookShelfLoginException("invalid username or password");
        }
    }

    @PostMapping("/save")
    public String saveLogin(Login loginSaveForm) {
        if (loginService.saveLogin(loginSaveForm)) {
            logger.info("login added please authenticate");
            return "redirect:/login";
        } else {
            logger.info("login add FAIL redirect back to login");
            return "redirect:/login";
        }
    }
}
