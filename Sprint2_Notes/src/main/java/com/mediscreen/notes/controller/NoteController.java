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
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@Validated
@CrossOrigin(origins = "${mediscreen.ui.cross.origin}")
public class NoteController {

  Logger logger = LoggerFactory.getLogger(NoteServiceImpl.class);

  @Autowired
  NoteService noteService;

  @PostMapping("patHistory/add")
  public void addNote(@RequestBody @Valid Note note, HttpServletResponse response) {
    if (noteService.addNote(note)) {
      logger.debug("Note valid and not in db. Is added");
      response.setStatus(200);
    } else {
      logger.debug("Can't be add");
      response.setStatus(400);
    }
  }

  @PutMapping("patHistory/update")
  public void updateNote(@RequestBody @Valid Note note, HttpServletResponse response) {
    if (note.getId().isEmpty()) {
      logger.debug("Id can't be null for update request");
      response.setStatus(400);
    } else if (noteService.updateNote(note)) {
      logger.debug("Note updated succesfully");
      response.setStatus(200);
    } else {
      logger.debug("Note can't be updated");
      response.setStatus(404);
    }
  }

  @DeleteMapping("patHistory/delete")
  public void deleteNote(@RequestParam String id, HttpServletResponse response) {
    if (noteService.deleteNote(id)) {
      logger.debug("Note deleted");
      response.setStatus(200);
    } else {
      logger.debug("Note can't be deleted");
      response.setStatus(404);
    }
  }

  @DeleteMapping("patHistory/patient/delete")
  public void deleteNotesByPatientId(@RequestParam Long id, HttpServletResponse response) {
    if (noteService.deleteNoteByPatientId(id)) {
      logger.debug("Notes deleted");
      response.setStatus(200);
    } else {
      logger.debug("Notes can't be deleted");
      response.setStatus(404);
    }
  }

  @GetMapping("patHistory")
  public List<Note> getNotes(HttpServletResponse response) {
    List<Note> result = noteService.getNotes();
    if (!result.isEmpty()) {
      logger.debug("Results found for getNotes");
      response.setStatus(200);
      return result;
    }
    logger.debug("No results found for getNotes");
    response.setStatus(404);
    return result;

  }

  @GetMapping("patHistory/note")
  public Optional<Note> getNoteById(@RequestParam String id, HttpServletResponse response) {
    Optional<Note> result = noteService.getNoteById(id);
    if (result.isEmpty()) {
      logger.debug("GetNoteById is empty");
      response.setStatus(404);
      return result;
    }
    logger.debug("GetNoteById is populated");
    response.setStatus(200);
    return noteService.getNoteById(id);
  }

  @GetMapping("patHistory/patient")
  public List<Note> getNotesByPatientId(@RequestParam Long id, HttpServletResponse response) {
    List<Note> result = noteService.getNotesByPatientId(id);
    if (result.isEmpty()) {
      logger.debug("GetNoteByPatientId is empty");
      response.setStatus(404);
      return result;
    }
    logger.debug("GetNoteByPatientId have {} results", result.size());
    response.setStatus(200);
    return result;
  }


}
