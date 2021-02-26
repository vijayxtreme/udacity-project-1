package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class NoteController {
    private NoteService noteService;

    NoteController(NoteService noteService){
        this.noteService = noteService;
    }

    @PostMapping("/addNote")
    public String postNote(Note note){
        if(note.getNoteid() != null) {
            noteService.updateNote(note);
        }else {
          noteService.createNote(note);
        }
        //condition for error
        return "result";
    }

    // Delete -- should check user logged in
    // otherwise anyone can just delete via the url
    @GetMapping("/deleteNote/{id}")
    public String deleteNote(@PathVariable String id){
        Note note = noteService.getNoteById(id);
        if(note != null){
            noteService.deleteNote(note);
        }else {
            System.out.println("Note does not exist");
        }
        return "result";
    }
}
