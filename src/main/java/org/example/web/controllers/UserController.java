package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.LoginService;
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
    private LoginService loginService;

    @Autowired
    public UserController(LoginService loginService) {
        this. loginService =  loginService;
    }

    @GetMapping("/storage")
    public String users(Model model) {
        logger.info("got user storage");
        model.addAttribute("loginForm", new LoginForm());
        model.addAttribute("userList", loginService.getAllUsers());
        return "user_storage";
    }

    @PostMapping("/save")
    public String saveUser(LoginForm loginForm) {
        if (loginForm.getUsername()!=null && loginForm.getPassword()!=null) {
            loginService.saveUser(loginForm);
        }
        logger.info("current repository size: " + loginService.getAllUsers().size());
        return "redirect:/users/storage";
    }

    @PostMapping("/remove")
    public String removeUser(@RequestParam(value = "userNameToRemove") String userNameToRemove) {
        loginService.removeUserByName(userNameToRemove);

        return "redirect:/users/storage";
    }
}
