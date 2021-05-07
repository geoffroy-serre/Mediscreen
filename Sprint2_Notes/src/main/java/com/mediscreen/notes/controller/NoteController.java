package com.mediscreen.notes.controller;

import com.mediscreen.notes.model.Note;
import com.mediscreen.notes.service.NoteService;
import com.mediscreen.notes.service.NoteServiceImpl;
import java.util.List;
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

  @PostMapping("notes/add")
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


}
