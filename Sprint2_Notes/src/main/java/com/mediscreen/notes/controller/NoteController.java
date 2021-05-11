package com.mediscreen.notes.controller;

import com.mediscreen.notes.model.Note;
import com.mediscreen.notes.service.NoteService;
import com.mediscreen.notes.service.NoteServiceImpl;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@CrossOrigin(origins = "${mediscreen.ui.cross.origin}")
public class NoteController {

  Logger logger = LoggerFactory.getLogger(NoteServiceImpl.class);

  @Autowired
  NoteService noteService;

  @PostMapping("note/add")
  public void addNote(@RequestBody Note note){
    noteService.addNote(note);
  }

  @GetMapping("notes")
  public List<Note> getNotes(){
    return noteService.getNotes();
  }

  @DeleteMapping("notes/delete")
  public void deleteNote(@RequestParam String id){
    noteService.deleteNote(id);
  }

  @GetMapping("note")
  public Optional<Note> getNoteById(@RequestParam String id){
      return noteService.getNoteById(id);
  }

  @GetMapping("notes/patient")
  public List<Note> getNotesByPatientId (@RequestParam Long id){
    return noteService.getNotesByPatientId(id);
  }


}
