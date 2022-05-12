package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Login;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository
public class LoginRepository implements LoginsRepository<Login>  {

    private final Logger logger = Logger.getLogger(BookRepository.class);
    private final List<Login> repo = new ArrayList<>();

    @Override
    public List<Login> retreiveAll() {
        return new ArrayList<>(repo);
    }

    @Override
    public void store(Login login) {
        //login.setId(login.hashCode());
        logger.info("store new book: " + login);
        repo.add(login);
    }

    public boolean removeLoginByUsername(String Username) {
        for (Login login : retreiveAll()) {
            if (login.getUsername().equals(Username)) {
                logger.info("remove book completed: " + login);
                return repo.remove(login);
            }
        }
        return false;
    }


}
