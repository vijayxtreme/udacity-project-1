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

    @RequestMapping(value = "/addNote", method = RequestMethod.POST)
    public ResponseEntity postNote(Note note){
        //if this note get note, then save otherwise create the note
        System.out.println("****----POST----****");
        System.out.println(note);
        System.out.println("****----POST----****");

        if(noteService.getNoteById(note) != null) {
            noteService.updateNote(note);
        }else {
          noteService.createNote(note);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    // Delete
    @GetMapping("/deleteNote")
    public void deleteNote(Note note){
        if(noteService.getNoteById(note)!=null){
            this.noteService.deleteNote(note);
        }else {
            System.out.println("Error trying to delete");
        }

    }
}
