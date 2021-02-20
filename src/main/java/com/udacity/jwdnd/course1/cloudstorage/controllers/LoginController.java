package com.udacity.jwdnd.course1.cloudstorage.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {
    //show the basic login template when accessing /controller
    @GetMapping()
    public String getLogin(){
        return "login";
    }

    //post handled by Spring Web Security
}
