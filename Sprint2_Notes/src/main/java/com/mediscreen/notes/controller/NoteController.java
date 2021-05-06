package com.mediscreen.notes.controller;

import com.mediscreen.notes.service.NoteService;
import com.mediscreen.notes.service.NoteServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@CrossOrigin(origins = "${mediscreen.ui.cross.origin}")
public class NoteController {

  Logger logger = LoggerFactory.getLogger(NoteServiceImpl.class);

  @Autowired
  NoteService noteService;

  public void test(){
    noteService.deleteNote(12L);
  }


}
