package com.mediscreen.notes.service;

import com.mediscreen.notes.model.Note;
import com.mediscreen.notes.repository.NoteRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NoteServiceImplTest {

  @Mock
  NoteRepository noteRepository;

  @InjectMocks
  NoteService noteService = new NoteServiceImpl();

  public Note note = new Note();

  @BeforeEach
  public void setup(){
    note.setNote("Note content Test");
    note.setTitle("Note title Test");
    note.setDate(LocalDate.now());
    note.setPatientId(123L);
  }

  @Test
  void getNoteById() {
    when(noteRepository.findById("id")).thenReturn(Optional.of(note));
    noteService.getNoteById("id");
    verify(noteRepository, times(1)).findById("id");
    assertNotNull(noteService.getNoteById("id"));
    assertFalse(noteService.getNoteById("id").isEmpty());
  }
  @Test
  void getNoteByIdNoResult() {
    when(noteRepository.findById("id")).thenReturn(Optional.empty());
    noteService.getNoteById("id");
    verify(noteRepository, times(1)).findById("id");
    assertNotNull(noteService.getNoteById("id"));
    assertTrue(noteService.getNoteById("id").isEmpty());
  }

  @Test
  void getNotes() {
    List<Note> notes = new ArrayList<>();
    note.setId("idTest");
    notes.add(note);
    when(noteRepository.findAll()).thenReturn(notes);
    noteService.getNotes();
    verify(noteRepository, times(1)).findAll();
    assertFalse(noteService.getNotes().isEmpty());
  }
  @Test
  void getNotesNoResult() {
    List<Note> notes = new ArrayList<>();
    when(noteRepository.findAll()).thenReturn(notes);
    noteService.getNotes();
    verify(noteRepository, times(1)).findAll();
    assertTrue(noteService.getNotes().isEmpty());
  }

  @Test
  void getNotesByPatientIdAndByDate() {
    List<Note> notes = new ArrayList<>();
    note.setId("idTest");
    notes.add(note);
    LocalDate date = LocalDate.now();
    when(noteRepository.findByPatientIdAndDate(134L,date)).thenReturn(notes);
    noteService.getNotesByPatientIdAndByDate(134L,date);
    verify(noteRepository, times(1)).findByPatientIdAndDate(134L,date);
    assertFalse(noteService.getNotesByPatientIdAndByDate(134L,date).isEmpty());
  }
  @Test
  void getNotesByPatientIdAndByDateNoResult() {
    List<Note> notes = new ArrayList<>();
    LocalDate date = LocalDate.now();
    when(noteRepository.findByPatientIdAndDate(134L,date)).thenReturn(notes);
    noteService.getNotesByPatientIdAndByDate(134L,date);
    verify(noteRepository, times(1)).findByPatientIdAndDate(134L,date);
    assertTrue(noteService.getNotesByPatientIdAndByDate(134L,date).isEmpty());
  }

  @Test
  void getNotesByPatientId() {
    List<Note> notes = new ArrayList<>();
    note.setId("idTest");
    notes.add(note);
    when(noteRepository.findNoteByPatientId(134L)).thenReturn(notes);
    noteService.getNotesByPatientId(134L);
    verify(noteRepository, times(1)).findNoteByPatientId(134L);
    assertFalse(noteService.getNotesByPatientId(134L).isEmpty());
  }
  @Test
  void getNotesByPatientIdNoResult() {
    List<Note> notes = new ArrayList<>();
    when(noteRepository.findNoteByPatientId(134L)).thenReturn(notes);
    noteService.getNotesByPatientId(134L);
    verify(noteRepository, times(1)).findNoteByPatientId(134L);
    assertTrue(noteService.getNotesByPatientId(134L).isEmpty());
  }

  @Test
  void addNote() {
    noteService.addNote(note);
    verify(noteRepository, times(1)).save(note);
  }
  @Test
  void addNoteWithId() {
    note.setId("idTest");
    noteService.addNote(note);
    verify(noteRepository, times(0)).save(note);
  }

  @Test
  void updateNote() {
    note.setId("idTest");
    when(noteRepository.existsById("idTest")).thenReturn(true);
    noteService.updateNote(note);
    verify(noteRepository, times(1)).existsById("idTest");
    verify(noteRepository, times(1)).save(note);
  }
  @Test
  void updateNoteNoId() {
    noteService.updateNote(note);
    /**
     * No call expected cause id is null
     */
    verify(noteRepository, times(0)).existsById("idTest");
    verify(noteRepository, times(0)).save(note);
  }
  @Test
  void updateNoteUnknownId() {
    note.setId("idTest");
    when(noteRepository.existsById("idTest")).thenReturn(false);
    noteService.updateNote(note);
    verify(noteRepository, times(1)).existsById("idTest");
    verify(noteRepository, times(0)).save(note);
  }

  @Test
  void deleteNote() {
    when(noteRepository.deleteById("idTest")).thenReturn(true);
    when(noteRepository.existsById("idTest")).thenReturn(true);
    noteService.deleteNote("idTest");
    verify(noteRepository, times(1)).existsById("idTest");
    verify(noteRepository, times(1)).deleteById("idTest");
    assertAll(()->noteService.deleteNote("idTest"));
  }

  @Test
  void deleteNoteUnKnown() {
    when(noteRepository.existsById("idTest")).thenReturn(false);
    noteService.deleteNote("idTest");
    verify(noteRepository, times(1)).existsById("idTest");
    verify(noteRepository, times(0)).deleteById("idTest");
  }

  @Test
  void existByID() {
    when(noteRepository.existsById("idTest")).thenReturn(true);
    noteService.existByID("idTest");
    verify(noteRepository, times(1)).existsById("idTest");
  }
  @Test
  void existByIDFalse() {
    when(noteRepository.existsById("idTest")).thenReturn(false);
    noteService.existByID("idTest");
    verify(noteRepository, times(1)).existsById("idTest");
  }
}
