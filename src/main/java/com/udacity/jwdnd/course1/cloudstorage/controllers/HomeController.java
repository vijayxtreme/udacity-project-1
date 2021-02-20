package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {
    private UserService userService;
    private NoteService noteService;

    public HomeController(UserService userService, NoteService noteService){
        this.userService = userService;
        this.noteService = noteService;
    }

    // the main home view for logged in users
    @GetMapping()
    public String getHomePage(Authentication authentication, Note note, Model model){
        String username = authentication.getName();
        User currentUser = this.userService.getUser(username);

        List<Note> notes = this.noteService.getAllNotesBelongToUser(currentUser);
        model.addAttribute("notes", notes);

        Integer currUserId = currentUser.getUserid();
        note.setUserid(currUserId);
        model.addAttribute("note", note);

        return "home";
    }

}
