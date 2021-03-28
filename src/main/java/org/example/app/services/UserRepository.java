package org.example.app.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.example.web.dto.LoginForm;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository implements ProjectUserRepository<LoginForm> {

    private final Logger logger = Logger.getLogger(UserRepository.class);
    private final List<LoginForm> repo = new ArrayList<>();

    @Override
    public List retreiveAllUsers() {
        return new ArrayList(repo);
    }

    @Override
    public void store(LoginForm loginForm) {
        loginForm.setUsername(loginForm.getUsername());
        loginForm.setPassword(loginForm.getPassword());
        logger.info("store new user: " + loginForm);
        repo.add(loginForm);
    }

    @Override
    public boolean removeItemByName(String user) {
        for (LoginForm loginForm:repo
             ) {
            if (loginForm.getUsername().equals(user)){
                logger.info("remove user completed" + loginForm);
                return repo.remove(loginForm);
            }
        }
        return false;
    }
}
