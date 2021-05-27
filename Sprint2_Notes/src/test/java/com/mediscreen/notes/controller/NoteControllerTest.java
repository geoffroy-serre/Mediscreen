package com.mediscreen.notes.controller;

import com.mediscreen.notes.model.Note;
import com.mediscreen.notes.service.NoteService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NoteController.class)
class NoteControllerTest {

  @Autowired
  MockMvc mockMvc;
  Note note;
  @MockBean
  private NoteService noteService;

  @BeforeEach
  void setupNote() {
    note = new Note();
    note.setNote("noteTest");
    note.setDate(LocalDate.now());
    note.setPatientId(134L);
    note.setTitle("titleTest");

  }

  @Test
  void addNote() throws Exception {
    String jsonRequest = "{ \"id\":\"\",\"patientId\":\"134\"," +
            "\"title\":\"titleTest\"," +
            "\"date\":\"2021-04-14\"," +
            "\"note\":\"NoteContent\"}";
    when(noteService.addNote(any(Note.class))).thenReturn(true);
    mockMvc.perform(post("/patHistory/add")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonRequest))
            .andExpect(status().isOk());

  }

  @Test
  void addNoteBad() throws Exception {
    String jsonRequest = "{ \"id\":\"\",\"patientId\":\"134\"," +
            "\"title\":\"titleTest\"," +
            "\"note\":\"NoteContent\"}";
    when(noteService.addNote(any(Note.class))).thenReturn(true);
    mockMvc.perform(post("/patHistory/add")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonRequest))
            .andExpect(status().isBadRequest());

  }

  @Test
  void addNoteError() throws Exception {
    String jsonRequest = "{ \"id\":\"\",\"patientId\":\"134\"," +
            "\"title\":\"titleTest\"," +
            "\"date\":\"1982-04-14\"," +
            "\"note\":\"NoteContent\"}";
    when(noteService.addNote(any(Note.class))).thenReturn(false);
    mockMvc.perform(post("/patHistory/add")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonRequest))
            .andExpect(status().isBadRequest());

  }

  @Test
  void getNotes() throws Exception {
    List<Note> notes = new ArrayList<>();
    notes.add(note);
    when(noteService.getNotes()).thenReturn(notes);
    mockMvc.perform(get("/patHistory")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.[0].note").value("noteTest"))
            .andExpect(jsonPath("$.[0].title").value("titleTest"))
            .andExpect(jsonPath("$.[0].patientId").value(134))
            .andReturn();

  }

  @Test
  void getNotes404() throws Exception {
    List<Note> notes = new ArrayList<>();
    when(noteService.getNotes()).thenReturn(notes);
    mockMvc.perform(get("/patHistory")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(content().string("[]"));

  }

  @Test
  void deleteNote() throws Exception {
    when(noteService.deleteNote("idTest")).thenReturn(true);
    mockMvc.perform(delete("/patHistory/delete")
            .param("id", "idTest"))
            .andExpect(status().isOk());
  }

  @Test
  void deleteNoteUnknown() throws Exception {
    when(noteService.deleteNote("idTest")).thenReturn(false);
    mockMvc.perform(delete("/patHistory/delete")
            .param("id", "idTest"))
            .andExpect(status().isNotFound());
  }

  @Test
  void deleteNoteBad() throws Exception {
    mockMvc.perform(delete("/patHistory/delete")
            .param("aydi", "unknown"))
            .andExpect(status().isBadRequest());
  }

  @Test
  void getNoteById() throws Exception {
    when(noteService.getNoteById("idTest")).thenReturn(Optional.of(note));
    mockMvc.perform(get("/patHistory/note")
            .param("id", "idTest"))
            .andExpect(status().isOk());
  }

  @Test
  void getNoteById404() throws Exception {
    when(noteService.getNoteById("idTest")).thenReturn(Optional.empty());
    mockMvc.perform(get("/patHistory/note")
            .param("id", "idTest"))
            .andExpect(status().isNotFound());
  }

  @Test
  void getNoteByIdBad() throws Exception {
    when(noteService.getNoteById("idTest")).thenReturn(Optional.empty());
    mockMvc.perform(get("/patHistory/note")
            .param("aydi", "idTest"))
            .andExpect(status().isBadRequest());
  }

  @Test
  void getNotesByPatientId() throws Exception {
    List<Note> notes = Collections.singletonList(note);
    when(noteService.getNotesByPatientId(134L)).thenReturn(notes);
    mockMvc.perform(get("/patHistory/patient")
            .param("id", String.valueOf(134L)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.[0].note").value("noteTest"))
            .andExpect(jsonPath("$.[0].title").value("titleTest"))
            .andExpect(jsonPath("$.[0].patientId").value(134));
  }

  @Test
  void getNotesByPatientIdBad() throws Exception {
    List<Note> notes = Collections.singletonList(note);
    when(noteService.getNotesByPatientId(134L)).thenReturn(notes);
    mockMvc.perform(get("/patHistory/patient")
            .param("aydi", String.valueOf(134L)))
            .andExpect(status().isBadRequest());

  }

  @Test
  void getNotesByPatientIdNoResult() throws Exception {
    List<Note> notes = new ArrayList<>();
    when(noteService.getNotesByPatientId(134L)).thenReturn(notes);
    mockMvc.perform(get("/patHistory/patient")
            .param("id", String.valueOf(134L)))
            .andExpect(status().isNotFound())
            .andExpect(content().string("[]"));

  }

  @Test
  void updateNoteBadNotId() throws Exception {
    String jsonRequest = "{ \"id\":\"\",\"patientId\":\"134\"," +
            "\"title\":\"titleTest\"," +
            "\"date\":\"1982-04-14\"," +
            "\"note\":\"NoteContent\"}";
    mockMvc.perform(put("/patHistory/update")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonRequest))
            .andExpect(status().isBadRequest());
  }

  @Test
  void updateNoteBadEmptyField() throws Exception {
    String jsonRequest = "{ \"id\":\"134\",\"patientId\":\"134\"," +
            "\"title\":\"\"," +
            "\"date\":\"1982-04-14\"," +
            "\"note\":\"NoteContent\"}";
    mockMvc.perform(put("/patHistory/update")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonRequest))
            .andExpect(status().isBadRequest());
  }

  @Test
  void updateNoteBadMissingField() throws Exception {
    String jsonRequest = "{ \"id\":\"134\",\"patientId\":\"134\"," +
            "\"title\":\"TitleTest\"," +
            "\"note\":\"NoteContent\"}";
    when(noteService.updateNote(any(Note.class))).thenReturn(true);
    mockMvc.perform(put("/patHistory/update")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonRequest))
            .andExpect(status().isBadRequest());
  }

  @Test
  void updateNoteOk() throws Exception {
    String jsonRequest = "{ \"id\":\"134\",\"patientId\":\"134\"," +
            "\"title\":\"TestTitle\"," +
            "\"date\":\"1982-04-14\"," +
            "\"note\":\"NoteContent\"}";
    when(noteService.updateNote(any(Note.class))).thenReturn(true);
    mockMvc.perform(put("/patHistory/update")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonRequest))
            .andExpect(status().isOk());
  }

  @Test
  void updateNote404() throws Exception {
    String jsonRequest = "{ \"id\":\"134\",\"patientId\":\"134\"," +
            "\"title\":\"TestTitle\"," +
            "\"date\":\"1982-04-14\"," +
            "\"note\":\"NoteContent\"}";
    when(noteService.updateNote(any(Note.class))).thenReturn(false);
    mockMvc.perform(put("/patHistory/update")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonRequest))
            .andExpect(status().isNotFound());
  }


  @Test
  void deleteNoteByPatientId() throws Exception {
    when(noteService.deleteNoteByPatientId(134L)).thenReturn(true);
    mockMvc.perform(delete("/patHistory/patient/delete")
            .param("id", String.valueOf(134L)))
            .andExpect(status().isOk());
  }

  @Test
  void deleteNoteByPatientIdUnknown() throws Exception {
    when(noteService.deleteNoteByPatientId(134L)).thenReturn(false);
    mockMvc.perform(delete("/patHistory/patient/delete")
            .param("id", String.valueOf(134L)))
            .andExpect(status().isNotFound());
  }

  @Test
  void deleteNoteByPatientIdBad() throws Exception {
    mockMvc.perform(delete("/patHistory/patient/delete")
            .param("aydi", String.valueOf(134L)))
            .andExpect(status().isBadRequest());
  }

}

