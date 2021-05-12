package com.mediscreen.notes.service;

import com.mediscreen.notes.model.Note;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface NoteService {


  /**
   * Gets note by id.
   *
   * @param id the id
   * @return Optional Note
   */
  Optional<Note> getNoteById(String id);


  /**
   * Gets All Note in DB.
   *
   * @return List of Note
   */
  List<Note> getNotes();

  /**
   * Gets notes by patient id and by date.
   *
   * @param id   Long
   * @param date LocalDate
   * @return List of Note by patient id and by date
   */
  List<Note> getNotesByPatientIdAndByDate(Long id, LocalDate date);

  /**
   * Gets notes by patient id.
   *
   * @param id Long
   * @return List of Note for given patient id
   */
  List<Note> getNotesByPatientId(Long id);

  /**
   * Add note.
   *
   * @param note Note
   */
  boolean addNote(Note note);

  /**
   * Update note boolean.
   * Check with existById() if note's id is known.
   * Then update not if exist.
   * @param note Note
   * @return true if note is updated successfully
   */
  boolean updateNote(Note note);

  /**
   * Delete note boolean.
   * Check with existById() if id is known.
   * Then delete note if exist.
   * @param id Long
   * @return true if note is deleted
   */
  boolean deleteNote(String id);

  /**
   * Exist by id boolean.
   *
   * @param id Long
   * @return true if note with this id exist.
   */
  boolean existByID(String id);
}
