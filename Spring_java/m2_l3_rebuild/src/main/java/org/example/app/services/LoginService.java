package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Login;
import org.example.web.dto.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginService {

    private Logger logger = Logger.getLogger(LoginService.class);

    private final LoginsRepository<Login> loginRepo;

    @Autowired
    public LoginService(LoginsRepository<Login> loginRepo) {
        this.loginRepo = loginRepo;
    }

    public List<Login> getAllLogins() {
        return loginRepo.retreiveAll();
    }

    public boolean saveLogin(Login login) {
        if (login.getUsername() != "" && login.getPassword() != ""){
            loginRepo.store(login);
            return true;
        } else {
            return false;
        }
    }

    public boolean authenticate(LoginForm loginForm) {

        logger.info("try auth with user-form: " + loginForm);
        for (Login login: getAllLogins() ) {
            if (login.getUsername().equals(loginForm.getUsername())
                    && login.getPassword().equals(loginForm.getPassword())){
                return true;
            }
        }

        return false;
    }
}
