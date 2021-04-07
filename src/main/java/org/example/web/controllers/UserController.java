package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.LoginService;
import org.example.app.services.StoreUsersService;
import org.example.web.dto.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/users")
public class UserController {

    private Logger logger = Logger.getLogger(UserController.class);
    private StoreUsersService storeUsersService;

    @Autowired
    public UserController(StoreUsersService storeUsersService) {
        this.storeUsersService=storeUsersService;
    }

    @GetMapping("/storage")
    public String users(Model model) {
        logger.info("got user storage");
        model.addAttribute("loginForm", new LoginForm());
        model.addAttribute("userList", storeUsersService.getAllUsers());
        return "user_storage";
    }

    @PostMapping("/save")
    public String saveUser(LoginForm loginForm) {
        if (loginForm.getUsername()!=null && loginForm.getPassword()!=null) {
            storeUsersService.saveUser(loginForm);
        }
        logger.info("current repository size: " + storeUsersService.getAllUsers().size());
        return "redirect:/login";
    }

    @PostMapping("/remove")
    public String removeUser(@RequestParam(value = "userNameToRemove") String userNameToRemove) {
        storeUsersService.removeUserByName(userNameToRemove);

        return "redirect:/users/storage";
    }
}
