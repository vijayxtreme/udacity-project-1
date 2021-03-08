package com.udacity.jwdnd.course1.cloudstorage.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
public class LoginController {
    //show the basic login template when accessing /controller
    @GetMapping
    public String getLogin(@RequestParam(required = false) String error, @RequestParam(required = false) String logout, Model model){
        if(error != null){
            model.addAttribute("error", "Invalid username or password");
        }
        if(logout != null){
            model.addAttribute("logout", "You have been logged out ;)");
        }

        return "login";
    }

    //post handled by Spring Web Security
}
