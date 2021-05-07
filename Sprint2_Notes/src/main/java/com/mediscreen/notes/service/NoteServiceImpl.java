package com.mediscreen.notes.service;


import com.mediscreen.notes.model.Note;
import com.mediscreen.notes.repository.NoteRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoteServiceImpl implements NoteService {

  @Autowired
  NoteRepository noteRepository;

  Logger logger = LoggerFactory.getLogger(NoteServiceImpl.class);

  /**
   * @inheritDoc
   */
  @Override
  public Optional<Note> getNoteById(Long id) {
    logger.debug("Entering getNoteById with id:{}.", id);
    return noteRepository.findById(id);
  }

  /**
   * @inheritDoc
   */
  @Override
  public List<Note> getNotes() {
    logger.debug("Entering getNotes");
    return noteRepository.findAll();
  }

  /**
   * @inheritDoc
   */
  @Override
  public List<Note> getNotesByPatientIdAndByDate(Long id, LocalDate date) {
    logger.debug("Entering getNotesByPatientIdAndByDate with id {}. and date {}.", id, date);
    return noteRepository.findByPatientIdAndDate(id, date);
  }

  /**
   * @inheritDoc
   */
  @Override
  public List<Note> getNotesByPatientId(Long id) {
    logger.debug("Entering getNotesByPatientId with id:{}.", id);
    return noteRepository.findByPatientId(id);
  }

  /**
   * @inheritDoc
   */
  @Override
  public void addNote(Note note) {
    logger.debug("Entering addNote with Note:{}.", note.toString());
    noteRepository.save(note);
  }

  /**
   * @inheritDoc
   */
  @Override
  public boolean updateNote(Note note) {
    logger.debug("Entering updateNote with Note:{}.", note.toString());
    if (existByID(note.getId())) {
      logger.debug("updateNote: Note with id {}. exist proceeding to save", note.getId());
      noteRepository.save(note);
      return true;
    }
    logger.debug("updateNote: Note with id {}. doesn't exist. Note not saved", note.getId());
    return false;
  }

  /**
   * @inheritDoc
   */
  @Override
  public boolean deleteNote(String id) {
    logger.debug("Entering deleteNote with Note:{}.", id);
    if (existByID(id)) {
      logger.debug("deleteNote: Note with id {}. exist proceeding to suppression", id);
      noteRepository.deleteById(id);
      return true;
    }
    logger.debug("deleteNote: Note with id {}. doesn't  exist suppression aborted", id);
    return false;
  }

  /**
   * @inheritDoc
   */
  @Override
  public boolean existByID(String id) {
    logger.debug("Entering existsById with id:{}.", id);
    return noteRepository.existsById(id);
  }


}
