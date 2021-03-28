package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginService {

    private Logger logger = Logger.getLogger(LoginService.class);
    private final ProjectLoginRepository<LoginForm> userRepo;

    @Autowired
    public LoginService(ProjectLoginRepository<LoginForm> userRepo) {
        this.userRepo=userRepo;
    }

    public List<LoginForm> getAllUsers(){
        return userRepo.retreiveAllUsers();
    }

    public void saveUser(LoginForm loginForm){
        userRepo.store(loginForm);
    }

    public boolean removeUserByName(String userNameToRemove){
        return userRepo.removeItemByName(userNameToRemove);
    }

    public boolean authenticate(LoginForm loginFrom) {
        logger.info("try auth with user-form: " + loginFrom);
        return userRepo.authenticate(loginFrom);
    }
}
