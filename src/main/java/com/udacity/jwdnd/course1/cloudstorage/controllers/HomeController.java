package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {
    private UserService userService;

    HomeController(UserService userService){
        this.userService = userService;
    }

    @GetMapping()
    public String getHomePage(Model model){
        return "home";
    }

    @PostMapping()
    public String post(){
        return "result";
    }


}
