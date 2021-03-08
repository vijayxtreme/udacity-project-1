package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class NoteController {
    private NoteService noteService;

    NoteController(NoteService noteService){
        this.noteService = noteService;
    }

    @PostMapping("/addNote")
    public String postNote(Note note, Model model){

        if(note.getUserid() == null || note.getNotetitle() == "" || note.getNotedescription() == ""){
            model.addAttribute("error", "Note fields were not filled out correctly.");
            return "result";
        }

        try {
            if(note.getNoteid() != null) {
                noteService.updateNote(note);
            }else {
                noteService.createNote(note);
            }
            model.addAttribute("success", "Successfully updated the note");
        }catch(Exception e){
            model.addAttribute("error", "Sorry could not save the note. There was an error.");
        }finally {
            return "result";
        }

    }

    // Delete -- should check user logged in
    // otherwise anyone can just delete via the url
    @GetMapping("/deleteNote/{id}")
    public String deleteNote(@PathVariable String id, Model model){
        if(id == ""){
            model.addAttribute("error", "There was an issue with your request, please try again later.");
            return "result";
        }
        try {
            Note note = noteService.getNoteById(id);
            if (note != null) {
                noteService.deleteNote(note);
                model.addAttribute("success", "Successfully deleted the note");
            } else {
                model.addAttribute("error", "Oops something went wrong");
            }
        }catch(Exception e){
            model.addAttribute("error", "There was an issue with your request, please try again later.");
        }finally {
            return "result";
        }
    }
}
