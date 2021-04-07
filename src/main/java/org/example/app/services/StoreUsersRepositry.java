package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.LoginForm;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class StoreUsersRepositry implements ProjectStoreUsersRepository<LoginForm> {

    private final Logger logger = Logger.getLogger(StoreUsersRepositry.class);
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
