package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticationService;
import com.udacity.jwdnd.course1.cloudstorage.services.HashService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignupController {
    private final UserService userService;

    public SignupController(UserService userService){
        this.userService = userService;
    }

    @GetMapping()
    public String getSignup(User user, Model model){
        return "signup";
    }

    //get the Signup form POJO, save it w/the service into the repo
    @PostMapping()
    public String postSignup(User user, Model model){
        String error = "";

        //if the userService returns a user then cancel out of this function
        //aka the userService says that the username is not available
        if(!userService.isUsernameAvailable(user.getUsername())){
            error = "Sorry, that username wasn't available.  Please try another name.";
            model.addAttribute("signupError", error);
            return "signup";
        }

        //user save
        int rowsAdded = userService.createUser(user);
        if(rowsAdded < 0){
            error = "Couldn't sign you up.  Please try again later";
            model.addAttribute("signupError", error);
            return "signup";
        }

        model.addAttribute("signupSuccess", "Awesome, you signed up!");
        return "signup";

    }

}
