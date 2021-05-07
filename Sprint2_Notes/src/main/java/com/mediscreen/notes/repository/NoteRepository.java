package com.mediscreen.notes.repository;

import com.mediscreen.notes.model.Note;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NoteRepository extends MongoRepository<Note,Long> {

  boolean deleteNoteById(Long id);
  List<Note> findByPatientId(Long id);
  List<Note> findByPatientIdAndDate(Long id, LocalDate date);
  void deleteById(String id);
  boolean existsById(String id);
}
