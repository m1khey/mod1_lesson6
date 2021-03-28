package org.example.app.services;

import org.example.web.dto.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final ProjectUserRepository<LoginForm> userRepo;

    @Autowired
    public UserService(ProjectUserRepository<LoginForm> userRepo) {
        this.userRepo=userRepo;
    }

    public List<LoginForm>  getAllUsers(){
        return userRepo.retreiveAllUsers();
    }

    public void saveUser(LoginForm loginForm){
        userRepo.store(loginForm);
    }

    public boolean removeUserByName(String userNameToRemove){
        return userRepo.removeItemByName(userNameToRemove);
    }
}
