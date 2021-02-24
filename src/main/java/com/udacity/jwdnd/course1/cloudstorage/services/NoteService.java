package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class NoteService {
    private final NoteMapper noteMapper;

    //DI
    public NoteService(NoteMapper noteMapper){
        this.noteMapper = noteMapper;
    }

    @PostConstruct()
    public void postConstruct(){
        System.out.println("Notes:");
        System.out.println(this.noteMapper.getAllNotes());
    }

    //get all Notes
    public List<Note> getAllNotes(){
        return noteMapper.getAllNotes();
    }


    //get notes associated with user
    public List<Note> getAllNotesBelongToUser(User user){
        Integer id = user.getUserid();
        return noteMapper.getNotesByUserId(String.valueOf(id));

    }

    //get Note by id
    public Note getNoteById(Note note){
        Integer id = note.getNoteid();
        return noteMapper.getNoteById(String.valueOf(id));
    }

    public int createNote(Note note){
        return noteMapper.createNote(note);
    }

    public void updateNote(Note note){
        noteMapper.updateNote(note);
    }

    public void deleteNote(Note note){
        noteMapper.deleteNote(note);
    }

}
