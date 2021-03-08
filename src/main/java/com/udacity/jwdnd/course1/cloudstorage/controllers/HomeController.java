package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping
public class HomeController {
    private UserService userService;
    private NoteService noteService;
    private FileService fileService;
    private CredentialService credentialService;

    //DI
    public HomeController(UserService userService, NoteService noteService, FileService fileService, CredentialService credentialService){
        this.userService = userService;
        this.noteService = noteService;
        this.fileService = fileService;
        this.credentialService = credentialService;
    }

    // the main home view for logged in users
    @GetMapping("/")
    public String getHomePage(Authentication authentication, Note note, File file, Credentials credential, Model model){
        if(authentication.getName() == null){
            return "login";
        }
        String username = authentication.getName();

        User currentUser = this.userService.getUser(username);
        Integer currUserId = currentUser.getUserid();
        model.addAttribute("currentUser", currUserId);

        List<Note> notes = this.noteService.getAllNotesBelongToUser(currentUser);
        Collections.reverse(notes);
        model.addAttribute("notes", notes);

        List<File> files = this.fileService.getAllFilesBelongToUser(currentUser);
        Collections.reverse(files);
        model.addAttribute("files", files);

        List<Credentials> credentials = this.credentialService.getAllCredentialsBelongToUser(currentUser);
        Collections.reverse(credentials);
        model.addAttribute("credentials", credentials);

        //refactor (since have this above in model)
        note.setUserid(currUserId);
        credential.setUserid(currUserId);

        //for new form objects to be created
        model.addAttribute("note", note);
        model.addAttribute("credential", credential);

        return "home";
    }

}
