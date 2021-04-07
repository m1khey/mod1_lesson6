package org.example.app.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.example.web.dto.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LoginRepository implements ProjectLoginRepository<LoginForm> {

    private final Logger logger = Logger.getLogger(LoginRepository.class);
    private final ProjectStoreUsersRepository<LoginForm> userRepo;

    @Autowired
    public LoginRepository(ProjectStoreUsersRepository<LoginForm> userRepo) {
        this.userRepo=userRepo;
    }



    public boolean authenticate(LoginForm loginFrom) {
        logger.info("authentication succeeded ");
        for (LoginForm lf:userRepo.retreiveAllUsers()
             ) {
            if (lf.getUsername().equals(loginFrom.getUsername()) &&
            lf.getPassword().equals(loginFrom.getPassword())) {
            return true;
            }
        }

        return false;
    }
}
